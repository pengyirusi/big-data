package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/9 20:50
 */
object Spark12_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - sortBy
        val rdd: RDD[Int] = sc.makeRDD(List(3,5,1,4,2,6), 2)

        val newRDD: RDD[Int] = rdd.sortBy((num: Int) => num)
        // [1 2 3] [4 5 6]
        // 底层也 shuffle 了

        newRDD.saveAsTextFile("spark/output")

    }
}
