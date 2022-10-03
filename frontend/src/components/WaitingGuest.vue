<template>
	<div style="background-color:#f0ebe7">
	<div id="main-container" class="container" style="height: 70rem;">

		<div v-if="!where" style="padding-top:5rem">
			<div>
				<h3 style="padding-top:5rem; display:inline; " class="waitingguest-font">어서오세요, </h3>
				<h2 style="display:inline" class="waitingguest-font"> {{ guestInfo.guestName }}({{ guestInfo.guestGroup }})</h2>
				<h3 style="display:inline" class="waitingguest-font"> 님 </h3>
			</div>
			<div style="margin-top:3rem">
				<h3 class="waitingguest-font" style="display:inline">신랑 </h3>
				<h2 class="waitingguest-font" style="display:inline; color:#00C3FF"> {{ wedding.weddingManNm }} </h2>
				<h3 class="waitingguest-font" style="display:inline"> 와 신부 </h3>
				<h2 class="waitingguest-font" style="display:inline; color: #FFB4FF" >{{ wedding.weddingWomanNm }} </h2>
				<h3 class="waitingguest-font" style="display:inline"> 님의 결혼식입니다</h3>
			</div>
			<div style="margin-top:3rem">
				<h3 class="waitingguest-font">두 분의 결혼식을 진심으로 축복해주세요</h3>
			</div>
			<p class="text-center" style="margin-top:3rem">
				<button style="margin-right:2rem" type="button" class="btn btn-outline-secondary" @click="clickButton('man')">신랑 대기방 입장</button>
				<button type="button" class="btn btn-outline-secondary" @click="clickButton('woman')">신부 대기방 입장</button>
				<button style="margin-left:2rem" type="button" class="btn btn-outline-secondary" @click="clickButton1('hall')">결혼식장 입장</button>
				<button style="margin-left:2rem" type="button" class="btn btn-outline-secondary" @click="clickButton1('guest')">하객 놀이방</button>
			</p>
      <div class="d-flex">
        <div style="width:40rem; margin:auto; margin-top:5rem" id="carouselExampleFade" class="carousel slide carousel-fade" data-bs-ride="carousel">
          <!-- 캐로셀 내용-->
          <div class="carousel-inner" v-if="!state.activeWeddingImage">
            <div class="carousel-item active">
              <img src="@/assets/wedding_main_1.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
              <img src="@/assets/wedding_main_2.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
              <img src="@/assets/wedding_main_3.jpg" class="d-block w-100" alt="...">
            </div>
          </div>
          <div class="carousel-inner" v-if="state.activeWeddingImage">
            <div class="carousel-item active">
              <img :src="state.activeWeddingImage" class="d-block w-100" :alt="state.activeWeddingImage">
            </div>
            <div class="carousel-item" v-for="weddingImage in state.weddingImages" :key="weddingImage">
              <img :src="weddingImage" class="d-block w-100" alt="...">
            </div>
          </div>
          <!-- 캐로셀 내용 끝 -->
          <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
          </button>
          <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
          </button>
        </div>
        <div class="memo" style="width:40rem; margin:auto;">
          <h3 class="message-font" style="margin-bottom:2rem; margin-top:5rem;">축하의 말</h3>
          <div class="message-font" v-for="content in state.guestBook" :key="content">
            <p style="margin-bottom:2rem;">작성자(소속): {{ content.guestName }}({{ content.guestGroup }})<br/>방명록: {{ content.guestMessage }}</p>
          </div>
          <div>
          <button @click="reloadGuestBook">
            <!-- <span>다른 방명록</span> -->
            <svg class="icon icon-loop">
              <use xlink:href="#icon-loop">
                <symbol id="icon-loop" viewBox="0 0 32 32">
                  <path d="M20 16v4h4.738l-5.371 5.371c-3.389 3.396-9.309 3.396-12.701 0l-2.83 2.824c2.451 2.454 5.715 3.805 9.184 3.805 3.471 0 6.727-1.352 9.175-3.805l5.805-5.8v5.605h4v-12h-12zM12 12h-4.738l5.371-5.371c3.389-3.396 9.309-3.396 12.701 0l2.83-2.824c-2.451-2.453-5.715-3.805-9.185-3.805s-6.727 1.352-9.175 3.805l-5.804 5.8v-5.605h-4v12h12v-4z"></path>
                </symbol>
              </use>
            </svg>
          </button>
          </div>
        </div>
      </div>
		</div>

		<div v-show="this.disabled">
			<room-chat
				ref="chat"
				:subscribers="subscribers"
				@message="sendMessage"
				@comeManVideo="comeManVideo"
				@goManVideo="goManVideo"
			></room-chat>
		</div>
		<div>
			<man-video
				v-if="where === 'man' || where === 'woman'"
				:where="where"
				@leaveVideo="leaveVideo"
				@outFirstMan="outFirstMan"
				></man-video>
			<hall-video v-if="where === 'hall'" :where="where" @leaveVideo="leaveVideo"></hall-video>
		</div>
    <div>
      <guest-to-guest
        v-if="where === 'guest'"
        @clickExit="leaveVideo"
      ></guest-to-guest>
    </div>
	</div>
