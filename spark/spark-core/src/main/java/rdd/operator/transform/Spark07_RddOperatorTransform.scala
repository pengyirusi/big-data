package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/9 19:14
 */
object Spark07_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - filter
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

        // 符合规则的 => 保留
        val filterRDD: RDD[Int] = rdd.filter((_: Int) % 2 != 0)

        filterRDD.collect().foreach(println)
    }
}
