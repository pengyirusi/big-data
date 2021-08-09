package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/9 21:15
 */
object Spark13_RddOperatorTransform1 {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - 双 value 类型
        val rdd1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
        val rdd2: RDD[Int] = sc.makeRDD(List(3, 4, 5, 6), 4)

        val rdd3: RDD[(Int, Int)] = rdd1.zip(rdd2)
        // 会报错
        // Can't zip RDDs with unequal numbers of partitions: List(2, 4)

        println(rdd3.collect().mkString(","))

        sc.stop()
    }
}
