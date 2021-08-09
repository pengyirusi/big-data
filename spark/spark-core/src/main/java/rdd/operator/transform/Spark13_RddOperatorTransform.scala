package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/9 21:03
 */
object Spark13_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - 双 value 类型
        val rdd1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
        val rdd2: RDD[Int] = sc.makeRDD(List(3, 4, 5, 6))

        // 交集
        val rdd3: RDD[Int] = rdd1.intersection(rdd2)
        println(rdd3.collect().mkString(","))

        // 并集
        val rdd4: RDD[Int] = rdd1.union(rdd2)
        println(rdd4.collect().mkString(","))

        // 差集
        val rdd5: RDD[Int] = rdd1.subtract(rdd2)
        println(rdd5.collect().mkString(","))
        val rdd6: RDD[Int] = rdd2.subtract(rdd1)
        println(rdd6.collect().mkString(","))

        // 交集 并集 差集 要求数据类型必须相同

        // 拉链 数据类型可以不相同
        val rdd7: RDD[(Int, Int)] = rdd1.zip(rdd2)
        println(rdd7.collect().mkString(","))

        // 输出
        // 3,4
        // 1,2,3,4,3,4,5,6
        // 1,2
        // 5,6
        // (1,3),(2,4),(3,5),(4,6)

        sc.stop()
    }
}
