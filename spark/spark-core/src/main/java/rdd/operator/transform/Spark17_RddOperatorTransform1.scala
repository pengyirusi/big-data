package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/10 8:51
 */
object Spark17_RddOperatorTransform1 {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - key-value 类型
        val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("a", 3), ("b", 4)), 2)

        // 分区内和分区外计算规则相同时
        rdd.foldByKey(0)((_: Int)+(_: Int)).collect().foreach(println)

        sc.stop()
    }
}
