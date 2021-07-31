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
        val sc = new SparkContext()

        // TODO 执行业务操作


        // TODO 关闭连接
        sc.stop()
    }
}
