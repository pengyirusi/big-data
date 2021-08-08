package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 21:50
 * 功能：计算所有分区最大值求和
 */
object Spark05_RddOperatorTransformTest {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - glom
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

        val glomRDD: RDD[Array[Int]] = rdd.glom()

        val maxRDD: RDD[Int] = glomRDD.map(
            (arr: Array[Int]) => arr.max
        )

        println(maxRDD.collect().sum)

        sc.stop()
    }
}
