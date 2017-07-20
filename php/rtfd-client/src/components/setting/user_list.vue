<template>
  <div style="width: 100%;">
    <!-- User List -->
    <el-table :data="user_list" style="width: 100%" border>
      <el-table-column prop="uid" label="UID"></el-table-column>
      <el-table-column prop="name" label="用户名"></el-table-column>
      <el-table-column prop="role_id" label="用户角色"></el-table-column>
      <el-table-column prop="group_id" label="用户组ID"></el-table-column>
      <el-table-column label="操作">
        <template scope="scope">
          <!-- Delete User -->
          <el-button @click.native.prevent="del_user(scope.$index)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- Add User Module -->
    <!--<el-form ref="rtfd-setting-add-user-form" label-position="left" label-width="80px" :model="login_info" :rules="form_validate">-->
      <!--<el-form-item label="Username" prop="username">-->
        <!--<el-input v-model="login_info.username"></el-input>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="Password" prop="password">-->
        <!--<el-input v-model="login_info.password" type="password"></el-input>-->
      <!--</el-form-item>-->
      <!--<el-form-item>-->
        <!--<el-button type="primary" @click="submit_login">Login</el-button>-->
        <!--<el-button @click="guest_login">Guest Login</el-button>-->
      <!--</el-form-item>-->
    <!--</el-form>-->

  </div>
</template>

<script>
export default {
  name: 'rtfd-setting-user-list',
  data: () => {
    return {}
  },
  props: {
    user_list: {
      required: true,
      type: Array
    }
  },
  methods: {
    del_user: function(index) {
      this.$emit('del_user', this.user_list[index].uid)
    }
  },
  components: {}
}
</script>

<style scoped>
</style>
