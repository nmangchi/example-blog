// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuetify from 'vuetify'
import { App } from './app'
import router from './router'
import store from './store'
import localforage from 'localforage'
import CKEditor from '@ckeditor/ckeditor5-vue'
import 'vuetify/dist/vuetify.min.css'

Vue.config.productionTip = false

Vue.use(Vuetify)
Vue.use(CKEditor)

localforage.config({
  name: 'blog'
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  components: { App },
  template: '<App/>'
})
