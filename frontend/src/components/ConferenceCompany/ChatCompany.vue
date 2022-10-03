<template>
	<div class="absolute flex top-0 h-screen z-20 right-0 flex-row border-l border-main-100">

		<!-- chat-bar -->
		<div
			ref="content"
			class="transition-all pt-16 pb-12 px-8 w-80 duration-700 bg-main-300 overflow-hidden flex flex-col items-center justify-between"
		>
			<ul
				id="chat-bar"
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
						<div class="flex flex-row items-center">
							<span class="text-main-100 text-md mr-2 font-bold">{{
								chat.userId
							}}</span>
							<span class="text-tc-400 text-xs">{{ chat.time }}</span>
						</div>
						<div>
							<p class="text-tc-300 text-sm break-all text-left">
								{{ chat.content }}
							</p>
						</div>
					</div>
				</li>
			</ul>

			 <div class="flex flex-col items-start w-full mt-8">
				<!-- 메시지 보낼 유저 선택창 -->
				<div
					class="relative inline-flex border-3 rounded-lg border-main-100 w-1/2 mb-4 h-7 p-0"
				>
					<select
						v-model="state.selectedUser"
						class="cursor-pointer font-bold px-4 py-0 text-sm form-select border-0 rounded-md text-gray-600 w-full h-5-5 border-transparent focus:border-transparent focus:ring-0 appearance-none"
					>
						<option selected="selected" value="all">all</option>
						<option
							v-for="(sub, idx) in state.subscribers"
							:value="sub.stream.connection"
							:key="idx"
						>
							{{ JSON.parse(sub.stream.connection.data).clientData }}
						</option>
					</select>
				</div>

				<!-- 메시지 작성 -->
				<div class="w-full p-0 flex flex-row justify-between items-center">
					<textarea
						v-model="state.message"
						@keydown.enter="sendMessage"
						class="w-5/6 border-2 border-main-100 text-xs focus:border-main-100 focus:border-2 resize-none"
						cols=""
						rows="2"
					></textarea>
          <button @click="sendMessage()">메세지보내기</button>
				</div>
			</div>
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
</style>

<script>
import { reactive } from 'vue';

export default {
	props: {
		subscribers: Object,
	},

	setup(props, { emit }) {
		const state = reactive({
			selectedUser: 'all',
			message: '',
			subscribers: props.subscribers,
			chats: [],

      Q: {
        man : [],
        woman : [],
      }

		});

		const sendMessage = () => {
			let strippeddMessage = state.message.trim();

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

			let chatBar = document.querySelector('#chat-bar');
			let isScrollBottom =
				chatBar.scrollHeight - chatBar.scrollTop <= chatBar.clientHeight + 2;

			// await 키워드 => 새로운 채팅 메시지 추가 완료 후 스크롤바가 아래로 이동되도록 함.
			await state.chats.push({
				userId: message.sender,
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
				in: 대기열에 들어갈래요
				out: 방에서 나갈래요

				신랑 -> 하객
				come: 방에 들어오세요
				go: 방에서 나가세요
			}
			*/
			const act = message.content.split('꽖쀓')[0]
			const where = message.content.split('꽖쀓')[1]
			const groupWho = message.content.split('꽖쀓')[2]
			// 신랑이 하객이 들어가고 싶다는 메시지 받고 큐에 넣기
			if (act === 'in') {
				if (where === 'man' && !state.Q.man.includes(groupWho)) {
					state.Q.man.push(groupWho)
					emit('insert-Q', ['man', groupWho])
				} else if (where === 'woman' && !state.Q.woman.includes(groupWho)) {
					state.Q.woman.push(groupWho)
					emit('insert-Q', ['woman', groupWho])
				}
			// 하객이 나가고싶다는 메시지를 보내서
			// 나가라고 함
			} else if (act === 'out') {
				if (where === 'man' && state.Q.man.includes(groupWho)) {
					emit('out-Q', ['man', groupWho])
					emit('message', {
						content: 'go' + '꽖쀓' + where + '꽖쀓' + groupWho,
						to: state.selectedUser,
					})
					const idx = state.Q.man.indexOf(groupWho)
					if (idx > -1) state.Q.man.splice(idx, 1)
				} else if (where === 'woman' && state.Q.woman.includes(groupWho)) {
					emit('out-Q', ['woman', groupWho])
					emit('message', {
						content: 'go' + '꽖쀓' + where + '꽖쀓' + groupWho,
						to: state.selectedUser,
					})
					const idx = state.Q.woman.indexOf(groupWho)
					if (idx > -1) state.Q.woman.splice(idx, 1)
				}
			// 신랑이 들어오세요 했을때 큐 제일 앞에 들어오라 함
			} else if (act === 'come' && !groupWho) {
				if (where === 'man') {
					emit('message', {
						content: act + '꽖쀓' + where + '꽖쀓' + state.Q.man[0],
					to: state.selectedUser,
					})
				} else if (where === 'woman') {
					emit('message', {
						content: act + '꽖쀓' + where + '꽖쀓' + state.Q.woman[0],
					to: state.selectedUser,
						}
					)
				}
			// 너 나가하기
			}	else if (act === 'go' && !groupWho) {
				if (where === 'man') {
					emit('message', {
						content: 'go' + '꽖쀓' + where + '꽖쀓' + state.Q.man[0],
						to: state.selectedUser,
					})
					emit('out-Q', ['man', state.Q.man[0]])
					state.Q.man.splice(0, 1)
				} else if (where === 'woman') {
					emit('message', {
						content: 'go' + '꽖쀓' + where + '꽖쀓' + state.Q.woman[0],
						to: state.selectedUser,
					})
					emit('out-Q', ['woman', state.Q.woman[0]])
					state.Q.woman.splice(0, 1)
				}
			}
		};

		return { state, sendMessage, addMessage };
	},
};
</script>
