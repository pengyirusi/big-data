package rdd.operator.transform

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/9 21:47
 */
object Spark15_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - key-value 类型
        val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("a", 2), ("b", 3), ("b", 4)), 2)

        // 相同的 key 聚合
        val reduceRDD: RDD[(String, Int)] = rdd.reduceByKey(
            (num1: Int, num2: Int) => num1 + num2
        )
        // 如果 key 的数量为 1 时 则一次都不会执行

        reduceRDD.collect().foreach(println)

        sc.stop()
    }
}
