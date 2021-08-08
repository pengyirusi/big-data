# chapter 04
spark 核心编程 = RDD + 累加器 + 广播变量

## RDD

Driver 与 Executor 的通信:

代码在 package test

简单模拟分布式计算:

代码在 package test2

stack.scala 相当于数据结构框架, subtask.scala 是真·task

+ RDD

弹性分布式数据集

+ 弹性
    1. 存储：内存与磁盘的自动切换
    2. 容错：数据丢失可以自动恢复
    3. 计算：计算出错重试机制
    4. 分片（分区）：根据需要重新分片
    
+ 分布式

数据存储在大数据集群不同节点上

+ 数据集

RDD 封装了计算的逻辑，不存储数据

+ 数据抽象

RDD 是一个抽象类，需要子类具体实现

+ 不可变

RDD 封装的计算逻辑不能发生改变

+ 可分区 并行计算

+ Others

RDD 是最小计算单元，逻辑不能太复杂，以便能复用

Driver 将任务分成多个 task( = **RDD** + 分配的资源 ) 分配给不同的 executor

RDD 的数据处理方式类似于 IO 流，也有装饰者模式

RDD 的数据只有在调用 collect 方法时才会真正执行业务逻辑操作，之前的操作只是在定义

RDD 是不保存数据的，但 IO 有 buffer 可以临时保存数据

+ 核心属性
    1. 分区列表，并行计算
    2. 每个分区都有计算（相同的计算逻辑）
    3. RDD 有 0 个或多个 dependency
    4. 分区器 将数据分区的工具
    5. 在每个分区计算时都有一个首选的位置（ 找效率最优的 executor ），移动数据不如移动计算
    

+ 执行原理

## 基础编程

+ RDD 创建: package rdd.builder
    1. 从内存（集合）中创建 ※
    2. 从外部存储文件创建 ※
    3. 从其他 RDD 创建
    4. 直接创建
  
3 和 4 一般在源码里使用，代码中一般用 1 和 2
  
+ RDD 的并行度和分区

package rdd.builder xxxPar.scala

### RDD 方法
+ RDD 方法 => RDD 算子

1. 转换：功能的补充和封装，旧的 RDD 包装成新的 RDD，如 map flatMap
2. 行动：触发任务的调度和作业的执行，如 collect
  

#### RDD 转换算子

代码 package rdd.operator.transform

+ map 转换映射

+ mapPartitions

+ mapPartitionsWithIndex

能看到分区编号









## 累加器

分布式共享只写变量

## 广播变量

分布式共享只读变量