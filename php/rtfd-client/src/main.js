//
// Rtfd Client
//
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import './assets/rtfd.css'
import axios from 'axios'
import md5 from 'js-md5'

axios.defaults.withCredentials = true
Vue.prototype.$action = function(action, options = {}) {
  return axios({
    url: 'http://192.168.1.251:10000/service.php',
    method: 'post',
    data: {
      act: action,
      ts: Math.floor((new Date()).getTime() / 1000),
      opts: options
    },
    transformRequest: [function (data) {
      let ret = ''
      let values = []
      for (let it in data) {
        if (data[it] instanceof Object) {
          for (let _it in data[it]) {
            values.push(data[it][_it] + '')
          }
          data[it] = JSON.stringify(data[it])
        } else {
          values.push(data[it] + '')
        }
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      // calc signature
      values = values.sort()
      let signature = md5([
        'ReadTheFuckDocs',
        values.join('-'),
        data['ts'] + ''
      ].join('~'))
      // join signature
      ret += 'sig' + '=' + signature
      // return result
      return ret
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

Vue.config.productionTip = false
Vue.use(ElementUI)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
