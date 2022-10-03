<template>
  <div class="detailquestion">
    <h1 class="detailquestion-font" style="padding-top:5rem;">상세 문의사항</h1>

    <div style="width:30rem">
      <div class="input-group" style="margin-top: 2rem">
        <span class="input-group-text" id="basic-addon1">작성 업체</span>
        <div class="card detailquestion-font">
          <div class="card-body" style="width:24rem">
            {{ state.form.QuestionDetailInfo.companyId }}
          </div>
        </div>
      </div>
      <div class="input-group" style="margin-top:0px">
        <span class="input-group-text" id="basic-addon1">작성 시간</span>
        <div class="card detailquestion-font">
          <div class="card-body" style="width:24rem">
            {{ state.form.QuestionDetailInfo.qdate }}
          </div>
        </div>
      </div>
      <div class="input-group" style="margin-top: 0px;">
        <span class="input-group-text">문의 제목</span>
        <div class="card detailquestion-font">
          <div class="card-body" style="width:24rem">
            {{ state.form.QuestionDetailInfo.qtitle }}
          </div>
        </div>
      </div>
      <div class="input-group" style="margin-top:0px">
        <span class="input-group-text ">문의 내용</span>
        <div class="card">
          <div class="card-body detailquestion-font" style="width:24rem">
            {{ state.form.QuestionDetailInfo.qcontent }}
          </div>
        </div>
      </div>
    </div>
    <div v-if="ismyquestion()" style="margin-top:2rem; width: 30rem;">
      <div>
        <button style="float:left" onclick="history.back()" type="button" class="btn btn-outline-secondary">뒤로</button>
      </div>
      <div style="width: 15rem; float: right;" >
        <button class="btn btn-outline-secondary detailquestion-font" @click="clickDelteQuestion">삭제하기</button>
        <button class="btn btn-outline-secondary detailquestion-font" @click="clickgoEditQuestion" style="float:right;">수정하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { computed, onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
export default {
  name:'detail-notice',
  setup() {
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      form: {
        QuestionDetailInfo: computed(() => store.getters['root/getQuestionDetailInfo']),
      },
    })
    const clickgoEditQuestion = function () {
      router.push({name:'editquestion', params: {qNum: state.form.QuestionDetailInfo.qnum}})
    }
    const clickDelteQuestion = function () {
      if (confirm('진짜 삭제하시겠습니까?')) {
        store.dispatch('root/requestDeleteQuestion', state.form.QuestionDetailInfo.qnum)
        .then(function() {
          store.dispatch('root/requestQuestionList')
          .then(function(result) {
            store.commit('root/setQuestionListInfo', result.data)
          })
          .catch(function(err) {
            console.log(err)
          })
          router.push({ name: 'questions' })
        })
        .catch(function(err) {
          console.log(err)
        })
      }
    }
    onMounted (() => {
      if (ismyquestion()) {
      } else {
        router.push({name:'questions'})
        alert('본인이 작성한 문의사항만 조회 가능합니다.')
      }
    })
    const ismyquestion = function () {
      if (localStorage.getItem('companyId') === state.form.QuestionDetailInfo.companyId || localStorage.getItem('companyId') === 'admin') {
        return true
      } else {
        return false
      }
    }
    return { state, clickgoEditQuestion, clickDelteQuestion, ismyquestion }
  },
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

.detailquestion{
  height: 50rem;
  background: #f0ebe7;
  display: flex;
  flex-direction: column;
  align-items: center
}

.detailquestion-font{
  font-family: 'Gowun Dodum', sans-serif;
}
</style>
