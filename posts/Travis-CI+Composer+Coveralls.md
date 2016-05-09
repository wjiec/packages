> 本文以PHP项目作为例子
> 所需要拥有(准备)的：
> * `Github`账号
> * 一个项目

> 看着篇幅挺大的，难免有什么遗漏，如果文中有错误的地方，还请各位斧正！谢谢。
> 因为本来篇幅就大，所以就没配图了，如果有很多人反馈看不懂或者失败了，我再后期补下图。谢谢！

[Travis-CI][1]
---
项目为保证项目始终处于健康稳定的状态，我们需要一个可以持续的自动的对贡献的代码进行自动化测试的服务。
`Travis-CI`就是在这样的背景下于2011年开启服务，到现在为止已经有超过300k个开源项目和235k的用户在使用。

`Travis-CI`所做的工作就是自动在虚拟机中运行`.travis.yml`中设定的内容进行单元测试，生成并导出报告。

[Composer][5]
---
开源项目之间一般有着相互依赖的关系，比如项目A的一个组件依赖于另一个项目B。当这种依赖关系多了之后就需要一个管理依赖的工具。

`Composer`就是PHP的一个*依赖管理工具*。它允许你申明项目所依赖的代码库，并且会在你的项目中安装他们。[*Composer中文*][6]

[Packagist][10]
---
> Packagist is the main Composer repository. It aggregates public PHP packages installable with Composer.
这个网站是主要的Composer仓库，通过Composer发布的项目，所储存的仓库就是这个网站，这也是Composer安装依赖的下载来源。*可以使用Github账号登录*
登录之后可以提交自己的项目，不过需要项目中有`composer.json`文件，这在之后进行介绍。

[Coveralls][2]
---
单元测试中，代码覆盖率经常被用来衡量测试好坏的指标。
所谓的代码覆盖率简单的说就是在运行完测试用例之后，走过了多少句的代码，比如说，你要测试的一个函数有100行，但是测试用例只走过了80行，所以这个测试用例的代码覆盖率就是80%

`Coveralls`就是一个根据单元测试导出的数据进行分析，展现代码覆盖率的一个工具。可以和很多的自动构建工具一起使用，本文以`Travis-CI`为例。

项目最终的目录结构
---
```
/
├── src/
│   └── ClassName.php
|── tests/
|   ├── ClassNameTest/
|   |   └── ClassNameTest.php
|   └── Bootstrap.php
|── .coveralls.yml
|── .travis.yml
|── LICENSE
|── README.md
|── composer.json
|── example.php
└── phpunit.xml.dist
```


### *下面开始具体的配置方法*

