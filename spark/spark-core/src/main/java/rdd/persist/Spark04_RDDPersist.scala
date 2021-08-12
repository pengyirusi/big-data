package rdd.persist

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/12 21:19
 */
object Spark04_RDDPersist {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        val sc = new SparkContext(sparkConf)
        sc.setCheckpointDir("spark/checkPoint")

        val list: List[String] = List("hello scala", "hello spark")
        val lines: RDD[String] = sc.makeRDD(list)

        val words: RDD[String] = lines.flatMap((_: String).split(" "))

        val wordToOne: RDD[(String, Int)] = words.map {
            word: String => {
                println("++++++++++++++++++")
                (word, 1)
            }
        }

        // cache：将数据临时存储在内存中进行数据重用 so 不安全
        // persist：自己设置存储在哪 比如磁盘 安全 but 涉及到磁盘 IO 效率低
        // cache 和 persist 都是临时存 算完了就删
        // checkPoint 永久的存在磁盘了 also 涉及到磁盘 IO 效率低
        // 而且 checkPoint 还会再次创建出一个作业 无形中多执行了一次
        // 为了提高效率 一般和 cache 联合使用 程序先 cache 后 checkPoint 只执行一次 还持久化了！

        wordToOne.cache()
        wordToOne.checkpoint()

        val wordToCount: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _)
        wordToCount.collect().foreach(println)

        println("**********************")

        val wordGroup: RDD[(String, Iterable[Int])] = wordToOne.groupByKey()
        wordGroup.collect().foreach(println)

        sc.stop()
    }
}
