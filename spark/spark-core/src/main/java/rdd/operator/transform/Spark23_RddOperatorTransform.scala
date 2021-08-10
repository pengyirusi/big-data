package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/10 21:22
 */
object Spark23_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - key-value 类型
        val rdd1: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3)),  2)
        val rdd2: RDD[(String, Int)] = sc.makeRDD(List(("d", 5), ("c", 6), ("a", 4), ("a", 7)),  2)

        // cogroup
        rdd1.cogroup(rdd2).collect().foreach(println)
        // (d,(CompactBuffer(),CompactBuffer(5)))
        // (b,(CompactBuffer(2),CompactBuffer()))
        // (a,(CompactBuffer(1),CompactBuffer(4, 7)))
        // (c,(CompactBuffer(3),CompactBuffer(6)))
        // left and right join

        sc.stop()
    }
}