Composer
---
如果想更深入的学习Composer可以查看官方文档(地址在这一节最后)。一个重要的概念就是`每一个项目都是一个包`[注][3]
所以我们首先需要在**项目根目录**新建一个`composer.json`文件，其中的内容为(*稍后我们再看其中的意思*)
```json
{
  "name": "jshadowman/package",
  "description": "this is a test package",
  "version": "0.0.1",
  "type": "library",
  "keywords": [ "database", "logging" ],
  "license": "MIT",
  "require": [
    "php": ">=5.4.0"
  ],
  "require-dev": [
    "satooshi/php-coveralls":"*",
    "phpunit/phpunit": "*"
  ],
  "autoload": {
    "files": [ "./src/ClassName.php" ]
  }
}
```
* `name:` 这个字段顾名思义，包的名字，应该包含Verdor name(供应商)和Project Name(项目名)。值得注意的是这个字段的值应该都是**小写**的，这和资源库发布注册有关。具体请参考 [Packagist][10]
* `description:` 这个字段应该是这个项目的一个简短的简介。一行即可
* `version:` 项目的版本，并不是必须的，**而且建议忽略**，具体请参考 [Version](https://getcomposer.org/doc/04-schema.md#version) [中文](http://docs.phpcomposer.com/04-schema.html#version)
* `type:` 项目的类型，可选的值有`library` `project` `metapackage` `composer-plugin` 具体请参考 [Type](https://getcomposer.org/doc/04-schema.md#type) [中文](http://docs.phpcomposer.com/04-schema.html#type)
* `keywords:` 用于在被搜索时的关键字，可以是一个数组 [Keywords](https://getcomposer.org/doc/04-schema.md#keywords) [中文](http://docs.phpcomposer.com/04-schema.html#keywords)
* `license:` 项目发布所使用的开源协议，可选的值请参考 [License](https://getcomposer.org/doc/04-schema.md#license) [中文](http://docs.phpcomposer.com/04-schema.html#license)
* `require:` 这表示项目所依赖的软件包列表，除非这些依赖被满足，否则不会完成安装。[Require](https://getcomposer.org/doc/04-schema.md#require) [中文](http://docs.phpcomposer.com/04-schema.html#require)
* `require:` 我们的项目依赖于`平台软件包`，也就是PHP，PHP的扩展包和一些系统类库。所以我们在`require`之中添加了对PHP的依赖，如果有依赖于其他的包，可以按照这种格式填写。具体请参考 [Platform-packages](https://getcomposer.org/doc/02-libraries.md#platform-packages) [中文](http://docs.phpcomposer.com/02-libraries.html#Platform-packages)
* `require-dev:` 这个字段列出的依赖只有在测试和开发的时候才会安装，属于额外的依赖。具体请参考 [Require-dev](https://getcomposer.org/doc/04-schema.md#require-dev) [中文](http://docs.phpcomposer.com/04-schema.html#require-dev)
* `require & require-dev:` 这两个字段之下的列表项应该是`包名到版本的映射`，其中版本有很多种写法，可以根据需求过滤。具体请参考 [Package-Links](https://getcomposer.org/doc/04-schema.md#package-links) [中文](http://docs.phpcomposer.com/04-schema.html#Package-links)
* `autoload:` 表示的是`autoloader`的自动加载映射。具体请参考 [Autoload](https://getcomposer.org/doc/04-schema.md#autoload) [中文](http://docs.phpcomposer.com/04-schema.html#autoload)
* `autoload:` 其中的映射关系设计到PHP命名空间(Name Space)的一些知识，具体请参考 [PSR-0](http://www.php-fig.org/psr/psr-0/) - [PSR-4](http://www.php-fig.org/psr/psr-4/) - [PSR0-4_Github](https://github.com/php-fig/fig-standards/tree/master/accepted)

`composer.json`中有很多可选的字段和值，编写的规范可以参考[Document][8]，[中文文档][9]

Coveralls
---
在项目的**根目录**新建`.coveralls.yml`文件，其中的内容为
```yml
coverage_clover: build/logs/clover.xml
json_path: build/logs/coveralls-upload.json
```
* `coverage_clover:` 表示使用指定目录的`Clover XML`格式的XML文件，默认指向`build/logs/clover.xml`
* `json_path:` 用来指定将被上传到`Coveralls`网站的json文件，默认指向`build/logs/coveralls-upload.json`
> 值得注意的是旧版本所使用的`src_dir`已经在1.0.0版本中**被移除**了，所以请注意**不要使用这个选项**
> [Removed src_dir from CoverallsConfiguration](https://github.com/satooshi/php-coveralls/blob/master/CHANGELOG.md)

还有其他的配置选项请参考[Github - satooshi/php-coveralls](https://github.com/satooshi/php-coveralls)

#### 接下来在`Coveralls`网站中添加仓库
* 进入 `Coveralls` 网站 `https://coveralls.io/`
* 点击右上角的 `SIGN IN`，在接下来的页面中选择`GITHUB SIGN IN`，然后使用自己的Github账号授权登录
* 如果你没使用过`Coveralls`的话，登录成功的界面应该是让你添加一个代码仓库
* 在`ADD REPO`标题下列表中将你的项目从`OFF`拨到`ON`
* 接下来配置`PHPunit`单元测试。

PHPUnit
---
在你的**项目根目录**新建`phpunit.xml.dist`文件，其实这个文件也不一定要新建在根目录，主要记得**修改文件内容中的路径就行**，不过最好就是根目录和tests文件夹内了。
`phpunit.xml.dist`文件的内容为
```xml
<phpunit bootstrap="tests/Bootstrap.php"
         colors="true"
         convertErrorsToExceptions="true"
         convertNoticesToExceptions="true"
         convertWarningsToExceptions="true"
         >
    <testsuites>
        <testsuite name="Class Test Suite">
            <directory>./tests</directory>
        </testsuite>
    </testsuites>
    <filter>
        <whitelist>
            <directory>./src</directory>
            <exclude>
                <directory>./vendor</directory>
                <directory>./tests</directory>
                <file>./example.php</file>
            </exclude>
        </whitelist>
    </filter>
    <logging>
        <log type="coverage-clover" target="build/logs/clover.xml"/>
        <log type="coverage-text" target="php://stdout" showUncoveredFiles="true"/>
    </logging>
</phpunit>
```
* 根元素`<phpunit>`中的属性
 * `bootstrap` 表示在测试运行前先运行一个 "Bootstrap" PHP文件，一般用于配合`Composer`中的自动载入，确保不会发生找不到类的情况
 * `colors` 表示是否使用彩色输出
 * `convertErrorsToExceptions` PHPUnit 将会安插一个错误处理函数来将错误转换为异常，设置为 false 则表示禁用
 * `convertNoticesToExceptions` 此选项设置为 true 时，由 convertErrorsToExceptions 安插的错误处理函数会将 E_NOTICE、E_USER_NOTICE、E_STRICT 错误转换为异常。
 * `convertWarningsToExceptions`此选项设置为 true 时，由 convertErrorsToExceptions 安插的错误处理函数会将 E_WARNING 或 E_USER_WARNING 错误转换为异常。
* 带有一个或多个 `<testsuite>` 子元素的 `<testsuites>` 元素用于多组的测试套件
* `<filter>` 顾名思义，过滤器，对目录下的文件或文件夹进行过滤
* `<filter>`下的`<whitelist>` 表示白名单，即需要的部分
* `<whitelist>`下的`<directory>` 顾名思义，即需要的目录
* `<whitelist>`下的`<exclude>` 这是需要排除的部分，底下的排除项目看标签名就知道了，可以排除目录或者单个文件
* 最后的`<logging>`部分就是日志纪录的内容了。
 * `<log type="coverage-clover" target="build/logs/clover.xml"/>` 表示导出`coverage-clover`格式的文件，导出文件名为`build/logs/clover.xml`
 * `<log type="coverage-text" target="php://stdout" showUncoveredFiles="true"/>` 表示将日志直接输出到标准输出，即终端上。

完整的XML格式，内容可以参考 [XML配置文件](https://phpunit.de/manual/current/zh_cn/appendixes.configuration.html)

>需要注意的是在根元素`<phpunit>`中的属性并不是所有都在那个页面介绍的，还有一部分在[命令行选项](https://phpunit.de/manual/current/zh_cn/textui.html#textui.clioptions)之中，所以如果在附录C找不到，那就去命令行选项(*注意根元素属性在命令行选项中是以`-`分隔的*)那一节找找，肯定有的。


Travis-CI
---
* 使用`Github`账号登录`Travis-CI` [Sign Up][1]
* 点击自己的头像进行个人资料界面，在下面你的项目中，点击你所需要自动构建的项目前的按钮，这个按钮就会变成绿色的勾
* 在点击到自己的用户信息界面之后，在你的Repo上面会有一个简单的使用介绍，开启Travis-CI是很简单的。

在你的**项目根目录**新建`.travis.yml`文件，其中的内容为
```yml
language: php

php:
  - '5.4'
  - '5.5'
  - '5.6'
  - '7.0'

before_script:
  - composer install --prefer-dist --dev --no-interaction

script:
  - mkdir -p build/logs
  - phpunit -c phpunit.xml.dist --coverage-clover build/logs/clover.xml

after_script:
  - travis_retry php vendor/bin/coveralls -v
```
* `language:` 顾名思义，这就是你项目所用的语言，所支持的语言和格式可以查阅 [Document][3]，[配置PHP][4]
* `php:` 这个底下是自动构建所使用的环境。注意，有固定的格式
* `before_script:` 顾名思义，在正式`script`之前运行的脚本(`Shell`)命令
* `script:` 开始测试所用的命令
* `after_script:` 在测试结束之后运行的命令，比如用于导出结果到`COVERALLS`

其中所用到命令介绍
---
> 开始测试之前需要做的准备工作，即安装项目需要的依赖包。
> `composer install --prefer-dist --dev --no-interaction`
> * 这句命令的作用是根据`composer.json`所描述的依赖关系进行依赖的安装，具体请参考 [Install](https://getcomposer.org/doc/03-cli.md#install) [中文](http://docs.phpcomposer.com/03-cli.html#install)
>  * `--prefer-dist:` composer 将尽可能的从 dist 获取依赖的项目，这将大幅度的加快在 build servers 上的安装
>  * `--dev:` 安装 require-dev 字段中列出的包
>  * `--no-interaction:` 不要询问任何交互问题。因为是自动进行依赖安装的，我们不能手动控制，所以发生任何需要交互的问题，我们都是处理不了的
> 
> 准备工作做好之后，开始正式的测试工作，首先当然需要先新建一个存放日志的目录
> `mkdir -p build/logs`
> * 这句命令会让系统创建一个连续的目录，如果父目录不存在就先创建父目录
> 
> 开始进行单元测试并导出代码覆盖率报告
> `phpunit -c phpunit.xml.dist --coverage-clover build/logs/clover.xml`
> * 这句命令是运行phpunit进行单元测试，具体请参考 [PHPUnit](https://phpunit.de) - [命令行选项](https://phpunit.de/manual/current/zh_cn/textui.html#textui.clioptions)
>  * `-c phpunit.xml.dist:` 从指定的文件中读取配置信息，这里的配置文件是`phpunit.xml.dist`
>  * `--coverage-clover build/logs/clover.xml:`生成并导出`Clover XML`格式的代码覆盖率报告
> 
> 测试完之后接下来就是导出报告到Coveralls了
> `travis_retry php vendor/bin/coveralls -v`
> * 这句命令是其实是`php vendor/bin/coveralls -v`，前面的`travis_retry`的作用是检查后面命令的返回值，如果不是0(返回值为0表示正常结束)，那就重复执行3次，如果3次都不为0，那就报错。
> * `php vendor/bin/coveralls -v:` 
>  * 这句命令是使用PHP执行vendor/bin/下的coveralls这个文件，`-v`表示`verbose`，即显示详细的报告。
> 这个命令执行之后就可以在`Coveralls`这个网站中看到详细的数据了。
> 
> `phpunit`执行的结果和`coveralls`导出的结果都可以在`Travis-CI`的`Build Jobs`下看到

git push
---
接下来就是把这些文件push到Github上，`Travis-CI`就会自动构建，然后开始单元测试，并把测试结果中的代码覆盖率发送到`Coveralls`。如此，一套流程就结束了。

展示
---
辛辛苦苦大半天，就是为了展示自己的成绩啊。
所以我们看到的别人家项目地下这么漂亮的图标我们也要有啊。

在`README.md`文件中添加(***注意将以下 Github_ID 替换为自己的 Github-ID，将 Repo_Name 替换为你的项目名字。没有尖括号哦~还有注意区分大小写哦~如果还需要改分支(branch)的话，看到链接你应该也懂吧？我相信你！ ***)
```markdown
[![Build Status](https://travis-ci.org/<Github_ID>/<Repo_Name>.svg?branch=master)](https://travis-ci.org/<Github_ID>/<Repo_Name>)
[![Coverage Status](https://coveralls.io/repos/github/<Github_ID>/<Repo_Name>/badge.svg?branch=master)](https://coveralls.io/github/<Github_ID>/<Repo_Name>?branch=master)
```

其实这些`markdown`语句可以直接复制的
* `Travis-CI:` `Build`图标可以在`Travis-CI`网站中自己项目名的右边有一个 `build:****` 的图标，直接点击这个图标，将`Image URL`改成`Makedown`就可以看到啦
* `Coveralls:` 也是进入自己Repo的详细信息中，中间是`LATEST BUILDS`信息，在最右边有一个`README BADGE`，底下那个图标右边有个按钮`Embed ▾`，点击复制`Markdown`的语句即可。

Packagist(可选)
---
在觉得自己的项目开发的差不多时，我们就可在在`Packagist`上发布自己的包啦，发布之后，就可以被别人的项目通过`Composer`所依赖~

* 可以使用Github账号登录，或者自己注册个账号登录，在右上角`Sign In`选择是注册还是使用Github账号登录
* 注册完之后，可以在右上方`Submit`中提交一个包，点击`Submit`按钮 - [Submit](https://packagist.org/packages/submit)
* 接下来会让你输入`Repository URL`，直接输入`git://github.com/<Github_ID>/<Repo_Name>`
* Packagit会在后台clone你的项目，并且检查项目中的`composer.json`文件，第一个要检查的就是包的名字，如果包名中有大写的字母，Packagit就会报一个包名不应该有大写字母的错误，所以，这就是上文所说包名最好是小写的来由。
* 提交之后就可以看到自己的这个包的一些信息了，比如被下载了多少次，被安装了多少次。
* 回到Github，打开代码仓库的`Settings -> Webhooks & services`，然后在`Services`右边有个`Add Service`的按钮，点击输入查找`Packagit`
* 之后会让你输入用户名和Token，这些信息都在你的`Packagit`主页中 [个人主页](https://packagist.org/profile/)
* 个人主页有个`Your API Token`的按钮，按下按钮，就可以看到自己的API TOKEN了，**注意保密哦**
* 其中`packagist`海油4个小图标，记得替换 `PACKAGIST_ID` 和 `PACKAGE_NAME` 哦，不是 `Github_ID` 和 `Repo_Name` 哦
```markdown
[![Latest Stable Version](https://poser.pugx.org/<PACKAGIST_ID>/<PACKAGE_NAME>/v/stable)](https://packagist.org/packages/<PACKAGIST_ID>/<PACKAGE_NAME>)
[![Total Downloads](https://poser.pugx.org/<PACKAGIST_ID>/<PACKAGE_NAME>/downloads)](https://packagist.org/packages/<PACKAGIST_ID>/<PACKAGE_NAME>)
[![Latest Unstable Version](https://poser.pugx.org/<PACKAGIST_ID>/<PACKAGE_NAME>/v/unstable)](https://packagist.org/packages/<PACKAGIST_ID>/<PACKAGE_NAME>)
[![License](https://poser.pugx.org/<PACKAGIST_ID>/<PACKAGE_NAME>/license)](https://packagist.org/packages/<PACKAGIST_ID>/<PACKAGE_NAME>)
```


  [1]: https://travis-ci.org/
  [2]: https://coveralls.io/
  [3]: https://docs.travis-ci.com/
  [4]: https://docs.travis-ci.com/user/languages/php
  [5]: https://getcomposer.org/
  [6]: http://docs.phpcomposer.com/
  [7]: http://docs.phpcomposer.com/02-libraries.html#Every-project-is-a-package
  [8]: https://getcomposer.org/doc/04-schema.md
  [9]: http://docs.phpcomposer.com/04-schema.html
  [10]: https://packagist.org/