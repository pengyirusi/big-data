package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/10 21:37
 * 数据：Z:\IDEA Project\big-data\spark\data\agent.log
 * 字段：时间戳 省份 城市 点击量 广告
 * 需求：统计出广告被点击数量排行的 Top3
 */
object Spark24_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[String] = sc.textFile("Z:\\IDEA Project\\big-data\\spark\\data\\agent.log")

        val rdd1: RDD[((String, String), Int)] = rdd.map(
            (line: String) => {
                val strs: Array[String] = line.split(" ")
                (("province: "+strs(1), " ad: " + strs(4)), strs(3).toInt)
            }
        )

        val rdd2: RDD[((String, String), Int)] = rdd1.reduceByKey((_: Int) + (_: Int))

        val rdd3: RDD[((String, String), Int)] = rdd2.sortBy((_: ((String, String), Int))._2, false)

        rdd3.take(3).foreach(println)
        // ((province: 0, ad: 2),1588)
        // ((province: 9, ad: 1),1563)
        // ((province: 8, ad: 2),1435)
    }
}
