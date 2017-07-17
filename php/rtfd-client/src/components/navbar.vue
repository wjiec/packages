<template>
  <el-menu mode="horizontal" theme="dark" id="rtfd-nav-bar-menu">

    <!-- Setting Menu -->
    <el-menu-item index="setting"><i class="el-icon-setting"></i></el-menu-item>

    <!-- Account Menu -->
    <el-menu-item index="account"><i class="el-icon-information"></i></el-menu-item>

    <!-- Loading Status -->
    <el-menu-item v-show="loading" index=""><i class="el-icon-loading"></i></el-menu-item>

    <!-- Docs List -->
    <el-submenu index="docs" v-if="docs_show" :default-active="select_doc">
      <template slot="title"><i class="el-icon-more"></i>{{ select_doc }}&nbsp;</template>
      <el-menu-item v-for="doc in docs" key="doc.name" index="doc.name"><i :class="doc.name === select_doc ? 'el-icon-star-on' : 'el-icon-star-off'"></i>{{ doc.name }}&nbsp;</el-menu-item>
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
    }
  },
  computed: {
    docs_show: function() {
      return this.docs.length > 0
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
