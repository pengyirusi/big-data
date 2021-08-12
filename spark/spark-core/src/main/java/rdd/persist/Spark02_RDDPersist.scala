package rdd.persist

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel

/**
 * Create by weiyupeng on 2021/8/12 21:03
 */
object Spark02_RDDPersist {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        val sc = new SparkContext(sparkConf)

        val list: List[String] = List("hello scala", "hello spark")
        val lines: RDD[String] = sc.makeRDD(list)

        val words: RDD[String] = lines.flatMap((_: String).split(" "))

        val wordToOne: RDD[(String, Int)] = words.map {
            word: String => {
                println("++++++++++++++++++")
                (word, 1)
            }
        }

        // wordToOne.cache() // 内存持久化 存到堆内存中

        // 各种级别的持久化 一大堆
        wordToOne.persist(StorageLevel.MEMORY_ONLY) // 内存
        wordToOne.persist(StorageLevel.DISK_ONLY) // 磁盘
        wordToOne.persist(StorageLevel.MEMORY_AND_DISK)
        // wordToOne.persist(StorageLevel.XXX)

        // 持久化操作必须在行动算子执行时才开始使用

        val wordToCount: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _)
        wordToCount.collect().foreach(println)

        println("**********************")

        val wordGroup: RDD[(String, Iterable[Int])] = wordToOne.groupByKey()
        wordGroup.collect().foreach(println)

        sc.stop()
    }
}
