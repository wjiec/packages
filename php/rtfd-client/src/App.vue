<template>
  <el-row id="rtfd" type="flex">

    <!-- Nav-bar Module [Always] -->
    <el-row id="rtfd-nav-bar" ref="rtfd-nav-bar" @click="toggle_tree">
      <rtfd-nav-bar
        :docs="docs"
        :is_guest="is_guest"
        :is_admin="is_admin"
        :loading="state_loading"
        :select_doc="select_doc"
        @checkout="checkout_doc"
        @toggle_view="pre_toggle"
      ></rtfd-nav-bar>
    </el-row>

    <!-- Login Module [Guest or Un-login] -->
    <transition name="el-zoom-in-center" mode="out-in" v-on:after-leave="toggle_view">
      <el-row key="login" id="rtfd-login" v-show="login_show || view_list.login" type="flex" justify="center" align="middle">
        <rtfd-login @user_login="user_login"></rtfd-login>
      </el-row>
    </transition>

    <!-- Markdown Module -->
    <transition name="el-zoom-in-center" mode="out-in" v-on:after-leave="toggle_view">
      <el-row key="markdown" id="rtfd-markdown" v-if="view_list.markdown" type="flex">
        <rtfd-markdown
          :doc_tree="doc_tree"
          :default_active="default_active"
          :doc_content="doc_content"
          :open_tree="open_tree"
          @open_file="open_file"
        ></rtfd-markdown>
      </el-row>
    </transition>

    <!-- Account Module -->
    <transition name="el-zoom-in-center" mode="out-in" v-on:after-leave="toggle_view">
      <el-row key="account" id="rtfd-account" v-if="view_list.account" type="flex" justify="center" align="middle">
        <rtfd-profile></rtfd-profile>
      </el-row>
    </transition>

    <!-- Setting Module -->
    <transition name="el-zoom-in-center" mode="out-in" v-on:after-leave="toggle_view">
      <el-row key="setting" id="rtfd-setting" v-if="view_list.setting" type="flex">
        <rtfd-setting></rtfd-setting>
      </el-row>
    </transition>

    <!-- Rtfd End -->
  </el-row>
</template>

<script>
import Vue from 'vue'
// var Vue = require('vue')
import { Message } from 'element-ui'
// import rtfdNavBar from './components/navbar'
// import rtfdLogin from './components/login'
// import rtfdMarkdown from './components/markdown'
// import rtfdProfile from './components/profile'
// import rtfdSetting from './components/setting'

const rtfdNavBar = Vue.component('rtfdNavBar', function (resolve) {
  require(['./components/navbar'], resolve)
})

const rtfdLogin = Vue.component('rtfdLogin', function (resolve) {
  require(['./components/login'], resolve)
})

const rtfdMarkdown = Vue.component('rtfdMarkdown', function (resolve) {
  require(['./components/markdown'], resolve)
})

const rtfdProfile = Vue.component('rtfdProfile', function (resolve) {
  require(['./components/profile'], resolve)
})

const rtfdSetting = Vue.component('rtfdSetting', function (resolve) {
  require(['./components/setting'], resolve)
})

