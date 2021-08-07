# chapter 03

### 计算核心组件：Driver Executor

+ Driver（is a main 程序）
    1. 用户程序转化为 job
    2. 在 Executor 之间调度 task
    3. 追踪 Executor 的执行情况
    4. 通过 UI 展示运行情况
    
+ Executor（is a jvm 进程）
    1. 执行任务，把任务返回给 Driver
    
### 资源核心组件：Master Worker

+ 计算不能直接连资源，要通过 ApplicationMaster

## 核心概念

+ Executor 与 Core

--num-executors     配置 Executor 的数量

--executor-memory   配置 Executor 内存大小

--executor-cores    配置 Executor 的虚拟 CPU 核数

+ Parallelism 并行度

并发：假装多核，并行：真·多核

+ DAG 有向无环图

依赖不能循环

+ YARN Client 模式（一般用于测试）

任务运行在本地机器

+ YARN Cluster 模式

任务运行在集群