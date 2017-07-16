<template>
  <el-row id="rtfd" type="flex" :justify="has_data ? '' : 'center'" :align="has_data ? '' : 'middle'">
    <template v-if="has_data">
      <transition name="el-fade-in-linear">
        <rtfd-nav-bar :docs="doc_tree" @load_file="load_file"/>
      </transition>
      <transition name="el-fade-in-linear">
        <rtfd-display :contents="contents"/>
      </transition>
    </template>
    <template v-if="!has_data">
      <i class="el-icon-loading"></i>
    </template>
  </el-row>
</template>
<script>
import axios from 'axios'
import md5 from 'js-md5'
// import { Message } from 'element-ui'
import rtfdNavBar from './components/navbar'
import rtfdDisplay from './components/display'

export default {
  name: 'rtfd',
  data: () => {
    return {
      doc_tree: [],
      contents: ''
    }
  },
  computed: {
    has_data: function() {
      return this.doc_tree.length !== 0
    }
  },
  mounted: function() {
    this.$action('getDocsTree').then((r) => {
      console.log(r)
    }, (r) => {
      console.log(r)
    })
//    this.$ajax({
//      method: 'get',
//      url: 'http://localhost:9788/get_docs_tree'
//    }).then((response) => {
//      let parser = (data) => {
//        data.forEach(function(_e, _i) {
//          if (_e.hasOwnProperty('children')) {
//            try {
//              data[_i].children = parser(JSON.parse(_e.children))
//            } catch (e) {}
//          }
//        })
//        return data
//      }
//      parser(response.data)
//      this.doc_tree = response.data
//    }, () => {
//      Message.error({
//        'message': 'Oops~ 加载目录树出错咯'
//      })
//    })
  },
  methods: {
    $action: function(action, options = {}) {
      return axios({
        url: 'http://localhost:12000/service.php',
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
  },
  components: {rtfdNavBar, rtfdDisplay}
}
</script>

<style>

  ::-webkit-scrollbar {
    height: 8px;
    width: 8px;
  }

  ::-webkit-scrollbar-thumb {
    border-radius: 10px;
    background: rgba(0, 0, 0, 0.2);
  }

  ::-webkit-scrollbar-thumb:window-inactive {
    background: rgba(0, 0, 0, 0.1);
    color: #F00;
  }

  ::-webkit-scrollbar-thumb:hover {
    background: rgba(0, 0, 0, 0.25);
  }

  ::-webkit-scrollbar-thumb:active {
    background: rgba(0, 0, 0, 0.4);
  }

  ::selection {
    background: rgba(211, 211, 211, 0.55);
    color: #e62739;
  }

  ::-moz-selection {
    background: rgba(211, 211, 211, 0.55);
    color: #e62739;
  }

  body {
    margin: 0;
    padding: 0;
  }

  #rtfd {
    height: 100vh;
  }
</style>
