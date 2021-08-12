package rdd.serial

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/12 8:46
 */
object Spark01_RDDSerial {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[String] = sc.makeRDD(Array("hello", "hello scala", "spark"))

        val search = new Search("a")

        search.getMatch1(rdd).collect().foreach(println)
        search.getMatch2(rdd).collect().foreach(println)

        sc.stop()
    }

    case class Search(query: String) {
        def isMatch(s: String): Boolean = {
            s.contains(query)
        }

        def getMatch1 (rdd: RDD[String]): RDD[String] = {
            rdd.filter(isMatch)
        }

        def getMatch2 (rdd: RDD[String]): RDD[String] = {
            rdd.filter(x => x.contains(query))
        }
    }
}
