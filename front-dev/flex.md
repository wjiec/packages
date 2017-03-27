Flex 弹性盒模型笔记
------------------
对于 flex 弹性盒, 是由 flex container 和 flex item 构成.
```html
<div id="flex-container">
    <p class="flex-item"></p>
    <div class="flex-item"></div>
</div>
```

使用
----
只要在 flex-container(flex容器)上设置flex属性即可
```css
#flex-container {
    display: flex;
    display: -webkit-flex; // Safari
}
```
还可以选择为行内容器
```css
#flex-container {
    display: inline-flex;
    display: -webkit-inline-flex; // Safari
}
```

容器元素的可选择的属性
--------------------
> Safari 需要加上 -webkit- 前缀
* `flex-direction`: 这个属性指定容器内主轴方向
  * row: 水平排列
  * column: 竖直方向上排列
  * row-reverse: 反向的水平排列
  * column-reverse: 反向的竖直排列
* `flex-wrap`: 指定 flex容器是多行的还是一行的, 可选的值有
  * `wrap`: 多行
  * `nowrap`: 单行
  * `wrap-reverse`: 反向的多行，从下往上排列
* `flex-flow`: flex-direction 和 flex-wrap 的简写形式
* `justify-content`: 这个属性指定子元素在主轴方向上的对齐方式, 可选的值有
  * `flex-start`: 从主轴起始点开始, 如果是 row 那就是横向从左往右, column 就是从上到下, row-reverse 那就是从右到左, reverse-column同理
  * `flex-end`: 从主轴终点开始, 同理
  * `center`: 对齐在主轴的中间
  * `space-between`: 每个子元素分开相同的距离, 左右边界没有空隙
  * `space-around`: 每个子元素分开相同的距离, 左右边界有空隙
* `align-items`: 子元素在交叉轴上的对齐方式, 可选的值有
  * `flex-start`: 从交叉轴起始点开始
  * `flex-end`: 从交叉轴终点开始
  * `center`: 对齐在交叉轴的中间
  * `stretch`: 占满整个交叉轴
  * `baseline`: 与文本的基线对齐
> 当在 `flex-direction: column;` 情况下需要填满交叉轴(横向), 设置`width: 100%;`或者`width: auto;`来填满整个横向空间
* `align-content`: 当 flex 子元素在交叉轴上存在剩余空间时, 比如子元素高度设置为小于容器的高度, 并且设置为多行的情况下才生效 这个选项起到和 `justofy-content`类似的作用
  * `flex-start`: 从起始点开始
  * `flex-end`: 从终点开始
  * `center`: 对齐在中间
  * `space-between`: 每个子元素分开相同的距离, 边界没有空隙
  * `space-around`: 每个子元素分开相同的距离, 边界有空隙

子元素可选择的属性
* `order`: 子元素在容器中的排列顺序, 默认以HTML结构进行排序, 值为`Integer`, 值越小那么排的就越靠前
* `flex-grow`: 表示子元素的拉伸比例, 默认都为1, 即大家拉伸同样的大小, `0 表示不拉伸`
  * 3个子元素的flex-grow值为: 1 1 1, 那么分别占用的大小为 1/3  1/3  1/3
  * 3个子元素的flex-grow值为: 1 2 1, 那么分别占用的大小为 1/4  2/4  1/4
  * 3个子元素的flex-grow值为: 1 2 3, 那么分别占用的大小为 1/6  2/6  3/6
* `flex-shrink`: 表示子元素的压缩比例, 默认为1, `0`表示不压缩，原理同 `flex-grow`
* `flex-basis`: 指定子元素的初始大小, 
* `flex`: 为 `flex-grow` `flex-shrink` `flex-basis` 的简写形式
* `align-self`: 覆盖容器指定的`align-items`设置的值
