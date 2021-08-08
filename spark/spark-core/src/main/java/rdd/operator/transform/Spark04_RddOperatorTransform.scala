package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 21:32
 * 功能：将多个 list 扁平化输出为 1 个 list
 */
object Spark04_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - flatMap
        val rdd: RDD[List[Int]] = sc.makeRDD(List(List(1, 2), List(3, 4)))

        rdd.collect().foreach(println)

        // val flatRDD: RDD[Int] = rdd.flatMap(
        //     (list: List[Int]) => {
        //         list
        //     }
        // )
        val flatRDD: RDD[Int] = rdd.flatMap(list => list)

        flatRDD.collect().foreach(println)

        sc.stop()
    }
}
