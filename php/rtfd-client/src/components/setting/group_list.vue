<template>
  <div style="width: 100%;">
    <h2>用户组列表</h2>
    <el-table :data="group_list" style="width: 100%" border>
      <el-table-column prop="gid" label="组ID"></el-table-column>
      <el-table-column prop="name" label="用户组名"></el-table-column>
      <el-table-column label="操作">
        <template scope="scope">
          <!-- Edit Group -->
          <el-dialog title="编辑" :visible.sync="update_visible" :size="$mobile ? 'large' : 'small'">
            <!-- EditUser Form -->
            <el-form ref="rtfd-setting-update-group-form" :model="will_update_group" label-position="left" label-width="80px" :rules="form_validate">
              <!-- Username/Password Form -->
              <el-form-item label="组名" prop="name" class="rtfd-setting-update-group-item">
                <el-input v-model="will_update_group.name"></el-input>
              </el-form-item>
            </el-form>
            <!-- EditUser Button -->
            <div slot="footer" class="dialog-footer">
              <el-button @click="update_visible = false">取 消</el-button>
              <el-button type="primary" @click="update_group(0, true)">确 定</el-button>
            </div>
          </el-dialog>
          <el-button @click.native.prevent="update_group(scope.$index)" type="text" size="small" :disabled="false">编辑</el-button>

          <!-- Delete Group -->
          <el-dialog title="警告" :visible.sync="del_visible" size="tiny">
            <span>确定要删除 {{ will_del_group.name }}</span>
            <span slot="footer" class="dialog-footer">
              <el-button @click="del_visible = false">取 消</el-button>
              <el-button type="primary" @click="del_group(0, true)">确 定</el-button>
            </span>
          </el-dialog>
          <el-button @click.native.prevent="del_group(scope.$index)" type="text" size="small" :disabled="true">删除</el-button>

          <!-- Operator End -->
        </template>
      </el-table-column>
    </el-table>

    <!-- Add User Module  -->
    <h2>新增用户角色</h2>
    <el-form id="rtfd-setting-add-group-form" ref="rtfd-setting-add-group-form" label-position="left" label-width="80px" :model="new_group" :rules="form_validate">

      <!-- Username/Password Form -->
      <el-form-item label="用户组名" prop="name" class="rtfd-setting-add-group-item">
        <el-input v-model="new_group.name"></el-input>
      </el-form-item>

      <!-- Button -->
      <el-form-item>
        <el-button type="primary" @click="add_group">Add</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'rtfd-setting-group-list',
  data: () => {
    return {
      update_visible: false,
      will_update_group: {
        gid: '',
        name: ''
      },
      del_visible: false,
      will_del_group: {
        gid: '',
        name: ''
      },
      new_group: {
        name: ''
      },
      form_validate: {
        name: {required: true, message: '请输入用户组名'}
      }
    }
  },
  props: {
    group_list: {
      required: true,
      type: Array
    }
  },
  methods: {
    add_group: function() {
      this.$refs['rtfd-setting-add-group-form'].validate((valid) => {
        if (valid) {
          this.$emit('add_group', {
            name: this.new_group.name
          })

          this.$refs['rtfd-setting-add-group-form'].resetFields()
        }
      })
    },
    update_group: function(index, real = false) {
      if (real) {
        this.$refs['rtfd-setting-update-group-form'].validate((valid) => {
          if (valid) {
            this.update_visible = false
            this.$emit('update_group', {
              gid: this.will_update_group.gid,
              name: this.will_update_group.name
            })

            this.$refs['rtfd-setting-update-group-form'].resetFields()
          }
        })
      } else {
        this.update_visible = true

        this.will_update_group.gid = this.group_list[index].gid
        this.will_update_group.name = this.group_list[index].name
      }
    },
    del_group: function(index, real = false) {
      console.log(index, real)
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

  .rtfd-setting-add-group-item {
    width: 50%;
  }

  @media screen and (max-width: 768px) {
    .rtfd-setting-add-group-item {
      width: 100%;
    }
  }
</style>
