package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 21:48
 * 功能：从服务器日志数据 apache.log 中获取用户请求 URL 资源路径
 */
object Spark01_RddOperatorTransformPart {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - map
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
        // 分区一：1 2
        // 分区二：3 4

        val mapRDD: RDD[Int] = rdd.map(_ * 2)

        mapRDD.saveAsTextFile("spark/output")
        // 分区一：2 4
        // 分区二：6 8
        // 说明：数据一直在一个分区被计算

        sc.stop()
    }
}
