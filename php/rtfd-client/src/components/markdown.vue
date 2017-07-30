<template>
  <div id="rtfd-markdown-area">

    <!-- Markdown Navigation -->
    <el-col id="rtfd-markdown-nav" :class="{activate: open_tree || !default_active}" :lg="4" :md="6" :sm="6" :xs="markdown_tree_xs_span">
      <el-menu
        v-if="menu_show"
          :router="true"
          :default-active="default_active"
          :default-openeds="default_opened"
          :unique-opened="false"
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
import Vue from 'vue'
// var Vue = require('vue')
// import rtfdMarkdownTree from './markdown/tree'

const rtfdMarkdownTree = Vue.component('rtfdMarkdownTree', function (resolve) {
  require(['./markdown/tree'], resolve)
})

export default {
  name: 'rtfd-markdown',
  data: () => {
    return {
      markdown_tree_class: {
        activate: false
      },
      markdown_tree_xs_span: 24,
      default_opened: []
    }
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
    },
    open_tree: {
      required: true
    }
  },
  methods: {
    select_file: function(index, indexPath) {
      this.$emit('open_file', 'file', index, indexPath, true)
    },
    open_folder: function(index, indexPath) {
      this.$emit('open_file', 'folder', index, indexPath)
    }
  },
  watch: {
    default_active: function(fileName) {
      // only using v-show ~
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
    }
  },
  mounted: function() {
    if (this.default_active && this.default_active !== 'true') {
      // auto loading contents
      this.$emit('open_file', 'file', this.default_active, [this.default_active])
    }

    let segments = this.$route.path.substr(1).split('!')
    // pop last element
    segments.pop()
    // join first and second
    let firstEl = segments.shift()
    let secondEl = segments.shift()
    segments.unshift(firstEl + '!' + secondEl)
    // reassign segment
    if (segments.length > 1) {
      for (let i = 1; i < segments.length; ++i) {
        segments[i] = segments[i - 1] + '!' + segments[i]
      }
    }
    // assign default open
    this.default_opened = segments

    if (this.$mobile) {
      this.markdown_tree_xs_span = 20
    }
  },
  components: {rtfdMarkdownTree}
}
</script>

<style>
  #rtfd-markdown-area {
    width: 100%;
    max-height: 100vh;
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

  @media screen and (max-width: 687px) {
    #rtfd-markdown-nav {
      position: absolute;
      z-index: 99;
      left: -100%;
      opacity: 0;
      transition: all .5s ease-in-out;
    }

    #rtfd-markdown-nav.activate {
      left: 0%;
      opacity: 1;
    }
  }

  #rtfd-markdown-body {
    padding: 1rem 8rem;
    border-left: 1px solid rgba(255, 255, 255, .85);
    overflow-y: scroll;
    max-height: 95vh;
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

  @media screen and (max-width: 687px) {
    #rtfd-markdown-body {
      padding: 1rem 1rem;
    }
  }

  #rtfd-markdown-contents {
    width: 100%;
    max-width: 100%;
    height: 100%;
    max-height: 100%;
  }

  #rtfd-markdown-body table {
    padding: 5px;
  }

  #rtfd-markdown-body table, #rtfd-markdown-body th, #rtfd-markdown-body td {
    line-height: 1.5;
    /*text-align: center !important;*/
    border-collapse: collapse;
    border: 1px solid #aaa;
  }

  #rtfd-markdown-body th, #rtfd-markdown-body td {
    margin: 0;
    padding: .4rem .8rem;
  }

  #rtfd-markdown-body blockquote {
    border-left: 3px solid #aaa;
    margin: 0;
    padding-left: 1rem;
  }

  #rtfd-markdown-body code {
    padding: 2px 4px;
    background: #f0f0f0;
  }
</style>
