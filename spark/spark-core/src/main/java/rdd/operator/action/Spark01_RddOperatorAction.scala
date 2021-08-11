package rdd.operator.action

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/10 22:57
 */
object Spark01_RddOperatorAction {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

        // TODO - 行动算子
        // reduce: 两两聚合
        val i: Int = rdd.reduce(_ * _)
        println(i)

        // collect: 将不同分区的数据按分区顺序采集到 Driver 端内存中
        val ints: Array[Int] = rdd.collect()
        println(ints.mkString(","))

        // count: 个数
        val l: Long = rdd.count()
        println(l)

        // first: 第一个
        val i1: Int = rdd.first()
        println(i1)

        // take: 获取 n 个
        val ints1: Array[Int] = rdd.take(3)
        println(ints1.mkString("Array(", ", ", ")"))

        val rdd2: RDD[Int] = sc.makeRDD(List(4, 2, 3, 1), 2)

        // takeOrdered: 取排序后的前 n 个数据
        val ints2: Array[Int] = rdd2.takeOrdered(3)
        println(ints2.mkString("Array(", ", ", ")"))

        sc.stop()
    }
}
