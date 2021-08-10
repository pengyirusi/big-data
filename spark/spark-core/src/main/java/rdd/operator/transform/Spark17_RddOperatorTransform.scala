package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/10 8:25
 */
object Spark17_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - key-value 类型
        val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("a", 2), ("a", 3), ("a", 4)), 2)

        // aggregate  n.合计
        // aggregateByKey 有两个参数列表
        // 第一个参数列表。有一个参数：1.初始值（在计算规则里都是两两运算的，第一个数没人算，就和这个参数算）
        // 第二个参数列表。有两个参数：1.分区内计算规则 2.分区外计算规则
        rdd.aggregateByKey(0)(
            (x: Int, y: Int) => math.max(x,y),
            (x: Int, y: Int) => x + y
        ).collect().foreach(println)

        sc.stop()
    }
}
