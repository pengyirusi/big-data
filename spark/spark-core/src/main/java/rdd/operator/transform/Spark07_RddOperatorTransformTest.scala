package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/9 19:18
 * 功能：从服务器日志数据 apache.log 中获取 2015 年 5 月 17 日 的请求路径
 */
object Spark07_RddOperatorTransformTest {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[String] = sc.textFile("Z:\\IDEA Project\\big-data\\spark\\data\\apache.log")

        val filterRDD: RDD[String] = rdd.filter((_: String).split(" ")(3).split(":")(0).equals("17/05/2015"))

        filterRDD.map((_: String).split(" ")(6)).collect().foreach(println)
    }
}
