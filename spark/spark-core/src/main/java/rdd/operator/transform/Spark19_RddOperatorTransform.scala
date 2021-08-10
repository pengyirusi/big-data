package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/10 20:04
 */
object Spark19_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - key-value 类型
        val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("a", 3), ("b", 5)), 2)

        // combineByKey 三个参数
        // 参数一：将相同 key 的第一个数据进行结构的转换，动态获得类型，所以要显示定义类型
        // 参数二：分区内的计算规则
        // 参数三：分区外的计算规则
        rdd.combineByKey(
            (v: Int) => (v, 1),
            (t: (Int, Int), v: Int) => (t._1 + v, t._2 + 1),
            (t1: (Int, Int), t2: (Int, Int)) => (t1._1 + t2._1, t1._2 + t2._2)
        ).collect().foreach(println)

        sc.stop()
    }
}
