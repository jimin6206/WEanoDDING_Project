<template>
  <div class="question">
    <div id="question">
    <h1 class="question-font" style="margin-top: 5rem">문의사항</h1>
    <div class="question-write">
        <div class="input-group mb-3" style="width:20rem; margin-top: 3rem; float: left;">
          <input  v-model="state.form.QuestionName" id = search type="text" class="form-control question-font" placeholder="제목으로 검색하세요" aria-label="Recipient's username" aria-describedby="button-addon2">
          <button @click="clickQuestionSearch" class="btn btn-outline-secondary question-font" type="button" id="button-addon2">Search</button>
        </div>
        <div>
        <router-link style="margin-bottom:1rem" class="btn btn-outline-secondary question-font" v-if="state.isloggedin" :to="{ name:'makequestion'}">글쓰기</router-link>
        </div>
    </div>
    <div style="margin-top:1rem;">
      <table class="table table-hover question-font" style="table_border">
        <thead style="background-color: #F6F6F6">
          <tr>
            <th scope="col" style="width:3rem">연번</th>
            <th scope="col">작성자</th>
            <th scope="col">제목</th>
          </tr>
        </thead>
        <tbody v-for="question in state.QuestionList" :key="question" class="question-tbody" style="border-top:0px solid black;border-bottom:0.01px solid black; ">
          <tr style="cursor:pointer" @click="DetailQuestionInfo(question)">
            <th>{{question.qnum}} </th>
            <th>{{question.companyId}} </th>
            <th >{{ question.qtitle }}</th>
          </tr>
        </tbody>
        <br/>
      </table>
    </div>
  </div>
  </div>
</template>

<script>
import { computed, onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
export default {
  name: 'questions-view',
  setup() {
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      form : {
        QuestionName : '',
      },
      QuestionList : computed(() => store.getters['root/getQuestionListInfo']),
      isadmin : false,
      isloggedin : computed(() => store.getters['root/getIsLoggedIn']),
    })
    onMounted(() => {
      store.commit('root/setLoggedIn')
      store.dispatch('root/requestQuestionList')
      .then(function(result) {
        // console.log(result)
        store.commit('root/setQuestionListInfo', result.data)
      })
      .catch(function(err) {
        console.log(err)
      })
    })
    const DetailQuestionInfo = function (question) {
      store.commit('root/setQuestionDetailInfo', question)
      router.push({ name:'detailquestion', params: { qNum: question.qnum }})
    }
    const clickQuestionSearch = function () {
      store.dispatch('root/requestNameQuestion', state.form.QuestionName)
      .then(function (result) {
        store.commit('root/setQuestionListInfo', result.data)
        state.form.QuestionName=''
      })
      .catch(function (err) {
        console.log(err)
      })
    }
    return { state, DetailQuestionInfo, clickQuestionSearch }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

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
.table{
  width : 48rem;
}
.question-font{
  font-family: 'Gowun Dodum', sans-serif;
}
.btnque{
    background-color: #0D1903;
    width: 150px;
    height: 40px;
}
.question-write{
  width: 48rem;
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
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