export default {
  name: 'rtfd',
  data: () => {
    return {
      info: {},
      docs: [],
      select_doc: '',
      doc_tree: {},
      toggle_state: {in: '', out: ''},
      doc_content: '',
      default_active: null,
      keep_guest: false,
      state_loading: false,
      view_list: {
        login: this.is_guest,
        markdown: false,
        account: false,
        setting: false
      },
      open_tree: true
    }
  },
  mounted: function() {
    // request init data
    this.start_loading()
    // init user information
    this.$action('Init').then((response) => {
      // disable loading icon
      this.stop_loading()
      // assign user data
      this.info = response.data
      // Cookie Logged in
      if (!this.is_guest) {
        Message.info('你现在的身份是: ' + this.info.user.username)
        // init docs list
        this.init_docs()
      }
      window.scrollTo(0, 0)
    }, () => {
      // cannot getting data
      Message.error('Oops~, 初始化数据出错咯')
    })

    // close tree
    if (this.$mobile && this.$route.path.substr(1)) {
      this.open_tree = false
    }
  },
  methods: {
    user_login: function(user) {
      if (user === false) {
        this.keep_guest = true
        Message.info('你现在的身份是: ' + this.info.user.username)
        this.init_docs()
      } else {
        this.start_loading()
        // user login
        this.$action('Login', user).then((response) => {
          this.info = response.data
          Message.info('你现在的身份是: ' + this.info.user.username)
          this.init_docs()
        }, (e) => {
          if (e.message.indexOf('timeout') !== -1) {
            Message.error('Oops~, 登录超时啦, 请检查网络连接')
          } else {
            Message.error('Oops~, 登录发生错误, 可能是用户名或者密码错咯')
          }
          this.stop_loading()
        })
      }
      window.scrollTo(0, 0)
    },
    init_docs: function() {
      this.$action('GetDocs').then((response) => {
        // docs list
        let docs = response.data
        // check empty
        if (docs.length === 0) {
          return Message.error('X_X, 你没有权限查看任何文档')
        }
        // assign to this.docs
        this.docs = docs

        let doc = this.docs[0].name
        if (decodeURIComponent(this.$route.path).indexOf('@') !== -1) {
          this.default_active = decodeURIComponent(decodeURIComponent(this.$route.path).substr(1))
          let docTmp = this.default_active.split('@')[0]
          // check doc is in docs
          for (let i = 0; i < this.docs.length; ++i) {
            if (this.docs[i].name === docTmp) {
              doc = docTmp
              break
            }
          }
        }

        let docName = doc
        if (this.default_active && this.default_active.indexOf('@') !== -1) {
          if (this.access_flag === false) {
            Message.error('Oops~, 你还没有权限访问这个文档哦~')
          }
        }

        // select default doc
        this.select_doc = docName
        // show markdown
        this.pre_toggle('markdown')
        // stop loading state
        this.stop_loading()
      }, () => {
        Message.error('Oops~, 获取可查看的文档列表发生错误啦')
        this.stop_loading()
      })
    },
    checkout_doc: function(doc) {
      this.select_doc = doc

      if (this.$mobile) {
        this.open_tree = true
      }
    },
    open_file: function(type, index, indexPath, close = false) {
      // start loading state
      this.start_loading()

      // file path
      let relativePath = ''
      // open file
      if (type === 'file') {
        relativePath = decodeURIComponent(indexPath[indexPath.length - 1])
      }
      // open folder
      if (type === 'folder') {
        relativePath = decodeURIComponent(indexPath[indexPath.length - 1] + '/' + 'README.md')
      }

      // loading file contents
      this.$action('GetDocContent', {path: relativePath, hl: 'no'}).then((response) => {
        this.doc_content = response.data.contents
        this.stop_loading()
      }, (e) => {
        if (e.message.indexOf('privilege level')) {
          Message.error('Oops~, 你没有权限查看这个文档哦')
        } else {
          Message.error('Oops~, 获取文档内容出错啦')
        }
        this.stop_loading()
      })

      if (close) {
        this.open_tree = false
      }
    },
    toggle_view: function() {
      // toggle to view
      switch (this.toggle_state.in) {
        case 'markdown': {
          this.view_list.markdown = true
          break
        }
        case 'account': {
          this.view_list.account = true
          break
        }
        case 'setting': {
          this.view_list.setting = true
          break
        }
      }
    },
    pre_toggle: function(_in, _out = null) {
      // reset all view state
      for (let k in this.view_list) {
        this.view_list[k] = false
      }

      this.toggle_state.in = _in
      this.toggle_state.out = _out
    },
    start_loading: function() {
      this.state_loading = true
    },
    stop_loading: function() {
      this.state_loading = false
    },
    toggle_tree: function() {
      if (this.$mobile) {
        this.open_tree = !this.open_tree
      }
    }
  },
  computed: {
    is_guest: function() {
      return this.info.user && this.info.user.role_name === 'Guest'
    },
    is_admin: function() {
      return this.info.user && this.info.user.role_name.toLowerCase() === 'admin'
    },
    login_show: function() {
      return !this.keep_guest && this.is_guest
    }
  },
  watch: {
    select_doc: function(doc) {
      if (doc === '') {
        return []
      }

      // loading doc tree
      this.$action('GetDocTree', {doc: doc}).then((response) => {
        this.doc_tree = response.data
      }, () => {
        return Message.error('Oops~, 读取文档目录结构出错啦')
      })
    }
  },
  components: {rtfdNavBar, rtfdLogin, rtfdMarkdown, rtfdProfile, rtfdSetting}
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
    font-family: "Arial", "Microsoft YaHei", "黑体", "宋体", sans-serif;
  }

  div#rtfd {
    flex-direction: column;
    height: 100vh;
  }

  #rtfd-login, #rtfd-markdown, #rtfd-account, #rtfd-setting {
    height: 100%;
  }

  #rtfd-markdown {
    background: #fefefe;
  }

  img {
    max-width: 80% !important;
  }

  p {
    line-height: 1.5;
  }

  pre, code {
    font-family: Consolas, Monaco, monospace;
  }

  div.el-select-dropdown {
    min-width: 217px;
  }

  h1, h2, h3, h4, h5, h6 {
    -ms-word-wrap: break-word;
    word-wrap: break-word;
  }
</style>
