<template>
  <el-table :data="dataArray" style="width: 80%;" max-height="300" :stripe="true" :border="true" @scroll.native="scrollTable" ref="dataTable">
    <el-table-column prop="date" label="Data" width="180"></el-table-column>
    <el-table-column prop="name" label="Name" width="180"></el-table-column>
    <el-table-column prop="address" label="Address"></el-table-column>
    <div slot="append" :class="{showLoading: !loadingNewData}">
      <p><i class="el-icon-loading"></i>New data is coming ...</p>
    </div>
  </el-table>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      loadingNewData: false,
      dataArray: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }]
    }
  },
  methods: {
    onInfinite: function (event) {
      if (!this.loadingNewData) {
        let containerHeight = event.target.clientHeight
        let scrollDistance = event.target.scrollTop
        let scrollHeight = event.target.scrollHeight

        // bottom
        if (containerHeight + scrollDistance === scrollHeight) {
          this.loadingNewData = true
          setTimeout(() => {
            this.dataArray.push(this.dataArray[0])
            this.loadingNewData = false
          }, 2000)
        }
      }
    }
  },
  mounted: function () {
    let container = this.$refs.dataTable.$el
    let wrapper = container.querySelector('.el-table__body-wrapper')
    wrapper.addEventListener('scroll', this.onInfinite)
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.showLoading {
  display: none;
}
</style>
