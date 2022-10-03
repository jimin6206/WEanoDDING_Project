<template>
  <div class ="management">
    <div id='app'>
      <div class="card" style="margin-top:20px">
        <div class="card-body manage-font">
          달력에서 날짜를 선택해 주세요.
        </div>
      </div>
      <v-date-picker class="manage-font" color="pink" v-model="date" @dayclick='dayClicked' style="width: 500px; height: 450px; margin-top:20px" />
      <div><button class="schedule btn btn-outline-secondary" @click="goMake">결혼식 생성하기</button></div>
    </div>

    <div style="width:500px">
      <div class="card">
        <div class="card-body manage-font">
          이름을 검색해서 결혼식 정보를 찾아보세요.
        </div>
      </div>
      <form onsubmit="return false;">
        <div class="input-group mb-3">
          <input type="text" id="search" v-model="state.form.weddingPersonName" class="manage-font form-control" placeholder="이름을 넣어주세요" aria-label="Recipient's username" aria-describedby="button-addon2">
          <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="clickSearch">Search</button>
          </div>
      </form>

      <div style="background-color:#f0ebe7; margin-top:0px; overflow-y: scroll; width: 530px; height:800px;">
      <ul v-for="wedding in state.form.weddingInfo" :key="wedding" style="margin-bottom:0px; padding-left: 0rem; background-color:#f0ebe7; border: 1px solid #f0ebe7;">
        <li class="card" v-if="state.isourlist" style="width:100%; background-color:#f0ebe7; border: 1px solid #f0ebe7;">
          <div class="card mb-3" style="width:500px; border: 1px solid D3D3D3;">
            <div style="width:500px" class="manage-font card-header">결혼 정보</div>
            <div style="width:500px" class="card-body text-secondary">
              <h5 style="text-align:left" class="manage-font card-title">시간 : {{ wedding.weddingDate }}</h5>
              <h5 style="text-align:left" class="manage-font card-title">장소 : {{ wedding.hallName }}</h5>
              <h5 style="text-align:left" class="manage-font card-title">신랑 : {{ wedding.weddingManNm }}</h5>
              <h5 style="text-align:left" class="manage-font card-title">신부 : {{ wedding.weddingWomanNm }}</h5>
            </div>
            <div>
              <button style="margin-bottom:25px" type="button" class="btn btn-outline-secondary" @click="clickStartWeddingGuest(wedding)">하객용(시연)</button>
              <button style="margin-bottom:25px; margin-left:25px" type="button" class="btn btn-outline-secondary" @click="clickDetailReservation(wedding)">상세 보기</button>
            </div>
            </div>

        </li>
      </ul>
    </div>
    </div>

    <!-- <div>
      {{ state.form.weddingInfo}}
    </div> -->

  </div>
</template>

<script>
import 'v-calendar/dist/style.css'
import { computed, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
export default {

  name: 'management-guest-view',
  setup() {
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      form: {
        selectedDay: null,
        weddingInfo: computed(() => store.getters['root/getWeddingInfo']),
        weddingPersonName: '',
      },
      isourlist: true,
    })
    const dayClicked = function(day) {
      state.form.selectedDay = day;
      store.dispatch('root/requestDateWedding', state.form.selectedDay.id)
      .then(function (result) {
        store.commit('root/setWeddingInfo', result.data)
      })
      .catch(function (err) {
        console.log(err)
      })
    }
    const goMake = function () {
      router.push({ name: 'makereservation'})
      store.commit('root/setWeddingDateInfo', state.form.selectedDay.id)
    }
    const clickSearch = function () {
      store.dispatch('root/requestNameWedding', state.form.weddingPersonName)
      .then(function (result) {
        store.commit('root/setWeddingInfo', result.data)
        state.form.weddingPersonName=''
      })
      .catch(function (err) {
        console.log(err)
      })
    }
    const clickDetailReservation = function (wedding) {
      router.push({ name: 'detailresrvation', params: {weddingnum: wedding.weddingNum }})
      store.commit('root/setWeddingDetailInfo', wedding )
    }

    // 결혼 시작하기(본인인증하는 화면으로 이동하기)
    const clickStartWeddingGuest = function(wedding) {
      store.commit('root/setNowWeddingInfo', wedding)
      router.push({name: 'certification', params: {weddingNum: wedding.weddingNum }})
    }
    // 결혼 시작하기(본인인증하는 화면으로 이동하기) 끝
    return { dayClicked, state, goMake, clickSearch, clickDetailReservation, clickStartWeddingGuest
     }
  }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');
  .manage-font{
    font-family: 'Gowun Dodum', sans-serif;
  }

  .management{
    height: 1000px;
    background: #f0ebe7;
    display: flex;
    align-items: baseline;
    justify-content: center;
    margin: auto;
    justify-content: space-evenly;
  }
  .vc-container{
    margin-top: 15px;
  }
  .vc-day{
    margin-top: 25px;
  }
  .vc-weeks{
    margin-top: 25px;
  }
  .schedule{
    margin-top: 25px;
    font-family: 'Gowun Dodum', sans-serif;
  }
  .input-group{
    margin-top: 20px;
  }
  .vc-header{
    background-color: #F6F6F6;
    padding: 10px 15px;
    border-radius: 5px;
  }
  #id{
    font-family: 'Gowun Dodum', sans-serif;
  }
</style>
