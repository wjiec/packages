<template>
  <div style="width: 100%;">
    <h2>用户列表</h2>
    <!-- User List -->
    <el-table :data="user_list" style="width: 100%" border>
      <el-table-column prop="user_id" label="UID"></el-table-column>
      <el-table-column prop="user_name" label="用户名"></el-table-column>
      <el-table-column prop="role_name" label="用户角色"></el-table-column>
      <el-table-column prop="group_name" label="用户组"></el-table-column>
      <el-table-column label="操作">
        <template scope="scope">
          <!-- Edit User -->
          <el-dialog title="编辑" :visible.sync="update_visible" size="small">
            <!-- EditUser Form -->
            <el-form ref="rtfd-setting-update-user-form" :model="will_update_user" label-position="left" label-width="80px" :rules="update_form_validate">
              <!-- Username/Password Form -->
              <el-form-item label="用户名" prop="username" class="rtfd-setting-update-user-item">
                <el-input v-model="will_update_user.username"></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password" class="rtfd-setting-update-user-item">
                <el-input v-model="will_update_user.password" type="password"></el-input>
              </el-form-item>

              <!-- User Role/Group Form -->
              <el-form-item label="用户角色" prop="role_id">
                <el-select v-model="will_update_user.role_id" placeholder="请选择用户角色">
                  <el-option v-for="role in role_list" :label="role.name" :value="role.rid" key="'role_' + role.name"></el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="用户组" prop="group_id" class="rtfd-setting-add-user-item">
                <el-select v-model="will_update_user.group_id" placeholder="请选择用户组">
                  <el-option v-for="group in group_list" :label="group.name" :value="group.gid" key="'group_' + group.name"></el-option>
                </el-select>
              </el-form-item>
            </el-form>
            <!-- EditUser Button -->
            <div slot="footer" class="dialog-footer">
              <el-button @click="update_visible = false">取 消</el-button>
              <el-button type="primary" @click="update_user(0, true)">确 定</el-button>
            </div>
          </el-dialog>
          <el-button @click.native.prevent="update_user(scope.$index)" type="text" size="small" :disabled="false">编辑</el-button>

          <!-- Delete User -->
          <el-dialog title="警告" :visible.sync="del_visible" size="tiny">
            <span>确定要删除 {{ will_del_user.name }}</span>
            <span slot="footer" class="dialog-footer">
              <el-button @click="del_visible = false">取 消</el-button>
              <el-button type="primary" @click="del_user(0, true)">确 定</el-button>
            </span>
          </el-dialog>
          <el-button @click.native.prevent="del_user(scope.$index)" type="text" size="small" :disabled="false">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- Add User Module  -->
    <h2>新增用户</h2>
    <el-form id="rtfd-setting-add-user-form" ref="rtfd-setting-add-user-form" label-position="left" label-width="80px" :model="new_user" :rules="form_validate">

      <!-- Username/Password Form -->
      <el-form-item label="用户名" prop="username" class="rtfd-setting-add-user-item">
        <el-input v-model="new_user.username"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password" class="rtfd-setting-add-user-item">
        <el-input v-model="new_user.password" type="password"></el-input>
      </el-form-item>

      <!-- User Role/Group Form -->
      <el-form-item label="用户角色" prop="role_id">
        <el-select v-model="new_user.role_id" placeholder="请选择用户角色">
          <el-option v-for="role in role_list" :label="role.name" :value="role.rid" key="role.name"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="用户组" prop="group_id" class="rtfd-setting-add-user-item">
        <el-select v-model="new_user.group_id" placeholder="请选择用户组">
          <el-option v-for="group in group_list" :label="group.name" :value="group.gid" key="group.name"></el-option>
        </el-select>
      </el-form-item>

      <!-- Button -->
      <el-form-item>
        <el-button type="primary" @click="add_user">Register</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
export default {
  name: 'rtfd-setting-user-list',
  data: () => {
    return {
      del_visible: false,
      will_del_user: {
        uid: '',
        name: ''
      },
      update_visible: false,
      will_update_user: {
        uid: '',
        username: '',
        password: '',
        role_id: '',
        group_id: ''
      },
      new_user: {
        username: '',
        password: '',
        role_id: '',
        group_id: ''
      },
      form_validate: {
        username: { required: true, message: '请输入用户名' },
        password: { required: true, message: '请输入密码' },
        role_id: { required: true, message: '请选择用户角色' },
        group_id: { required: true, message: '请选择用户组' }
      },
      update_form_validate: {
        username: { required: true, message: '请输入用户名' },
        role_id: { required: true, message: '请选择用户角色' },
        group_id: { required: true, message: '请选择用户组' }
      }
    }
  },
  props: {
    user_list: {
      required: true,
      type: Array
    },
    role_list: {
      required: true,
      type: Array
    },
    group_list: {
      required: true,
      type: Array
    }
  },
  methods: {
    add_user: function() {
      this.$refs['rtfd-setting-add-user-form'].validate((valid) => {
        if (valid) {
          this.$emit('add_user', {
            username: this.new_user.username,
            password: this.new_user.password,
            role_id: this.new_user.role_id,
            group_id: this.new_user.group_id
          })
        }

        this.$refs['rtfd-setting-add-user-form'].resetFields()
      })
    },
    del_user: function(index, real = false) {
      if (real === false) {
        this.del_visible = true
        this.will_del_user.uid = this.user_list[index].user_id
        this.will_del_user.name = this.user_list[index].user_name
      } else {
        this.del_visible = false
        this.$emit('del_user', this.will_del_user.uid)
      }
    },
    update_user: function(index, real = false) {
      if (real === false) {
        this.will_update_user.uid = this.user_list[index].user_id
        this.will_update_user.username = this.user_list[index].user_name
        this.will_update_user.password = ''

        for (let i = 0; i < this.role_list.length; ++i) {
          if (this.role_list[i].name === this.user_list[index].role_name) {
            this.will_update_user.role_id = this.role_list[i].rid
            break
          }
        }

        for (let i = 0; i < this.group_list.length; ++i) {
          if (this.group_list[i].name === this.user_list[index].group_name) {
            this.will_update_user.group_id = this.group_list[i].gid
            break
          }
        }

        this.update_visible = true
      } else {
        this.$refs['rtfd-setting-update-user-form'].validate((valid) => {
          if (valid) {
            this.update_visible = false
            this.$emit('update_user', {
              uid: this.will_update_user.uid,
              username: this.will_update_user.username,
              password: this.will_update_user.password,
              role_id: this.will_update_user.role_id,
              group_id: this.will_update_user.group_id
            })

            this.$refs['rtfd-setting-update-user-form'].resetFields()
          }
        })
      }
    }
  },
  components: {}
}
</script>

<style scoped>
  h2 {
    padding: 1rem;
    border-bottom: 1px solid rgba(0, 0, 0, .15);
  }

  #rtfd-setting-add-user-form {
    /*display: flex;*/
    /*flex-direction: column;*/
  }

  .rtfd-setting-add-user-item {
    width: 50%;
  }

  @media screen and (max-width: 768px) {
    .rtfd-setting-add-user-item {
      width: 100%;
    }
  }

  .rtfd-setting-update-user-item {
    width: 75%;
  }
</style>
