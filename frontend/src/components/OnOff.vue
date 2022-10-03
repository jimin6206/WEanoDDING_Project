<template>
  <button class="btn btn-outline-secondary question-font btnGet" @click="videoOnOff">
    <!-- 켜진 캠 -->
    <span v-if="state.video">캠 끄기
      <svg class="icon icon-videocam">
        <use xlink:href="#icon-videocam">
          <symbol id="icon-videocam" viewBox="0 0 24 24">
            <path d="M17.016 10.5l3.984-3.984v10.969l-3.984-3.984v3.516q0 0.422-0.305 0.703t-0.727 0.281h-12q-0.422 0-0.703-0.281t-0.281-0.703v-10.031q0-0.422 0.281-0.703t0.703-0.281h12q0.422 0 0.727 0.281t0.305 0.703v3.516z"></path>
          </symbol>
        </use>
      </svg>
    </span>
    <!-- 꺼진 캠 -->
    <span v-if="!state.video">캠 켜기
      <svg class="icon icon-videocam_off">
        <use xlink:href="#icon-videocam_off">
          <symbol id="icon-videocam_off" viewBox="0 0 24 24">
            <path d="M3.281 2.016l17.719 17.719-1.266 1.266-3.188-3.188q-0.281 0.188-0.563 0.188h-12q-0.422 0-0.703-0.281t-0.281-0.703v-10.031q0-0.422 0.281-0.703t0.703-0.281h0.75l-2.719-2.719zM21 6.516v10.688l-11.203-11.203h6.188q0.422 0 0.727 0.281t0.305 0.703v3.516z"></path>
          </symbol>
        </use>
      </svg>
    </span>
  </button>

  <button class="btn btn-outline-secondary question-font btnGet" @click="audioOnOff">
    <span v-if="state.audio">마이크 끄기
      <!-- 켜진 마이크 -->
      <svg class="icon icon-microphone">
        <use xlink:href="#icon-microphone">
          <symbol id="icon-microphone" viewBox="0 0 18 28">
            <path d="M18 11v2c0 4.625-3.5 8.437-8 8.937v2.063h4c0.547 0 1 0.453 1 1s-0.453 1-1 1h-10c-0.547 0-1-0.453-1-1s0.453-1 1-1h4v-2.063c-4.5-0.5-8-4.312-8-8.937v-2c0-0.547 0.453-1 1-1s1 0.453 1 1v2c0 3.859 3.141 7 7 7s7-3.141 7-7v-2c0-0.547 0.453-1 1-1s1 0.453 1 1zM14 5v8c0 2.75-2.25 5-5 5s-5-2.25-5-5v-8c0-2.75 2.25-5 5-5s5 2.25 5 5z"></path>
          </symbol>
        </use>
      </svg>
    </span>

    <!-- 꺼진 마이크 -->
    <span v-if="!state.audio">마이크 켜기
      <svg class="icon icon-microphone">
        <use xlink:href="#icon-microphone-slash">
          <symbol id="icon-microphone-slash" viewBox="0 0 22 28">
            <path d="M4.234 14.766l-1.578 1.578c-0.422-1.031-0.656-2.156-0.656-3.344v-2c0-0.547 0.453-1 1-1s1 0.453 1 1v2c0 0.609 0.094 1.203 0.234 1.766zM21.641 5.359l-5.641 5.641v2c0 2.75-2.25 5-5 5-0.594 0-1.172-0.109-1.703-0.297l-1.5 1.5c0.953 0.5 2.047 0.797 3.203 0.797 3.859 0 7-3.141 7-7v-2c0-0.547 0.453-1 1-1s1 0.453 1 1v2c0 4.625-3.5 8.437-8 8.937v2.063h4c0.547 0 1 0.453 1 1s-0.453 1-1 1h-10c-0.547 0-1-0.453-1-1s0.453-1 1-1h4v-2.063c-1.328-0.141-2.578-0.594-3.672-1.266l-3.969 3.969c-0.203 0.203-0.516 0.203-0.719 0l-1.281-1.281c-0.203-0.203-0.203-0.516 0-0.719l19.281-19.281c0.203-0.203 0.516-0.203 0.719 0l1.281 1.281c0.203 0.203 0.203 0.516 0 0.719zM15.703 3.297l-9.703 9.703v-8c0-2.75 2.25-5 5-5 2.156 0 4 1.391 4.703 3.297z"></path>
          </symbol>
        </use>
      </svg>
    </span>
  </button>
</template>

<script>
import { reactive } from 'vue';
export default {
  name: 'on-off',
  setup(props, { emit }) {
    const state = reactive({
			video: true,
			audio: true,
		});
    const videoOnOff = () => {
			if (state.video) {
				state.video = false;
			} else {
				state.video = true;
			}
			emit('videoOnOff', {
				video: state.video,
			});
		};
		const audioOnOff = () => {
			if (state.audio) {
				state.audio = false;
			} else {
				state.audio = true;
			}
			console.log(state.audio);
			emit('audioOnOff', {
				audio: state.audio,
			});
		};
    return {
			state,
			videoOnOff,
			audioOnOff,
		};
  },
}
</script>
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');
.btnGet{
  margin-right: 10px;
}

.icon {
  display: inline-block;
  width: 1.5em;
  height: 1.5em;
  stroke-width: 0;
  stroke: currentColor;
  fill: currentColor;
}

</style>
