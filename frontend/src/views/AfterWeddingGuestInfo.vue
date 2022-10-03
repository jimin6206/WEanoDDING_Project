<template>
  <div class="afterwedding-font">
    <div class="afterwedding">
      <div style="padding-top:5rem; width: 45%;">
        <h2>신랑측 하객</h2>
        <table class="table table-striped table-hover" style="margin-top:3rem;">
          <thead>
            <tr>
              <th scope="col" style="width:10%">이름</th>
              <th scope="col" style="width:20%">소속</th>
              <th scope="col" style="width:15%">전화번호</th>
              <th scope="col" style="width:10%">축의금</th>
              <th scope="col" style="width:45%">방명록</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="manGuestInfo in state.manGuestList" :key="manGuestInfo">
              <th scope="row">{{ manGuestInfo.guestName }}</th>
              <td>{{ manGuestInfo.guestGroup }}</td>
              <td>{{ manGuestInfo.guestPhone }}</td>
              <td>{{ manGuestInfo.guestMoney }}</td>
              <td>{{ manGuestInfo.guestMessage }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div style="padding-top:5rem; width:45%">
        <h2>신부측 하객</h2>
        <table class="table table-striped table-hover" style="margin-top:3rem;">
          <thead>
            <tr>
              <th scope="col" style="width:10%">이름</th>
              <th scope="col" style="width:20%">소속</th>
              <th scope="col" style="width:15%">전화번호</th>
              <th scope="col" style="width:10%">축의금</th>
              <th scope="col" style="width:45%">방명록</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="womanGuestInfo in state.womanGuestList" :key="womanGuestInfo">
              <th scope="row">{{ womanGuestInfo.guestName }}</th>
              <td>{{ womanGuestInfo.guestGroup }}</td>
              <td>{{ womanGuestInfo.guestPhone }}</td>
              <td>{{ womanGuestInfo.guestMoney }}</td>
              <td>{{ womanGuestInfo.guestMessage }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div style="background: #f0ebe7; padding-top:3rem; padding-bottom: 3rem; ">
        <button style="width:10rem; height:4rem; font-size:20px;" onclick="history.back()" type="button" class="btn btn-outline-secondary">돌아가기</button>
    </div>
  </div>

</template>

<script>
import { onMounted, reactive } from 'vue'
import { useStore } from 'vuex'

export default {
  props: {
    weddingNum: Number
  },
  setup(props) {
    const store = useStore()
    const state = reactive({
      manGuestList: [],
      womanGuestList: [],
    })

    const fetchGuestInfo = function() {
      store.dispatch('root/requestAfterWeddingGuestInfo', props.weddingNum)
        .then(response => {
          response.data.forEach(element => {
            if (element.guestSelect === 1) {
              state.manGuestList.push(element)
            } else {
              state.womanGuestList.push(element)
            }
          })
        })
        .catch(error => {
          console.log(error)
        })
    }

    onMounted(() => {
      fetchGuestInfo()
    })

    return { state }
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

.afterwedding{
  background: #f0ebe7;
  display: flex;
  justify-content: space-around;
}
.afterwedding-font{
  font-family: 'Gowun Dodum', sans-serif;
}
</style>
