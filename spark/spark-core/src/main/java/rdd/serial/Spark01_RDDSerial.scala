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
        // Search 不序列化会报错
        // 类的构造参数其实是类的属性，构造参数需要进行闭包检测，其实就等同于类进行闭包检测

        search.getMatch2(rdd).collect().foreach(println)
        // Search 不序列化不会报错 神奇！
        // 因为 val s = query 后边只用了 s 而没用 query
        // query 是类的属性 s 只是一个 String 而 String 本身就是序列化的
        // 所以 没有初始化类 直接把 s 放到 Executor 中 算就完了！


        sc.stop()
    }

    class Search(query: String) extends Serializable {
        def isMatch(s: String): Boolean = {
            s.contains(query) // means s.contains(this.query)
        }

        // 函数序列化
        def getMatch1 (rdd: RDD[String]): RDD[String] = {
            rdd.filter(isMatch)
        }

        // 属性序列化
        def getMatch2 (rdd: RDD[String]): RDD[String] = {
            val s = query
            rdd.filter(x => x.contains(s))
        }
    }
}
