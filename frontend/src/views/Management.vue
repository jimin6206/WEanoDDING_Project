<template>
  <div class ="management">
    <div id='app' style="margin-right:2rem; margin-top:5rem;">
      <div class="card" style="margin-top:20px">
        <div class="card-body manage-font">
          달력에서 날짜를 선택해 주세요.
        </div>
      </div>
      <v-date-picker class="manage-font" color="pink" v-model="date" @dayclick='dayClicked' style="width: 500px; height: 450px; margin-top:20px" />
      <div><button class="schedule btn btn-outline-secondary" @click="goMake">결혼식 예약하기</button></div>
    </div>

    <div style="width:500px; margin-left:2rem; margin-top:5rem;">
      <div class="card">
        <div class="card-body manage-font">
          이름을 검색해서 결혼식 정보를 찾아보세요.
        </div>
      </div>
      <form onsubmit="return false;" style="width:500px">
        <div class="input-group mb-3">
          <input type="text" id="search" v-model="state.form.weddingPersonName" class="manage-font form-control" placeholder="이름을 넣어주세요" aria-label="Recipient's username" aria-describedby="button-addon2">
          <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="clickSearch">Search</button>
        </div>
      </form>

      <div style="background-color:#f0ebe7; margin-top:0px; overflow-y: scroll; width: 530px; height:43rem;">
      <ul v-for="wedding in state.form.weddingInfo" :key="wedding" style="margin-bottom:0px; padding-left: 0rem; background-color:#f0ebe7; border: 1px solid #f0ebe7;">
        <li class="card" v-if="isourlist(wedding.companyId)" style="width:100%; background-color:#f0ebe7; border: 1px solid #f0ebe7;">
          <div class="card mb-3" style="width:500px; border: 1px solid D3D3D3;">
            <div style="width:500px" class="manage-font card-header">결혼 정보</div>
            <div style="width:500px" class="card-body text-secondary">
              <h5 style="text-align:left" class="manage-font card-title">시간 : {{ wedding.weddingDate }}</h5>
              <h5 style="text-align:left" class="manage-font card-title">장소 : {{ wedding.hallName }}</h5>
              <h5 style="text-align:left" class="manage-font card-title">신랑 : {{ wedding.weddingManNm }}</h5>
              <h5 style="text-align:left" class="manage-font card-title">신부 : {{ wedding.weddingWomanNm }}</h5>
            </div>
            <div style="display:flex; justify-content:space-evenly;">
              <button v-if="!wedding.weddingEnd" style="margin-bottom:25px; " type="button" class="btn btn-outline-secondary" @click="clickStartWedding(wedding)">결혼식 시작</button>
              <button style="margin-bottom:25px; " type="button" class="btn btn-outline-secondary" @click="clickDetailReservation(wedding)">상세 보기</button>
              <button v-if="!wedding.weddingEnd" style="margin-bottom:25px;" type="button" class="btn btn-danger btnExit" @click="clickWeddingEnd(wedding.weddingNum)">결혼식 종료</button>
              <button v-if="wedding.weddingEnd" style="margin-bottom:25px;" type="button" class="btn btn-outline-secondary" @click="clickAfterWeddingGuestInfo(wedding.weddingNum)">참석 하객 정보 보기</button>
            </div>
            </div>
        </li>
      </ul>
    </div>
    </div>
  </div>
</template>

<script>
import 'v-calendar/dist/style.css'
import { computed, onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
export default {

  name: 'management-view',
  setup() {
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      form: {
        selectedDay: null,
        weddingInfo: computed(() => store.getters['root/getWeddingInfo']),
        weddingPersonName: '',
      },
      isloggedin: computed(() => store.getters['root/getIsLoggedIn']),
      isourlist: false,
    })
    const isourlist = function(companyID) {
      if (companyID === localStorage.getItem('companyId')) {
        return true
      } else {
        return false
      }
    }
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
      if (this.state.form.selectedDay === null) {
        alert('날짜를 선택해주세요')
      } else {
        if (new Date().getFullYear()+'-'+("0" + (1 + new Date().getMonth())).slice(-2)+'-'+("0" + new Date().getDate()).slice(-2) <= state.form.selectedDay.id) {
          router.push({ name: 'makereservation'})
          store.commit('root/setWeddingDateInfo', state.form.selectedDay.id)
        } else {
          alert(new Date().getFullYear()+'-'+("0" + (1 + new Date().getMonth())).slice(-2)+'-'+("0" + new Date().getDate()).slice(-2) + ' 이후의 날짜를 선택해주세요')
        }
      }
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

    // 결혼 시작하기(화상통화 선택하는 화면으로 이동하기)
    const clickStartWedding = function(wedding) {
      store.commit('root/setNowWeddingInfo', wedding)
      router.push({name: 'waiting'})
    }
    // 결혼 시작하기(화상통화 선택하는 화면으로 이동하기) 끝

    // 결혼 종료하기
    const clickWeddingEnd = function(weddingNum) {
      if (confirm('결혼식을 종료하시겠습니까? 결혼식의 수정 등 일체의 행위가 불가능해집니다.')) {
        store.dispatch('root/requestWeddingEnd', weddingNum)
        router.go()
      }
    }
    // 결혼 종료하기 끝

    // 결혼결과 확인하기
    const clickAfterWeddingGuestInfo = function(weddingNum) {
      router.push({ name: 'afterWeddingGuestInfo', params: {weddingNum: weddingNum}})
    }
    // 결혼결과 확인하기 끝

    onMounted(() =>{
      store.commit('root/setIsLoggedIn')
      if (state.isloggedin) {
      } else {
        alert('로그인 후 이용해주세요')
        router.push({ name:'login' })
      }
    })

    return { dayClicked, state, goMake, clickSearch, clickDetailReservation, clickStartWedding, isourlist, clickWeddingEnd, clickAfterWeddingGuestInfo }
  }
};
</script>

<style>
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
</style>
