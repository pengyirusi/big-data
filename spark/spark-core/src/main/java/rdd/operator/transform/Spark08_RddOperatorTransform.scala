package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/9 19:26
 */
object Spark08_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - sample
        // 三个参数： 1.抽完是否放回  2.抽取概率次数，可以 > 1，泊松分布  [3.随机数种子 seed]
        // 第三个参数不写的话则按照系统时间随机
        val rdd: RDD[Int] = sc.makeRDD(List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))

        // 用途：解决数据倾斜的问题

        rdd.sample(true,2,0).collect().foreach(print)
    }
}
