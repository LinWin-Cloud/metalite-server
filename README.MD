# MetaLite Server 数据库

MetaLite Server数据库是基于Java开发的数据库软件，具有轻量，快捷部署，效率高，配置低，文件系统合理，抗网络负载强大的特点。由LinwinCloud个人独立开发完成

```
名称:			MetaLite Server Database
语言:			Java
所用服务形式:		异步IO
所用协议:		HTTP/2
开发者:			LinwinCloud
协议:			GPL-3
支持平台:		Linux , Windows
最低配置:		1核1GB内存云服务器
设计运行环境:		1核2GB内置云服务器
设计运行Jdk:		oracle-jdk1.8
```

### 性能指标
1. 纯HTTP服务指标
测试采用 4GB内存2核 Ubuntu 服务器测试，SSD硬盘
```
Concurrency Level:      1000
Time taken for tests:   9.792 seconds
Complete requests:      99350
Failed requests:        0
Non-2xx responses:      99350
Total transferred:      12319400 bytes
HTML transferred:       1987000 bytes
Requests per second:    10146.27 [#/sec] (mean)
Time per request:       98.558 [ms] (mean)
Time per request:       0.099 [ms] (mean, across all concurrent requests)
Transfer rate:          1228.65 [Kbytes/sec] received
```

2. 查询速度
测试条件在SSD硬盘下，6600个数据库下，查找6000个符合查询的数据库名称
```
Time: 0.1s
Software CPU: 5%
```

### 实现原理
> 抛弃以前开发的Linwin DB Server数据库的设计，把数据全部加载到内存改为直接硬盘读取，避免了加载数据的时间和内存占用

> 简单粗暴，吸取了Linwin DB Server数据库设计不足，改良了Myscript数据库脚本，使其更加SQL化

> Key-Value数据库，不过于此不同的是，不再是简单的Key和Value相对应，效仿SQL
```
用户|
    | 数据库1 |-| 数据1-| Key1 - Value1
    |         |-|       | Key2 - Value2	
    | 数据库2 |-| 数据2

```

> Java HashMap对象写入或者读取文件，对HashMap对象进行读取和写入，读取效率快而且准确率高

### 文档
> 提供中文文档
> https://gitee.com/LinWin-Cloud/metalite-server/wikis

### 合作伙伴
> LinWin-Cloud
> zmh-program

### 关注
> B站: https://space.bilibili.com/1012391123

> OSCHINA: https://my.oschina.net/LinWinCloud

> Github: https://github.com/LinWin-Cloud/

> Gitee: https://github.com/LinWin-Cloud/
