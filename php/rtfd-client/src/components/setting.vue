<template>
  <div id="rtfd-setting-area">

    <!-- Setting Navigation -->
    <el-col id="rtfd-setting-nav" :lg="4" :md="6" :sm="6" :xs="24">
      <!-- User Manager -->
      <el-menu @select="select_setting" default-active="user_list">
        <el-submenu index="user_manager">
          <!-- Menu Title -->
          <template slot="title"><i class="el-icon-menu"></i>用户管理</template>

          <!-- Menu List -->
          <el-menu-item index="user_list">用户列表</el-menu-item>
          <el-menu-item index="role_list">用户角色列表</el-menu-item>
          <el-menu-item index="group_list">用户组列表</el-menu-item>
        </el-submenu>

        <!-- Document Manager -->
        <el-submenu index="docs_manager">
          <!-- Menu Title -->
          <template slot="title"><i class="el-icon-menu"></i>文档管理</template>

          <!-- Menu List -->
          <el-menu-item index="docs_list">文档列表</el-menu-item>
        </el-submenu>

        <!-- Setting End -->
      </el-menu>
    </el-col>

    <!-- Setting Body -->
    <el-col id="rtfd-setting-body" :lg="20" :md="18" :sm="18" :xs="24">

      <!-- UserList Module -->
      <transition name="el-fade-in" mode="out-in" v-on:after-leave="toggle_setting">
        <el-row key="k_user_list" id="rtfd-setting-user-list" v-show="view_list.user_list" type="flex" justify="center">
          <rtfd-setting-user-list
            :user_list="user_list"
            :role_list="role_list"
            :group_list="group_list"
            @add_user="add_user"
            @del_user="del_user"
            @update_user="update_user"
          ></rtfd-setting-user-list>
        </el-row>
      </transition>

      <!-- UserList Module -->
      <transition name="el-fade-in" mode="out-in" v-on:after-leave="toggle_setting">
        <el-row key="k_user_list" id="rtfd-setting-role-list" v-show="view_list.role_list" type="flex" justify="center">
          <rtfd-setting-role-list
            :role_list="role_list"
            @add_role="add_role"
            @update_role="update_role"
          ></rtfd-setting-role-list>
        </el-row>
      </transition>

      <!-- UserManager Module -->
      <transition name="el-fade-in" mode="out-in" v-on:after-leave="toggle_setting">
        <el-row key="k_user_list" id="rtfd-setting-group-list" v-show="view_list.group_list" type="flex" justify="center">
          <rtfd-setting-group-list
            :group_list="group_list"
            @add_group="add_group"
            @update_group="update_group"
          ></rtfd-setting-group-list>
        </el-row>
      </transition>

      <!-- DocsList Module -->
      <transition name="el-fade-in" mode="out-in" v-on:after-leave="toggle_setting">
        <el-row key="k_docs_list" id="rtfd-setting-docs-list" v-show="view_list.docs_list" type="flex" justify="center">
          <rtfd-setting-docs-list
            :user_list="user_list"
            :group_list="group_list"
            :docs_list="docs_list"
            @add_doc="add_doc"
            @update_doc="update_doc"
            @del_doc="del_doc"
          ></rtfd-setting-docs-list>
        </el-row>
      </transition>

      <!-- Setting Body End -->
    </el-col>

    <!-- Markdown End -->
  </div>
</template>

<script>
import { Message } from 'element-ui'
import rtfdSettingUserList from './setting/user_list'
import rtfdSettingRoleList from './setting/role_list'
import rtfdSettingDocsList from './setting/docs_list'
import rtfdSettingGroupList from './setting/group_list'

