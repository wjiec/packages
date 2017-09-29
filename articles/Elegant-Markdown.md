优雅的Markdown
-------------

开头引用 _维基百科_ 中介绍 Markdown 历史的一句话

> John Gruber created the Markdown language in 2004 in collaboration with Aaron Swartz on the syntax,
> with the goal of enabling people "**to write using an easy-to-read, easy-to-write plain text format**,
> and optionally convert it to structurally valid XHTML (or HTML)"

总的来说, Markdown 的设计目标就是 **易于阅读, 易于书写, 易于修改**


---


### 分类明确有序

不论是 Markdown 还是其他类型的文档, 分类或者段落有序都是基本要求.
比如要书写一个探讨设计模式的文档, 那对于设计模式而言有几种类型, 分别是

* 创建类(_Creational_)
* 结构类(_Structural_)
* 行为类(_Behavioral_)

其中创建类底下又分为各个不同的设计模式, 比如, 抽象工厂模式, 建造者模式等等...

所以我们在写关于设计模式的文档时就可以根据这个 **层级关系** 进行段落和层次的包含设计, 如下
```plain
DesignPatterns
--------------

关于设计模式的介绍...


### 创建类

这一类类型的介绍...


#### 抽象工厂模式

抽象工厂模式的介绍...

##### 优缺点

...

##### 代码示例

...


#### 建造者模式

...
```

一个具有友好的层级关系的文档可以非常明确的在跳跃式的阅读或者查找时得出某一部分内容所属于的范围,
对于阅读的人来说, 可以达到减少文档的上手难度作用.

比如我要在一个非常复杂的系统中查询一个读取文件内容的方法, 如果在友好的文档中,
我只要顺着`IO -> 文件IO -> Input -> getFileContents`就可以很快速的找到我想要的东西.

对于属于同一大类的不同节点, 可以合理使用`---`进行隔离

---


### 当断则断

在文档中如果需要引用或者书写如下所示的一段 **超长(一般定义为超过80/120字符)** 的文本,
那就需要对文档进行截断处理, 这样在 **查看或者修改文档文件** 时可以在左右一屏之内看到所有的内容.

> The key design goal is readability – that the language be readable as-is,
> without looking like it has been marked up with tags or formatting instructions,
> unlike text formatted with a markup language, such as Rich Text Format (RTF) or HTML,
> which have obvious tags and formatting instructions.
> To this end, its main inspiration is the existing conventions for marking up plain text in email,
> though it also draws from earlier markup languages, notably setext, Textile, and reStructuredText.

#### 不合理的写法

```plain
> The key design goal is readability – that the language be readable as-is, without looking like it has been marked up with tags or formatting instructions,[9] unlike text formatted with a markup language, such as Rich Text Format (RTF) or HTML, which have obvious tags and formatting instructions. To this end, its main inspiration is the existing conventions for marking up plain text in email, though it also draws from earlier markup languages, notably setext, Textile, and reStructuredText.
```

这种写法对于修改文档, 或者直接查看源文档(_没有条件下_)极其的不友好,
需要左右拖动编辑器的滚动条才能查看或者修改. 而且非常难以选中一段文字.

#### 可读的写法

```plain
> The key design goal is readability – that the language be readable as-is,
> without looking like it has been marked up with tags or formatting instructions,
> unlike text formatted with a markup language, such as Rich Text Format (RTF) or HTML,
> which have obvious tags and formatting instructions.
> To this end, its main inspiration is the existing conventions for marking up plain text in email,
> though it also draws from earlier markup languages, notably setext, Textile, and reStructuredText.
```

合理的做法应该在合适的地方(_比如逗号或者句号等断句类标点符号的位置_)进行换行操作,
由 Markdown 的语法转换规则决定的, 多个空白字符将会被缩减到一个(_代码块语法例外_)空格字符

比如如下的 Markdown, 在前端(_各种类型的Markdown预览器_)显示时会显示成这样 `a b`

```plain
a     b
```

所以换行所带来的影响就是多了个` `字符, 如果在合适的地方进行换行操作, 反而不会带来副作用.


---


### 距离产生美

标题同样适用于其他类型的文档, 对于多段文字或者不同格式/层级的段落, 使用空行隔开, 看起来会更加分明.
就类似于没断句的文言文和有了标点符号的文言文, 虽然都是同一片文章, 但是看起来的效果自然不言而喻.


