package bc

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
 * Create by weiyupeng on 2021/8/13 19:59
 */
object Spark01_RddBc {
    def main(args: Array[String]): Unit = {
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("ACC")
        val sc = new SparkContext(sparkConf)

        val rdd1: RDD[(String, Int)] = sc.makeRDD(List(
            ("a", 1),
            ("b", 2),
            ("c", 3)
        ))
        val rdd2: RDD[(String, Int)] = sc.makeRDD(List(
            ("a", 4),
            ("b", 5),
            ("c", 6)
        ))
        val map: mutable.Map[String, Int] = mutable.Map(
            ("a", 4),
            ("b", 5),
            ("c", 6)
        )

        // val joinRDD: RDD[(String, (Int, Int))] = rdd1.join(rdd2)
        // joinRDD.collect().foreach(println)
        // join 会导致数据量几何增长，会影响 shuffle 性能，不推荐使用
        // 改用 mapValues

        // rdd1.map {
        //     case (w, c) => {
        //         val add: Int = map.getOrElse(w, 0)
        //         (w, (c, add))
        //     }
        // }.collect().foreach(println)
        // 其实都一样 还是笛卡尔积 老师讲的有问题

        /**
         * 使用广播变量！
         */
        // 封装广播变量
        val bc: Broadcast[mutable.Map[String, Int]] = sc.broadcast(map)
        rdd1.map {
            case (w, c) => {
                // 访问广播变量
                val add: Int = bc.value.getOrElse(w, 0)
                (w, (c, add))
            }
        }.collect().foreach(println)


        sc.stop()
    }
}
