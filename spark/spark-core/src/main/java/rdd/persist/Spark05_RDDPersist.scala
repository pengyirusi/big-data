package rdd.persist

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/12 21:25
 */
object Spark05_RDDPersist {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        val sc = new SparkContext(sparkConf)
        sc.setCheckpointDir("spark/checkPoint")

        val list: List[String] = List("hello scala", "hello spark")
        val lines: RDD[String] = sc.makeRDD(list)

        val words: RDD[String] = lines.flatMap((_: String).split(" "))

        val wordToOne: RDD[(String, Int)] = words.map {
            word: String => (word, 1)
        }

        // wordToOne.cache()
        wordToOne.checkpoint()

        println(wordToOne.toDebugString)

        val wordToCount: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _)
        wordToCount.collect().foreach(println)

        println("**********************")

        println(wordToOne.toDebugString)

        // cache：将数据临时存储在内存中进行数据重用 so 不安全
        // cache 会在血缘关系添加新的依赖 一旦出现问题 可以从头读取数据
        // persist：自己设置存储在哪 比如磁盘 安全 but 涉及到磁盘 IO 效率低
        // cache 和 persist 都是临时存 算完了就删
        // checkPoint 永久的存在磁盘了 also 涉及到磁盘 IO 效率低
        // 而且 checkPoint 还会再次创建出一个作业 无形中多执行了一次
        // 为了提高效率 一般和 cache 联合使用 程序先 cache 后 checkPoint 只执行一次 还持久化了！
        // checkPoint 会切断血缘关系 重新建立新的血缘关系 等同于改变数据源

        // cache 增加血缘关系
        // (1) MapPartitionsRDD[2] at map at Spark05_RDDPersist.scala:20 [Memory Deserialized 1x Replicated]
        // |  MapPartitionsRDD[1] at flatMap at Spark05_RDDPersist.scala:18 [Memory Deserialized 1x Replicated]
        // |  ParallelCollectionRDD[0] at makeRDD at Spark05_RDDPersist.scala:16 [Memory Deserialized 1x Replicated]
        // (scala,1)
        // (spark,1)
        // (hello,2)
        // **********************
        // (1) MapPartitionsRDD[2] at map at Spark05_RDDPersist.scala:20 [Memory Deserialized 1x Replicated]
        // |       CachedPartitions: 1; MemorySize: 368.0 B; ExternalBlockStoreSize: 0.0 B; DiskSize: 0.0 B
        //         |  MapPartitionsRDD[1] at flatMap at Spark05_RDDPersist.scala:18 [Memory Deserialized 1x Replicated]
        // |  ParallelCollectionRDD[0] at makeRDD at Spark05_RDDPersist.scala:16 [Memory Deserialized 1x Replicated]

        // checkPoint 切断血缘关系
        // (1) MapPartitionsRDD[2] at map at Spark05_RDDPersist.scala:20 []
        // |  MapPartitionsRDD[1] at flatMap at Spark05_RDDPersist.scala:18 []
        // |  ParallelCollectionRDD[0] at makeRDD at Spark05_RDDPersist.scala:16 []
        // (scala,1)
        // (spark,1)
        // (hello,2)
        // **********************
        // (1) MapPartitionsRDD[2] at map at Spark05_RDDPersist.scala:20 []
        // |  ReliableCheckpointRDD[4] at collect at Spark05_RDDPersist.scala:30 []

        sc.stop()
    }
}
