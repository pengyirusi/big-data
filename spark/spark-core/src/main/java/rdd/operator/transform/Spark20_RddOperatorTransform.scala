package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/10 20:43
 */
object Spark20_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - key-value 类型
        val rdd1: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("b", 3), ("c", 4)),  2)
        val rdd2: RDD[(String, Int)] = sc.makeRDD(List(("a", 3), ("b", 4), ("b", 5), ("d", 4)), 22)

        // join 相同的 key 对应的所有元素会连接到一起
        // 要求 key 的类型必须相同 value 无所谓
        rdd1.join(rdd2).collect().foreach(println)
        // (a,(1,3))
        // (b,(2,4))
        // (b,(2,5))
        // (b,(3,4))
        // (b,(3,5))
        // 类似 笛卡尔积
        // 没找到相同的 key 则没有输出

        sc.stop()
    }
}
