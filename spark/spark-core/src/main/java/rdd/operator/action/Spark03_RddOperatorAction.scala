package rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/11 21:08
 */
object Spark03_RddOperatorAction {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 4), 2)

        // TODO - 行动算子
        // countByValue
        val map: collection.Map[Int, Long] = rdd.countByValue()
        println(map)
        // Map(4 -> 1, 2 -> 1, 1 -> 1, 3 -> 1)
        // List 转换成了 Map，单值转换成 key，value 是单值出现的次数

        // countByKey: 统计 key 的词频
        val tuples: RDD[(Int, String)] = sc.makeRDD(List((1, "a"), (3, "n"), (1, "a"), (2, "a")))
        println(tuples.countByKey())

        sc.stop()
    }
}
