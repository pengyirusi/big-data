package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 11:11
 * 功能：想知道每个数据所在的分区
 */
object Spark03_RddOperatorTransform1 {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - mapPartitions
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7), 5)

        val mpiRDD: RDD[Int] = rdd.mapPartitionsWithIndex(
            (index, iter) => {
                iter.map((_: Int) =>(_: Int,index))
            }
        )

        mpiRDD.collect().foreach(println)

        sc.stop()
    }
}
