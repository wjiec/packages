<template>
  <div id="rtfd-markdown-area">

    <!-- Markdown Navigation -->
    <el-col id="rtfd-markdown-nav" :lg="4" :md="6" :sm="6" :xs="24">
      <el-menu>
        <el-menu-item index="1"><i class="el-icon-menu"></i>asd</el-menu-item>
        <el-menu-item index="2"><i class="el-icon-menu"></i>asd</el-menu-item>
      </el-menu>
    </el-col>

    <!-- Markdown Body -->
    <el-col id="rtfd-markdown-body" :lg="20" :md="18" :sm="18" :xs="24">
      <el-row type="flex" justify="center" align="middle">
        <p>123</p>
      </el-row>
    </el-col>

    <!-- Markdown End -->
  </div>
</template>

<script>
  export default {
    name: 'rtfd-markdown',
    data: () => {
      return {}
    },
    components: {}
  }
</script>

<style scoped>
  #rtfd-markdown-area {
    width: 100%;
  }

  #rtfd-markdown-nav, #rtfd-markdown-body {
    height: 100%;
    overflow: scroll;
  }

  #rtfd-markdown-nav {
    background: #eef1f6;
    border-right: 1px solid rgba(0, 0, 0, .08);
  }

  #rtfd-markdown-nav li {
    border-top: 1px solid rgba(255, 255, 255, .85);
    border-bottom: 1px solid rgba(0, 0, 0, .08);
  }

  #rtfd-markdown-body {
    border-left: 1px solid rgba(255, 255, 255, .85);
  }
</style>
