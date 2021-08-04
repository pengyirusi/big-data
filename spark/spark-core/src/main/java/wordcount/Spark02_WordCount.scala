package wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/7/31 21:07
 */
object Spark02_WordCount {
    def main(args: Array[String]): Unit = {

        // TODO 建立和 Spark 框架的连接
        // JDBC ~ Connection == Spark ~ SparkContext
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        val sc = new SparkContext(sparkConf)

        // TODO 执行业务操作

        val lines: RDD[String] = sc.textFile("Z:\\IDEA Project\\big-data\\spark\\data")

        val words: RDD[String] = lines.flatMap((_: String).split(" "))

        val wordToOne: RDD[(String, Int)] = words.map {
            word => (word, 1)
        }

        val wordGroup: RDD[(String, Iterable[(String, Int)])] = wordToOne.groupBy (
            (t: (String, Int)) => t._1
        )

        val wordToCount: RDD[(String, Int)] = wordGroup.map {
            case (word, list) => {
                list.reduce(
                    (t1, t2) => {
                        (t1._1, t1._2 + t2._2)
                    }
                )
            }
        }

        val array: Array[(String, Int)] = wordToCount.collect()
        array.foreach(println)

        // TODO 关闭连接
        sc.stop()
    }
}
