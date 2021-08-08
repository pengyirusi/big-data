package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 22:06
 * 功能：根据首字母分组
 */
object Spark06_RddOperatorTransform1 {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - groupBy
        val rdd: RDD[String] = sc.makeRDD(List("hello", "scala", "spark", "hadoop"), 2)

        val groupRDD: RDD[(Char, Iterable[String])] = rdd.groupBy((_: String).charAt(0))

        groupRDD.collect().foreach(println)

        sc.stop()
    }
}
