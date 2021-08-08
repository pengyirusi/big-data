package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 11:11
 */
object Spark03_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - mapPartitions
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

        // 只要一个分区的数据
        val mpiRDD: RDD[Int] = rdd.mapPartitionsWithIndex(
            (index, iter) => {
                if (index == 0) {
                    iter
                } else {
                    Nil.iterator
                }
            }
        )

        mpiRDD.collect().foreach(println)

        sc.stop()
    }
}
