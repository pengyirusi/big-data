package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/9 20:46
 */
object Spark11_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - repartition
        // 扩大分区
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 2)

        val newRDD: RDD[Int] = rdd.repartition(3)
        // 底层调用了 coalesce 并默认把 shuffle 设置为 true
        // 扩大分区推荐使用 repartition

        newRDD.saveAsTextFile("spark/output")
        // [3 5] [1 6] [2 4]

        sc.stop()
    }
}
