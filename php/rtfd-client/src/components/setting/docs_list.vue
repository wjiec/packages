<template>
  <div style="width: 100%;">
    <h2>文档库列表</h2>
    <el-table :data="docs_list" style="width: 100%" border>
      <el-table-column prop="cid" label="文档库ID"></el-table-column>
      <el-table-column prop="name" label="文档库名"></el-table-column>
      <el-table-column prop="path" label="路径"></el-table-column>
      <el-table-column prop="owner_id" label="所有者"></el-table-column>
      <el-table-column prop="group_id" label="用户组"></el-table-column>
      <el-table-column prop="mpl" label="最小权限"></el-table-column>
      <el-table-column label="操作">
        <template scope="scope">
          <!-- Edit Group -->
          <el-dialog title="编辑" :visible.sync="update_visible" :size="$mobile ? 'large' : 'small'">
            <!-- EditUser Form -->
            <el-form id="rtfd-setting-update-doc-form" ref="rtfd-setting-update-doc-form" label-position="left" label-width="80px" :model="will_update_doc" :rules="form_validate">

              <!-- Doc Name -->
              <el-form-item label="文档库名" prop="name" class="rtfd-setting-update-doc-item">
                <el-input v-model="will_update_doc.name"></el-input>
              </el-form-item>

              <!-- Doc Path -->
              <el-form-item label="文档路径" prop="path" class="rtfd-setting-update-doc-item">
                <el-input v-model="will_update_doc.path"></el-input>
              </el-form-item>

              <!-- Doc Path -->
              <el-form-item label="最小权限" prop="mpl" class="rtfd-setting-update-doc-item">
                <el-input v-model="will_update_doc.mpl"></el-input>
              </el-form-item>

              <!-- Doc Owner -->
              <el-form-item label="所有者ID" prop="owner_id" class="rtfd-setting-update-doc-item">
                <el-select v-model="will_update_doc.owner_id" placeholder="请选择所有者">
                  <el-option v-for="user in user_list" :label="user.user_name" :value="user.user_id" key="user.user_name"></el-option>
                </el-select>
              </el-form-item>

              <!-- Doc Group -->
              <el-form-item label="所有组ID" prop="group_id" class="rtfd-setting-update-doc-item">
                <el-select v-model="will_update_doc.group_id" placeholder="请选择所有者">
                  <el-option v-for="group in group_list" :label="group.name" :value="group.gid" key="group.name"></el-option>
                </el-select>
              </el-form-item>

              <!-- Update Dialog End -->
            </el-form>

            <!-- EditUser Button -->
            <div slot="footer" class="dialog-footer">
              <el-button @click="update_visible = false">取 消</el-button>
              <el-button type="primary" @click="update_doc(0, true)">确 定</el-button>
            </div>
          </el-dialog>
          <el-button @click.native.prevent="update_doc(scope.$index)" type="text" size="small" :disabled="false">编辑</el-button>

          <!-- Delete Docs -->
          <el-dialog title="警告" :visible.sync="del_visible" size="tiny">
            <span>确定要删除 {{ will_del_doc.name }}</span>
            <span slot="footer" class="dialog-footer">
              <el-button @click="del_visible = false">取 消</el-button>
              <el-button type="primary" @click="del_doc(0, true)">确 定</el-button>
            </span>
          </el-dialog>
          <el-button @click.native.prevent="del_doc(scope.$index)" type="text" size="small" :disabled="false">删除</el-button>

          <!-- Operator End -->
        </template>
      </el-table-column>
    </el-table>

    <!-- Add User Module  -->
    <h2>新建文档库</h2>
    <el-form id="rtfd-setting-add-doc-form" ref="rtfd-setting-add-doc-form" label-position="left" label-width="80px" :model="new_doc" :rules="form_validate">

      <!-- Doc Name -->
      <el-form-item label="文档库名" prop="name" class="rtfd-setting-add-doc-item">
        <el-input v-model="new_doc.name"></el-input>
      </el-form-item>

      <!-- Doc Path -->
      <el-form-item label="文档路径" prop="path" class="rtfd-setting-add-doc-item">
        <el-input v-model="new_doc.path"></el-input>
      </el-form-item>

      <!-- Doc Path -->
      <el-form-item label="最小权限" prop="mpl" class="rtfd-setting-add-doc-item">
        <el-input v-model="new_doc.mpl"></el-input>
      </el-form-item>

      <!-- Doc Owner -->
      <el-form-item label="所有者ID" prop="owner_id" class="rtfd-setting-add-doc-item">
        <el-select v-model="new_doc.owner_id" placeholder="请选择所有者">
          <el-option v-for="user in user_list" :label="user.user_name" :value="user.user_id" key="user.user_name"></el-option>
        </el-select>
      </el-form-item>

      <!-- Doc Group -->
      <el-form-item label="所有组ID" prop="group_id" class="rtfd-setting-add-doc-item">
        <el-select v-model="new_doc.group_id" placeholder="请选择所有者">
          <el-option v-for="group in group_list" :label="group.name" :value="group.gid" key="group.name"></el-option>
        </el-select>
      </el-form-item>

      <!-- Button -->
      <el-form-item>
        <el-button type="primary" @click="add_doc">Add</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'rtfd-setting-docs-list',
  data: () => {
    return {
      del_visible: false,
      will_del_doc: {
        cid: '',
        name: ''
      },
      update_visible: false,
      will_update_doc: {
        cid: '',
        name: '',
        path: '',
        mpl: '',
        owner_id: '1',
        group_id: '1'
      },
      new_doc: {
        name: '',
        path: '',
        mpl: '',
        owner_id: '1',
        group_id: '1'
      },
      form_validate: {
        name: {required: true, message: '文档库名不能为空'},
        path: {required: true, message: '文档库路径不能为空'},
        mpl: {required: true, message: '最小权限不能为空'},
        owner_id: {required: true, message: '文档库所有者不能为空'},
        group_id: {required: true, message: '文档库所有组不能为空'}
      }
    }
  },
  props: {
    user_list: {
      required: true,
      type: Array
    },
    group_list: {
      required: true,
      type: Array
    },
    docs_list: {
      required: true,
      type: Array
    }
  },
  methods: {
    add_doc: function() {
      this.$refs['rtfd-setting-add-doc-form'].validate((valid) => {
        if (valid) {
          this.$emit('add_doc', {
            name: this.new_doc.name,
            path: this.new_doc.path,
            mpl: this.new_doc.mpl,
            owner_id: this.new_doc.owner_id,
            group_id: this.new_doc.group_id
          })

          this.$refs['rtfd-setting-add-doc-form'].resetFields()
        }
      })
    },
    update_doc: function(index, real = false) {
      if (real) {
        this.update_visible = false
        this.$emit('update_doc', {
          cid: this.will_update_doc.cid,
          name: this.will_update_doc.name,
          path: this.will_update_doc.path,
          mpl: this.will_update_doc.mpl,
          owner_id: this.will_update_doc.owner_id,
          group_id: this.will_update_doc.group_id
        })
      } else {
        this.update_visible = true

        this.will_update_doc.cid = this.docs_list[index].cid
        this.will_update_doc.name = this.docs_list[index].name
        this.will_update_doc.path = this.docs_list[index].path
        this.will_update_doc.mpl = this.docs_list[index].mpl
        this.will_update_doc.owner_id = this.docs_list[index].owner_id
        this.will_update_doc.group_id = this.docs_list[index].group_id
      }
    },
    del_doc: function(index, real = false) {
      if (real) {
        this.del_visible = false
        this.$emit('del_doc', {
          cid: this.will_del_doc.cid
        })
      } else {
        this.del_visible = true

        this.will_del_doc.cid = this.docs_list[index].cid
        this.will_del_doc.name = this.docs_list[index].name
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

  .rtfd-setting-add-doc-item {
    width: 50%;
  }

  @media screen and (max-width: 768px) {
    .rtfd-setting-add-doc-item {
      width: 100%;
    }
  }
</style>
