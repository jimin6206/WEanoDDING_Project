<template>
  <div class="editquestion editquestion-font" style="padding-top:5rem">
    <h1>문의사항 수정</h1>
    <form onsubmit="return false;">
      <div style="width:30rem; margin-top:2rem">
        <div class="input-group">
          <span class="input-group-text">문의 제목</span>
          <textarea v-model="state.form.QuestionDetailInfo.qtitle" class="form-control" aria-label="With textarea"></textarea>
        </div>
        <div class="input-group" style="margin-top:0px; height: 15rem;">
          <span class="input-group-text">문의 내용</span>
          <textarea v-model="state.form.QuestionDetailInfo.qcontent" class="form-control" aria-label="With textarea"></textarea>
        </div>
        <div style="margin-top:2rem">
          <button style="float:left" onclick="history.back()" type="button" class="btn btn-outline-danger">취소</button>
          <button style="float:right" class="btn btn-outline-secondary" @click="EditQuestion">수정하기</button>
        </div>
      </div>
    </form>
  </div>
</template>
<script>
import { computed, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
export default {
  name:'edit-question',
  setup() {
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      form: {
        QuestionDetailInfo: computed(() => store.getters['root/getQuestionDetailInfo']),
      },
    })
    const EditQuestion = function () {
      store.dispatch('root/requestEditQuestion',[state.form.QuestionDetailInfo.qnum,
        {
          qtitle: state.form.QuestionDetailInfo.qtitle,
          qcontent: state.form.QuestionDetailInfo.qcontent,
        }
      ])
      .then(function() {
        store.dispatch('root/requestQuestionList')
          .then(function(result) {
            store.commit('root/setQuestionListInfo', result.data)
            router.push({ name:'detailquestion', params: {qNum: state.form.QuestionDetailInfo.qnum }})
          })
          .catch(function(err) {
            console.log(err)
          })
      })
      .catch(function(err) {
        console.log(err)
      })
    }
    return { state, EditQuestion }
  },
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

.editquestion{
  height: 50rem;
  background: #f0ebe7;
  display: flex;
  flex-direction: column;
  align-items: center
}

.editquestion-font{
 font-family: 'Gowun Dodum', sans-serif;
}
</style>
