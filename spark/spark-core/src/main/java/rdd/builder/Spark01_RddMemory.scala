package rdd.builder

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/7 15:48
 */
object Spark01_RddMemory {
    def main(args: Array[String]): Unit = {

        // TODO 准备环境
        // local[*] 中的 * 代表模拟计算的核数为本机的核数
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
        val sc = new SparkContext(sparkConf)

        // TODO 从内存创建 RDD
        // 从内存中创建 RDD，将内存中集合的数据作为处理的数据源
        val seq: Seq[Int] = Seq[Int](1, 2, 3, 4)

        // parallelize 表示并行
        // val rdd: RDD[Int] = sc.parallelize(seq)
        val rdd: RDD[Int] = sc.makeRDD(seq) // 底层调用的还是 parallelize 方法

        rdd.collect().foreach(println)

        // TODO 关闭环境
        sc.stop()

    }
}