export default {
  name: 'rtfd-setting',
  data: () => {
    return {
      user_list: [],
      role_list: [],
      group_list: [],
      docs_list: [],
      current_view: null,
      view_list: {
        user_list: true,
        role_list: false,
        group_list: false,
        docs_list: false
      }
    }
  },
  mounted: function() {
    this.pre_toggle('user_list')

    // Getting users list
    this.refresh_user()
  },
  methods: {
    select_setting: function(index, indexPath) {
      this.pre_toggle(indexPath[indexPath.length - 1])
    },
    toggle_setting: function() {
      switch (this.current_view) {
        case 'user_list': {
          this.view_list.user_list = true
          break
        }
        case 'docs_list': {
          this.view_list.docs_list = true
          break
        }
        case 'role_list': {
          this.view_list.role_list = true
          break
        }
        case 'group_list': {
          this.view_list.group_list = true
          break
        }
      }
    },
    pre_toggle: function(_in) {
      // reset all view state
      for (let k in this.view_list) {
        this.view_list[k] = false
      }

      this.current_view = _in
    },
    add_user: function(user) {
      this.$action('AddUser', user).then(() => {
        // success
        Message.success('Okay, 成功添加用户')
        // refresh user list
        this.refresh_user()
      }, (e) => {
        if (e.response.data.error.indexOf('duplicate') !== -1) {
          Message.error('Oops~, 用户已存在咯')
        } else {
          Message.error('Oops~, 添加新用户失败了')
        }
      })
    },
    del_user: function(uid) {
      this.$action('DelUser', {uid: uid}).then(() => {
        // success
        Message.success('Okay, 成功删除用户')
        // refresh user list
        this.refresh_user()
      }, (e) => {
        if (e.response.data.error.indexOf('admin') !== -1) {
          Message.error('Oops~, 这个管理员账户不可以删除哦')
        } else {
          Message.error('Oops~, 删除用户失败了')
        }
      })
    },
    update_user: function(user) {
      this.$action('UpdateUser', user).then(() => {
        // success
        Message.success('Okay, 成功更新用户')
        // refresh user list
        this.refresh_user()
      }, (e) => {
        if (e.response.data.error.indexOf('not found') !== -1) {
          Message.error('Oops~, 没找到这个用户')
        } else {
          Message.error('Oops~, 更新用户失败了')
        }
      })
    },
    add_role: function(role) {
      this.$action('AddRole', role).then(() => {
        // success
        Message.success('Okay, 成功创建用户角色')
        // refresh user list
        this.refresh_user()
      }, (e) => {
        if (e.response.data.error.indexOf('duplicate') !== -1) {
          Message.error('Oops~, 用户角色已经存在咯')
        } else {
          Message.error('Oops~, 添加用户角色失败了')
        }
      })
    },
    update_role: function(role) {
      this.$action('UpdateRole', role).then(() => {
        // success
        Message.success('Okay, 成功更新用户角色')
        // refresh user list
        this.refresh_user()
      }, (e) => {
        if (e.response.data.error.indexOf('not found') !== -1) {
          Message.error('Oops~, 没找到这个用户角色')
        } else if (e.response.data.error.indexOf('admin') !== -1) {
          Message.error('Oops~, 不能修改admin用户角色')
        } else {
          Message.error('Oops~, 更新用户角色失败了')
        }
      })
    },
    add_group: function(group) {
      this.$action('AddGroup', group).then(() => {
        // success
        Message.success('Okay, 成功创建用户组')
        // refresh user list
        this.refresh_user()
      }, (e) => {
        if (e.response.data.error.indexOf('duplicate') !== -1) {
          Message.error('Oops~, 用户组已经存在咯')
        } else {
          Message.error('Oops~, 添加用户组失败了')
        }
      })
    },
    update_group: function(group) {
      this.$action('UpdateGroup', group).then(() => {
        // success
        Message.success('Okay, 成功更新用户组')
        // refresh user list
        this.refresh_user()
      }, (e) => {
        if (e.response.data.error.indexOf('not found') !== -1) {
          Message.error('Oops~, 没找到这个用户组')
        } else if (e.response.data.error.indexOf('admin') !== -1) {
          Message.error('Oops~, 不能修改admin用户组')
        } else {
          Message.error('Oops~, 更新用户组失败了')
        }
      })
    },
    add_doc: function(doc) {
      this.$action('AddDoc', doc).then(() => {
        // success
        Message.success('Okay, 成功创建文档库')
        // refresh user list
        this.refresh_user()
      }, (e) => {
        if (e.response.data.error.indexOf('duplicate') !== -1) {
          Message.error('Oops~, 文档库已经存在咯')
        } else if (e.response.data.error.indexOf('owner not found') !== -1) {
          Message.error('Oops~, 用户不存在')
        } else if (e.response.data.error.indexOf('group not found') !== -1) {
          Message.error('Oops~, 用户组不存在')
        } else if (e.response.data.error.indexOf('directory invalid') !== -1) {
          Message.error('Oops~, 文档库路径非法')
        } else {
          Message.error('Oops~, 添加文档库失败了')
        }
      })
    },
    del_doc: function(cid) {
      this.$action('DelDoc', cid).then(() => {
        // success
        Message.success('Okay, 成功删除文档库')
        // refresh user list
        this.refresh_user()
      }, () => {
        Message.error('Oops~, 删除文档库失败了')
      })
    },
    update_doc: function(doc) {
      this.$action('UpdateDoc', doc).then(() => {
        // success
        Message.success('Okay, 成功更新文档库')
        // refresh user list
        this.refresh_user()
      }, (e) => {
        if (e.response.data.error.indexOf('not found') !== -1) {
          Message.error('Oops~, 没找到这个文档库')
        } else {
          Message.error('Oops~, 更新文档库失败了')
        }
      })
    },
    refresh_user: function() {
      // Getting users list
      this.$action('GetUsers').then((response) => {
        this.user_list = response.data.users
        this.role_list = response.data.roles
        this.group_list = response.data.groups
        this.docs_list = response.data.docs
      }, () => {
        Message.error('Oops~, 获取用户列表出现错误了')
      })
    }
  },
  components: {rtfdSettingUserList, rtfdSettingDocsList, rtfdSettingRoleList, rtfdSettingGroupList}
}
</script>

<style scoped>
  #rtfd-setting-area {
    width: 100%;
    height: 100%;
  }

  #rtfd-setting-nav, #rtfd-setting-body {
    height: 100%;
    overflow-x: hidden;
    overflow-y: scroll;
  }

  #rtfd-setting-nav {
    background: #eef1f6;
    border-right: 1px solid rgba(0, 0, 0, .08);
  }

  #rtfd-setting-body {
    padding: 1rem 8rem;
    border-left: 1px solid rgba(255, 255, 255, .85);
  }

  @media screen and (max-width: 1200px) {
    #rtfd-setting-body {
      padding: 1rem 4rem;
    }
  }

  @media screen and (max-width: 987px) {
    #rtfd-setting-body {
      padding: 1rem 1rem;
    }
  }
</style>
