package rdd.builder

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/7 15:48
 */
object Spark02_RddFile1 {
    def main(args: Array[String]): Unit = {

        // TODO 准备环境
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
        val sc = new SparkContext(sparkConf)

        // TODO 创建 RDD
        // 从文件中创建 RDD，将文件中的数据作为处理的数据源
        // textFile 以行为单位
        // wholeTextFiles 以文件为单位，返回的是元组 (filePath, fileText)
        val rdd: RDD[(String, String)] = sc.wholeTextFiles("spark/data")

        rdd.collect().foreach(println)

        // TODO 关闭环境
        sc.stop()

    }
}
