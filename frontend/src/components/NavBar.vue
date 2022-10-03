<template>
<div id="mainlogo">

    <div class="navbarlogo" style="width:10rem;">
      <router-link class="link_tag" to="/">웨안오딩</router-link>
    </div>
  <nav id="nav">

      <ul class="navbarmenu">
        <li><router-link class="link_tag" to="/introduce">About us</router-link></li>
        <li><router-link class="link_tag" to="/management">Management</router-link></li>
        <li><router-link class="link_tag" to="/questions">Questions</router-link></li>
        <li><router-link class="link_tag" to="/notice">Notice</router-link></li>
      </ul>

      <div class="navbaraccount">
        <div v-if="!state.isloggedin" >
          <button type="button" class="btnstyle" @click="goSignup">Sign up</button>
          <button type="button" class="btnstyle" @click="goLogin">Login</button>
        </div>
        <div v-if="state.isloggedin">
          <button type="button" class="btnstyle" @click="clickMyPage">My page</button>
          <button type="button" class="btnstyle" @click="clickLogout">logout</button>
        </div>
      </div>
  </nav>
  <div class="line" text-align="center"></div>
</div>
</template>

<script>
import { reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
export default {
  name: 'nav-bar',
  setup() {
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      // 로그인되어있는지 확인
      isloggedin: computed(() => store.getters['root/getIsLoggedIn'])
    })

    // 로그인 체크
    const loginCheck = function() {
      if (!state.isloggedin) {
        alert('로그인이 필요합니다')
        router.push({ name: 'login' })
      }
    }
    // 로그인 체크 끝

    // 홀 정보 받아오기
    const fetchHallsInfo = function() {
      if (state.isloggedin) {
        store.dispatch('root/requestHallsInfo', localStorage.getItem('companyId'))
          .then((result) => {
            store.commit('root/setHallsInfo', result.data)
          })
          .catch(error => {
            console.log(error)
          })
      }
    }
    // 홀 정보 받아오기 끝

    // 로그인되어있는지를 확인
    onMounted(() => {
      store.commit('root/setIsLoggedIn')
      store.commit('root/setIsGuest')
      fetchHallsInfo()
    })
    // 로그인되어있는지를 확인 끝

    // 로그인페이지로 이동
    const goLogin = function () {
      router.push({
        name: 'login'
      })
    }
    // 끝

    // 로그아웃버튼 클릭
    const clickLogout = () => {
      loginCheck()
      if(state.isloggedin){
        localStorage.removeItem('token')
        localStorage.removeItem('companyId')
        store.commit('root/setIsLoggedIn')
        store.commit('root/setIsGuest')
        alert('로그아웃 되었습니다.')
        router.push({ name: 'home'})
      }
    }
    // 끝

    // 회원가입으로 이동
    const goSignup = () => {
      router.push({
        name: 'signup'
      })
    }
    // 끝

    // 마이페이지 클릭
    const clickMyPage = () => {
      loginCheck()
      router.push({ name: 'mypage', params: {companyId: localStorage.getItem('companyId')}})
    }
    // 끝

    return { state, goLogin, clickLogout, goSignup, clickMyPage }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Quicksand:wght@300&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Poor+Story&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

  #nav {
    background: #f0ebe7;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 100px;
  }
  #mainlogo {
    background: #f0ebe7;
    align-items: center;
    position:relative;

  }
  #test{
    font-size: 10px;
    margin-top: 10px;
  }
  .navbarmenu {
    display: flex;
    list-style: none;
    padding-left: 0;
    margin-bottom: 0px;
    font-size: 20px;
    margin-left: 20px;
    margin-right: 20px;
    padding-top: 15px;
  }
  .navbarmenu li {
    display: flex;
    text-decoration: none;
    padding: 8px 12px;
    font-family: 'Quicksand', sans-serif;
  }
  .navbaraccount {
    display: flex;
    padding-left: 0px;
    margin-bottom: 0px;
  }

  .navbarlogo{

    font-family: 'Gowun Dodum', sans-serif;
    font-size: 30px;
    padding-top: 15px;
    position:absolute;
    left: 0;
    right:0;
    margin-right: auto;
    margin-left: auto;

  }
  .link_tag{
    font-weight: bold;
    color: #2c3e50;
    text-decoration: none;

  }
  .link_tag:hover{
    font-weight: bold;
    color: #FF73A5;
    text-decoration: none;

  }
  .btnstyle{

    font-family: 'Quicksand', sans-serif !important;
    font-size: 20px;
    margin-left: 20px;
    margin-right: 20px;

    border: 0px;
    outline: 0px;
    background: #f0ebe7;

  }

  .line{
    margin: auto;
    width: 95%;
    border-bottom: 1px solid #0D1903;
  }
</style>
