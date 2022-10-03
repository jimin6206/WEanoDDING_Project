<template>
<div class="cert" style="height:52rem">
  <div class="cert-font">
    <h1>신랑 {{ wedding.weddingManNm }}와 신부 {{ wedding.weddingWomanNm }}의</h1>
    <h1>결혼식에 오신걸 환영합니다.</h1>
    <br/>
    <h3>본인인증을 통해 참석부탁드립니다</h3>
  </div>
  <button class="btn btn-outline-secondary btnCert" @click="certification">본인인증</button><br>

</div>
</template>

<script>
import axios from 'axios'
import { onMounted } from 'vue'
import { useStore, mapGetters } from 'vuex';
import router from '@/router';
import store from '@/store'
axios.defaults.headers.post['Content-Type'] = 'application/json';

const IMP = window.IMP; // 생략 가능
IMP.init(process.env.VUE_APP_IAMPORT_API_ID); // Example: imp00000000
export default {
  computed: mapGetters({
    wedding: 'root/getNowWeddingInfo',
  }),
  props: {
    weddingNum: Number
  },
  data() {
    return {
      guestName: '',
      guestPhone: '',
    }
  },
  methods: {
    certification: function() {
      IMP.certification({ // param
        merchant_uid: this.wedding.weddingNum + '-' + new Date().getTime(), // 주문 번호
        m_redirect_url : "{리디렉션 될 URL}", // 모바일환경에서 popup:false(기본값) 인 경우 필수, 예: https://www.myservice.com/payments/complete/mobile
        popup : true // PC환경에서는 popup 파라메터가 무시되고 항상 true 로 적용됨
      }, function (rsp) { // callback
        if (rsp.success) {
          axios.post(`https://i7e206.p.ssafy.io/api/v1/certify/${rsp.imp_uid}`)
            .then((resp) => {
              this.guestName = JSON.parse(resp.data.certificationsInfo).response.name
              this.guestPhone = JSON.parse(resp.data.certificationsInfo).response.phone
              store.commit('root/setNowGuestInfo', {guestName : this.guestName, guestPhone: this.guestPhone})
              router.push({ name: 'entrance' })
            })
            .catch((error) => {
              console.log(error)
            })
        } else {
          alert("인증에 실패하였습니다. 에러 내용: " +  rsp.error_msg);
        }
      });
    },
  },
  setup(props) {
    const store = useStore()
    onMounted(() => {
      store.dispatch('root/requestWeddingInfoByNum', props.weddingNum)
        .then((result) => {
          store.commit('root/setNowWeddingInfo', result.data)
        })
        .catch((error) => {
          console.log(error)
        })
    })
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

.cert{
  background: #f0ebe7;
}

.cert-font{
  font-family: 'Gowun Dodum', sans-serif;
  padding-top:3rem;
  margin-bottom: 3rem;
}
.btnCert{
  font-size:20px;
  margin-bottom: 3rem;
}
</style>
