package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/10 8:54
 */
object Spark18_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - key-value 类型
        val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("a", 3), ("b", 5)), 2)

        // aggregateByKey 最终的返同数据应该和初始值的类型保持一致
        // val newRDD: RDD[(String, String)] = rdd.aggregateByKey("0")(_ + _, _ + _)

        // 获取相同 key 的数据平均值
        val newRDD: RDD[(String, (Int, Int))] = rdd.aggregateByKey((0, 0))( // sum count
            (x: (Int, Int), y: Int) => (x._1 + y, x._2 + 1),
            (x: (Int, Int), y: (Int, Int)) => (x._1 + y._1, x._2 + y._2)
        )

        // newRDD.map(
        //     (kv: (String, (Int, Int))) => (kv._1, kv._2._1 / kv._2._2)
        // ).collect().foreach(println)

        newRDD.mapValues(
            (x: (Int, Int)) => {
                1.0 * x._1 / x._2
            }
        ).collect().foreach(println)

        sc.stop()
    }
}
