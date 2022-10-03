<template>
  <div class="hallvideoguest">
    <div id="session" v-if="session">
      <div id="session-header">
        <h1 style="padding-top:5rem; margin-bottom:2rem" id="session-title">{{ roomName }}</h1>
        <on-off
          ref="on-off"
          @audioOnOff="audioOnOff"
          @videoOnOff="videoOnOff"
        ></on-off>
        <input
          class="btn btn-large btn-danger btnExit"
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="나가기"
        />
      </div>
    </div>
    <div class="container">
      <div class="left">
      <!-- 하객 비디오 -->
        <div class="container" style="margin-top:2rem">
          <div id="video-container" class="row row-cols-3">
            <guest-to-guest-user-video class="small-video" :stream-manager="mainStreamManager" style="margin-bottom:0rem;"/>
            <guest-to-guest-user-video
              style="margin-bottom:0rem;"
              class="small-video"
              v-for="sub in subscribers"
              :key="sub.stream.connection.connectionId"
              :stream-manager="sub"
              @click.native="updateMainVideoStreamManager(sub)"
            />
          </div>
        </div>
      </div>
      <div class="right">
        <room-chat style="margin-top:2rem"
          class="chatDesign"
          v-show="this.disabled"
          ref="chat"
          @message="sendMessage"
          :subscribers="subscribers"
        ></room-chat>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import GuestToGuestUserVideo from "@/components/GuestToGuestUserVideo";
import OnOff from "@/components/OnOff";
import RoomChat from "@/components/ConferenceGuest/ChatForGuestToGuest";

import { OpenVidu } from "openvidu-browser";
import { mapGetters } from "vuex";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ':' + process.env.VUE_APP_OPENVIDU_PORT;
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {

  props: {
    room: String,
  },

  components: {
    GuestToGuestUserVideo,
    OnOff,
    RoomChat,
  },

  data() {
    return {
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      disabled: true,
      mySessionId: "",
      myUserName: "",
      roomName: ''
    };
  },

  methods: {

    joinSession(target) {
      this.mySessionId = target;
      this.myUserName = this.guest.guestGroup + ' ' + this.guest.guestName;
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();
      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // public 채팅 signal 받기
      this.session.on("signal:public-chat", (event) => {
        this.$refs.chat.addMessage(
          event.data,
          JSON.parse(event.data).sender === this.myUserName,
          false
        );
      });

      // private 채팅 signal 받기
      this.session.on("signal:private-chat", (event) => {
        this.$refs.chat.addMessage(event.data, false, true);
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(target + this.mySessionId).then((token) => {
        this.session
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---
            // let publisher = this.maincompany(undefined, {
            let publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              // resolution: "1280x480", // The resolution of your video
              // resolution: '1290x960',  // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---

            this.session.publish(this.publisher);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      this.$emit("leave-guest-room");
      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
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

    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) =>
        this.createToken(sessionId)
      );
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },

    sendMessage({ content, to }) {
      let now = new Date();
      let current = now.toLocaleTimeString([], {
        hour: "2-digit",
        minute: "2-digit",
        hour12: false, // true인 경우 오후 10:25와 같이 나타냄.
      });

      let messageData = {
        content: content,
        sender: this.myUserName,
        time: current,
      };

      // 전체 메시지
      if (to === "all") {
        this.session
          .signal({
            data: JSON.stringify(messageData),
            to: [],
            type: "public-chat",
          })
          .then(() => {
          })
          .catch((error) => {
            console.log(error);
          });
      }

      // 개인 메시지
      if (to !== "all") {
        this.session
          .signal({
            data: JSON.stringify(messageData),
            to: [to],
            type: "private-chat",
          })
          .then(() => {
            // 내가 보낸 개인 메시지 추가
            this.$refs.chat.addMessage(JSON.stringify(messageData), true, true);
          })
          .catch((error) => {
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
    setRoomName() {
      switch (this.room) {
        case '1':
          this.roomName = '신부 친구 1방'
          break
        case '2':
          this.roomName = '신부 친구 2방'
          break
        case '3':
          this.roomName = '신부 친구 3방'
          break
        case '4':
          this.roomName = '신랑 친구 1방'
          break
        case '5':
          this.roomName = '신랑 친구 2방'
          break
        case '6':
          this.roomName = '신랑 친구 3방'
          break
      }
    },
  },
  computed: mapGetters({
    wedding: "root/getNowWeddingInfo",
    guest: "root/getNowGuestInfo",
  }),

  mounted() {
    this.joinSession(`${this.wedding.weddingNum}guestRoom${this.room}`)
    this.setRoomName()
  },
  beforeUnmount() {
    this.leaveSession()
  },
};
</script>

<style scoped>

.container{
  height: 70rem;
}
#video-container {
  overflow-x: auto;
  max-height: 40.25rem;
}

::-webkit-scrollbar {
    width: 8px;
}
::-webkit-scrollbar-thumb {
    height: 30%; /* 스크롤바의 길이 */
    background: lightgray; /* 스크롤바의 색상 */
    border-radius: 10px;
}
::-webkit-scrollbar-track {
    background: F6F6F6;  /*스크롤바 뒷 배경 색상*/
}
.btnExit{
  font-size: 20px;
}

.hallvideoguest{
  height: 70rem;
}

#main-container.container{
  height: 100rem;
}

.left {
	float:left;
	width:70%;
	height: 35rem;
	padding-right: 3.75rem;
}

.right {
	float:right;
	width:30%;
	height: 35rem;
}
</style>
