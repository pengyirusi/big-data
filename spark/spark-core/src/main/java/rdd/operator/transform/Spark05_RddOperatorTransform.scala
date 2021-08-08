package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 21:44
 */
object Spark05_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - glom
        // 将同一个分区的数据直接转换为相同类型的内存数组进行处理，分区不变
        // flat : 整体 => 个体
        // glom : 个体 => 整体
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

        val glomRDD: RDD[Array[Int]] = rdd.glom()

        glomRDD.collect().foreach(data=>println(data.mkString(",")))

        sc.stop()
    }
}
