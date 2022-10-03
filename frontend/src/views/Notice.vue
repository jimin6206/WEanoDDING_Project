<template>
  <div class="notice">
    <div id="notice">
      <h1 class="notice-font" style="padding-top: 5rem">공지사항</h1>

      <div style="margin-top:3rem">
        <router-link style="float:right" class="btn btn-outline-secondary notice-font" v-if="state.isadmin"  to='/notice/make'>글쓰기</router-link>
      </div>

      <table class="table table-hover notice-font" style="margin-top:7rem">
        <thead style="background-color: #F6F6F6">
          <tr>
            <th scope="col">연번</th>
            <th scope="col">작성자</th>
            <th scope="col">제목</th>
          </tr>
        </thead>
        <tbody v-for="notice in state.NoticeList" :key="notice" class="notice-tbody" style="border-top:0px solid black;border-bottom:0.01px solid black; ">
          <tr style="cursor:pointer" @click="DetailNoticeInfo(notice)">
            <th>{{notice.noticeNum}} </th>
            <th>{{notice.noticeId}} </th>
            <th>{{ notice.noticeTitle }}</th>
          </tr>
        </tbody>
      </table>

    </div>
  </div>
</template>

<script>
import { computed, onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
export default {

  name: 'notice-view',
  setup() {
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      NoticeList : computed(() => store.getters['root/getNoticeListInfo']),
      isadmin: computed(() => store.getters['root/getIsAdmin']),
    })
    onMounted(() => {
      store.commit('root/setIsAdmin')
      store.dispatch('root/requestNoticeList')
      .then(function(result) {
        store.commit('root/setNoticeListInfo', result.data)
      })
      .catch(function(err) {
        console.log(err)
      })
    })
    const DetailNoticeInfo = function (notice) {
      store.commit('root/setNoticeDetailInfo', notice)
      router.push({ name:'detailnotice', params: {noticeNum: notice.noticeNum }})
    }
    return { state, DetailNoticeInfo }
  }

}

</script>
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

 .notice-font{
  font-family: 'Gowun Dodum', sans-serif;
 }

 #notice{
  background: #f0ebe7;
  }

  .notice{
  height: 1000px;
  background: #f0ebe7;
  display: flex;
  flex-direction: column;
  align-items: center;
  }
  .table{
  width : 48rem;
  }
  .btnque{
    background-color: #0D1903;
    width: 150px;
    height: 40px;
  }
  .question-tbody{
  border-top: 0px solid black;
  }
  a{
  text-decoration: none;
  color: black;
  }
  .question-tbody{
  align-items: center
  }
</style>
