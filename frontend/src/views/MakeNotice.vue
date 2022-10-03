<template>
  <div class="notice">
    <div id="notice">
      <div>
        <h1 class="makenotice-font" style="padding-top:5rem">공지사항 작성</h1>
      </div>
      <div class="d-flex justify-content-center" >
        <form onsubmit="return false;">

          <div class="input-group questiontitleform" style="width:30rem; margin-top: 2rem;">
            <span class="makenotice-font input-group-text questiontitle">제목</span>
            <textarea v-model="state.form.NoticeTitle" type="text" id="notice_title" class="form-control question-font"></textarea>
          </div>
          <div class="input-group noticecontentform">
            <span class="makenotice-font input-group-text noticecontent">내용</span>
            <textarea v-model="state.form.NoticeContent" type="text" id="notice_content" class="form-control notice-font"></textarea>
          </div>
          <div style="margin-top:2rem">
            <button style="float:left" onclick="history.back()" type="button" class="btn btn-outline-danger">취소</button>
            <button style="float:right;" class="makenotice-font btn btn-outline-secondary" @click="GoMakeNotice">등록하기</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { computed, onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
export default {
  name: 'make-notice',
  setup() {
    const router = useRouter()
    const store = useStore()

    const state = reactive({
      form: {
        NoticeTitle: '',
        NoticeContent: '',
      },
      isadmin: computed(() => store.getters['root/getIsAdmin']),
    })
    onMounted(() => {
      store.commit('root/setIsAdmin')
      if (state.isadmin) {
      } else {
        router.push({ name:'notice'})
      }
    })
    const GoMakeNotice = function() {
      store.dispatch('root/requestMakeNotice',{
        noticeId: localStorage.getItem('companyId'),
        noticeDate: new Date().getFullYear()+'-'+(new Date().getMonth()+1)+'-'+new Date().getDate()+' '+new Date().getHours()+':'+new Date().getMinutes()+':'+new Date().getSeconds(),
        noticeTitle: state.form.NoticeTitle,
        noticeContent: state.form.NoticeContent,
      })
      .then(function () {
        alert('생성완료')
        router.push({ name:'notice'})
      })
      .catch(function (err) {
        console.log(err)
      })
    }
    return { state, GoMakeNotice }
  },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

.makenotice-font{
  font-family: 'Gowun Dodum', sans-serif;
}
#notice{
  background: #f0ebe7;
  display: flex;
  flex-direction: column;
}
.notice{
  height: 1000px;
  background: #f0ebe7;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.noticecontentform{
  height: 500px;
}
.input-group{
  margin-top: 0px;
}
</style>
