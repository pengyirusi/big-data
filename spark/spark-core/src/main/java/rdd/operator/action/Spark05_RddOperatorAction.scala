package rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/11 21:44
 */
object Spark05_RddOperatorAction {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 4), 2)

        // TODO - 行动算子
        // foreach
        rdd.collect().foreach(println) // 先采集 按分区顺序输出 driver端的打印
        println("-------------------------")
        rdd.foreach(println) // 顺序有可能乱套 executor端的打印 分布式打印 各分区按顺序 不同分区可能交叉

        sc.stop()
    }
}
