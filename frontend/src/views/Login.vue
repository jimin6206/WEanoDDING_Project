<template>
<div class = "login">

  <div class="loginform">
    <p class="loginTitle">LOGIN</p>
    <form onsubmit="return false;">
      <label for="login-id"></label>
      <input class="form-control" type="text" id="login-id" placeholder="id" v-model="state.form.companyId">
      <label for="login-password"></label>
      <input class="form-control" type="password" id="login-password" placeholder="password" v-model="state.form.companyPassword">
      <br>
      <button class="btn btnLogin btn btn-outline-secondary" @click="clickLogin">로그인</button>
      <p style="margin-top:1rem" class="movesignup">Not a member yet?  <router-link class="link_tag" to="/signup">회원가입</router-link></p>
    </form>
  </div>

</div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
export default {
  name: 'login-view',
  setup() {
    const router = useRouter()
    const store = useStore()
    const loginForm = ref(null)

    const state = reactive({
      form: {
        companyId: '',
        companyPassword: '',
      },
    })

    onMounted(() => {
      if (localStorage.getItem('token')) {
        alert('이미 로그인 되어 있습니다')
        router.back()
      }
    })

    // 홀 정보 받아오기
    const fetchHallsInfo = function() {
      store.dispatch('root/requestHallsInfo', localStorage.getItem('companyId'))
        .then((result) => {
          store.commit('root/setHallsInfo', result.data)
        })
        .catch(error => {
          console.log(error)
        })
    }
    // 홀 정보 받아오기 끝

    const clickLogin = function () {
      // 로그인 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      store.dispatch('root/requestLogin', { companyId: state.form.companyId, companyPassword: state.form.companyPassword })
      .then(function (result) {
          if (result.data.accessToken) {
          localStorage.setItem('token', result.data.accessToken)
          localStorage.setItem('companyId', state.form.companyId)
          store.commit('root/setIsLoggedIn')
          store.commit('root/setIsGuest')
          fetchHallsInfo()
          router.push({
            name: 'home'
          })
        }
      })
      .catch(function (err) {
        alert(err)
      })
    }

    return { loginForm, state, clickLogin }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Quicksand:wght@300&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Fjalla+One&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

  .login{
    height: 600px;
    background: #f0ebe7;
    position:relative;


  }

  .loginform{
    position:absolute;
    left:0; right:0; margin-left:auto; margin-right:auto;
    top:50px;
    width: 400px;
    height: 500px;
  }

  .loginTitle{
    margin-top: 30px;
    margin-bottom: 20px;
    font-size: 50px;
    font-family: 'Gowun Dodum', sans-serif;
  }

  .form-control{

    left:0; right:0; margin-left:auto; margin-right:auto;
    width: 300px;
    height: 50px;
  }

  .btnLogin{
    width: 300px;
    height: 50px;
    font-family: 'Gowun Dodum', sans-serif;
  }
  .movesignup{
    margin-top: 5px;
    color: gray;
    font-size: 17px;
    font-family: 'Gowun Dodum', sans-serif;
  }

  .link_tag{
    text-decoration: none;
    color: black;
  }

</style>
