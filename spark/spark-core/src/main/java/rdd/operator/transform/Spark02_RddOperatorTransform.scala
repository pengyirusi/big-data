package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 11:11
 */
object Spark02_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - mapPartitions
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

        // mapPartitions 比 map 更快，它是一次性把分区的数据全读下来再数据处理，而 map 是来一条执行一条
        // 问题：整个分区的数据加载到内存，费内存，容易内存溢出
        val mpRDD: RDD[Int] = rdd.mapPartitions(
            iter => { // mapPartitions 的参数是迭代器
                println(">>") // 只执行了 2 次，因为有 2 个分区
                iter.map((_: Int) * 2)
            }
        )

        mpRDD.collect().foreach(println)

        sc.stop()
    }
}