#### 较不合理的写法
`````plain
## 人工智能
人工智能(_Artificial Intelligence_), 英文缩写为AI.
它是研究, 开发用于模拟, 延伸和扩展人的智能的理论, 方法, 技术及应用系统的一门新的技术科学.
### 机器学习
它是人工智能的核心, 是使计算机具有智能的根本途径, 其应用遍及人工智能的各个领域.
#### 深度学习
深度学习是机器学习中一种基于对数据进行表征学习的方法.
##### 神经网络
神经网络是实现深度学习的一种算法.
它是一种模仿动物神经网络行为特征，进行分布式并行信息处理的算法数学模型.
这种网络依靠系统的复杂程度, 通过调整内部大量节点之间相互连接的关系, 从而达到处理信息的目的.
* 引入gluon
```python
from mxnet import ndarray as nd
from mxnet import autograd
from mxnet import gluon
```
* 初始化向量
```python
true_w = [2, -3.4]
true_b = 4.2
X = nd.random_normal(shape=(1000, 2))
y = true_w[0] * X[:, 0] + true_w[1] * X[:, 1] + true_b
y += .01 * nd.random_normal(shape=y.shape)
```
`````

如上一段文档内容之间没有任何空行来隔开不用的段落, 看起来就会比较臃肿.
而且一些小的重要的信息字段很容易在一大段的文本中被忽略.
虽然这种写法在前端上看起来和下面的版本的一致, 但是这种源文档的可读性却是很差的

#### 可读的写法
`````plain
## 人工智能

人工智能(_Artificial Intelligence_), 英文缩写为AI.
它是研究, 开发用于模拟, 延伸和扩展人的智能的理论, 方法, 技术及应用系统的一门新的技术科学.


### 机器学习

它是人工智能的核心, 是使计算机具有智能的根本途径, 其应用遍及人工智能的各个领域.

#### 深度学习

深度学习是机器学习中一种基于对数据进行表征学习的方法.

##### 神经网络

神经网络是实现深度学习的一种算法.
它是一种模仿动物神经网络行为特征，进行分布式并行信息处理的算法数学模型.
这种网络依靠系统的复杂程度, 通过调整内部大量节点之间相互连接的关系, 从而达到处理信息的目的.

* 引入gluon
```python
from mxnet import ndarray as nd
from mxnet import autograd
from mxnet import gluon
```

* 初始化向量
```python
true_w = [2, -3.4]
true_b = 4.2
X = nd.random_normal(shape=(1000, 2))
y = true_w[0] * X[:, 0] + true_w[1] * X[:, 1] + true_b
y += .01 * nd.random_normal(shape=y.shape)
```
`````

虽然只是多了一些空行, 但是看上去就是就会清晰很多, 各个段落/层次可以很明显看到.


---


### 告诉解析器更多的东西

对于代码块而言, 因为解析器需要进行语法高亮, 所以最好能给出这个代码块的语言,
也可以增加可读性, 如下例子.

#### 不合理的代码块

`````plain
```
import requests

r = requests.get('https://www.baidu.com')
```
```
<font color="red">TestCase</font>
<dict>
  <item key="key1" value="val1" />
  <item key="key2" value="val2" />
  <item key="key3" value="val3" />
</dict>
```
`````

对于第一种情况, 解析器还可以根据语法判断是python, 但是第二种解析器就很难判断到底是HTML还是XML了.
所以对于第二种情况还是应该声明下该代码块的语言, 而且也有利于区分不同的代码块

#### 合理的代码块

`````plain
```python
import requests

r = requests.get('https://www.baidu.com')
```

```xml
<font color="red">TestCase</font>
<dict>
  <item key="key1" value="val1" />
  <item key="key2" value="val2" />
  <item key="key3" value="val3" />
</dict>
```
`````

在这里, 我们将两个代码块通过空行进行了分隔, 并且使用 **\`\`\`** 后跟语言的方式声明了这个代码块的语言.
这里有2个好处

* 两个代码块一目了然, **\`\`\`** 之后跟语言的是代码块的头部.
* 解析器可以根据声明的语言进行合适的语法高亮


---


### 结语

必要的空行, 空格, 有意义的变量名字, 统一的代码风格, 这是一个优秀的代码所要拥有的特征.

文档的书写也是一样, 必要的空行, 断句, 提供有效的帮助信息是一个优秀的文档所要具备的特征.


### 参考文档

1. [Markdown - Wikipedia][1]



[1]: https://en.wikipedia.org/wiki/Markdown
