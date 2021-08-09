package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/9 20:03
 */
object Spark09_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - distinct
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 1, 2, 3))

        rdd distinct() collect() foreach println
    }
}
