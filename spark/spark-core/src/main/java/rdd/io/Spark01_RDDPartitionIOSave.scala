package rdd.io

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/13 19:44
 */
object Spark01_RDDPartitionIOSave {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[(String, Int)] = sc.makeRDD(
            List(
                ("a", 1),
                ("b", 2),
                ("c", 3)
            )
        )
        rdd.saveAsTextFile("Z:/IDEA Project/big-data/spark/spark-core/src/main/java/rdd/io/output1")
        rdd.saveAsObjectFile("Z:/IDEA Project/big-data/spark/spark-core/src/main/java/rdd/io/output2")
        rdd.saveAsSequenceFile("Z:/IDEA Project/big-data/spark/spark-core/src/main/java/rdd/io/output3")
    }
}
