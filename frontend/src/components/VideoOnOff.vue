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
</template>

<script>
import { reactive } from 'vue';
export default {
  name: 'video-on-off',
  setup(props, { emit }) {
    const state = reactive({
			video: true,
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
    return {
			state,
			videoOnOff,
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
