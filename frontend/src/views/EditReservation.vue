<template>
	<div class="editReservation">
		<div>
			<h1 class="editreservation-font" style="padding-top:5rem">예약 수정 페이지</h1>
			<form onsubmit="return false;">
				<br>
				<h3 class="editreservation-font" style="margin-top:10px; display:inline">예약 날짜 : </h3>
				<h2 class="editreservation-font" style="display:inline">{{ state.form.weddingDetailInfo.weddingDate.slice(0,10) }}</h2>

				<div class="input-group mb-3" style="width:18rem; margin:auto; margin-top:1rem">
					<span class="input-group-text" id="basic-addon1">시간</span>
					<input type="time" v-model="state.form.weddingdatetime" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1">
				</div>

				<h4 class="editreservation-font" style="margin-top:2rem">예식장을 선택하세요</h4>
				<div style="width:18rem; margin:auto;">
					<select v-model="state.form.weddinghall" class="form-select " aria-label="Default select example">
						<option class="editreservation-font" v-for="hall in state.hallinfo" :key="hall">{{hall.hallName}}</option>
					</select>
				</div>

				<h4 class="editreservation-font" style="margin-top:2rem">신랑의 정보를 입력하세요</h4>
				<div style="width:18rem; margin:auto;">
  					<input type="text" v-model="state.form.weddingDetailInfo.weddingManNm" class="form-control" id="formGroupExampleInput">
				</div>
				<div style="width:18rem; margin:auto;">
  					<input type="text" v-model="state.form.weddingDetailInfo.weddingManPh" class="form-control" id="formGroupExampleInput" >
				</div>

				<h4 class="editreservation-font" style="margin-top:2rem">신부의 정보를 입력하세요</h4>
				<div style="width:18rem; margin:auto;">
  					<input type="text" v-model="state.form.weddingDetailInfo.weddingWomanNm" class="form-control" id="formGroupExampleInput">
				</div>
				<div style="width:18rem; margin:auto;">
  					<input type="text" v-model="state.form.weddingDetailInfo.weddingWomanPh" class="form-control" id="formGroupExampleInput">
				</div>

				<div class="mb-3">
					<h4 class="editreservation-font" style="margin-top:2rem">사진을 올려주세요</h4>
					<input style="width:18rem;" class="form-control" type="file" id="formFile" multiple accept="imag/jpg">
				</div>

        <div style="margin-top:2rem; width: 18rem;">
          <button style="float:left" onclick="history.back()" type="button" class="btn btn-outline-danger">취소</button>
				  <button style="float:right" @click="clickChangeReservation" type="button" class="btn btn-outline-secondary">수정하기</button>
        </div>
      </form>
		</div>
	</div>
</template>

<script>
import { computed, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
export default {
	name: 'edit-reservation',
	setup() {
		const router = useRouter()
		const store = useStore()
		const state = reactive({
			form: {
				weddingDetailInfo: computed(() => store.getters['root/getWeddingDetailInfo']),
				weddingdatetime: '',
				weddingdate: computed(() => store.getters['root/getWeddingDateInfo'].weddingdate),
				weddinghall: '',
				weddingmanname: '',
				weddingwomanname: '',
				weddingmantelnum: '',
				weddingwomantelnum: '',
			},
			hallinfo: computed(() => store.getters['root/getHallsInfo']),
		})
        const clickChangeReservation = function () {
            if (!!state.form.weddingdatetime && !!state.form.weddinghall && !!state.form.weddingDetailInfo.weddingManNm && !!state.form.weddingDetailInfo.weddingManPh && !!state.form.weddingDetailInfo.weddingWomanNm && !!state.form.weddingDetailInfo.weddingWomanPh) {
                store.dispatch('root/requestChangeReservation', [state.form.weddingDetailInfo.weddingNum,
                    {
                    weddingDate: state.form.weddingDetailInfo.weddingDate.slice(0,10) + ' ' + state.form.weddingdatetime,
                    hallName: state.form.weddinghall,
                    weddingManNm: state.form.weddingDetailInfo.weddingManNm,
                    weddingManPh: state.form.weddingDetailInfo.weddingManPh,
                    weddingWomanNm: state.form.weddingDetailInfo.weddingWomanNm,
                    weddingWomanPh: state.form.weddingDetailInfo.weddingWomanPh,
                    }
                ])
                .then(function() {
                    store.dispatch('root/requestDateWedding',state.form.weddingDetailInfo.weddingDate)
                    .then(function(result) {
                        store.commit('root/setWeddingInfo', result.data)
                    })
                    .catch(function(err) {
                        console.log(err)
                    })
                    router.push({ name: 'management' })
                })
                .catch(function(err) {
                    console.log(err)
                })
            } else {
                alert('모든항목을 채워주세요')
            }
        }

	return { state, clickChangeReservation }
	}
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

.editReservation{
	 background: #f0ebe7;
	 display: flex;
	 justify-content: center;
	 height: 55rem;
}

.editreservation-font{
	font-family: 'Gowun Dodum', sans-serif;
}
</style>
