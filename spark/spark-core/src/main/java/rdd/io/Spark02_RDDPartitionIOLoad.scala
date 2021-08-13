package rdd.io

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/13 19:48
 */
object Spark02_RDDPartitionIOLoad {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        val sc = new SparkContext(sparkConf)

        val rdd1: RDD[String] = sc.textFile("Z:/IDEA Project/big-data/spark/spark-core/src/main/java/rdd/io/output1")
        println(rdd1.collect().mkString(","))

        val rdd2: RDD[(String, Int)] = sc.objectFile[(String, Int)]("Z:/IDEA Project/big-data/spark/spark-core/src/main/java/rdd/io/output2")
        println(rdd2.collect().mkString(","))

        val rdd3: RDD[(String, Int)] = sc.sequenceFile[String, Int]("Z:/IDEA Project/big-data/spark/spark-core/src/main/java/rdd/io/output3")
        println(rdd3.collect().mkString(","))

    }
}
