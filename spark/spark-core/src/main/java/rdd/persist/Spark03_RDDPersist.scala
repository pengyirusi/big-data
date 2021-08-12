package rdd.persist

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel

/**
 * Create by weiyupeng on 2021/8/12 21:14
 */
object Spark03_RDDPersist {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        val sc = new SparkContext(sparkConf)
        sc.setCheckpointDir("spark/checkPoint")
        // 检查点路径一般都放到 HDFS 中

        val list: List[String] = List("hello scala", "hello spark")
        val lines: RDD[String] = sc.makeRDD(list)

        val words: RDD[String] = lines.flatMap((_: String).split(" "))

        val wordToOne: RDD[(String, Int)] = words.map {
            word: String => {
                println("++++++++++++++++++")
                (word, 1)
            }
        }

        // checkpoint 需要落盘 需要指定检查点保存路径
        // 检查点路径中保存的作业 执行结束后不会被删除
        wordToOne.checkpoint()

        val wordToCount: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _)
        wordToCount.collect().foreach(println)

        println("**********************")

        val wordGroup: RDD[(String, Iterable[Int])] = wordToOne.groupByKey()
        wordGroup.collect().foreach(println)

        sc.stop()
    }
}
