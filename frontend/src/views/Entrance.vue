<template>

  <div class="entrance" style="height: 52rem">
    <div>
      <h1 class="guest-font2" >방명록 작성</h1>
    </div>
    <div>
      <form onsubmit="return false;">
        <p class="guest-font">이름 : {{ state.form.guestName }}</p>
        <br>
        <p class="guest-font">전화번호 : {{ state.form.guestPhone }}</p>
        <br>
        <input class="group-size" type="text" id="guest-group" placeholder="소속을 입력해주세요" v-model="state.form.guestGroup">
        <br>
        <textarea class="entrance-size" id="guest-message" placeholder="방명록(축하의 말을 남겨주세요)" v-model="state.form.guestMessage" rows="5" cols="33"></textarea>
        <br>


        <div class="radio-margin">
          <input type="radio" name="guest-select" id="man" value="1" v-model="state.form.guestSelect">
          <label class="guest-font" for="man">신랑측</label>
          <input type="radio" name="guest-select"  id="woman" value="2" v-model="state.form.guestSelect">
          <label class="guest-font" for="woman">신부측</label>
        </div>
        <div>
          <button class="btn btn-outline-secondary btnNext guest-font" @click="clickNext">다음</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex'
import { computed, reactive } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'user-entrance',
  setup() {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      form: {
        guestName: computed(() => store.getters['root/getNowGuestInfo'].guestName),
        guestGroup: '',
        guestPhone: computed(() => store.getters['root/getNowGuestInfo'].guestPhone),
        guestMessage: '',
        guestSelect: '',
        weddingNum: computed(() => store.getters['root/getNowWeddingInfo'].weddingNum),
      }
    })

    // 유효성검사(적어야 넘가가게)
    const checkOK = function() {
      if (!!state.form.guestName && !!state.form.guestGroup && !!state.form.guestPhone && !!state.form.guestMessage && !!state.form.guestSelect) {
        return true
      } else {
        alert('입력해야 할 칸이 비어있습니다')
        return false
      }
    }
    // 유효성검사(적어야 넘가가게) 끝

    // 다음 버튼 누르면 하객정보 저장하고 넘어가기
    const clickNext = function() {
      if(checkOK()) {
        const payload = {
          guestName: state.form.guestName,
          guestGroup: state.form.guestGroup,
          guestPhone: state.form.guestPhone,
          guestMessage: state.form.guestMessage,
          guestSelect: state.form.guestSelect,
          weddingNum: state.form.weddingNum
        }
        store.commit('root/setNowGuestInfo', payload)
        router.push({name: 'pay'})
      }
    }
    // 다음 버튼 누르면 하객정보 저장하고 넘어가기 끝

    // 스킵 버튼 누르면 하객정보 저장하고 넘어가기
    const clickSkip = function() {
      state.form.guestGroup = '싸피 7기 부울경 2반'
      state.form.guestMessage = '결혼 축하한다'
      state.form.guestSelect = '1'
      if(checkOK()) {
        const payload = {
          guestName: state.form.guestName,
          guestGroup: state.form.guestGroup,
          guestPhone: state.form.guestPhone,
          guestMessage: state.form.guestMessage,
          guestSelect: state.form.guestSelect,
          weddingNum: state.form.weddingNum
        }
        store.commit('root/setNowGuestInfo', payload)
        router.push({name: 'pay'})
      }
    }
    // 스킵 버튼 누르면 하객정보 저장하고 넘어가기 끝

    return { state, clickNext, clickSkip }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

.entrance{
  background: #f0ebe7;
  display: flex;
  justify-content: center;
  height: 1000px;
  flex-direction: column;
}

.entrance-font{
  font-family: 'Gowun Dodum', sans-serif;
}

.entrance-card{
  margin-top: 20px;
}
.btnNext{
  margin-top: 1rem;
  width: 200px;
  height: 50px;
}
.radio-margin{
  margin-top: 1rem;
}
.group-size{
  width: 500px;
  height: 50px;
}
.entrance-size{
  width: 500px;
  height: 300px;
}
.guest-font{
  font-size: 20px;
  font-family: 'Gowun Dodum', sans-serif;
}
.guest-font2{
  font-family: 'Gowun Dodum', sans-serif;
    margin-bottom: 3rem;
}
</style>
