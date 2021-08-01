package core.wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/7/31 21:07
 */
object Spark03_WordCount {
    def main(args: Array[String]): Unit = {

        // TODO 建立和 Spark 框架的连接
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        val sc = new SparkContext(sparkConf)

        // TODO 执行业务操作

        val lines: RDD[String] = sc.textFile("Z:\\IDEA Project\\big-data\\spark\\data")

        val words: RDD[String] = lines.flatMap((_: String).split(" "))

        val wordToOne: RDD[(String, Int)] = words.map {
            word => (word, 1)
        }

        // Spark 框架提供了更多的功能，可以将分组聚合用一个方法实现
        // reduceByKey : 相同的 key 的数据，可以对 value 进行 reduce 聚合
        val wordToCount: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _) // 匿名函数，_+_ 相当于 (x,y)=>{x+y}

        val array: Array[(String, Int)] = wordToCount.collect()
        array.foreach(println)

        // TODO 关闭连接
        sc.stop()
    }
}
