<template>
<div class="guest-size" style="background-color:#f0ebe7">
  <div class = "manVideo">
    <h1 class="manvideo-font" style="margin-top:3rem">오늘의 주인공을 축복해주세요</h1>
    <div id="session" v-if="session" style="width:100%;">
      <div class="man-video">
        <div id="main-video" style="margin-right:1.5rem">
          <user-video :stream-manager="mainStreamManager"/>
          <on-off ref="on-off" @audioOnOff="audioOnOff" @videoOnOff="videoOnOff"></on-off>
        </div>
        <div id="video-container">
          <user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)"/>
        </div>

        <div class="waiting" style="width:22rem; margin-left:1.5rem; border-radius:10px">
          <div style="border:1px solid; background-color:#f0ebe7; margin-top:0px; overflow-y: scroll; width: 20rem; height:500px;">
            <h5 class="manvideo-font" style=" border-radius: 3px; width:100%; height:2rem; background-color:#F6F6F6">대기자 명단</h5>
              <ul v-for="waitingMan in Q[where]" :key="waitingMan">
              <li class="manvideo-font" >{{ waitingMan }}</li>
            </ul>
          </div>
             <div class="manvideo-button" style="margin-top:1.5rem; margin-bottom:1rem">
                <button style="margin-right:5px" class="btn btn-outline-secondary question-font btnGet" @click="comeFirstMan(this.where)">받기</button>
                <button style="margin-left:5px" class="btn btn-outline-secondary question-font btnGet" @click="goFirstMan(this.where)">내보내기</button>
              </div>
           </div>
      </div>
      <div style="margin-top:1rem">
            <input class="btn btn-large btn-danger btnOut" type="button" id="buttonLeaveSession" @click="leaveSession" value="나가기">
         </div>
    </div>
  </div>
  </div>
</template>


<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/components/UserVideo';
import { mapGetters } from 'vuex'
import OnOff from '@/components/OnOff'

axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ':' + process.env.VUE_APP_OPENVIDU_PORT;
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: 'man-company',
  props: {
    where: String,
      Q: Object,
  },

   components: {
      UserVideo,
      OnOff,
   },

   data () {
      return {
         OV: undefined,
         session: undefined,
         mainStreamManager: undefined,
         publisher: undefined,
         subscribers: [],

      mySessionId: '',
         myUserName: '',
      }
   },

   methods: {

      joinSession (target) {
      this.mySessionId = target
      this.myUserName = target + ' Company'
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
                     publishAudio: true,     // Whether you want to start publishing with your audio unmuted or not
                     publishVideo: true,     // Whether you want to start publishing with your video enabled or not
                     resolution: '640x480',  // The resolution of your video
                     frameRate: 30,         // The frame rate of your video
                     insertMode: 'APPEND',   // How the video is inserted in the target element 'video-container'
                     mirror: false          // Whether to mirror your local video or not
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

         this.$emit('leave-video')
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
       *   1) Initialize a Session in OpenVidu Server   (POST /openvidu/api/sessions)
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
      this.$emit('come-first-man', where)
    },

    goFirstMan(where) {
      this.$emit('go-first-man', where)
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
      this.joinSession(this.wedding.weddingNum + this.where)
   },
   beforeUnmount() {
      this.leaveSession()
   },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');
.guest-size{
  width: 100%;
}
#app{
  background-color: #f0ebe7;
  height: 1000px;
}
#session{
  margin-top: 3rem;
  height: 500px;
  width: 800px;
}
.manvideo-font{
   font-family: 'Gowun Dodum', sans-serif;
}
.man-video{
   display: flex;
   justify-content: center;
}
.btnOut{
  margin-top:2rem;
  width: 120px;
  height: 40px;
}
.manvideo-button{
   display:flex;
   justify-content: center;
}
</style>
