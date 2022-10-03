<template>
	<div style="background-color:#f0ebe7">
	<div id="main-container" class="container" style="height: 55rem;">
    <div v-if="!where" class="card" style="width: 30rem; height: 45rem; margin-top:5rem;">
      <img src=@/assets/weddinghome.png class="card-img-top" alt="...">
        <div class="card-body">
        <p class="card-text waiting-font">두 사람이 만나 하나의 매듭이 되고</p>
        <p class="card-text waiting-font">하나의 길이 되어</p>
        <p class="card-text waiting-font">하나의 보금자리를 이루려 합니다</p>
        <p class="card-text waiting-font">변함없는 믿음과 사랑으로 축복해 주십시오</p>

        <p class="card-text waiting-font">신랑 : {{ wedding.weddingManNm }}</p>
        <p class="card-text waiting-font">신부 : {{ wedding.weddingWomanNm }}</p>
        <br>
        <button style="margin-right:2rem" type="button" class="btn btn-outline-secondary" @click="clickButton('man')">신랑 대기방</button>
        <button type="button" class="btn btn-outline-secondary" @click="clickButton('woman')">신부 대기방</button>
        <button style="margin-left:2rem" type="button" class="btn btn-outline-secondary" @click="clickButton('hall')">결혼식장</button>
      </div>
    </div>
		<div v-show="this.disabled">
			<room-chat
				ref="chat"
				@message="sendMessage"
				:subscribers="subscribers"
				@insertQ="insertQ"
				@outQ="outQ"
			></room-chat>
		</div>
		<man-video
			v-if="where === 'man' || where === 'woman'"
			:where="where" @leaveVideo="leaveVideo"
			:Q="Q"
			@comeFirstMan="comeFirstMan"
			@goFirstMan="goFirstMan"
		></man-video>
		<hall-video v-if="where === 'hall'" :where="where" @leaveVideo="leaveVideo"></hall-video>
	</div>
	</div>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import { mapGetters } from 'vuex'
import ManVideo from '@/components/ConferenceCompany/ManVideoCompany.vue'
import HallVideo from '@/components/ConferenceCompany/HallVideoCompany.vue'
import RoomChat from '@/components/ConferenceCompany/ChatCompany.vue'

axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ':' + process.env.VUE_APP_OPENVIDU_PORT;
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
	name: 'waiting-room',

	components: {
		ManVideo,
		HallVideo,
		RoomChat,
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
			Q: {}
		}
	},

	methods: {
		clickButton (target) {
			this.where = target
			this.Q[target] = []
		},
		clickButton1 (target) {
			this.where = target
		},
		leaveVideo () {
			this.where = false
		},
		insertQ ([where, groupWho]) {
			this.Q[where].push(groupWho)
		},
		outQ ([where, groupWho]) {
			const idx = this.Q[where].indexOf(groupWho)
			if (idx > -1) this.Q[where].splice(idx, 1)
		},

		joinSession (target) {
      this.mySessionId = target
      this.myUserName = target + 'Company'
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

		comeFirstMan(where) {
			this.sendMessage({content: 'come꽖쀓' + where, to: 'all'})
		},
		goFirstMan (where) {
			this.sendMessage({content: 'go꽖쀓' + where, to: 'all'})
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
  }),

	mounted() {
		this.joinSession(this.wedding.weddingNum + 'waiting')
	},
	beforeUnmount() {
		this.leaveSession();
	},
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

  .container{
    display:flex;
    justify-content: center;
		background-color: #f0ebe7;
  }

  .waiting-font{
    margin-top: 25px;
    font-family: 'Gowun Dodum', sans-serif;
  }
</style>
