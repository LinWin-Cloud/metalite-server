# MetaLite Server 数据库

MetaLite Server数据库是基于Java开发的数据库软件，具有轻量，快捷部署，效率高，配置低，文件系统合理，抗网络负载强大的特点。由LinwinCloud个人独立开发完成

```
名称: 			       MetaLite Server Database
语言:	 		         Java
所用服务形式: 		  异步IO
所用协议:		        HTTP/2
开发者:			        LinwinCloud
协议:			         GPL-3
支持平台: 	  	    Linux , Windows
最低配置:		        1核1GB内存云服务器
设计运行环境:	  	  1核2GB内置云服务器
设计运行Jdk:		    oracle-jdk1.8
```

### 性能指标
1. 纯HTTP服务指标
```
Server Software:        
Server Hostname:        127.0.0.1
Server Port:            1145

Document Path:          /
Document Length:        20 bytes

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

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0   15 141.2      0    3048
Processing:     1   13 176.3      4    6859
Waiting:        0   12 176.2      4    6859
Total:          2   28 258.4      4    7871

Percentage of the requests served within a certain time (ms)
  50%      4
  66%      4
  75%      5
  80%      5
  90%      5
  95%      7
  98%     67
  99%   1025
 100%   7871 (longest request)
```
