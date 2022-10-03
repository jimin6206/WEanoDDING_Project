<template>
	<div class="signup">
    <div class="signupform">
      <h1 class="signuptitle" >SIGN UP</h1>
      <br>
      <form onsubmit="return false;">
        <div class="mb-4">
          <div class="input-group d-flex">
            <input class="form-control inputBnt2" type="text" id="signup-id" placeholder="id" v-model="state.form.companyId" @input="signupBtnDisable">
            <button class="btn btnCheck btn-outline-secondary" @click="clickIdCheck">중복체크</button>
          </div>
          <p class="mt-0" id="Idcheck" v-if="!state.idOk && state.form.companyId != ''" style="margin-top:1rem">아이디 중복체크를 해주세요</p>
        </div>
      </form>
      <form onsubmit="return false;">
        <input class="form-control inputBnt" placeholder="password" type="password" id="signup-password" v-model="state.form.companyPassword" @input="passwordCheck">
        <p v-if="!state.passwordOk && state.form.companyPassword != ''">비밀번호는 9~16자리, 대소문자와 특수문자가 사용되어야 합니다</p>
        <br>
        <input class="form-control inputBnt" placeholder="confirm password" type="password" id="signup-password2" v-model="state.form.companyPassword2" @input="password2Check">
        <p v-if="!state.password2Ok && state.form.companyPassword2 != ''">비밀 번호가 일치하지 않습니다</p>
        <br>
        <input class="form-control inputBnt" placeholder="company name" type="text" id="signup-companyname" v-model="state.form.companyName" @input="companyNameCheck">
        <p v-if="!state.companyNameOk" style="color:#3CB371">회사이름은 최대 16자리까지만 가능합니다 <br/>회사 이름이 16자리가 넘는 경우는 전화로 문의해주세요</p>
        <br>
        <input class="form-control inputBnt" placeholder="company number" type="text" id="signup-companynum" v-model="state.form.companyTelnum" @input="companyTelnumCheck">
        <p v-if="!state.companyTelnumOk" style="color:#3CB371">회사 전화번호를 정확히 입력해주세요</p>
        <br>
        <button class="btn btnsignup btn-outline-secondary" @click="clickSignup" :disabled="state.disabled">회원가입</button>
      </form>
    </div>
	</div>
