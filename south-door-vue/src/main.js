// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import App from './App'
import router from './router'
import axios from 'axios'
import vuex from 'vuex'
import { isEmpty } from "./utils/validate.js";

Vue.use(ElementUI)
Vue.config.productionTip = false

Vue.prototype.$axios = axios
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'
// axios.defaults.baseURL = 'http://localhost:8000/api/'
// axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*'
// axios.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest'
/* eslint-disable no-new */
new Vue({
  el: '#app',
  vuex,
  router,
  components: { App },
  template: '<App/>'
})

router.beforeEach((to, from, next) => {
  let user = sessionStorage.getItem("user")
  if (isEmpty(user) && (to.path.indexOf('manage') !== -1 || to.path.indexOf('edit') !== -1)) {
    next(from.path)
  } else {
    next()
  }
})