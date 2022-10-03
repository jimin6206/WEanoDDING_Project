<template>
  <div class="detailnotice">
    <h1 class="detailnotice-font" style="padding-top:5rem;">상세 공지사항</h1>
      <div class="input-group" style="width: 30rem; margin-top: 2rem">
        <div class="card detailnotice-font">
          <div class="card-body" style="width:29.5rem">
            웨안오딩
          </div>
        </div>

        <div class="input-group">
          <span class="input-group-text detailnotice-font" id="basic-addon1">공지 시간</span>
          <div class="card detailnotice-font">
            <div class="card-body" style="width:24rem">
              {{state.form.NoticeDetailInfo.noticeDate}}
            </div>
          </div>
        </div>

        <div class="input-group">
          <span class="input-group-text detailnotice-font" id="basic-addon1">공지 제목</span>
          <div class="card detailnotice-font">
            <div class="card-body" style="width:24rem">
              {{state.form.NoticeDetailInfo.noticeTitle}}
            </div>
          </div>
        </div>

        <div class="input-group">
          <span class="input-group-text detailnotice-font" id="basic-addon1">공지 내용</span>
          <div class="card detailnotice-font">
            <div class="card-body" style="width:24rem">
              {{state.form.NoticeDetailInfo.noticeContent}}
            </div>
          </div>
        </div>
        <div>
          <div style="margin-top:2rem; width: 30rem;">
            <button style="float:left" onclick="history.back()" type="button" class="btn btn-outline-secondary">뒤로</button>
          </div>
          <div style="width: 15rem; float: right;" >
            <button v-if="state.isadmin" class="btn btn-outline-secondary detailnotice-font" @click="clickDelteNotice">삭제하기</button>
            <button v-if="state.isadmin" class="btn btn-outline-secondary detailnotice-font" @click="clickgoEditNotice" style="float:right;">수정하기</button>
          </div>
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
        NoticeDetailInfo: computed(() => store.getters['root/getNoticeDetailInfo']),
      },
      isadmin: computed(() => store.getters['root/getIsAdmin']),
    })
    const clickgoEditNotice = function () {
      router.push({name:'editnotice', params: {noticeNum: state.form.NoticeDetailInfo.noticeNum}})
    }
    const clickDelteNotice = function () {
      if (confirm('진짜 삭제하시겠습니까?')) {
        store.dispatch('root/requestDeleteNotice', state.form.NoticeDetailInfo.noticeNum)
        .then(function() {
          store.dispatch('root/requestNoticeList')
          .then(function(result) {
            store.commit('root/setNoticeListInfo', result.data)
          })
          .catch(function(err) {
            console.log(err)
          })
          router.push({ name: 'notice' })
        })
        .catch(function(err) {
          console.log(err)
        })
      }
    }
    onMounted(()=>{
      store.commit('root/setIsAdmin')
    })
    return { state, clickgoEditNotice,clickDelteNotice }
  },
}
</script>

<style scoped>
.detailnotice{
  height: 50rem;
  background: #f0ebe7;
  display: flex;
  flex-direction: column;
  align-items: center
}

.detailnotice-font{
  font-family: 'Gowun Dodum', sans-serif;
}

.input-group{
  margin-top: 0px;
}
</style>
