<template>
  <el-row id="rtfd" type="flex" v-show="true">

    <!-- Nav-bar Module [Always] -->
    <el-row id="rtfd-nav-bar">
      <rtfd-nav-bar :loading="state_loading" :docs="docs" :select_doc="select_doc"></rtfd-nav-bar>
    </el-row>

    <!-- Login Module [Guest or Un-login] -->
    <transition name="el-zoom-in-center" mode="out-in" v-on:after-leave="toggle_view('markdown')">
      <el-row key="login" id="rtfd-login" v-if="login_show" type="flex" justify="center" align="middle">
        <rtfd-login @user_login="user_login"></rtfd-login>
      </el-row>
    </transition>

    <!-- Markdown Display Module -->
    <transition name="el-zoom-in-center" mode="out-in">
      <el-row key="markdown" id="rtfd-markdown" v-if="show_markdown" type="flex">
        <rtfd-markdown></rtfd-markdown>
      </el-row>
    </transition>

    <!-- Rtfd End -->
  </el-row>
</template>

<script>
import { Message } from 'element-ui'
import rtfdNavBar from './components/navbar'
import rtfdLogin from './components/login'
import rtfdMarkdown from './components/markdown'

export default {
  name: 'rtfd',
  data: () => {
    return {
      info: {},
      docs: [],
      select_doc: '',
      keep_guest: false,
      state_loading: false,
      show_markdown: false
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
      // Logged in
      if (!this.is_guest) {
        this.display_show = true
        Message.info('你现在的身份是: ' + this.info.user.username)
      }
    }, () => {
      // cannot getting data
      Message.error('Oops~, 初始化数据出错咯')
    })
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
        }, () => {
          Message.error('Oops~, 登录发生错误, 可能是用户名或者密码错咯')
          this.stop_loading()
        })
      }
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
        // select default doc
        this.select_doc = this.docs[0].name
      }, () => {
        Message.error('Oops~, 获取可查看的文档列表发生错误啦')
        this.stop_loading()
      })
    },
    toggle_view: function(view) {
      // reset all view state
      this.show_markdown = false
      // toggle to view
      switch (view) {
        case 'markdown': {
          this.show_markdown = true
          break
        }
      }
    },
    start_loading: function() {
      this.state_loading = true
    },
    stop_loading: function() {
      this.state_loading = false
    }
  },
  computed: {
    is_guest: function() {
      return this.info.user && this.info.user.role_name === 'Guest'
    },
    login_show: function() {
      return !this.keep_guest && this.is_guest
    }
  },
  components: {rtfdNavBar, rtfdLogin, rtfdMarkdown}
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
    flex-direction: column;
    height: 100vh;
  }

  #rtfd-login, #rtfd-markdown {
    height: 100%;
  }

  #rtfd-markdown {
    background: #fefefe;
  }
</style>
