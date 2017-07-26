<template>
  <div id="rtfd-profile-area">
    <el-card class="box-card">
      <div slot="header" class="clear-fix">
        <span style="line-height: 36px;">个人资料</span>
        <el-button class="rtfd-profile-btn" style="float: right;" type="primary" @click="update_profile">更新</el-button>
        <el-button class="rtfd-profile-btn" style="float: right;" type="primary" @click="logout">登出</el-button>
      </div>
      <div>
        <el-form label-position="left" label-width="80px" :model="profile">
          <el-form-item label="UID">
            <el-input v-model="profile.uid" :disabled="true"></el-input>
          </el-form-item>

          <el-form-item label="用户名">
            <el-input v-model="profile.name" :disabled="true"></el-input>
          </el-form-item>

          <el-form-item label="昵称">
            <el-input v-model="profile.nickname"></el-input>
          </el-form-item>

          <el-form-item label="密码">
            <el-input v-model="profile.password"></el-input>
          </el-form-item>

          <el-form-item label="用户角色">
            <el-input v-model="profile.role" :disabled="true"></el-input>
          </el-form-item>

          <el-form-item label="用户组">
            <el-input v-model="profile.group" :disabled="true"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
import { Message } from 'element-ui'

export default {
  name: 'rtfd-profile',
  data: () => {
    return {
      profile: {
        uid: '',
        name: '',
        nickname: '',
        password: '',
        role: '',
        group: ''
      }
    }
  },
  mounted: function() {
    this.refresh_profile()
  },
  methods: {
    refresh_profile: function() {
      this.$action('GetProfile').then((response) => {
        this.profile.uid = response.data.uid
        this.profile.name = response.data.name
        this.profile.nickname = response.data.nickname
        this.profile.role = response.data.role
        this.profile.group = response.data.group
      }, () => {
        Message.error('获取个人资料出错了')
      })
    },
    update_profile: function() {
      let newProfile = {
        nickname: this.profile.nickname,
        password: this.profile.password
      }

      this.$action('UpdateProfile', newProfile).then(() => {
        Message.success('更新用户资料成功')
      }, () => {
        Message.error('更新用户资料失败了')
      })
    },
    logout: function() {
      this.$action('Logout').then(() => {
        this.$router.go(0)
      }, () => {
        Message.error('登出系统发生错误了')
      })
    }
  },
  components: {}
}
</script>

<style scoped>
  #rtfd-profile-area {
    width: 40%;
  }

  @media screen and (max-width: 1200px) {
    #rtfd-profile-area {
      width: 60%;
    }
  }

  @media screen and (max-width: 987px) {
    #rtfd-profile-area {
      width: 80%;
    }
  }

  @media screen and (max-width: 687px) {
    #rtfd-profile-area {
      width: 100%;
    }
  }

  .clear-fix:before, .clear-fix:after {
    display: table;
    content: "";
  }

  .clear-fix:after {
    clear: both
  }

  .box-card {
    width: 100%;
  }

  .rtfd-profile-btn {
    margin: 0 .25rem;
  }
</style>
