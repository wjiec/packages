<template>
  <el-menu mode="horizontal" theme="dark" id="rtfd-nav-bar-menu">
    <el-menu-item index="setting"><i class="el-icon-setting"></i></el-menu-item>
    <el-menu-item index="account"><i class="el-icon-information"></i></el-menu-item>
    <el-menu-item v-show="loading" index=""><i class="el-icon-loading"></i></el-menu-item>
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
