package rdd.builder

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/7 15:48
 */
object Spark01_RddMemoryPar {
    def main(args: Array[String]): Unit = {

        // TODO 准备环境
        // local[*] 中的 * 代表模拟计算的核数为本机的核数
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
        sparkConf.set("spark.default.parallelism", "4")
        val sc = new SparkContext(sparkConf)

        // TODO 从内存创建 RDD
        // RDD 的 并行度 和 分区
        // 第 2 个参数表示分区数，不写的话执行默认值，我这里是 12 个分区，十二核 !
        // 源码 scheduler.conf.getInt("spark.default.parallelism", totalCores)
        // 也可以改设置，如上边的 sparkConf.set("spark.default.parallelism", "4")
        val rdd: RDD[Int] = sc.makeRDD(
            List(1, 2, 3, 4, 5, 6, 7, 8), 2 // 把前边的 4 覆盖了
        )

        rdd.saveAsTextFile("spark/output")

        // TODO 关闭环境
        sc.stop()

    }
}
