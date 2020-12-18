// @ts-nocheck
import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
import axios from 'axios'
import 'bootstrap/dist/css/bootstrap.css'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faUserSecret } from '@fortawesome/free-solid-svg-icons'
import { faEdit } from '@fortawesome/free-solid-svg-icons'
import { faDumpsterFire } from '@fortawesome/free-solid-svg-icons'
import { faTrash } from '@fortawesome/free-solid-svg-icons'
import { faCheck } from '@fortawesome/free-solid-svg-icons'
import { faWindowClose } from '@fortawesome/free-solid-svg-icons'
import { faLock } from '@fortawesome/free-solid-svg-icons'
import { faPortrait } from '@fortawesome/free-solid-svg-icons'
import { faAt } from '@fortawesome/free-solid-svg-icons'
import { faIdCard } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

/* Styles
--------------------------------- */
import '../assets/buttons.css'
import '../assets/styles.css'

library.add(faUserSecret, faEdit, faDumpsterFire, faTrash, faCheck, faWindowClose, faLock, faPortrait, faAt, faIdCard)
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.config.productionTip = false
axios.defaults.baseURL = process.env.VUE_APP_REMOTE_API;

new Vue({
  router,
  store,
  el: '#app',
  components: { App },
  template: '<App/>',
  render: h => h(App)
}).$mount('#app')
