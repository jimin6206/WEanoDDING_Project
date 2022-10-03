<template>
<div class="pay">
  <h1 class="pay-font pay-title">당신의 마음(축의금)을<br/>표현하세요!</h1>
  <input class="pay-size" type="text" placeholder="송금할 금액을 적어주세요" v-model="money">
  <br>
  <button class="btn btn-outline-secondary btnPay pay-font" @click="requestPay('card')">카드 결제하기</button>
  <button class="btn btn-outline-secondary btnPay pay-font" @click="requestPay('trans')">실시간 계좌이체</button>
  <button class="btn btn-outline-secondary btnPay pay-font" @click="requestPay('vbank')">가상계좌</button>
  <br/>
  <button class="btn btn-outline-secondary btnPay pay-font" @click="requestPay('phone')">휴대폰 소액결제</button>
  <button class="btn btn-outline-secondary btnPay pay-font" @click="skipPay">마음만 드릴게요(축의금 0원)</button>
</div>
</template>

<script>
import { mapGetters } from 'vuex'
import store from '@/store'
import router from '@/router'
const IMP = window.IMP; // 생략 가능
IMP.init(process.env.VUE_APP_IAMPORT_API_ID); // Example: imp00000000
export default {
  data() {
    return {
      money: '',
    }
  },
  computed: mapGetters({
    wedding: 'root/getNowWeddingInfo',
		guestInfo: 'root/getNowGuestInfo'
  }),
  methods: {
    requestPay: function (method) {
      // IMP.request_pay(param, callback) 결제창 호출
      IMP.request_pay({ // param
        pg: "nice",
        pay_method: method,
        merchant_uid: this.wedding.weddingNum + '-' + new Date().getTime(),
        name: "웨안오딩",
        amount: this.money,
        buyer_name: this.guestInfo.guestGroup + this.guestInfo.guestName,
        buyer_tel: this.guestInfo.guestPhoneNm,
      }, rsp => { // callback
        if (rsp.success) {
          alert('결제가 완료되었습니다')
          store.dispatch('root/requestGuestSignup', {...this.guestInfo, guestMoney: this.money})
            .then((response) => {
              store.commit('root/setNowGuestInfo', {...this.guestInfo, guestMoney: this.money})
              router.push({name: 'waiting'})
            })
            .catch((error) => {
              console.log(error)
            })
        } else {
          alert('결제에 실패하였습니다')
        }
      });
    },
    skipPay() {
      store.dispatch('root/requestGuestSignup', {...this.guestInfo, guestMoney: 0})
        .then((response) => {
          store.commit('root/setNowGuestInfo', {...this.guestInfo, guestMoney: 0})
          router.push({name: 'waiting'})
        })
        .catch((error) => {
          console.log(error)
        })
    }
  }
}
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');
  .pay{
    height: 52rem;
      background: #f0ebe7;
  }
  .pay-font{
  font-family: 'Gowun Dodum', sans-serif;
}
.pay-title{
  margin-bottom: 3rem;
  padding-top: 3rem;
}
.pay-size{
  width: 250px;
  height: 50px;
  font-size: 18px;
  margin-bottom: 3rem;
}
.btnPay{
  margin-right: 1rem;
  margin-bottom: 1rem;
  font-size:16px;
}
</style>
