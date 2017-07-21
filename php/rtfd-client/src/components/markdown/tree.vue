<template>
  <div>
    <!-- Render Directory First -->
    <el-submenu v-for="doc in doc_tree" v-if="!doc.is_file" :index="doc.path" :key="doc.path">
      <!-- Directory Name -->
      <template slot="title"><i class="el-icon-menu"></i>{{ doc.name }}</template>

      <!-- Child Directory -->
        <rtfd-markdown-tree
          :doc_tree="doc.children"
        ></rtfd-markdown-tree>
    </el-submenu>

    <!-- Render Files Second -->
    <el-menu-item
      v-for="doc in doc_tree"
        v-if="doc.is_file"
          :index="doc.path"
          :key="doc.path"
    ><i class="el-icon-document"></i>{{ doc.name }}</el-menu-item>

    <!-- Markdown Tree End -->
  </div>
</template>

<script>
export default {
  name: 'rtfd-markdown-tree',
  data: () => {
    return {}
  },
  props: {
    doc_tree: {
      required: true
    }
  },
  components: {}
}
</script>

<style scoped>
</style>
