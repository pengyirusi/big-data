package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 22:13
 * 功能：从服务器日志数据 apache.log 获取每个时间段访问量
 */
object Spark06_RddOperatorTransform2 {
    def main(args: Array[String]): Unit = {
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[String] = sc.textFile("Z:\\IDEA Project\\big-data\\spark\\data\\apache.log")

        val groupRDD: RDD[(String, Iterable[String])] = rdd.groupBy(_.split(" ")(3).split(":")(1))

        val numRDD: RDD[(String, Int)] = groupRDD.map(
            (t: (String, Iterable[String])) => {
                (t._1, t._2.size)
            }
        )

        numRDD.collect().foreach(println)
    }
}
