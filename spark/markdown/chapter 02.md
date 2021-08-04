# chapter 02

+ 运行环境
    1. 本地模式
    2. 独立模式
    3. YARN3
    
+ [安装并配置环境变量](https://blog.csdn.net/qq_42754919/article/details/109402379)

安装成功：

```bash
D:\bigdata\spark-3.1.2-bin-hadoop3.2\bin>spark-shell
Using Spark's default log4j profile: org/apache/spark/log4j-defaults.properties
Setting default log level to "WARN".
To adjust logging level use sc.setLogLevel(newLevel). For SparkR, use setLogLevel(newLevel).
Spark context Web UI available at http://WIN-J3JVGIJ9JBG:4040
Spark context available as 'sc' (master = local[*], app id = local-1628087798669).
Spark session available as 'spark'.
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 3.1.2
      /_/

Using Scala version 2.12.10 (Java HotSpot(TM) 64-Bit Server VM, Java 1.8.0_181)
Type in expressions to have them evaluated.
Type :help for more information.

scala>
```

命令行一行代码进行 WordCount

```bash
scala> sc.textFile("Z:/IDEA Project/big-data/spark/data/1.txt").flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).collec
t
res9: Array[(String, Int)] = Array((scala,1), (hello,2), (spark,1))
```