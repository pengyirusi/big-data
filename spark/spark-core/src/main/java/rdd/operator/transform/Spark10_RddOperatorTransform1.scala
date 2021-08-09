package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/9 20:37
 */
object Spark10_RddOperatorTransform1 {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - coalesce
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 2)

        // 扩大分区
        val newRDD: RDD[Int] = rdd.coalesce(3)
        // 发现并没有扩大  不 shuffle 就没用啊

        val newRDD1: RDD[Int] = newRDD.coalesce(3, true)
        // [3 5] [1 6] [2 4] 分区扩大了

        newRDD1.saveAsTextFile("spark/output")

        sc.stop()
    }
}
