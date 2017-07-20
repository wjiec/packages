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
      <transition name="el-zoom-in-center" mode="out-in" v-on:after-leave="toggle_setting">
        <el-row key="k_user_list" id="rtfd-setting-user-list" v-show="view_list.user_list" type="flex" justify="center">
          <rtfd-setting-user-list
            :user_list="user_list"
            @add_user="add_user"
            @del_user="del_user"
            @update_user="update_user"
          ></rtfd-setting-user-list>
        </el-row>
      </transition>

      <!-- DocsList Module -->
      <transition name="el-zoom-in-center" mode="out-in" v-on:after-leave="toggle_setting">
        <el-row key="k_docs_list" id="rtfd-setting-docs-list" v-show="view_list.docs_list" type="flex" justify="center">
          <rtfd-setting-docs-list
            :docs_list="docs_list"
            @add_docs="add_docs"
            @del_docs="del_docs"
            @update_docs="update_docs"
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
import rtfdSettingDocsList from './setting/docs_list'

export default {
  name: 'rtfd-setting',
  data: () => {
    return {
      user_list: [],
      docs_list: [],
      current_view: null,
      view_list: {
        user_list: true,
        docs_list: false
      }
    }
  },
  mounted: function() {
    this.pre_toggle('user_list')

    // Getting users list
    this.$action('GetUsers').then((response) => {
      this.user_list = response.data.users
    }, () => {
      Message.error('Oops~, 获取用户列表出现错误了')
    })
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
      }
    },
    pre_toggle: function(_in) {
      // reset all view state
      for (let k in this.view_list) {
        this.view_list[k] = false
      }

      this.current_view = _in
    },
    add_user: function() {

    },
    del_user: function(uid) {
      console.log('del_user', uid)
    },
    update_user: function() {

    },
    add_docs: function() {

    },
    del_docs: function() {

    },
    update_docs: function() {

    }
  },
  components: {rtfdSettingUserList, rtfdSettingDocsList}
}
</script>

<style scoped>
  #rtfd-setting-area {
    width: 100%;
  }

  #rtfd-setting-nav {
    height: 100%;
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
