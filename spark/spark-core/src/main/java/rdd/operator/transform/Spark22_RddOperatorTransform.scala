package rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/10 21:13
 */
object Spark22_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - key-value 类型
        val rdd1: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3)),  2)
        val rdd2: RDD[(String, Int)] = sc.makeRDD(List(("d", 5), ("c", 6), ("a", 4)),  2)

        // leftOuterJoin
        rdd1.leftOuterJoin(rdd2).collect().foreach(println)
        // (b,(2,None))
        // (a,(1,Some(4)))
        // (c,(3,Some(6)))

        // rightOuterJoin
        rdd2.rightOuterJoin(rdd1).collect().foreach(println)
        // (b,(None,2))
        // (a,(Some(4),1))
        // (c,(Some(6),3))
        // 就是顺序反了一下 ?

        sc.stop()
    }
}
