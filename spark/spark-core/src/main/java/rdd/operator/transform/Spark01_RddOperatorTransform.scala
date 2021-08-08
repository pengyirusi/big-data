package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 11:11
 */
object Spark01_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - map
        // 将逐条的数据进行转换：1.类型转换 2.值转换

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
        // 1 2 3 4 => 2 4 6 8

        // 转换函数
        def mapFunction(num: Int): Int = {
            num * 2
        }
        // val mapRdd: RDD[Int] = rdd.map(mapFunction)

        val mapRdd: RDD[Int] = rdd.map((_: Int)*2) // 匿名函数

        mapRdd.collect().foreach(println)

        sc.stop()
    }
}
