<template>
	<div class="absolute flex top-0 h-screen z-20 right-0 flex-row border-l border-main-100">

		<!-- chat-bar -->
		<div
			ref="content"
			class="transition-all pt-16 pb-12 px-8 w-80 duration-700 bg-main-300 overflow-hidden flex flex-col items-center justify-between"
		>
			<ul
				id="chat-bar-hall"
				class="chatbarSize"
			>
				<li
					class="flex flex-col mb-2"
					v-for="(chat, idx) in state.chats"
					:key="idx"
				>
					<!-- 내가 보낸 채팅인 경우 -->
					<div v-if="chat.isMyMessage" class="border-r-2 border-sub-200 pr-2">
						<div class="flex flex-row-reverse items-center">
							<span class="text-sub-200 text-md ml-2 font-bold">Me</span>
							<span class="text-tc-400 text-xs">{{ chat.time }}</span>
						</div>
						<div>
							<p class="text-tc-300 text-sm break-all text-right">
								{{ chat.content }}
							</p>
						</div>
					</div>
					<!-- 다른 사람이 보낸 채팅인 경우 -->
					<div v-else class="border-l-2 border-main-100 pl-2">
						<div class="flex flex-row text-start">
							<span class="text-main-100 text-md mr-2 font-bold">{{
								chat.userId
							}}</span>
						</div>
						<div class="d-flex align-items-end justify-content-start" style="margin-top:0.5rem;">
							<p class="your-text d-flex justify-content-start text-tc-300 text-sm break-all text-left" style="margin: 0px; max-width:16rem;">
								{{ '\u00A0' + chat.content + '\u00A0' }}
							</p>
							<span class="text-tc-400 text-xs" style="font-size:small; padding-left:1rem;">{{ chat.time }}</span>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
</template>

<style scoped>
.border-3 {
	border-width: 3px;
}

.h-5-5 {
	height: 1.375rem;
}
.chatbarSize {
	list-style: none;
}
#chat-bar-hall {
	background: #faf8f6;
	height: 35rem;
	overflow-x:visible;
	overflow-y	: auto;
}
.your-text {
	position: relative;
	background: #faf8f6;
	border: 1px solid black;
	border-radius:10px;
}
.your-text:after, .your-text:before {
	right: 100%;
	top: 50%;
	border: solid transparent;
	content: "";
	height: 0;
	width: 0;
	position: absolute;
	pointer-events: none;
}

.your-text:after {
	border-color: rgba(213, 213, 213, 0);
	border-right-color: #faf8f6;
	border-width: 5px;
	margin-top: -5px;
}
.your-text:before {
	border-color: rgba(239, 245, 71, 0);
	border-right-color: black;
	border-width: 6px;
	margin-top: -6px;
}
</style>

<script>
import { computed, reactive } from 'vue';
import { useStore } from 'vuex';

export default {
	props: {
		subscribers: Object,
	},

	setup(props, { emit }) {
		const store = useStore()
		const state = reactive({
			selectedUser: 'all',
			message: '',
			subscribers: props.subscribers,
			chats: [],
			guestInfo: computed(() => store.getters['root/getNowGuestInfo']),
			badwords: ['시발', '좆같네', '미친', '새끼']
		});


		const sendMessage = () => {
			let strippeddMessage = state.message.trim();
			let filtering = state.message.split(' ')
			filtering.forEach(function(i) {

				if (state.badwords.includes(i)) {
					alert('바른말 고운말을 사용하자')
					strippeddMessage=''
					state.message=''
				} else {
				}
			})
			if (strippeddMessage === '') return;

			emit('message', {
				content: strippeddMessage,
				to: state.selectedUser,
			});

			event.preventDefault(); // enter키 누를 때 줄바꿈 방지
			state.message = ''; // 메시지 창 초기화

		};

		const addMessage = async (messageData, isMyMessage, isPrivate) => {
			let message = JSON.parse(messageData);

			// 내가 보낸 메시지인 경우
			if (isMyMessage) {
				message.sender += ' (You)';
			}

			// 개인 메시지인 경우
			if (isPrivate) {
				message.content += ' (private)';
			}

			let chatBar = document.getElementById("chat-bar-hall");
			let isScrollBottom =
			chatBar.scrollHeight - chatBar.scrollTop <= chatBar.clientHeight + 2;
			// await 키워드 => 새로운 채팅 메시지 추가 완료 후 스크롤바가 아래로 이동되도록 함.
			await state.chats.push({
				userId: message.sender.split('hall')[1],
				time: message.time,
				content: message.content,
				isMyMessage: isMyMessage,
			});

			// 채팅 스크롤이 끝까지 내려가 있는 경우 => 스크롤바 맨 아래로 이동시키기
			if (isScrollBottom) {
				chatBar.scrollTo({ top: chatBar.scrollHeight, behavior: 'smooth' });
			}
			/*
			act : {
				하객 -> 신랑
				in: 대기열에 들어갈게요
				out: 방에서 나갈게요

				신랑 -> 하객
				come: 방에 들어오세요
				go: 방에서 나가세요
			}
			*/
			const act = message.content.split('꽖쀓')[0]
			const where = message.content.split('꽖쀓')[1]
			const groupWho = message.content.split('꽖쀓')[2]

			if (act === 'come' && groupWho) {
				if (groupWho === state.guestInfo.guestGroup + ' ' + state.guestInfo.guestName) {
					emit('come-man-video', where)
				}
			} else if (act === 'go' && groupWho) {
				if (groupWho === state.guestInfo.guestGroup + ' ' + state.guestInfo.guestName) {
					emit('go-man-video')
				}
			}
		};

		return { state, sendMessage, addMessage };
	},
};
</script>
