package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 11:11
 * 功能：获取每个分区的最大值
 */
object Spark02_RddOperatorTransformTest {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - mapPartitions
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

        val mpRDD: RDD[Int] = rdd.mapPartitions(
            iter => {
                List(iter.max).iterator // 返回值必须也是迭代器
            }
        )

        mpRDD.collect().foreach(println)

        sc.stop()
    }
}
