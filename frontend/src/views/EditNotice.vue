<template>
<div class="editnotice editnotice-font" style="padding-top:5rem">
    <h1>공지사항 수정</h1>
    <form onsubmit="return false;">
      <div style="width:30rem; margin-top:2rem">
        <div class="input-group">
          <span class="input-group-text">공지 제목</span>
          <textarea v-model="state.form.NoticeDetailInfo.noticeTitle" class="form-control" aria-label="With textarea"></textarea>
        </div>
        <div class="input-group" style="margin-top:0px; height: 15rem;">
          <span class="input-group-text">공지 내용</span>
          <textarea v-model="state.form.NoticeDetailInfo.noticeContent" class="form-control" aria-label="With textarea"></textarea>
        </div>
        <div style="margin-top:2rem">
          <button style="float:left" onclick="history.back()" type="button" class="btn btn-outline-danger">취소</button>
          <button style="float:right" class="btn btn-outline-secondary" @click="EditNotice">수정하기</button>
        </div>
      </div>
    </form>
  </div>
</template>
<script>
import { computed, onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
export default {
  name:'edit-notice',
  setup() {
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      form: {
        NoticeDetailInfo: computed(() => store.getters['root/getNoticeDetailInfo']),
      },
      isadmin: computed(() => store.getters['root/getIsAdmin']),
    })
    const EditNotice = function () {
      store.dispatch('root/requestEditNotice',[state.form.NoticeDetailInfo.noticeNum,
        {
          noticeTitle: state.form.NoticeDetailInfo.noticeTitle,
          noticeContent: state.form.NoticeDetailInfo.noticeContent,
        }
      ])
      .then(function() {
        store.dispatch('root/requestNoticeList')
          .then(function(result) {
            store.commit('root/setNoticeListInfo', result.data)
            router.push({ name:'detailnotice', params: {noticeNum: state.form.NoticeDetailInfo.noticeNum }})
          })
          .catch(function(err) {
            console.log(err)
          })
      })
      .catch(function(err) {
        console.log(err)
      })
    }
    onMounted(() => {
      store.commit('root/setIsAdmin')
      if (state.isadmin) {
      } else {
        router.push({ name:'notice'})
      }
    })
    return { state, EditNotice }
  },
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

.editnotice{
  height: 50rem;
  background: #f0ebe7;
  display: flex;
  flex-direction: column;
  align-items: center
}

.editnotice-font{
 font-family: 'Gowun Dodum', sans-serif;
}
</style>
