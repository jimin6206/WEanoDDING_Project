import { createRouter, createWebHistory } from 'vue-router'

const GUEST = '/guest'

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import(/* webpackChunkName: "home" */ '@/views/Home.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import(/* webpackChunkName: "login" */ '@/views/Login.vue')
  },

  {
    path: '/signup',
    name: 'signup',
    component: () => import(/* webpackChunkName: "signup" */ '@/views/Signup.vue')
  },
  {
    path: '/mypage',
    name: 'mypage',
    component: () => import(/* webpackChunkName: "signup" */ '@/views/MyPage.vue')
  },
  {
    path: '/introduce',
    name: 'introduce',
    component: () => import(/* webpackChunkName: "introduce" */'@/views/Introduce.vue')
  },
  {
    path: '/management',
    name: 'management',
    component: () => import(/* webpackChunkName: "management" */ '@/views/Management.vue')
  },
  {
    path: '/management/guest',
    name: 'management-guest',
    component: () => import(/* webpackChunkName: "management" */ '@/views/ManagementGuest.vue')
  },
  {
    path: '/makereservation',
    name: 'makereservation',
    component: () => import(/* webpackChunkName: "makereservation" */ '@/views/MakeReservation.vue')
  },
  {
    path: '/reservations/detail/:weddingnum',
    name: 'detailresrvation',
    component: () => import(/* webpackChunkName: "detailresrvation" */ '@/views/DetailReservation.vue')
  },
  {
    path: '/waiting',
    name: 'waiting',
    component: () => import(/* webpackChunkName: "openvidu" */ '@/views/WaitingHall.vue')
  },
  {
    path: '/reservations/edit/:weddingnum',
    name: 'editreservation',
    component: () => import(/* webpackChunkName: "editreservation" */ '@/views/EditReservation.vue')
  },
  {
    path: '/notice',
    name: 'notice',
    component: () => import(/* webpackChunkName: "notice" */ '@/views/Notice.vue'),
  },
  {
    path: '/notice/:noticeNum',
    name: 'detailnotice',
    component: () => import(/* webpackChunkName: "detailnotice" */ '@/views/DetailNotice.vue'),
  },
  {
    path: '/notice/make',
    name: 'makenotice',
    component: () => import(/* webpackChunkName: "notice" */ '@/views/MakeNotice.vue'),
  },
  {
    path: '/notice/edit/:noticeNum',
    name: 'editnotice',
    component: () => import(/* webpackChunkName: "notice" */ '@/views/EditNotice.vue'),
  },
  {
    path: '/questions',
    name: 'questions',
    component: () => import(/* webpackChunkName: "questions" */ '@/views/Questions.vue')
  },
  {
    path: '/questions/make',
    name: 'makequestion',
    component: () => import(/* webpackChunkName: "makequestion" */ '@/views/MakeQuestion.vue')
  },
  {
    path: '/questions/:qNum',
    name: 'detailquestion',
    component: () => import(/* webpackChunkName: "detailquestion" */ '@/views/DetailQuestion.vue')
  },
  {
    path: '/questions/edit/:qNum',
    name: 'editquestion',
    component: () => import(/* webpackChunkName: "editquestion" */ '@/views/EditQuestion.vue'),
  },
  {
    path: GUEST + '/entrance',
    name: 'entrance',
    component: () => import(/* webpackChunkName: "entrance" */ '@/views/Entrance.vue'),
  },
  {
    path: '/pay',
    name: 'pay',
    component: () => import(/* webpackChunkName: "IamportPay" */ '@/views/IamportPay.vue'),
  },
  {
    path: '/:weddingNum',
    name: 'certification',
    component: () => import(/* webpackChunkName: "IamportCertification" */ '@/views/IamportCertification.vue'),
    props: true
  },
  {
    path: '/after/:weddingNum',
    name: 'afterWeddingGuestInfo',
    component: () => import(/* webpackChunkName: "afterWeddingGuestInfo" */ '@/views/AfterWeddingGuestInfo.vue'),
    props: true
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
