<template>
    <el-col id="rtfd-nav-bar" :lg="4" :md="6" :sm="8" :xs="24">
      <el-menu @open="open_file" @close="open_file" @select="open_file">
        <rtfd-menu-item
          v-for="doc in docs"
          :name="doc.name"
          :index="doc.index"
          :data="doc.data"
          :children="doc.children"
          :key="doc.index"/>
      </el-menu>
    </el-col>
</template>

<script>
import rtfdMenuItem from './menu/menu-item'

export default {
  name: 'nav-bar',
  props: {
    docs: {
      required: true
    }
  },
  methods: {
    open_file: function(index, path) {
      let fileIndex = path[path.length - 1]
      fileIndex = fileIndex.split('-')
      fileIndex = fileIndex.map((v) => parseInt(v) - 1)
      this.$emit('load_file', this.find_file(fileIndex, this.docs))
    },
    find_file: function(index, docs) {
      if (index.length === 1) {
        return docs[index[0]].data
      }
      let newIndex = index.splice(0, 1)
      return this.find_file(index, docs[newIndex[0]].children)
    }
  },
  components: {rtfdMenuItem}
}
</script>

<style scoped>
  #rtfd-nav-bar {
    background: #eef1f6;
    overflow: scroll;
  }
</style>
