package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 21:28
 * 功能：想知道每个数据所在的分区
 */
object Spark03_RddOperatorTransform1 {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - mapPartitionsWithIndex
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7), 3)

        val mpiRDD: RDD[(Int, Int)] = rdd.mapPartitionsWithIndex(
            (index: Int, iter: Iterator[Int]) => iter.map((index, _: Int))
        )

        mpiRDD.collect().foreach(println)

        sc.stop()
    }
}
