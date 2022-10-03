<template>
  <div class="detail" >
    <div style="margin-bottom: 20px;" class="detail-font"><h2>결혼 상세 정보</h2></div>
    <div class="card detail-card" style="width: 30rem; height: 44rem;">
      <img src="@/assets/weddinghome.png" class="card-img-top" alt="detail">
      <div class="card-body text-secondary">
        <h5 style="text-align:left; margin-top: 10px;" class="detail-font card-title">회사 이름 : {{ state.form.weddingDetailInfo.companyId }}</h5>
        <h5 style="text-align:left; margin-top: 10px;" class="detail-font card-title">결혼 날짜 : {{ state.form.weddingDetailInfo.weddingDate }}</h5>
        <h5 style="text-align:left; margin-top: 10px;" class="detail-font card-title">결혼 식장 : {{ state.form.weddingDetailInfo.hallName }}</h5>
        <h5 style="text-align:left; margin-top: 10px;" class="detail-font card-title">신랑 이름 : {{ state.form.weddingDetailInfo.weddingManNm }}</h5>
        <h5 style="text-align:left; margin-top: 10px;" class="detail-font card-title">신랑 번호 : {{ state.form.weddingDetailInfo.weddingManPh }}</h5>
        <h5 style="text-align:left; margin-top: 10px;" class="detail-font card-title">신부 이름 : {{ state.form.weddingDetailInfo.weddingWomanNm }}</h5>
        <h5 style="text-align:left; margin-top: 10px;" class="detail-font card-title">신부 번호 : {{ state.form.weddingDetailInfo.weddingWomanPh }}</h5>
        <br>
        <div v-if="!!state.ismyreservation">
          <button v-if="!state.form.weddingDetailInfo.weddingEnd" style="margin-right: 15px" @click="clickgoChangeReservation" type="button" class="detail-font btn btn-outline-secondary">수정하기</button>
          <button style="margin-left:15px" @click="clickDeleteReservation" type="button" class="detail-font btn btn-outline-secondary">삭제하기</button>
        </div>
        <button id="alertStart" style="float:left" onclick="history.back()" type="button" class="btn btn-outline-secondary">뒤로</button>
      </div>
    </div>
  </div>
</template>

<script>
import { computed, reactive, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
export default {
  name: 'detail-reservaion',
  setup() {
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      form: {
        weddingDetailInfo: computed(() => store.getters['root/getWeddingDetailInfo']),
      },
      ismyreservation: false,
    })
    const clickgoChangeReservation = function () {
      router.push({ name: 'editreservation', params: {weddingnum: state.form.weddingDetailInfo.weddingNum }})
    }
    const clickDeleteReservation = function () {
      if (confirm('진짜 삭제하시겠습니까?')) {
        store.dispatch('root/requestDeleteReservation', state.form.weddingDetailInfo.weddingNum)
        .then(function() {
          store.dispatch('root/requestDateWedding',state.form.weddingDetailInfo.weddingDate)
          .then(function(result) {
            store.commit('root/setWeddingInfo', result.data)
          })
          .catch(function(err) {
            console.log(err)
          })
          router.push({ name: 'management' })
        })
        .catch(function(err) {
          console.log(err)
        })
      }
    }
    onMounted (() => {
      ismyreservation()
    })
    const ismyreservation = function () {
      if (localStorage.getItem('companyId') === state.form.weddingDetailInfo.companyId) {
        state.ismyreservation = true
      } else {
        state.ismyreservation = false
      }
    }
    return { state, clickgoChangeReservation, clickDeleteReservation, ismyreservation }
  },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

.detail{
  height: 1000px;
  background: #f0ebe7;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.detail-font{
  font-family: 'Gowun Dodum', sans-serif;
  margin-bottom: 15px;
}

/* .card-detail{
  width: 500px;
  height: 800px;
} */
</style>