</template>
<script>
import { ref, reactive, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'signup-view',
  setup() {
		const router = useRouter()
    const store = useStore()
    const signupForm = ref(null)
    const state = reactive({
      form: {
        companyId: '',
        companyPassword: '',
        companyPassword2: '',
        companyName: '',
        companyTelnum: '',
      },
      // rules: {
      //   companyId: [
      //     { required: true, message: 'Please input ID', trigger: 'blur' },
      //     {max: 16, message: "id 최대 16자까지", trigger: 'blur'},
      //   ],
      //   companyPassword: [
      //     { required: true, message: 'Please input password', trigger: 'blur' },
      //     {min:9, max: 16, message: "password 최소 9자에서 최대 16자까지", trigger: 'blur'},
      //     {validate: (rule, value, callback) => {
      //       let patternEngAtListOne = new RegExp(/[a-zA-Z]+/);
      //       let patternSpeAtListOne = new RegExp(/[~!@#$%^&*()_+|<>?:{}`]+/);
      //       let patternNumAtListOne = new RegExp(/[0-9]+/);

      //       let reg =
      //       patternEngAtListOne.test(value) &&
      //       patternSpeAtListOne.test(value) &&
      //       patternNumAtListOne.test(value);
      //         if (!reg) {
      //           callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.'))
      //         }
      //       },
      //       trigger: 'blur'
      //     }
      //   ],
      //   companyPassword2: [
      //     { required: true, message: 'please enter the same password again.', trigger: 'blur' },
      //     {min:9, max: 16, message: "password 최소 9자에서 최대 16자까지", trigger: 'blur'},
      //     {validate: (rule, value, callback) => {
      //       let patternEngAtListOne = new RegExp(/[a-zA-Z]+/);
      //       let patternSpeAtListOne = new RegExp(/[~!@#$%^&*()_+|<>?:{}`]+/);
      //       let patternNumAtListOne = new RegExp(/[0-9]+/);

      //       let reg =
      //       patternEngAtListOne.test(value) &&
      //       patternSpeAtListOne.test(value) &&
      //       patternNumAtListOne.test(value);
      //         if (!reg) {
      //           callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.'))
      //         }
      //         let reg2 = value == state.form.companyPassword
      //         if(!reg2) {
      //           callback(new Error('please enter the same password again.'))
      //         }
      //       },
      //       trigger: 'blur'
      //     },
      //   ],
      //   companyName: [
      //     { required: true, message: 'Please input CompanyName', trigger: 'blur' },
      //     {max: 16, message: "id 최대 16자까지", trigger: 'blur'},
      //   ],
      //   companyTelnum: [
      //     { required: true, message: 'Please input CompanyTelnum', trigger: 'blur' },
      //     {max: 16, message: "id 최대 16자까지", trigger: 'blur'},
      //   ],
      // },
      disabled: 'disabled',
      // 커스텀 유효성 검사
      idOk: false,
      passwordOk: false,
      password2Ok: false,
      companyNameOk: false,
      companyTelnumOk: false,
    })

    onMounted(() => {
      console.log(signupForm.value)
    })

    // 회원가입버튼 활성화
    const disabledCheck = function() {
      if (state.idOk && state.passwordOk && state.password2Ok && state.companyNameOk && state.companyTelnumOk) {
        state.disabled = false
      } else {
        state.disabled = 'disabled'
      }
    }

    // ID 중복체크
    const clickIdCheck = () => {
      store.dispatch('root/requestIdCheck', state.form.companyId)
      .then(result => {
        if (result.status === 200) {
          alert('사용가능한 ID입니다')
          state.idOk = true
          // 만약 회원가입이 가능하다면 버튼 활성화
          disabledCheck()
        }
      })
      .catch(err => {
        state.idOk = false
        if (err.response.status === 409) {
          alert('이미 존재하는 ID입니다')
        }else if (err.response.status === 401) {
          alert('인증에 실패하였습니다')
        }
        state.disabled = 'disabled'
      })
    }

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
      if (9 <= state.form.companyPassword.length && state.form.companyPassword.length <= 16 && passwordCheckFunc(state.form.companyPassword, upperChar) && passwordCheckFunc(state.form.companyPassword, lowerChar) && passwordCheckFunc(state.form.companyPassword, specialChar) && passwordCheckFunc(state.form.companyPassword, numberChar)) {
        state.passwordOk = true
        // console.log('password', true)
      } else {
        state.passwordOk = false
        // console.log('password', false)
      }
      // 첫번째를 바꿀때 두번째도 확인은 해야함
      if (state.form.companyPassword2 !== state.form.companyPassword) {
        state.password2Ok = false
        // console.log('password2', false)
      }
      disabledCheck()
    }

    // 두번째 비밀번호 유효성 검사
    const password2Check = function () {
      if (state.form.companyPassword2 === state.form.companyPassword && 9 <= state.form.companyPassword2.length && state.form.companyPassword2.length <= 16 && passwordCheckFunc(state.form.companyPassword2, upperChar) && passwordCheckFunc(state.form.companyPassword2, lowerChar) && passwordCheckFunc(state.form.companyPassword2, specialChar) && passwordCheckFunc(state.form.companyPassword2, numberChar)) {
        state.password2Ok = true
        // console.log('password2', true)
      } else {
        state.password2Ok = false
        // console.log('password2', false)
      }
      disabledCheck()
    }

    // 회사이름 유효성 검사(?, 회사이름을 16자로 제한?)
    const companyNameCheck = function() {
      if (state.form.companyName.length <= 16 && state.form.companyName.length > 0) {
        state.companyNameOk = true
      } else {
        state.companyNameOk = false
      }
      disabledCheck()
    }

    // 회사이름 유효성 검사(?, 회사이름을 16자로 제한?)
    const companyTelnumCheck = function() {
      if (state.form.companyTelnum.length <= 16 && state.form.companyTelnum.length > 0) {
        state.companyTelnumOk = true
      } else {
        state.companyTelnumOk = false
      }
      disabledCheck()
    }

    // 아이디 변경하면 다시 아이디 체크하도록
    const signupBtnDisable = function() {
      state.idOk = false
      disabledCheck()
    }


    // 회원가입 버튼 클릭
    const clickSignup = function () {
      // 로그인 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      // console.log('submit')
      store.dispatch('root/requestSignup', { companyId: state.form.companyId, companyName: state.form.companyName,companyTelnum: state.form.companyTelnum, companyPassword: state.form.companyPassword})
      .then(function () {
        alert("회원가입이 되었습니다")
        router.push({name : 'login'});
      })
      .catch(function (err) {
        alert(err)
      })
    }


    return { signupForm, state, clickSignup, clickIdCheck, passwordCheck, password2Check, companyNameCheck, companyTelnumCheck, signupBtnDisable }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Quicksand:wght@300&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Fjalla+One&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');
  .signup{
    height: 55rem;
    background: #f0ebe7;
  }
  .signupform{
    position:absolute;
    left:0; right:0; margin-left:auto; margin-right:auto;
    top:150px;
    width: 500px;


  }
  .signuptitle{
    margin-top: 30px;
    margin-bottom: 20px;
    font-size: 50px;
    font-family: 'Gowun Dodum', sans-serif;
  }
  .form-control{

    left:0; right:0; margin-left:auto; margin-right:auto;

  }
  .inputBnt{
    width: 300px;
    height: 50px;
  }
  .inputBnt2{
    width: 200px;
    height: 50px;
  }
  .input-group{
    left:0; right:0; margin-left:auto; margin-right:auto;
    width: 300px;
    height: 50px;
  }

  .btnCheck{
    margin-bottom: 5px;
    width: 100px;
    height: 50px;
    margin-bottom: 20px;
    font-family: 'Gowun Dodum', sans-serif;

  }
  .btnsignup{
    width: 200px;
    height: 50px;
    margin-bottom: 50px;
    font-family: 'Gowun Dodum', sans-serif;
  }
  form p {
    color: red;
    font-family: 'Gowun Dodum', sans-serif;
    font-size: 13px;
  }



</style>
