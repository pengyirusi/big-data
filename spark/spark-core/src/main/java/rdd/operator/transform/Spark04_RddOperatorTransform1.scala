package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 21:35
 */
object Spark04_RddOperatorTransform1 {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - flatMap
        val rdd: RDD[String] = sc.makeRDD(
            List("hello scala", "hello spark")
        )

        val flatRDD: RDD[String] = rdd.flatMap(
            s => {
                s.split(" ")
            }
        )

        flatRDD.collect().foreach(println)

        sc.stop()
    }
}
