package acc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/13 18:57
 */
object Spark01_RddAcc {
    def main(args: Array[String]): Unit = {
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("ACC")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

        // val sum: Int = rdd.reduce(_ + _)
        // println(sum)

        var sum: Int = 0
        rdd.foreach(
            (num: Int) => {
                sum += num // foreach 是分布式遍历
            }
        )
        println("sum = " + sum)
        // sum = 0
        // sum 是在 Executor 里加的 并没有返回给 Driver 所以 Driver 里存的 sum 没变过 还是 0
        // 此时 ACC 出场了！

        sc.stop()
    }
}
