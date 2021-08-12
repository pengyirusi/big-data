package rdd.dep

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/12 20:12
 */
object Spark02_RDDDep {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        val sc = new SparkContext(sparkConf)


        val lines: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"))
        println("lines: \n", lines.dependencies) // 查看依赖关系

        val words: RDD[String] = lines.flatMap((_: String).split(" "))
        println("words: \n", words.dependencies)

        val wordToOne: RDD[(String, Int)] = words.map {
            word => (word, 1)
        }
        println("wordToOne: \n", wordToOne.dependencies)

        val wordToCount: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _) // 匿名函数，_+_ 相当于 (x,y)=>{x+y}
        println("wordToCount: \n", wordToCount.dependencies)

        val array: Array[(String, Int)] = wordToCount.collect()
        array.foreach(println)

        sc.stop()
    }
}
