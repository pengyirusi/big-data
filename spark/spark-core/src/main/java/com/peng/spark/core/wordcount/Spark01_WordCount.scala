package com.peng.spark.core.wordcount

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/7/31 21:07
 */
object Spark01_WordCount {
    def main(args: Array[String]): Unit = {
        // Application
        // Spark 框架

        // TODO 建立和 Spark 框架的连接
        // JDBC ~ Connection == Spark ~ SparkContext
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        val sc = new SparkContext(sparkConf)

        // TODO 执行业务操作

        // 1. 读取文件，获取一行一行的数据

        // 2. 将一行数据进行拆分，形成一个一个的单词（分词）

        // 3.


        // TODO 关闭连接
        sc.stop()
    }
}
