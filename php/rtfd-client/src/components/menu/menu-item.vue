<template>
  <el-submenu v-if="has_sub_menu" :index="index">
    <template slot="title"><i class="el-icon-menu"></i>{{ name }}</template>
    <template v-for="child in children">
      <rtfd-menu v-if="!child.is_doc"
                 :name="child.name"
                 :data="child.data"
                 :index="child.index"
                 :children="child.children"
                 :key="child.index"/>
    </template>
    <template v-for="child in children">
      <el-menu-item v-if="child.is_doc" :index="child.index" :data-path="child.data">{{ child.name }}</el-menu-item>
    </template>
  </el-submenu>
  <el-menu-item v-else :index="index"><i class="el-icon-menu"></i>{{ name }}</el-menu-item>
</template>

<script>
  export default {
    name: 'rtfd-menu',
    props: {
      name: {
        required: true,
        type: String
      },
      data: {
        required: true,
        type: String
      },
      index: {
        required: true,
        type: String
      },
      children: {
        required: true,
        type: Array
      }
    },
    computed: {
      has_sub_menu: function() {
        return this.children && this.children.length !== 0
      }
    }
  }
</script>

<style scoped>
</style>
