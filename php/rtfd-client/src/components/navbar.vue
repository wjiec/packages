<template>
  <el-menu mode="horizontal" theme="dark" id="rtfd-nav-bar-menu" @select="checkout">

    <!-- Setting Menu -->
    <el-menu-item index="setting" v-if="is_admin"><i class="el-icon-setting"></i></el-menu-item>

    <!-- Account Menu -->
    <el-menu-item index="account" v-if="!is_guest"><i class="el-icon-information"></i></el-menu-item>

    <!-- Markdown Menu -->
    <el-menu-item index="markdown"><i class="el-icon-menu"></i></el-menu-item>

    <!-- Loading Status -->
    <el-menu-item v-show="loading" index=""><i class="el-icon-loading"></i></el-menu-item>

    <!-- Docs List -->
    <el-submenu index="docs" v-if="docs_show" :default-active="select_doc">
      <template slot="title"><i class="el-icon-more" v-if="!is_mobile"></i>{{ select_doc }}&nbsp;</template>
      <el-menu-item v-for="doc in docs" key="doc.name" :index="doc.name"><i :class="doc.name === select_doc ? 'el-icon-star-on' : 'el-icon-star-off'"></i>{{ doc.name }}&nbsp;</el-menu-item>
    </el-submenu>

    <!-- Nav-bar End -->
  </el-menu>
</template>

<script>
export default {
  name: 'rtfd-nav-bar',
  data: () => {
    return {}
  },
  props: {
    loading: {
      required: true,
      type: Boolean
    },
    docs: {
      required: true,
      type: Array
    },
    select_doc: {
      required: true,
      type: String
    },
    is_guest: {
      required: true
    },
    is_admin: {
      required: true
    }
  },
  computed: {
    docs_show: function() {
      return this.docs.length > 0
    },
    doc_title: function() {
      return this.$mobile ? '' : this.select_doc
    },
    is_mobile: function() {
      return this.$mobile
    }
  },
  methods: {
    checkout: function(index, indexPath) {
      // select doc item
      if (indexPath[0] === 'docs') {
        this.$emit('checkout', index)
      }

      // select account
      if (indexPath[0] === 'markdown') {
        this.$emit('toggle_view', indexPath[0])
      }

      // select account
      if (indexPath[0] === 'account') {
        this.$emit('toggle_view', indexPath[0])
      }

      // select setting
      if (indexPath[0] === 'setting') {
        this.$emit('toggle_view', indexPath[0])
      }
    }
  },
  components: {}
}
</script>

<style scoped>
  .el-menu--horizontal .el-menu-item {
    float: right;
  }
</style>
