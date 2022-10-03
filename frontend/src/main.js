import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from './axios/axios'
import VueAxios from './axios/axios'
import VCalendar from 'v-calendar';

// Vue.use(VCalendar, {
//     screens: {
//       tablet: '576px',
//       laptop: '992px',
//       desktop: '1200px',
//     },
//   })

createApp(App).use(store).use(router).use(VueAxios, axios).use(VCalendar, {}).mount('#app')
