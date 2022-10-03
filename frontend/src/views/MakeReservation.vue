<template>
	<div class="makereservation">
    <div>
      <h1 class="makereservation-font" style="padding-top:5rem">결혼식 예약하기</h1>
      <form onsubmit="return false;">
        <h3 class="makereservation-font" style="margin-top:10px; display:inline">예약 날짜 : </h3>
        <h2 class="makereservation-font" style="display:inline">{{ state.form.weddingdate }}</h2>

        <div class="input-group mb-3" style="width:18rem; margin:auto; margin-top:1rem">
					<span class="input-group-text" id="basic-addon1">시간</span>
					<input type="time" v-model="state.form.weddingdatetime" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1">
				</div>

        <h4 class="makereservation-font" style="margin-top:2rem">예식장을 선택하세요</h4>
				<div style="width:18rem; margin:auto;">
					<select v-model="state.form.weddinghall" class="form-select " aria-label="Default select example">
						<option class="makereservation-font" v-for="hall in state.hallinfo" :key="hall">{{hall.hallName}}</option>
					</select>
				</div>

        <h4 class="makereservation-font" style="margin-top:2rem">신랑의 정보를 입력하세요</h4>
				<div style="width:18rem; margin:auto;">
  					<input type="text" placeholder="신랑 성함" v-model="state.form.weddingmanname" class="form-control" id="formGroupExampleInput">
				</div>
				<div style="width:18rem; margin:auto;">
  					<input type="text" placeholder="신랑 번호" v-model="state.form.weddingmantelnum" class="form-control" id="formGroupExampleInput" >
				</div>

        <h4 class="makereservation-font" style="margin-top:2rem">신부의 정보를 입력하세요</h4>
				<div style="width:18rem; margin:auto;">
  					<input type="text" placeholder="신부 성함" v-model="state.form.weddingwomanname" class="form-control" id="formGroupExampleInput">
				</div>
				<div style="width:18rem; margin:auto;">
  					<input type="text" placeholder="신부 번호" v-model="state.form.weddingwomantelnum" class="form-control" id="formGroupExampleInput" >
				</div>

        <div class="mb-3">
					<h4 class="makereservation-font" style="margin-top:2rem">사진을 올려주세요</h4>
					<input style="width:18rem;" class="form-control" type="file" id="formFile" multiple accept="image/*" @change="inputImageChange">
				</div>

        <div style="width:18rem; margin-top:2rem">
          <button style="float:left" onclick="history.back()" type="button" class="btn btn-outline-danger">취소</button>
          <button style="float:right;" class="btn btnReservation btn-outline-secondary" @click="clickMake">예약하기</button>
        </div>

      </form>
    </div>
	</div>
</template>

<script>
import { computed, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
// import axios from 'axios'
export default {
	name: 'make-reservation',
	setup() {
		const router = useRouter()
		const store = useStore()
		const state = reactive({
			form: {
				weddingdatetime: '',
				weddingdate: computed(() => store.getters['root/getWeddingDateInfo']),
				weddinghall: '',
				weddingmanname: '',
				weddingwomanname: '',
				weddingmantelnum: '',
				weddingwomantelnum: '',
        weddingImages: '',
			},
			hallinfo: computed(() => store.getters['root/getHallsInfo']),
		})
		const clickMake = function () {
			if (!!state.form.weddingdatetime && !!state.form.weddinghall && !!state.form.weddingmanname && !!state.form.weddingmantelnum && !!state.form.weddingwomanname && !!state.form.weddingwomantelnum) {
				store.dispatch('root/requestMakeReservation', {
					companyId: localStorage.getItem('companyId'),
					hallName: state.form.weddinghall,
					weddingDate: state.form.weddingdate +' ' + state.form.weddingdatetime,
					weddingManNm: state.form.weddingmanname,
					weddingManPh: state.form.weddingmantelnum,
					weddingWomanNm: state.form.weddingwomanname,
					weddingWomanPh: state.form.weddingwomantelnum,
        })
          .then(function (response) {
            alert('예약완료')
            if (state.form.weddingImages){
              const formData = new FormData()
              const json = JSON.stringify({
                'wedding_manNm': response.data.weddingManNm,
                'wedding_num': response.data.weddingNum,
                'wedding_womanNm': response.data.weddingWomanNm})
              const blob = new Blob([json], { type: "application/json" });
              formData.append("uploadPostReq", blob)
              for (let index = 0; index < state.form.weddingImages.length;
               index++) {
                formData.append(`files`, state.form.weddingImages[index])
              }
              store.dispatch('root/requestImageUpload', formData)
                .then((response) => {
                  console.log('이미지 업로드 완료', response.data)
                })
                .catch((error) => {
                  console.log('이미지 업로드 실패', error)
                })
            }
            store.dispatch('root/requestDateWedding',state.form.weddingdate)
              .then(function(result) {
                store.commit('root/setWeddingInfo', result.data)
                router.push({name: 'management'})
              })
              .catch(function(err) {
                console.log('해당날짜 웨딩정보 요청 실패', err)
              })
          })
          .catch(function (err) {
            console.log('예약 만들기 실패', err)
          })
			} else {
				alert('모든 항목을 입력해주세요')
			}
		}

    const inputImageChange = function(event) {
      state.form.weddingImages = event.target.files
    }

	return { state, clickMake, inputImageChange }
	}
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Fjalla+One&display=swap');

.makereservation{
	 background: #f0ebe7;
	 display: flex;
	 justify-content: center;
	 height: 55rem;
}

.makereservation-font{
	font-family: 'Gowun Dodum', sans-serif;
}

</style>
