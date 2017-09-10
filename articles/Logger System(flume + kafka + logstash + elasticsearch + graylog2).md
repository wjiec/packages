大数据实时计算系统
-----------------

```
`flume` + `kafka` + `logstash` + `elasticsearch`  [实时计算]  +  Kibana  [可视化查询]
                                                             +  graylog2  [可视化查询]
                  + `HDFS` + `Storm`              [离线计算]
```

* `Flume`: 负责从各节点上实时采集数据
* `Kafka`: 由于采集数据的速度和数据处理的速度不一定同步，因此添加一个消息中间件来作为缓冲
* `logstash`: 负责建立`Kafka`到`ElasticSearch`的数据管道
* `ElasticSearch`: 对数据进行保存和实时计算

>
> **NOTE:** **以下各个组件的版本号必须相对应匹配, 否则可能会出现兼容性问题**
>


### Flume *1.7.0*
在`conf`目录下新建一个`producer.conf`文件, 这个文件中描述Flueme的3个组件的信息
* `Sources`: 相当于数据的生产者, 负责在Source指定的位置等待数据
* `Channels`: 数据的仓库, 数据将会暂存在里面, Flume会在合适的时候将数据转发到`Sinks`中
* `Sinks`: 数据的消费者, 在本系统中, Flume将数据转发到`Kafka`中

文件内容和注释如下
```
# 这里先声明3个组件的信息
producer.sources = r
producer.sinks = k
producer.channels = c
# 多生产者或者多消费者, 可以使用如下方式进行配置
# producer.sources = r1 r2 r3
# producer.sinks = k1 k2 k3


# flume提供了大量内置的Source(生产者)、Channel(仓库渠道)和Sink(消费者)类型.
# 而且不同类型的Source、Channel和Sink可以非常灵活的根据用户配置自由组合
# 如果官方没有提供所需要的组件, 可以自己根据接口来实现


# 这里采用 `netcat` 这个组件, 这个组件将发到这个端口的所有数据发送给消费者(`Kafka`)
producer.sources.r.type = netcat
producer.sources.r.bind = 0.0.0.0
producer.sources.r.port = 54345


# 采用内置的`KafkaSink`组件, 将数据转发到Kafka中
producer.sinks.k.type = org.apache.flume.sink.kafka.KafkaSink
# 配置的是 `kafka-server` 的地址
producer.sinks.k.kafka.bootstrap.servers = 127.0.0.1:9092
# 配置 `kafka-zookeeper` 的 `topic` 字段, 需要和 `logstash` 中配置的一致
producer.sinks.k.kafka.topic = log
producer.sinks.k.flumeBatchSize = 20


# 配置数据暂存的方式, 这里采用在内存中暂存数据
producer.channels.c.type = memory
producer.channels.c.capacity = 1000
producer.channels.c.transactionCapacity = 100


# 将 `Sources` 和 `Sinks` 使用 `Channel` 连接起来
producer.sources.r.channels = c
producer.sinks.k.channel = c
```

配置好之后使用一下命令启动`Flume`
```
bin/flume-ng agent -n producer -c conf -f conf/producer.conf
# `agent`: 表示以 `agent` 的方式启动
# `-n`: 指定 agent 的名字
# `-c`: 指定配置文件目录
# `-f`: 指定 agent 的配置文件

# 调试模式可以使用以下命令启动
bin/flume-ng agent -n producer -c conf -f conf/producer.conf -Dflume.root.logger=DEBUG,console
```

