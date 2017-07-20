<template>
  <div id="rtfd-markdown-area">

    <!-- Markdown Navigation -->
    <el-col id="rtfd-markdown-nav" :lg="4" :md="6" :sm="6" :xs="24">
      <el-menu
        v-if="menu_show"
          :router="true"
          :default-active="default_active"
          :default-opened="default_opened"
          @select="select_file"
          @open="open_folder"
      >

        <!-- Menu Tree -->
        <rtfd-markdown-tree :doc_tree="doc_tree"></rtfd-markdown-tree>
      </el-menu>
    </el-col>

    <!-- Markdown Body -->
    <el-col id="rtfd-markdown-body" :lg="20" :md="18" :sm="18" :xs="24">
      <el-row type="flex" justify="center">
        <div id="rtfd-markdown-contents" v-html="markdown_content">
        </div>
      </el-row>
    </el-col>

    <!-- Markdown End -->
  </div>
</template>

<script>
import rtfdMarkdownTree from './markdown/tree'

export default {
  name: 'rtfd-markdown',
  data: () => {
    return {}
  },
  props: {
    doc_tree: {
      required: true
    },
    doc_content: {
      required: true,
      type: String
    },
    default_active: {
      required: true
    }
  },
  methods: {
    select_file: function(index, indexPath) {
      this.$emit('open_file', 'file', index, indexPath)
    },
    open_folder: function(index, indexPath) {
      this.$emit('open_file', 'folder', index, indexPath)
    }
  },
  watch: {
    default_active: function(fileName) {
      this.$emit('open_file', 'file', fileName, [fileName])
    }
  },
  computed: {
    menu_show: function() {
      if (!this.doc_tree) {
        return false
      }
      return true
    },
    markdown_content: function() {
      return this.$marked(this.doc_content)
    },
    default_opened: function() {
      return []
    }
  },
  components: {rtfdMarkdownTree}
}
</script>

<style>
  #rtfd-markdown-area {
    width: 100%;
  }

  #rtfd-markdown-nav, #rtfd-markdown-body {
    height: 100%;
    overflow-x: hidden;
    overflow-y: scroll;
  }

  #rtfd-markdown-nav {
    background: #eef1f6;
    border-right: 1px solid rgba(0, 0, 0, .08);
  }

  /*#rtfd-markdown-nav li {*/
    /*border-top: 1px solid rgba(255, 255, 255, .85);*/
    /*border-bottom: 1px solid rgba(0, 0, 0, .08);*/
  /*}*/

  #rtfd-markdown-body {
    padding: 1rem 8rem;
    border-left: 1px solid rgba(255, 255, 255, .85);
  }

  @media screen and (max-width: 1200px) {
    #rtfd-markdown-body {
      padding: 1rem 4rem;
    }
  }

  @media screen and (max-width: 987px) {
    #rtfd-markdown-body {
      padding: 1rem 1rem;
    }
  }

  #rtfd-markdown-contents {
    width: 100%;
    max-width: 100%;
  }
</style>
