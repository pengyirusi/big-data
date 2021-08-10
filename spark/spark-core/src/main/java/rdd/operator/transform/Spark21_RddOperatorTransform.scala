package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/10 20:57
 */
object Spark21_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - key-value 类型
        val rdd1: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3)),  2)
        val rdd2: RDD[(String, Int)] = sc.makeRDD(List(("d", 5), ("c", 6), ("a", 4)),  2)

        val joinRDD: RDD[(String, (Int, Int))] = rdd1.join(rdd2)

        joinRDD.collect().foreach(println)

        sc.stop()
    }
}
