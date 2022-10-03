<template>
  <div class="question">
    <div id="question">
      <h1 class="makequestion-font" style="padding-top:5rem">문의사항 작성</h1>
      <div class="d-flex justify-content-center" >
        <form onsubmit="return false;">

          <div class="input-group questiontitleform" style="width:30rem; margin-top: 2rem;">
            <span class="makequestion-font input-group-text questiontitle">제목</span>
            <textarea v-model="state.form.QuestionTitle" type="text" id="question_title" class="form-control question-font"></textarea>
          </div>
          <div class="input-group questioncontentform" style="margin-top:0px">
            <span class="makequestion-font input-group-text questioncontent">내용</span>
            <textarea v-model="state.form.QuestionContent" type="text" id="question_content" class="form-control question-font"></textarea>
          </div>
          <div>
            <button style="float:left; margin-top:2rem" onclick="history.back()" type="button" class="btn btn-outline-danger">취소</button>
            <button style="float:right; margin-top:2rem" class="makequestion-font btn btn-outline-secondary" @click="GoMakeQuestion">등록하기</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
export default {
  name:'make-question',
  setup() {
    const router = useRouter()
    const store = useStore()

    const state = reactive({
      form: {
        QuestionTitle: '',
        QuestionContent: '',
      }
    })
    const GoMakeQuestion = function() {
      store.dispatch('root/requestMakeQuestion',{
        companyId: localStorage.getItem('companyId'),
        qdate: new Date().getFullYear()+'-'+(new Date().getMonth()+1)+'-'+new Date().getDate()+' '+new Date().getHours()+':'+new Date().getMinutes()+':'+new Date().getSeconds(),
        qtitle: state.form.QuestionTitle,
        qcontent: state.form.QuestionContent,
      })
      .then(function () {
        alert('생성완료')
        router.push({ name:'questions'})
      })
      .catch(function (err) {
        console.log(err)
      })
    }
    return { state, GoMakeQuestion }
  },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

.makequestion-font{
  font-family: 'Gowun Dodum', sans-serif;
}

#qustion{
  background: #f0ebe7;
  display: flex;
}
.question{
  height: 1000px;
  background: #f0ebe7;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.questioncontentform{
  height: 500px;
}
</style>
