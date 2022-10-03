// API
import $axios from 'axios'

// 로그인
export function requestLogin ({ state }, payload) {
  const url = '/auth/login'
  let body = payload
  return $axios.post(url, body)
}
// 로그인 끝

// 회원가입
export function requestSignup ({ state }, payload) {
  const url = '/companys'
  let body = payload
  return $axios.post(url, body)
}
// 회원가입 끝

// 아이디 중복 확인
export function requestIdCheck ({ state }, payload) {
  const url = `/companys/${payload}`
  return $axios.get(url)
}
// 아이디 중복 확인 끝

// 회원정보
export function requestMyPage({ state }, payload) {
  const url = `/companys/me/${payload}`
  return $axios.get(url)
}
// 회원정보 끝


// 회원의 홀 정보 가져오기
export function requestHallsInfo({ state }, payload) {
  const url = `/halls/${payload}`
  return $axios.get(url)
}
// 회원의 홀 정보 가져오기 끝

// 회원의 홀 추가
export function requestHallPlus({ state }, payload) {
  const url = `/halls`
  let body = payload
  return $axios.post(url, body)
}
// 회원의 홀 추가 끝

// 회원의 홀 삭제
export function requestHallDelete({ state }, payload) {
  const url = `/halls/${payload}`
  return $axios.delete(url)
}
// 회원의 홀 삭제 끝

// 웨딩넘버로 검색
export function requestWeddingInfoByNum({ state }, payload) {
  const url = `/weddings/num/${payload}`
  return $axios.get(url)
}
// 웨딩넘버로 검색 끝

// 날짜별 웨딩 검색
export function requestDateWedding({ state }, payload) {
  const url = `/weddings/date/${payload}`
  return $axios.get(url)
}
// 날짜별 웨딩 검색 끝

// 이름별 웨딩 검색
export function requestNameWedding({ state }, payload) {
  const url = `/weddings/name/${payload}`
  return $axios.get(url)
}
// 이름별 웨딩 검색 끝

// 예약하기
export function requestMakeReservation({ state }, payload) {
  const url = '/weddings'
  let body = payload
  return $axios.post(url, body)
}

// 회원탈퇴
export function requestQuit({ state }, payload) {
  const url = `/companys/${payload}`
  return $axios.delete(url)
}
// 회원탈퇴 끝

// 회원정보 변경
export function requestChangeInfo({ state }, [params, payload]) {
  const url = `/companys/${params}`
  return $axios.patch(url, payload)
}
// 회원정보 변경 끝

//예약정보 변경
export function requestChangeReservation({ state }, [params, payload]) {
  const url = `/weddings/${params}`
  return $axios.patch(url, payload)
}
//예약정보 변경 끝

//예약정보 삭제
export function requestDeleteReservation({ state }, payload) {
  const url = `/weddings/${payload}`
  return $axios.delete(url)
}
//예약정보 변경 끝

// 하객 입장
export function requestGuestSignup ({ state }, payload) {
  const url = '/guests'
  return $axios.post(url, payload)
}
// 하객 입장 끝

// 공지사항 등록
export function requestMakeNotice({ state }, payload) {
  const url = '/notices'
  let body = payload
  return $axios.post(url, body)
}
// 공지사항 등록

// 공지사항 상세조회
export function requestNotice ({ state }, payload) {
  const url = `/notices/${payload}`
  return $axios.get(url)
}
// 공지사항 상세조회 끝

// 공지사항 전체 조회
export function requestNoticeList({ state }, payload) {
  const url = '/notices/list'
  return $axios.get(url)
}
// 공지사항 전체 조회 끝

//공지사항 삭제
export function requestDeleteNotice({ state }, payload) {
  const url = `/notices/${payload}`
  return $axios.delete(url)
}
//공지사항 삭제 끝

//공지사항 수정
export function requestEditNotice({ state }, [params, payload]) {
  const url = `/notices/${params}`
  return $axios.patch(url, payload)
}
//공지사항 수정 끝

// 문의사항 등록
export function requestMakeQuestion({ state }, payload) {
  const url = '/questions'
  let body = payload
  return $axios.post(url, body)
}
// 문의사항 등록

// 문의사항 전체 조회
export function requestQuestionList({ state }, payload) {
  const url = '/questions/list'
  return $axios.get(url)
}
// 문의사항 전체 조회 끝

// 문의사항 상세조회
export function requestQuestion ({ state }, payload) {
  const url = `/questions/${payload}`
  return $axios.get(url)
}
// 문의사항 상세조회 끝

//문의사항 삭제
export function requestDeleteQuestion({ state }, payload) {
  const url = `/questions/${payload}`
  return $axios.delete(url)
}
//문의사항 삭제 끝

//문의사항 수정
export function requestEditQuestion({ state }, [params, payload]) {
  const url = `/questions/${params}`
  return $axios.patch(url, payload)
}
//문의사항 수정 끝

//문의사항 제목으로 검색
export function requestNameQuestion({ state }, payload) {
  const url = `/questions/like/${payload}`
  return $axios.get(url)
}
//문의사항 제목으로 검색 끝

//이미지 저장
export function requestImageUpload({ state }, formData) {
  const url = `/image`
  return $axios.post(url, formData, {headers: {'Content-Type': 'multipart/form-data'}})
}
//이미지 저장 끝

// 방명록 불러오기
export function requestGuestBook({ state }, params) {
  const url = `/guests/list/${params}`
  return $axios.get(url)
}
// 방명록 불러오기 끝

// 웨딩사진 목록 불러오기
export function requestWeddingImageList({ state }, payload) {
  const url = '/image/list'
  return $axios.post(url, payload)
}
// 웨딩사진 목록 불러오기 끝

// 웨딩사진 불러오기
export function requestWeddingImage({ state }, params) {
  const url = `/image/${params}`
  return $axios.get(url)
}
// 웨딩사진 불러오기 끝

// 결혼식 종료하기
export function requestWeddingEnd({ state }, params) {
  const url = `/weddings/end/${params}`
  return $axios.patch(url)
}
// 결혼식 종료하기 끝

// 모든 게스트의 정보 받아오기
export function requestAfterWeddingGuestInfo({ state }, params) {
  const url = `/guests/listInfo/${params}`
  return $axios.get(url)
}
// 모든 게스트의 정보 받아오기 끝
