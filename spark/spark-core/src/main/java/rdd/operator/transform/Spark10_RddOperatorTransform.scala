package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/9 20:10
 */
object Spark10_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - coalesce
        // 缩减分区
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 3)
        // old : [1 2] [3 4] [5 6]

        val newRDD: RDD[Int] = rdd.coalesce(2)
        // new : [1 2] [3 4 5 6]
        // why ?
        // coalesce 默认不会把分区的数据打乱组合
        // 这种方式可能会导致数据不均衡，如果想均衡可以 shuffle 如下
        // val newRDD: RDD[Int] = rdd.coalesce(2, true)
        // [1 4 5] [2 3 6] shuffle 打乱 非常乱

        newRDD.saveAsTextFile("spark/output")

        sc.stop()
    }
}
