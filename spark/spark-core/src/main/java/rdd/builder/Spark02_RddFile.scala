package rdd.builder

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/7 15:48
 */
object Spark02_RddFile {
    def main(args: Array[String]): Unit = {

        // TODO 准备环境
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
        val sc = new SparkContext(sparkConf)

        // TODO 创建 RDD
        // 从文件中创建 RDD，将文件中的数据作为处理的数据源
        // val rdd: RDD[String] = sc.textFile("Z:\\IDEA Project\\big-data\\spark\\data")
        // 可以绝对路径，也可以相对路径
        // 相对路径：以项目 big-data 为起始路径，子目录斜杠可以 \\ 也可以 /
        // 可以指定文件，也可以指定目录
        // val rdd: RDD[String] = sc.textFile("spark\\data")
        val rdd: RDD[String] = sc.textFile("spark/data")
        // 也可以使用 regex
        // val rdd: RDD[String] = sc.textFile("spark/data/1*")
        // 也可以分布式存储系统路径：HDFS
        // val rdd: RDD[String] = sc.textFile("hdfs://linux1:8020/1.txt")

        rdd.collect().foreach(println)

        // TODO 关闭环境
        sc.stop()

    }
}
