package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 11:19
 */
object Spark01_RddOperatorTransformPar {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - map
        // val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 1)
        // 串行
        // >> num = 1
        // ## num = 1
        // >> num = 2
        // ## num = 2
        // >> num = 3
        // ## num = 3
        // >> num = 4
        // ## num = 4

        // val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
        // 2 个分区并行
        // >> num = 1
        // >> num = 3
        // ## num = 3
        // >> num = 4
        // ## num = 4
        // ## num = 1
        // >> num = 2
        // ## num = 2

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 3)
        // 3 个分区并行
        // >> num = 1
        // >> num = 2
        // >> num = 3
        // ## num = 3
        // ## num = 2
        // ## num = 1
        // >> num = 4
        // ## num = 4

        val mapRDD: RDD[Int] = rdd.map(
            num => {
                println(">> num = " + num)
                num
            }
        )

        val mapRDD1: RDD[Int] = mapRDD.map(
            num => {
                println("## num = " + num)
                num
            }
        )

        mapRDD1.collect()

        sc.stop()

    }
}
