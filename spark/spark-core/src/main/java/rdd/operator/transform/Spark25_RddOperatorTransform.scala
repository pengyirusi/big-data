package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/10 22:00
 * 数据：Z:\IDEA Project\big-data\spark\data\agent.log
 * 字段：时间戳 省份 城市 用户id 广告
 * 需求：统计出每个省份的广告被点击数量排行的 Top3
 */
object Spark25_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[String] = sc.textFile("Z:\\IDEA Project\\big-data\\spark\\data\\agent.log")

        val rdd1: RDD[((String, String), Int)] = rdd.map(
            (line: String) => {
                val strs: Array[String] = line.split(" ")
                ((strs(1), strs(4)), 1)
            }
        )

        val rdd2: RDD[((String, String), Int)] = rdd1.reduceByKey((_: Int) + (_: Int))

        val rdd3: RDD[(String, (String, Int))] = rdd2.map {
            case ((prv, ad), sum) => {
                (prv, (ad, sum))
            }
        }

        val rdd4: RDD[(String, Iterable[(String, Int)])] = rdd3.groupByKey()

        val rdd5: RDD[(String, List[(String, Int)])] = rdd4.mapValues(
            iter => {
                iter.toList.sortBy(_._2)(Ordering.Int.reverse).take(3)
            }
        )

        rdd5.collect().foreach(println)
    }
}
