一套用于构建用户界面的渐进式框架
-----------------------------


### Vue是什么
Vue.js 是一个JavaScript `MVVM`库, 是一套构建用户界面的渐进式框架.
它是以数据驱动和组件化的思想构建的，采用自底向上增量开发的设计。
相比于其他同类型的MVVM框架，Vue.js提供了更加简洁、更易于理解的API，
因为作者是华人的关系，Vue拥有着对华人开发者最友好的api文档和官方教程。

##### MVVM
所谓的`MVVM`即`Model+View+ViewModel`, View的变化会同步修改`ViewModel`中绑定的变量,
`ViewModel`中的变量修改也会同步在`View`上显示

### 特性

我们先看一下2017年度最受欢迎的Javascript项目有哪些.

![Javascript框架排名](http://doulemd.iok.la/static_images/ca162a9ea03f3ffab385a0c8dccf9ebc.png)

**Vue.js以超过40k个Star再次强势登顶年度排行榜冠军,并且与第二名的React相差一大截**
那是什么原因令Vue.js如此出众, 引人入胜呢?

1. 首先, Vue.js学习曲线平缓, 使用的是让WEB开发者更数字的React式组件开发方案.

2. 把模板, 逻辑, 样式放到单个vue文件里让传统WEB开发者更让人亲切, 也切合单文件组件的设计理念.

3. 由`Evan You`个人维护, 通过众筹的方式获取支持的开源项目,
而不是由`Facebook`或者`Google`这样的互联网巨头来主导, 这就避免了之前`React`出现的版权问题.

4. 具有良好的生态系统, 活跃的社区, 层出不穷的组件, 在社区中更是出现了官方支持的标准库
  * 路由 `Vue-router`
  * 状态管理 `Vuex`


### 核心原理

Vue的核心原理其实非常的简单, 是通过使用`Object.definProperty()`来实现数据的双向绑定.
可以简单的理解为原本一个变量是存放在内存中的, 存取都是根据内存地址去查找的.
现在通过这个方法, 我们把变量存放到视图中, 存取都是根据DOM对象的id或者class.
以下是一个非常简单但是能说明这个原理的例子.
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Vue Core</title>
    <script>
        // 绑定的对象
        var bindObject = {}

        // 将id为time的元素绑定到对象的time属性上
        Object.defineProperty(bindObject, 'time', {
            get: function() {
                // 根据id去取值
                return document.querySelector('#time').innerHTML
            },
            set: function(newValue) {
                // 根据id去存值
                document.querySelector('#time').innerHTML = newValue
            }
        })

        // 模拟修改数据
        setInterval(function() {
            bindObject.time = (new Date()).toString()
        }, 1000)
    </script>
</head>
<body>
    <h1 id="time"></h1>
</body>
</html>
```

当然, `Vue`并不会这样做, 因为这样做一旦需要更新的数据太多就会造成绘制线程的阻塞.
`Vue`内部实现了一个`Virtual DOM`, 使用内部数据结构模拟真实的`DOM`结构,
所有的修改都在`VDOM`里, `Vue`会在合适的时候修改`VDOM`里和真实DOM对象有差异的地方.
即:**能够以最小的代价修改DOM元素, 减少因为修改DOM元素而造成的页面重绘问题**


### Vue安装及dev

官方有一整套的解决方案用于构建Vue应用, 而且还提供了不同项目模板可供选择. 以`webpack`为例.
```shell
# 安装vue脚手架工具
cnpm i vue-cli -g
# 如果使用的nodejs > 8, 可以安装最新的3.0版本的脚手架工具
cnpm i @vue/cli -g

# 这里采用 vue-cli 包

# 创建应用
vue init webpack my-vue-project
# 其中 webpack 是项目模板, my-vue-project 是项目名称
# 在执行过程中会有很多选项, 一路默认回车就好

# 进入目录开始服务
cd my-vue-project
npm run dev
```
执行以上步骤之后, 应该会如下图这样显示

![开启服务成功示例](http://doulemd.iok.la/static_images/d976e916bbbe0a4d3157f83c4accd95d.png)

访问`http://localhost:8081`就可以看到默认的欢迎界面了

![欢迎界面](http://doulemd.iok.la/static_images/bd79d77beef5e1c9a360118faa49b11c.png)


### 例子: 用户列表

作为一个简单的例子, 我们展示显示一个表格, 表格中展示用户的信息.
我们用左键模拟添加用户, 右键模拟删除用户.

#### 用户列表模块

我们先根据编写用户列表模块, 需求有以下几点
  * 从父组件传入一个数组, 数组中的每个元素为一个用户
  * 给父组件传递新增用户, 或者删除用户的事件

```html
<template>
    <table>
        <thead><th><td>名字</td><td>性别</td><td>年龄</td></th></thead>
        <tbody @click="addUser">
            <tr
              v-for="user in users"
              :key="Math.random() + user.id"
              @click.right.stop.prevent="delUser(user.id)"
            >
                <td>{{ user.name }}</td>
                <td>{{ user.isMan ? '男' : '女' }}</td>
                <td>{{ user.age }}</td>
            </tr>
        </tbody>
    </table>
</template>

<script>
export default {
  name: 'user-lists',
  data: () => {
    return {
    }
  },
  props: {
    users: Array
  },
  methods: {
    addUser: function () {
      // id, name, is_man, age
      let id = this.users.length
      this.$emit('addUser', id, 'user' + id, id % 2, id)
    },
    delUser: function (id) {
      this.$emit('delUser', id)
    }
  }
}
</script>

<style>
</style>
```

#### 引入用户列表组件

修改`App.Vue`引入用户列表组件
```html
<template>
  <div id="app">
    <img src="./assets/logo.png">
    <router-view/>
    <user-lists :users="users" @addUser="addUser" @delUser="delUser"></user-lists>
  </div>
</template>

<script>
import userLists from './components/UserList'

export default {
  name: 'App',
  data: () => {
    return {
      users: [{id: 0, name: 'user0', isMan: false, age: 0}]
    }
  },
  methods: {
    addUser: function (id, name, isMan, age) {
      this.users.push({
        id: id,
        name: name,
        isMan: isMan,
        age: age
      })
    },
    delUser: function (id) {
      let index = 0

      while (true) {
        if (this.users[index].id === id) {
          break
        }

        if (index === this.users.length) {
          index = -1
          break
        }

        index += 1
      }

      if (index !== -1) {
        this.users.splice(index, 1)
      }
    }
  },
  components: {userLists}
}
</script>

<style>
/* ... */
</style>
```

最终实现的效果可以访问: `http://192.168.1.251:8081`

[效果](http://192.168.1.251:8081)


#### 总结

由以上例子可以看出, 使用Vue相较于普通web前端项目至少40%的代码都被用来修改DOM, 有几点优势
  1. 专注于服务逻辑, 不用额外的代码用于修改DOM元素
  2. 模块化开发大大提高了系统的扩展性
  3. 完备的开发调试模式
  4. 由ESLint提供的强制代码风格约束

从模块化开发这一点就可以看出, 在社区中必然存在着大量的第三方通用组件库. 事实也正是如此.
  * PC端有`Element`和`iView`两大最受欢迎的UI组件
  * 移动端有`Mint UI`和`vux`量大UI组件
  * `Vuetify`则适用于PC端和移动端,同时内置了服务端渲染, PWA, CLI模块等支持
  * `Weex`更可以通过VUE来生成移动桌面应用.

根据不同的搭配组合以实现不同的效果, 甚至能使用纯前端做出桌面软件, 这在几年前是不可想象的.
