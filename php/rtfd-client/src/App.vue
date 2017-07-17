<template>
  <el-row id="rtfd" type="flex" v-show="true">
    <!-- Nav-bar Module [Always] -->
    <el-row id="rtfd-nav-bar">
      <rtfd-nav-bar :loading="state_loading"></rtfd-nav-bar>
    </el-row>

    <!-- Login Module [Only Guest] -->
    <!--<transition name="el-zoom-in-center" mode="out-in">-->
      <!--<el-row key="login" id="rtfd-login" ref="rtfd-login" v-if="login_show" type="flex" justify="center" align="middle">-->
        <!--<rtfd-login @user_login="user_login"></rtfd-login>-->
      <!--</el-row>-->
    <!--</transition>-->
    <transition name="el-zoom-in-center" mode="out-in">
      <el-row key="login" id="rtfd-login" ref="login" v-if="login_show" type="flex" justify="center" align="middle">
        <rtfd-login @user_login="user_login"></rtfd-login>
      </el-row>
    </transition>

    <!-- Markdown Display Module -->
    <transition name="el-zoom-in-center" mode="out-in">
      <el-row key="display" id="rtfd-display" v-if="!login_show" type="flex" justify="center" align="middle">
        <rtfd-display></rtfd-display>
      </el-row>
    </transition>

    <!-- End -->
  </el-row>
</template>

<script>
import { Message } from 'element-ui'
import rtfdNavBar from './components/navbar'
import rtfdLogin from './components/login'
import rtfdDisplay from './components/display'

export default {
  name: 'rtfd',
  data: () => {
    return {
      info: {},
      keep_guest: false,
      state_loading: false,
      display_show: false
    }
  },
  mounted: function() {
    // request init data
    this.$action('Init').then((response) => {
      this.info = response.data
      if (!this.is_guest) {
        Message.info('你现在的身份是: ' + this.info.user.username)
      }
    }, () => {
      Message.error('Oops~, 初始化数据出错咯')
    })
  },
  methods: {
    user_login: function(user) {
      if (user === false) {
        this.keep_guest = true
        Message.info('你现在的身份是: ' + this.info.user.username)
      } else {
        this.start_loading()
        // user login
        this.$action('Login', user).then((response) => {
          this.info = response.data
          Message.info('你现在的身份是: ' + this.info.user.username)
          this.stop_loading()
        }, () => {
          Message.error('Oops~登录发生错误, 可能是用户名或者密码错咯')
          this.stop_loading()
          return
        })
      }
    },
    start_loading: function() {
      this.state_loading = true
    },
    stop_loading: function() {
      this.state_loading = false
      this.display_show = true
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
  components: {rtfdNavBar, rtfdLogin, rtfdDisplay}
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

  #rtfd-main-area {
    height: 100%;
  }

  #rtfd-login, #rtfd-display {
    height: 100%;
  }
</style>
