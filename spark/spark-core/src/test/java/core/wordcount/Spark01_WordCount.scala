package core.wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/7/31 21:07
 */
object Spark01_WordCount {
    def main(args: Array[String]): Unit = {
        // Application
        // Spark 框架

        // TODO 建立和 Spark 框架的连接

        // JDBC ~ Connection == Spark ~ SparkContext
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        val sc = new SparkContext(sparkConf)

        // TODO 执行业务操作

        // 1. 读取文件，获取一行一行的数据
        val lines: RDD[String] = sc.textFile("Z:\\IDEA Project\\big-data\\spark\\data")

        // 2. 将一行数据进行拆分，形成一个一个的单词（分词）
        val words: RDD[String] = lines.flatMap((_: String).split(" "))

        // 3. 将数据根据单词进行分组，便于统计
        val wordGroup: RDD[(String, Iterable[String])] = words.groupBy((word: String) => word)

        // 4. 对分组后的数据进行转换
        val wordToCount: RDD[(String, Int)] = wordGroup.map {
            case (word, list) => {
                (word, list.size)
            }
        }

        // 5. 将转换结果采集到控制台打印出来
        val array: Array[(String, Int)] = wordToCount.collect()
        array.foreach(println)

        // TODO 关闭连接
        sc.stop()
    }
}
