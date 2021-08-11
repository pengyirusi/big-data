package rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/11 20:59
 */
object Spark02_RddOperatorAction {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

        // TODO - 行动算子
        // aggregate
        val i: Int = rdd.aggregate(10)(_ + _, _ + _)
        // aggregateByKey 中的初始值只是分区内的
        // aggregate 中的初始值 既当分区内的 又当分区间的 有啥用呢 ？
        println(i)

        // fold 分区内和分区间的算法相同时 简写方法
        val j: Int = rdd.fold(10)(_+_)
        println(j)

        sc.stop()
    }
}