> [flume-sources](http://flume.apache.org/FlumeUserGuide.html#flume-sources)
>
> [flume-sinks](http://flume.apache.org/FlumeUserGuide.html#flume-sinks)
>
> [flume-channels](http://flume.apache.org/FlumeUserGuide.html#flume-channels)


#### 集群化
Flume作为收集数据的前端, 可以有多种做法达到集群化的目的
* `一台机器一个Flume`: 通过在Flume中配置多个Source,
再将每个Source通过Channel传递给分布在不同机器上的 KafkaSink
* `多台机器多个Flume`: 通过在多台机器上架设Flume, 每个Flume拥有1个或者多个Source,
并分配到不同主机上的Kafka


### kafka *0.9.0.1*

kafaka在这个系统中作为`flume`的消费者, 是为了防止数据的生成速度与数据的计算/保存速度不一致而设置的缓冲器

要启动`kafka`需要分成2步走, 如下所示.

#### 启动 `zookeeper` 服务
> 可以根据机器的内存大小修改`zookeeper-server-start.sh`文件中的Java内存配置

使用以下命令启动 `zookerper`
```
bin/zookeeper-server-start.sh config/zookeeper.properties
```

> **NOTE:** `Zookeeper` 默认监听 ***2181*** 端口

#### 启动 `kafka-server` 服务
> 可以根据机器的内存大小修改`kafka-server-start.sh`文件中的Java内存配置

使用以下命令启动 `kafka-server`
```
bin/kafka-server-start.sh config/server.properties
```

> **NOTE:** `kafka-server` 默认监听 ***9092*** 端口


### logstash *5.5.0*

logstash在系统中作为连接 `kafka` 和 `elasticsearch` 的管道, 首先需要安装两个插件

* `logstash-input-kafka`: `logstash` 的输入端
* `logstash-output-elasticsearch` `logstash` 的输出端

> **NOTE:** `logstash-input-kafka` 有兼容性要求, 所以需要安装指定版本的插件
> https://www.elastic.co/guide/en/logstash/current/plugins-inputs-kafka.html#_description_23

使用如下命令安装2个插件
```
bin/logstash-plugin install --version 4.2.0 logstash-input-kafka
bin/logstash-plugin install logstash-output-elasticsearch
```

在`config`目录下创建`kafka-logstash-es.conf`配置文件,
内容如下(**删除其中的注释内容**)
```
input {
    kafka {
        # 这里配置的是 `kafka-server` 监听的端口
        bootstrap_servers => "localhost:9092"
        # 这里要与 `flume` 中配置的 `topic` 字段一致, 否则接收不到数据
        topics => ["log"]
    }
}

output {
    elasticsearch {
        # 这里配置的为 `elasticsearch` 的入口地址
        hosts => ["localhost:9200"]
        # 这个将被作为 `document` 的 `Index`
        index => "log-dev-sys"
    }
}
```

使用 **非root权限** 指定命令启动 `logstash`
```
bin/logstash -f config/kafka-logstash-es.conf
```

#### 集群化
通过配置Logstash的输入端和输出端以达到集群的目的, 可以在多台主机爽通过配置input来简介到不同主机上的Kafka服务, 通过output将数据推送到ES中


### ElasticSearch
Elasticsearch 是一个实时的分布式搜索分析引擎,

1. 使用容易
2. 因为采用的是RESTful形式的API, 所以可以通过任何语言与之进行通信(新建, 查询)
3. 分布式的实时文档(数据的意思)存储，每个字段 可以被索引与搜索
4. 支持分布式实时分析搜索引擎

可以根据自己的实际情况在`/elasticsearch/config/jvm.options`文件中
修改 `ElasticSearch` 所占用的内存
```
-Xms2g
-Xmx2g
```

执行以下命令启动 `elasticsearch` 服务
```
bin/elasticsearch
```

> **NOTE:** `ElasticSearch`默认监听本地 ***9300*** 端口,
> 可以在`config/elasticsearch.yml`文件的`network`节中配置其他的地址或者端口


#### 可能出现的问题

以下是启动 `elasticsearch` 中可能会出现的问题

##### max file descriptors [4096] for elasticsearch process is too low, increase to at least [65536]

```
$ sudo vim /etc/security/limits.conf
# 修改或添加以下内容

*        hard    nofile           655360
*        soft    nofile           655360
```

再次启动`elasticsearch`还是有这个错误的话, 需要重新登录用户

##### max virtual memory areas vm.max_map_count [65536] is too low, increase to at least [262144]

```
$ sudo vim /etc/sysctl.conf

# 增加以下行
vm.max_map_count=655360

# 刷新配置
$ sysctl -p
```


### 测试

执行 `telnet localhost 54345` 来模拟客户端发送数据
```
{"src":"php","reason":"parse error"}
```

执行`elasticsearch`查询
```
curl localhost:9200/_search
```

可以得到以下数据
```
{
    "took": 1,
    "timed_out": false,
    "_shards": {
        "total": 1,
        "successful": 1,
        "failed": 0
    },
    "hits": {
        "total": 1,
        "max_score": 1,
        "hits": [
            {
                "_index": "log-dev-sys",
                "_type": "logs",
                "_id": "AV0r57GnA4x5FRpYlybO",
                "_score": 1,
                "_source": {
                    "@timestamp": "2017-07-10T09:49:50.871Z",
                    "@version": "1",
                    "message": "{\"src\":\"php\",\"reason\":\"parse error\"}\r"
                }
            }
        ]
    }
}
```

### Kibana *5.4.3*
这是一个可视化的`ElasticSearch`工具

修改 `config/kibana.yml`配置文件中关于监听地址和端口的配置
```
server.host: "0.0.0.0"
```

下载与 `ElasticSearch` 相同版本的 `Kibana`, 执行以下命令打开 `Kibana` 服务
```
bin/kibana
```

> **NOTE:** `Kibana`默认监听 ***5601*** 端口

启动之后在其中填入在 `logstash` 的 `output` 字段中设置的 `index` 的值,
就可以或者这个索引下的数据了


### Graylog2 2.2.3

Graylog以ElasticSearch为前端手机数据, 自身作为后端监控和二次分析ElasticSearch的数据.

Graylog是一个比Kinbana更加强大的可视化工具, 可以监控流量,提供查询,图表信息等.

> **NOTE:** Graylog目前的最新版本为2.2.3, 只支持版本为 **2.X** 的ElasticSearch.

#### 首先安装 MongoDB
增加`/etc/yum.repos.d/mongodb-org-3.2.repo`文件
```
[mongodb-org-3.2]
name=MongoDB Repository
baseurl=https://repo.mongodb.org/yum/redhat/$releasever/mongodb-org/3.2/x86_64/
gpgcheck=1
enabled=1
gpgkey=https://www.mongodb.org/static/pgp/server-3.2.asc
```

安装`mongodb`并启动
```
$ yum install mongodb-org
$ /etc/init.d/mongod start
```

#### 安装ElasticSearch 2.4.5
修改`/etc/elasticsearch/elasticsearch.yml`文件
```
cluster.name: graylog
```
启动elasticsearch

#### Graylog
下载最新版的Graylog之后, 解压到指定位置, 将根目录下的`graylog.conf.example`文件复制到`/etc/graylog/server/server.conf`, 并修改其中几项值

```
# 使用命令 `pwgen -N 1 -s 96` 的结果作为值
password_secret =

# 这个值将作为龙之太的登录密码
# 使用 `echo -n my_password | sha256sum` 的结果作为值
root_password_sha2 =

root_timezone = Asia/Shanghai
rest_listen_uri =  http://0.0.0.0:9000/api/
web_listen_uri = http://0.0.0.0:9000/
allow_highlighting = true

# 当前允许的ElasticSearch数目
elasticsearch_shards = 1

# 需要与 elasticsearch.yml 中配置的`cluster.name`值一致
elasticsearch_index_prefix = graylog
```

执行以下命令启动 graylog
```
bin/graylogctl start
```

检查是否启动成功
```
bin/graylogctl status
```

#### 访问控制台
Graylog的web控制台默认监听 `9000` 端口, 访问这个地址, 在输入框中输入
```
UserName: admin
# 前面设置的密码
Password: my_password
```
