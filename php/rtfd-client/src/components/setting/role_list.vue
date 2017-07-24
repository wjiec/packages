<template>
  <div style="width: 100%;">
    <h2>用户角色列表</h2>
    <el-table :data="role_list" style="width: 100%" border>
      <el-table-column prop="rid" label="角色ID"></el-table-column>
      <el-table-column prop="name" label="角色名"></el-table-column>
      <el-table-column prop="privilege_level" label="权限级别"></el-table-column>
      <el-table-column label="操作">
        <template scope="scope">
          <!-- Edit Role -->
          <el-dialog title="编辑" :visible.sync="update_visible" size="small">
            <!-- EditUser Form -->
            <el-form ref="rtfd-setting-update-role-form" :model="will_update_role" label-position="left" label-width="80px" :rules="form_validate">
              <!-- Username/Password Form -->
              <el-form-item label="角色名" prop="name" class="rtfd-setting-update-role-item">
                <el-input v-model="will_update_role.name"></el-input>
              </el-form-item>
              <el-form-item label="权限等级" prop="privilege_level" class="rtfd-setting-update-role-item">
                <el-input v-model="will_update_role.privilege_level"></el-input>
              </el-form-item>
            </el-form>
            <!-- EditUser Button -->
            <div slot="footer" class="dialog-footer">
              <el-button @click="update_visible = false">取 消</el-button>
              <el-button type="primary" @click="update_role(0, true)">确 定</el-button>
            </div>
          </el-dialog>
          <el-button @click.native.prevent="update_role(scope.$index)" type="text" size="small" :disabled="false">编辑</el-button>

          <!-- Delete Role -->
          <el-dialog title="警告" :visible.sync="del_visible" size="tiny">
            <span>确定要删除 {{ will_del_role.name }}</span>
            <span slot="footer" class="dialog-footer">
              <el-button @click="del_visible = false">取 消</el-button>
              <el-button type="primary" @click="del_role(0, true)">确 定</el-button>
            </span>
          </el-dialog>
          <el-button @click.native.prevent="del_role(scope.$index)" type="text" size="small" :disabled="true">删除</el-button>

          <!-- Operator End -->
        </template>
      </el-table-column>
    </el-table>

    <!-- Add User Module  -->
    <h2>新增用户角色</h2>
    <el-form id="rtfd-setting-add-role-form" ref="rtfd-setting-add-role-form" label-position="left" label-width="80px" :model="new_role" :rules="form_validate">

      <!-- Username/Password Form -->
      <el-form-item label="角色名" prop="name" class="rtfd-setting-add-role-item">
        <el-input v-model="new_role.name"></el-input>
      </el-form-item>
      <el-form-item label="权限等级" prop="privilege_level" class="rtfd-setting-add-role-item">
        <el-input v-model="new_role.privilege_level"></el-input>
      </el-form-item>

      <!-- Button -->
      <el-form-item>
        <el-button type="primary" @click="add_role">Add</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'rtfd-setting-role-list',
  props: {
    role_list: {
      required: true,
      type: Array
    }
  },
  data: () => {
    return {
      del_visible: false,
      will_del_role: {
        rid: '',
        name: ''
      },
      update_visible: false,
      will_update_role: {
        rid: '',
        name: '',
        privilege_level: ''
      },
      new_role: {
        name: '',
        privilege_level: ''
      },
      form_validate: {
        name: { required: true, message: '请输入角色名' },
        privilege_level: { required: true, message: '权限等级不合法' }
      }
    }
  },
  methods: {
    add_role: function() {
      this.new_role.privilege_level = parseInt(this.new_role.privilege_level)
      if (isNaN(this.new_role.privilege_level)) {
        this.new_role.privilege_level = ''
      }
      if (this.new_role.privilege_level < 0 || this.new_role.privilege_level > 100) {
        this.new_role.privilege_level = ''
      }

      this.$refs['rtfd-setting-add-role-form'].validate((valid) => {
        if (valid) {
          this.$emit('add_role', {
            name: this.new_role.name,
            pl: this.new_role.privilege_level
          })

          this.$refs['rtfd-setting-add-role-form'].resetFields()
        }
      })
    },
    update_role: function(index, real = false) {
      if (real) {
        this.$refs['rtfd-setting-update-role-form'].validate((valid) => {
          if (valid) {
            this.will_update_role.privilege_level = parseInt(this.will_update_role.privilege_level)
            if (isNaN(this.will_update_role.privilege_level) ||
              this.will_update_role.privilege_level < 0 ||
              this.will_update_role.privilege_level > 100) {
              // --------------------------------------------
              this.will_update_role.privilege_level = ''
              return false
            }

            this.update_visible = false
            this.$emit('update_role', {
              rid: this.will_update_role.rid,
              name: this.will_update_role.name,
              pl: this.will_update_role.privilege_level
            })
          } else {
            return false
          }
        })
      } else {
        this.update_visible = true

        this.will_update_role.rid = this.role_list[index].rid
        this.will_update_role.name = this.role_list[index].name
        this.will_update_role.privilege_level = parseInt(this.role_list[index].privilege_level)
      }
    },
    del_role: function(index, real = false) {
      console.log(index, real)
      if (real) {
        this.del_visible = false
      } else {
        this.del_visible = true
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

  .rtfd-setting-add-role-item {
    width: 50%;
  }

  @media screen and (max-width: 768px) {
    .rtfd-setting-add-role-item {
      width: 100%;
    }
  }
</style>
