<template>
  <div class="mypage mypage-font">
    <!-- Button trigger modal end -->
      <h1 class="mypagetitle" style="padding-top:5rem">마이페이지</h1>
      <div class="loginwedding">
        <div class="infoLogin" style="width:25%">
          <h3 class="mypage-font">로그인 정보</h3>
          <div class="input-group mb-3 row" style="margin-top:2rem">
            <span class="input-group-text col-sm-2 col-form-label mypageinfo" style="justify-content: center; width: 7rem;">아이디</span>
            <span type="text" class="form-control a2" id="id">{{ state.companyInfo.companyId }}</span>
          </div>
          <div class="input-group mb-3 row">
            <span class="input-group-text col-sm-2 col-form-label mypageinfo" style="justify-content: center; width: 7rem;">비밀번호</span>
            <input type="password" class="form-control a" placeholder="새 비밀번호" @input="inputNewCompanyPassword">
          </div>
          <div class="input-group mb-3 row">
            <span class="input-group-text col-sm-2 col-form-label mypageinfo" style="justify-content: center; width: 7rem;">업체이름</span>
            <input type="text" class="form-control a" :value="state.companyInfo.companyName" @input="inputNewCompanyName">
          </div>
          <div class="input-group mb-3 row">
            <span class="input-group-text col-sm-2 col-form-label mypageinfo" style="justify-content: center; width: 7rem;">업체전화번호</span>
            <input type="text" class="form-control a" :value="state.companyInfo.companyTelnum" @input="inputNewCompanyTelnum">
          </div>
        <!-- Button trigger modal -->
        <div style="margin-top:2rem">
          <button class="btn btnmypage btn-outline-secondary" @click="clickChangeCompany">수정하기</button>
          <button class="btn btnmypage btn-outline-secondary" type="button" data-bs-toggle="modal" data-bs-target="#quitModal">
          회원탈퇴
          </button>
          <button class="btn btn-outline-danger" @click="clickMyPageCancel" style="margin-left:0.5rem">취소</button>
        </div>
      </div>

      <div class="mypage-font infoweddinsg" style="width:40%">
        <h3>웨딩홀 정보</h3>
        <div class="mb-3 row  justify-content-center" style="margin-top:2rem">
          <label class="col-sm-2 col-form-label halladd" for="hall">홀 정보</label>
          <input class="form-control b" type="text" id="hall" v-model="state.newHallName">
          <button class="btn btnhalladd btn-outline-secondary" @click="clickHallPlus">추가</button><br/>
        </div>
        <div class="d-flex justify-content-center hallcenter">
          <div class="halloutline" style="background-color:#f0ebe7; margin-top:0px; overflow-y: scroll; width: 530px; height:14rem;">
            <ul class="halllist" v-for="hall in state.hallsInfo" :key="hall">
              <li class="hallname">{{ hall.hallName }}
              <button class="btn btnhalldelete btn-outline-secondary" @click="clickDeleteHall(hall.hallName)">삭제</button></li>
            </ul>
          </div>
        </div>
      </div>

    </div>



    <!-- Modal -->
    <div class="modal fade" id="quitModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">회원탈퇴 확인</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            정말로 탈퇴하시겠습니까?
            홀의 정보 및 지금까지 진행했던 결혼식, 하객의 정보 등 회사와 관련된 모든 정보가 삭제됩니다.
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">아니오</button>
            <button type="button" class="btn btn-danger" data-bs-dismiss="modal" @click="clickQuitBtn">네, 탈퇴하겠습니다</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { computed, reactive, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'mypage-view',
  setup() {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      newHallName: '',
      companyInfo: computed(() => store.getters['root/getCompanyInfo']),
      hallsInfo: computed(() => store.getters['root/getHallsInfo']),
      form: {
        newCompanyName: '',
        newCompanyTelnum: '',
        newCompanyPassword: '',
      },
    })

    // 취소버튼 클릭시
    const clickMyPageCancel = function() {
      router.push({name: 'home'})
    }

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

    // 회사 정보 받아오기
    const fetchCompanyInfo = function() {
      store.dispatch('root/requestMyPage', localStorage.getItem('companyId'))
        .then(function(result) {
          store.commit('root/setCompanyInfo', result.data)
        })
        .catch(function (err) {
          alert(err)
        })
    }
    // 회사 정보 받아오기 끝


    // 홀 이름중복 확인하기 (중복되면 true)
    const hallNameCheck = function(newHallName) {
      if (newHallName.trim() === '') {
        return true
      }
      for (const hall of state.hallsInfo) {
        if (hall.hallName === newHallName) {
          return true
        }
      }
      return false
    }
    // 홀 이름중복 확인하기 끝

    // 홀 추가하기
    const clickHallPlus = function() {
      // 중복확인 후 홀 추가
      if (hallNameCheck(state.newHallName)) {
        alert('홀 이름이 비어있거나 이미 있는 홀입니다')
        state.newHallName = ''
      } else {
        store.dispatch('root/requestHallPlus', { companyId: localStorage.getItem('companyId'), hallName: state.newHallName})
        .then(() => {
          fetchHallsInfo()
          state.newHallName = ''
        })
        .catch((error) => {
          console.log(error)
        })
      }
    }
    // 홀 추가하기 끝

    // 홀 삭제하기
    const clickDeleteHall = function(hallName) {
      store.dispatch('root/requestHallDelete', `${localStorage.getItem('companyId')}/${hallName}`)
        .then(() => {
          fetchHallsInfo()
        })
        .catch(error => {
          console.log(error)
        })
    }
    // 홀 삭제하기 끝

    // 회원탈퇴
    const clickQuitBtn = function () {
      store.dispatch('root/requestQuit', localStorage.getItem('companyId'))
        .then(() => {
          localStorage.removeItem('token')
          localStorage.removeItem('companyId')
          store.commit('root/setIsLoggedIn')
          alert('탈퇴가 정상적으로 진행되었습니다')
          router.push({name: 'home'})
        })
        .catch(error => {
          alert(error)
        })
    }
    // 회원탈퇴 끝

    // 비밀번호 유효성 검사에 쓰일 문자열
    const upperChar = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
    const lowerChar = 'abcdefghijklmnopqrstuvwxyz'
    const numberChar = '1234567890'
    const specialChar = '`~!@#$%^&*()_+-=[]{}|;:,.<>/?""\'\\'

    // 비밀번호 문자열 포함 확인 함수
    const passwordCheckFunc = function (password, checkChar) {
      for (let char of password){
        if (checkChar.includes(char)) {
          return true
        }
      }
      return false
    }

    // 첫번째 비밀번호 유효성 검사
    const passwordCheck = function () {
      if (9 <= state.form.newCompanyPassword.length && state.form.newCompanyPassword.length <= 16 && passwordCheckFunc(state.form.newCompanyPassword, upperChar) && passwordCheckFunc(state.form.newCompanyPassword, lowerChar) && passwordCheckFunc(state.form.newCompanyPassword, specialChar) && passwordCheckFunc(state.form.newCompanyPassword, numberChar)) {
        return true
      } else {
        return false
      }
    }

    //companyName null아닌지 검사
    const companyNameCheck = function () {
      if (state.form.newCompanyName.length > 0 ) {
        return true
      } else {
        return false
      }
    }

    //companyTelnum null아닌지 검사
    const companyTelnumCheck = function () {
      if (state.form.newCompanyTelnum.length > 0 ) {
        return true
      } else {
        return false
      }
    }


    // 로그인 비밀번호 수정
    const clickChangeCompany = function() {
      if (passwordCheck() && companyNameCheck() && companyTelnumCheck() ) {
        store.dispatch('root/requestChangeInfo', [localStorage.getItem('companyId'), {companyName: state.form.newCompanyName, companyPassword: state.form.newCompanyPassword, companyTelnum: state.form.newCompanyTelnum}])
          .then(() => {
            alert('변경성공');
            fetchCompanyInfo()
            router.push({name: 'home'})
          })
          .catch((error) => {
            console.log(error)
          })
      } else {
        alert('형식이 옳지않습니다.')
      }
    }
    // 로그인 비밀번호 수정 끝

    // 회사이름 수정하기 폼 내용 적용
    const inputNewCompanyName = function(event) {
      state.form.newCompanyName = event.target.value
    }
    // 회사이름 수정하기 폼 내용 적용 끝

    // 회사전화번호 수정하기 폼 내용 적용
    const inputNewCompanyTelnum = function(event) {
      state.form.newCompanyTelnum = event.target.value
    }
    // 회사전화번호 수정하기 폼 내용 적용 끝

    // 로그인 비밀번호 수정하기 폼 내용 적용
    const inputNewCompanyPassword = function(event) {
      state.form.newCompanyPassword = event.target.value
    }
    // 로그인 비밀번호 수정하기 폼 내용 적용 끝

    // 마이페이지로 들어오면 정보들 받아오러 감
    onMounted(() => {
      if (localStorage.getItem('token')) {
        fetchCompanyInfo()
        fetchHallsInfo()
      } else {
        alert('로그인이 필요합니다')
        router.push({ name: 'login' })
      }
    })
    // 마이페이지로 들어오면 정보들 받아옴 끝


    return { state, clickHallPlus, clickDeleteHall, clickQuitBtn, inputNewCompanyName, inputNewCompanyTelnum, inputNewCompanyPassword, clickChangeCompany, clickMyPageCancel }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Quicksand:wght@300&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Fjalla+One&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

  .mypage-font{
    font-family: 'Gowun Dodum', sans-serif;
  }

  .mypage{
    height: 50rem;
    background: #f0ebe7;
    display: flex;
    justify-content: flex-start;
    flex-direction: column
  }
  .full-screen{
    margin: 20px;
  }
  .mypagetitle{
    margin-top: 50px;
    margin-bottom: 50px;
    font-size: 35px;
    font-family: 'Gowun Dodum', sans-serif;
  }
  .a2{
    width: 200px;
    height: 50px;
  }
  .a{
    font-size: 15px;
    width: 200px;
    height: 50px;
  }
  .b{
    font-size: 15px;
    width: 100px;
    height: 40px;
  }
  .halladd{
    font-size: 20px;
    font-family: 'Gowun Dodum', sans-serif;
  }
  #id{
    font-size: 20px;
  }
  .btnhalladd{
    margin-left:10px;
    width: 70px;
    height: 40px;
    font-family: 'Gowun Dodum', sans-serif;
  }
  .btnhalldelete{
    margin-left:10px;
    width: 70px;
    height: 40px;
    font-family: 'Gowun Dodum', sans-serif;
  }
  .hallname{
    font-size:15px;
    margin-top: 15px;
  }
  .btnmypage{
    margin-left:10px;
    width: 120px;
    height: 40px;
    font-family: 'Gowun Dodum', sans-serif;
  }
  .halloutline{
    border-radius: 30px;
    width: 300px;

  }
  .halllist{
    list-style: none;
  }
  .hallcenter{
    margin-bottom: 20px;
  }
  .loginwedding{
    display: flex;
    justify-content: center;
  }
  .infowedding{
    display: flex;
  }

</style>
