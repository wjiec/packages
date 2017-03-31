Error_Logger
------------

错误打点服务器


### 安装
在`setup.py`所在目录执行以下命令进行安装
```
sudo python setup.py install
```

### 使用
`setup.py`将会在系统中注册`errot_logger_server`这个命令，执行以下命令来获取帮助
```
error_logger_server --help
```

### 参数
> 必须指定的参数有 `config` 和 `daemon`
```
error_logger_server [-h] [-c CONFIG] [-s SERVER]
                    [-d {start,stop,debug}]
```
* `-c`或`--config`: 用来指定配置文件所在的目录，具体请看config.json.md文档
* `-s`或`--server`: 用来指定服务器将绑定在哪个地址，默认为`0.0.0.0`
* `-d`或`--daemon`: 用来指定服务器启动模式，可选的值有 `start` `stop` `debug`
