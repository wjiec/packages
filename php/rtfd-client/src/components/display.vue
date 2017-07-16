<template>
  <el-col id="rtfd-display" :lg="20" :md="18" :sm="16" :xs="0">
    <el-row id="rtfd-display-area" type="flex" justify="center" :align="contents.length ? '' : 'middle'">
      <transition name="el-fade-in-linear">
        <div v-if="contents.length" v-html="markdown_contents"></div>
        <div class="waiting-loading" v-else="contents.length">
          <p><i class="el-icon-loading"></i></p>
          <p>Read The Documents</p>
        </div>
      </transition>
    </el-row>
  </el-col>
</template>

<script>
  let marked = require('marked')

  marked.setOptions({
    highlight: function (code) {
      return require('highlight.js').highlightAuto(code).value
    }
  })

  export default {
    name: 'rtfd-display',
    props: {
      contents: {
        required: true,
        type: String
      }
    },
    computed: {
      markdown_contents: function() {
        return this.contents.length ? marked(this.contents, {
          highlight: function (code) {
            return require('highlight.js').highlightAuto(code).value
          }
        }) : ''
      }
    }
  }
</script>

<style scoped>
  #rtfd-display {
    overflow: scroll;
    margin: 1rem;
    -webkit-box-shadow: inset 0 0 20px 2px rgba(0, 0, 0, .15);
    -moz-box-shadow: inset 0 0 20px 2px rgba(0, 0, 0, .15);
    box-shadow: inset 0 0 20px 2px rgba(0, 0, 0, .15);
    border-radius: 3px;
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
  }
  #rtfd-display-area {
    height: 100%;
  }
  .waiting-loading {
    text-align: center;
  }
</style>
