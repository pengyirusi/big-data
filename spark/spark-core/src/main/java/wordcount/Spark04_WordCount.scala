package wordcount

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

import scala.collection.mutable

/**
 * Create by weiyupeng on 2021/8/11 21:16
 * 功能：word count 的多种实现方式
 */
object Spark04_WordCount {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        val sc = new SparkContext(sparkConf)



        sc.stop()
    }

    def wordCount1(sc : SparkContext): Unit = {
        val rdd: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"))
        val words: RDD[String] = rdd.flatMap(_.split(" "))
        val groupRDD: RDD[(String, Iterable[String])] = words.groupBy(word => word)
        val wordCount: RDD[(String, Int)] = groupRDD.mapValues(iter => iter.size)
    }

    def wordCount2(sc : SparkContext): Unit = {
        val rdd: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"))
        val words: RDD[String] = rdd.flatMap(_.split(" "))
        val wordOne: RDD[(String, Int)] = words.map((_, 1))
        val groupRDD: RDD[(String, Iterable[Int])] = wordOne.groupByKey() // shuffle 了，效率不高
        val wordCount: RDD[(String, Int)] = groupRDD.mapValues(iter => iter.size)
    }

    def wordCount3(sc : SparkContext): Unit = {
        val rdd: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"))
        val words: RDD[String] = rdd.flatMap(_.split(" "))
        val wordOne: RDD[(String, Int)] = words.map((_, 1))
        val wordCount: RDD[(String, Int)] = wordOne.reduceByKey(_ + _)
    }

    def wordCount4(sc : SparkContext): Unit = {
        val rdd: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"))
        val words: RDD[String] = rdd.flatMap(_.split(" "))
        val wordOne: RDD[(String, Int)] = words.map((_, 1))
        val wordCount: RDD[(String, Int)] = wordOne.aggregateByKey(0)(_ + _, _ + _)
    }

    def wordCount5(sc : SparkContext): Unit = {
        val rdd: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"))
        val words: RDD[String] = rdd.flatMap(_.split(" "))
        val wordOne: RDD[(String, Int)] = words.map((_, 1))
        val wordCount: RDD[(String, Int)] = wordOne.foldByKey(0)(_ + _)
    }

    def wordCount6(sc : SparkContext): Unit = {
        val rdd: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"))
        val words: RDD[String] = rdd.flatMap(_.split(" "))
        val wordOne: RDD[(String, Int)] = words.map((_, 1))
        val wordCount: RDD[(String, Int)] = wordOne.combineByKey(
            (t: Int) =>t,
            (x: Int, y: Int) => x + y,
            (x: Int, y: Int) => x + y
        )
    }

    def wordCount7(sc : SparkContext): Unit = {
        val rdd: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"))
        val words: RDD[String] = rdd.flatMap(_.split(" "))
        val wordOne: RDD[(String, Int)] = words.map((_, 1))
        val wordCount: collection.Map[String, Long] = wordOne.countByKey()
    }

    def wordCount8(sc : SparkContext): Unit = {
        val rdd: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"))
        val words: RDD[String] = rdd.flatMap(_.split(" "))
        val wordCount: collection.Map[String, Long] = words.countByValue()
    }

    def wordCount9(sc : SparkContext): Unit = {
        val rdd: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"))
        val words: RDD[String] = rdd.flatMap(_.split(" "))
        val mapWord: RDD[mutable.Map[String, Long]] = words.map(
            word => {
                mutable.Map[String, Long]((word, 1))
            }
        )
        val wordCount: mutable.Map[String, Long] = mapWord.reduce(
            (map1, map2) => {
                map2.foreach {
                    case (word, count) => {
                        map1.update(word, map1.getOrElse(word, 0L) + count)
                    }
                }
                map1
            }
        )
    }
}
