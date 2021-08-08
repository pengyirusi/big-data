package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 21:41
 * 功能：将 List(List(1, 2), 3, List(4, 5)) 扁平化输出
 */
object Spark04_RddOperatorTransform2 {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - flatMap
        val rdd: RDD[Any] = sc.makeRDD(List(List(1, 2), 3, List(4, 5)))

        val flatRDD: RDD[Any] = rdd.flatMap(
            element => {
                element match {
                    case list: List[_] => list
                    case num => List(num) // 不是集合，强行变成集合
                }
            }
        )

        flatRDD.collect().foreach(println)

        sc.stop()
    }
}
