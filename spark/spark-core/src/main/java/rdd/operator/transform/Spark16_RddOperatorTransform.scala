package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/9 21:53
 */
object Spark16_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - key-value 类型
        val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("a", 2), ("b", 3), ("b", 4)), 2)

        // 相同的 key 分到一个组 形成一个对偶元组
        rdd.groupByKey().collect().foreach(println)
    }
}
