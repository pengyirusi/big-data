package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 11:11
 * 功能：从服务器日志数据 apache.log 中获取用户请求 URL 资源路径
 */
object Spark01_RddOperatorTransformTest {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - map
        val rdd: RDD[String] = sc.textFile("Z:\\IDEA Project\\big-data\\spark\\data\\apache.log")

        def extractUrl(str: String): String = {
            val i: Int = str.indexOf("GET")
            str.substring(i + 4)
        }

        val mapRdd: RDD[String] = rdd.map(extractUrl)

        mapRdd.collect().foreach(println)

        sc.stop()
    }
}
