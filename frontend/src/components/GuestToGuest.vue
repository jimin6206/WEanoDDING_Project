<template>
	<div style="background-color:#f0ebe7; height: 50rem;">

      <div v-if="!state.room" style="padding-top:5rem">
        <div>
          <h3 style="padding-top:5rem; display:inline; " class="waitingguest-font">어서오세요, </h3>
          <h2 style="display:inline" class="waitingguest-font"> {{ state.guestInfo.guestName }}({{ state.guestInfo.guestGroup }})</h2>
          <h3 style="display:inline" class="waitingguest-font"> 님 </h3>
        </div>
        <div style="margin-top:3rem">
          <h3 class="waitingguest-font" style="display:inline">하객끼리의 소통을 위한 공간입니다</h3>
        </div>
        <div style="margin-top:3rem">
          <h3 class="waitingguest-font">참가하고 싶은 방을 눌러주세요</h3>
        </div>
        <div class="text-center" style="margin-top:3rem">
          <button style="margin-left:1rem" type="button" class="btn btn-outline-secondary" @click="clickButton('1')">신부 친구 1방</button>
          <button style="margin-left:1rem" type="button" class="btn btn-outline-secondary" @click="clickButton('2')">신부 친구 2방</button>
          <button style="margin-left:1rem" type="button" class="btn btn-outline-secondary" @click="clickButton('3')">신부 친구 3방</button>
        </div>
         <div class="text-center" style="margin-top:1rem">
          <button style="margin-left:1rem" type="button" class="btn btn-outline-secondary" @click="clickButton('4')">신랑 친구 1방</button>
          <button style="margin-left:1rem" type="button" class="btn btn-outline-secondary" @click="clickButton('5')">신랑 친구 2방</button>
          <button style="margin-left:1rem" type="button" class="btn btn-outline-secondary" @click="clickButton('6')">신랑 친구 3방</button>
        </div>
        <div style="margin-top:3rem">
          <button type="button" class="btn btn-danger" @click="clickExit">돌아가기</button>
        </div>
        <div style="width:40rem; margin:auto; margin-top:3rem" id="carouselExampleFade" class="carousel slide carousel-fade" data-bs-ride="carousel">
          <div class="carousel-inner">
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
          <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
          </button>
          <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
          </button>
        </div>
      </div>

      <guest-to-guest-video
        v-if="state.room"
        :room="state.room"
        @leaveGuestRoom="leaveGuestRoom"
      ></guest-to-guest-video>
    </div>
</template>

<script>
import { computed, reactive } from 'vue'
import { useStore } from 'vuex'
import GuestToGuestVideo from '@/components/ConferenceGuest/GuestToGuestVideo'

export default {
  components: {
    GuestToGuestVideo,
  },
  setup(props, { emit }) {
    const store = useStore()
    const state = reactive({
      room: false,
      guestInfo: computed(() => store.getters['root/getNowGuestInfo'])
    })

    const clickButton = function(target) {
      state.room = target
    }

    const leaveGuestRoom = function() {
      state.room = false
    }

    const clickExit = function() {
      emit('click-exit')
    }

    return { state, clickButton, leaveGuestRoom, clickExit }
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

	.waitingguest-font{
		font-family: 'Gowun Dodum', sans-serif;
	}
</style>
