<template>
  <el-form ref="rtfd-form" label-position="left" label-width="80px" :model="login_info" :rules="form_validate">
    <el-form-item label="Username" prop="username">
      <el-input v-model="login_info.username"></el-input>
    </el-form-item>
    <el-form-item label="Password" prop="password">
      <el-input v-model="login_info.password" type="password"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit_login">Login</el-button>
      <el-button @click="guest_login">Guest Login</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: 'rtfd-login',
  data: () => {
    return {
      login_info: {
        username: '',
        password: ''
      },
      form_validate: {
        username: {
          required: true,
          message: '用户名不能为空',
          trigger: 'blur'
        },
        password: {
          required: true,
          message: '密码不能为空',
          trigger: 'blur'
        }
      }
    }
  },
  methods: {
    submit_login: function() {
      this.$refs['rtfd-form'].validate((valid) => {
        if (valid) {
          this.$emit('user_login', this.login_info)
        }
      })
    },
    guest_login: function() {
      this.$emit('user_login', false)
    }
  },
  components: {}
}
</script>

<style scoped>
  form {
    background: #fafafa;
    padding: 3rem 5rem;
    border: 1px solid rgba(0, 0, 0, .1);
    border-radius: 5px;
    box-shadow: 1px 3px 10px 1px rgba(0, 0, 0, .1),
                1px 1px 10px 1px rgba(0, 0, 0, .25);
  }

  @media screen and (max-width: 987px) {
    form {
      padding: 3rem;
    }
  }
</style>
