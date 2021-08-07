package rdd.builder

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/7 15:48
 */
object Spark01_RddMemoryPar1 {
    def main(args: Array[String]): Unit = {

        // TODO 准备环境
        // local[*] 中的 * 代表模拟计算的核数为本机的核数
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
        val sc = new SparkContext(sparkConf)

        // TODO 从内存创建 RDD
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8), 2)
        // 分成 2 个分区 1, 2, 3, 4 和 5, 6, 7, 8
        // 分成 3 个分区 1, 2 和 3, 4, 5 和 6, 7, 8
        // 发现规律：前边的分区数据少，说明是向下取整
        // 源码证明 ParallelCollectionRDD.scala :
//         def positions(length: Long, numSlices: Int): Iterator[(Int, Int)] = {
//             (0 until numSlices).iterator.map { i =>
//                 val start = ((i * length) / numSlices).toInt
//                 val end = (((i + 1) * length) / numSlices).toInt
//                 (start, end)
//             }
//         }
        // from until : 左闭右开 [ )

        rdd.saveAsTextFile("spark/output")

        // TODO 关闭环境
        sc.stop()
    }
}
