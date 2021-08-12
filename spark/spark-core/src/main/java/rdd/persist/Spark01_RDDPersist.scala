package rdd.persist

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/12 20:51
 */
object Spark01_RDDPersist {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        val sc = new SparkContext(sparkConf)

        val list: List[String] = List("hello scala", "hello spark")
        val lines: RDD[String] = sc.makeRDD(list)

        val words: RDD[String] = lines.flatMap((_: String).split(" "))

        val wordToOne: RDD[(String, Int)] = words.map {
            word => {
                println("++++++++++++++++++")
                (word, 1)
            }
        }

        val wordToCount: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _)
        wordToCount.collect().foreach(println)

        println("**********************")

        val wordGroup: RDD[(String, Iterable[Int])] = wordToOne.groupByKey()
        wordGroup.collect().foreach(println)

        // wordToOne  重复使用了  结果与预期相同
        // RDD 是在 Executor 中的  不存数据  为啥能重用呢 ?
        // 实际上还是递归调用 RDD 算子  数据不能重用
        // 所以说 就是写着省事了 实际上计算时间不变 没卵用啊
        // 这时持久化出现了！ 将 wordToOne 持久化，让它可以被多人使用！
        // 持久化：保存到内存 / 保存成一个文件
        // look Spark02_RDDPersist

        sc.stop()
    }
}
