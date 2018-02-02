<template>
  <el-container>
    <!-- 数据表格 -->
    <el-table :data="dataArray" style="width: 80%;" :max-height="tableHeight" :border="true" @scroll.native="scrollTable" ref="dataTable">
      <el-table-column prop="date" label="Data" width="180"></el-table-column>
      <el-table-column prop="name" label="Name" width="180"></el-table-column>
      <el-table-column prop="address" label="Address"></el-table-column>
      <el-table-column prop="" label="" width="10"></el-table-column>
    </el-table>
    <!-- 假的滚动条 -->
    <div class="infinite-scrollbar" ref="infiniteScrollBar">
      <div class="infinite-scrollbar-block" :style="{marginTop: progress + 'px'}"></div>
    </div>
  </el-container>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      tableHeight: 250, // 表格高度
      loadingNewData: false, // 防止多次异步加载
      progress: 0, // 滚动条位置
      pageSize: 10, // 表格的固定大小
      currentScrollTop: 0, // 当前实际滚动位置
      startIndex: 0, // 实际显示的开始索引
      endIndex: 0, // 实际显示的结束索引
      dataSource: [], // 数据源
      dataArray: [] // 实际显示数据
    }
  },
  watch: {
    startIndex: function (newValue, oldValue) {
      // 根据当前索引计算滚动条应该在的位置
      this.progress = this.startIndex / (this.dataSource.length - this.pageSize) * (this.tableHeight - 20)
    }
  },
  methods: {
    onInfinite: function (event) {
      // 判断是否可以加载数据
      if (!this.loadingNewData) {
        let containerHeight = event.target.clientHeight
        let scrollDistance = event.target.scrollTop
        let scrollHeight = event.target.scrollHeight

        // 加载底部数据
        if (containerHeight + scrollDistance + 66 >= scrollHeight) {
          // 判断是否还有数据
          if (this.endIndex < this.dataSource.length - 1) {
            this.loadingNewData = true

            // 模拟网络加载
            setTimeout(() => {
              // 修改索引范围
              this.startIndex += 1
              this.endIndex += 1

              // 删除顶部元素和增加一个尾元素
              this.dataArray.splice(0, 1)
              this.dataArray.push(this.dataSource[this.endIndex])

              // 设置标志位可加载
              this.loadingNewData = false
              // 新数据进来后修改滚动条位置(真实的滚动条)
              event.target.scrollTop -= 65
            }, 1)
          }
        }

        // 加载顶部数据
        if (scrollDistance < 66) {
          // 判断顶部是否还有数据
          if (this.startIndex > 0) {
            this.loadingNewData = true

            // 模拟网络加载
            setTimeout(() => {
              // 修改索引范围
              this.startIndex -= 1
              this.endIndex -= 1

              // 删除尾元素和增加一个顶部元素
              this.dataArray.splice(0, 0, this.dataSource[this.startIndex])
              this.dataArray.pop()

              // 设置标志位可加载
              this.loadingNewData = false
              // 新数据进来后修改滚动条位置(真实的滚动条)
              event.target.scrollTop += 65
            }, 1)
          }
        }
      }
    }
  },
  mounted: function () {
    // 生成假数据
    for (let i = 0; i < 50; ++i) {
      this.dataSource.push({
        date: '2016-05-03',
        name: '王小虎' + i,
        address: '上海市普陀区金沙江路 1518 弄'
      })
    }

    // 初始化表格数据
    for (let i = 0; i < this.pageSize; ++i) {
      this.dataArray.push(this.dataSource[i])
    }

    // 初始化显示范围
    this.endIndex = this.pageSize - 1

    // 绑定滚动事件
    let container = this.$refs.dataTable.$el
    let wrapper = container.querySelector('.el-table__body-wrapper')
    wrapper.addEventListener('scroll', this.onInfinite)
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  /* 隐藏真实的滚动条 */
  ::-webkit-scrollbar {
    display: none;
  }

  /* 假滚动条样式 */
  .infinite-scrollbar {
    width: 10px;
    position: relative;
    right: 10px;
    background: #efefef;
  }

  /* 假滚动条滑块的样式 */
  .infinite-scrollbar .infinite-scrollbar-block {
    height: 20px;
    border-radius: 2px;
    background: #aaa;
    transition: all .25s ease-in-out;
  }
</style>
