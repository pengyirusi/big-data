package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/9 20:53
 */
object Spark12_RddOperatorTransform1 {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - sortBy
        val rdd: RDD[(String, Int)] = sc.makeRDD(List(("1", 1), ("2", 3), ("11", 2)), 2)

        val newRDD: RDD[(String, Int)] = rdd.sortBy((_: (String, Int))._2)

        newRDD.collect().foreach(println)
        // [(1,1) (11,2)]  [(2,3)]

        val newRDD2: RDD[(String, Int)] = rdd.sortBy((_: (String, Int))._1.toInt, false)
        // 降序

        newRDD2.collect().foreach(println)
        // (11,2) (2,3) (1,1)
    }
}