</div>
</template>

<script>
import axios from 'axios';
import { onMounted, reactive, computed } from 'vue';
import { mapGetters, useStore } from 'vuex'
import { OpenVidu } from 'openvidu-browser';
import ManVideo from '@/components/ConferenceGuest/ManVideoGuest.vue'
import HallVideo from '@/components/ConferenceGuest/HallVideoGuest.vue'
import RoomChat from '@/components/ConferenceGuest/ChatGuest.vue'
import GuestToGuest from '@/components/GuestToGuest.vue'

axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ':' + process.env.VUE_APP_OPENVIDU_PORT;
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
	name: 'waiting-room',

	components: {
		ManVideo,
		HallVideo,
		RoomChat,
    GuestToGuest,
	},

	data () {
		return {
			OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],
			disabled: false,
      mySessionId: '',
			myUserName: '',
			where: false,
		}
	},

	methods: {
		clickButton1 (target) {
			this.where = target
		},
		clickButton (target) {
			// this.where = target
			this.sendMessage({content: 'in' + '꽖쀓' + target + '꽖쀓' + this.guestInfo.guestGroup + ' ' + this.guestInfo.guestName, to: 'all'})
		},
		leaveVideo () {
			this.where = false
		},
		comeManVideo (where) {
			this.where = where
		},
		outFirstMan (where) {
			this.sendMessage({content: 'out' + '꽖쀓' + where + '꽖쀓' + this.guestInfo.guestGroup + ' ' + this.guestInfo.guestName, to: 'all'})
		},
		goManVideo () {
			this.where = false
		},

		joinSession (target) {
      this.mySessionId = target
      this.myUserName = target + this.guestInfo.guestGroup + this.guestInfo.guestName
			// --- Get an OpenVidu object ---
			this.OV = new OpenVidu();

			// --- Init a session ---
			this.session = this.OV.initSession();
			// --- Specify the actions when events take place in the session ---

			// On every new Stream received...
			this.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.session.subscribe(stream);
				this.subscribers.push(subscriber);
			});

			// On every Stream destroyed...
			this.session.on('streamDestroyed', ({ stream }) => {
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
			});

			// On every asynchronous exception...
			this.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});

			// public 채팅 signal 받기
			this.session.on('signal:public-chat', event => {
				this.$refs.chat.addMessage(
					event.data,
					JSON.parse(event.data).sender === this.myUserName,
					false,
				);
			});

			// private 채팅 signal 받기
			this.session.on('signal:private-chat', event => {
				this.$refs.chat.addMessage(event.data, false, true);
			});

			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
			this.getToken(target + this.mySessionId).then(token => {
				this.session.connect(token, { clientData: this.myUserName })
					.then(() => {

						// --- Get your own camera stream with the desired properties ---

						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
							resolution: '640x480',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: false       	// Whether to mirror your local video or not
						});

						this.mainStreamManager = publisher;
						this.publisher = publisher;

						// --- Publish your stream ---

						this.session.publish(this.publisher);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});

			window.addEventListener('beforeunload', this.leaveSession)
		},

		leaveSession () {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;

			window.removeEventListener('beforeunload', this.leaveSession);
		},

		updateMainVideoStreamManager (stream) {
			if (this.mainStreamManager === stream) return;
			this.mainStreamManager = stream;
		},

		/**
		 * --------------------------
		 * SERVER-SIDE RESPONSIBILITY
		 * --------------------------
		 * These methods retrieve the mandatory user token from OpenVidu Server.
		 * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
		 * the API REST, openvidu-java-client or openvidu-node-client):
		 *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
		 *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
		 *   3) The Connection.token must be consumed in Session.connect() method
		 */

		getToken (mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
		createSession (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
		createToken (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},

		sendMessage({ content, to }) {
			let now = new Date();
			let current = now.toLocaleTimeString([], {
				hour: '2-digit',
				minute: '2-digit',
				hour12: false, // true인 경우 오후 10:25와 같이 나타냄.
			});

			let messageData = {
				content: content,
				sender: this.myUserName,
				time: current,
			};

			// 전체 메시지
			if (to === 'all') {
				this.session
					.signal({
						data: JSON.stringify(messageData),
						to: [],
						type: 'public-chat',
					})
					.then(() => {
					})
					.catch(error => {
						console.log(error);
					});
			}

			// 개인 메시지
			if (to !== 'all') {
				this.session
					.signal({
						data: JSON.stringify(messageData),
						to: [to],
						type: 'private-chat',
					})
					.then(() => {
						// 내가 보낸 개인 메시지 추가
						this.$refs.chat.addMessage(JSON.stringify(messageData), true, true);
					})
					.catch(error => {
						console.log(error);
					});
			}
		},

		audioOnOff({ audio }) {
			this.publisher.publishAudio(audio);
		},

		videoOnOff({ video }) {
			this.publisher.publishVideo(video);
		},
	},
  computed: mapGetters({
    wedding: 'root/getNowWeddingInfo',
		guestInfo: 'root/getNowGuestInfo'
  }),

	mounted() {
    this.joinSession(this.wedding.weddingNum + 'waiting')
	},
	beforeUnmount() {
		this.leaveSession();
	},
  setup() {
    const store = useStore()
    const state = reactive({
      guestBook: '',
      weddingImages: [],
      activeWeddingImage : '',
      wedding_manNm: computed(() => store.getters['root/getNowWeddingInfo'].weddingManNm),
      weddingNum: computed(() => store.getters['root/getNowWeddingInfo'].weddingNum),
      wedding_womanNm: computed(() => store.getters['root/getNowWeddingInfo'].weddingWomanNm),
    })

    const reloadGuestBook = function() {
      store.dispatch('root/requestGuestBook', state.weddingNum)
        .then(response => {
          state.guestBook = response.data
        })
        .catch(error => {
          console.log(error)
        })
    }
      // 웨딩사진 리스트 불러오기
      const fetchWeddingImageList = function () {
        store.dispatch('root/requestWeddingImageList', {
          wedding_manNm: state.wedding_manNm, wedding_num: state.weddingNum, wedding_womanNm: state.wedding_womanNm
        })
          .then(response => {
            // 리스트가 있다면 웨딩사진 불러오기
            if (response.data) {
              for (let i in response.data) {
                if (i === '1') {
                  state.activeWeddingImage = `https://i7e206.p.ssafy.io/api/v1/image/${response.data[i]}`
                } else {
                  state.weddingImages.push(`https://i7e206.p.ssafy.io/api/v1/image/${response.data[i]}`)
                }
              }
            }
          })
          .catch(error => {
            console.log(error)
          })
      }
      // 웨딩사진 리스트 불러오기 끝

    onMounted(() => {
      reloadGuestBook()
      fetchWeddingImageList()
    })

    return { state, reloadGuestBook }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

	.waitingguest-font{
		font-family: 'Gowun Dodum', sans-serif;
	}

  .message-font{
    font-family: 'Gowun Dodum', sans-serif;
  }

  .icon {
    display: inline-block;
    width: 1em;
    height: 1em;
    stroke-width: 0;
    stroke: currentColor;
    fill: currentColor;
  }

  .memo{
    height: 600px;
    background-image: url('@/assets/memo.png');
    background-size: cover;
    margin-top:0px;
    padding-bottom: 3rem;
    border-radius: 20px;
  }

</style>
