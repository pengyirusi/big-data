package framework.service

import framework.common.TService
import framework.dao.WordCountDao
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/15 18:01
 */
class WordCountService extends TService{
    private val wordCountDao = new WordCountDao

    // 数据分析
    def dataAnalysis() = {
        val lines: RDD[String] = wordCountDao.readFile("Z:\\IDEA Project\\big-data\\spark\\data\\1.txt")

        val words: RDD[String] = lines.flatMap((_: String).split(" "))

        val wordToOne: RDD[(String, Int)] = words.map {
            word => (word, 1)
        }

        val wordToCount: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _)

        val array: Array[(String, Int)] = wordToCount.collect()

        array
    }
}
