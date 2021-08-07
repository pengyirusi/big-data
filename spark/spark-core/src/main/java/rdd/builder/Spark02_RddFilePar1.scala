package rdd.builder

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/7 15:48
 */
object Spark02_RddFilePar1 {
    def main(args: Array[String]): Unit = {

        // TODO 准备环境
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
        val sc = new SparkContext(sparkConf)

        // TODO 数据分区的分配
        // 1. 数据以行为单位读取，和字节数没有关系
        // 2. 数据读取以偏移量（字节的偏移量）为单位，但如果读取多个文件就得以文件为单位了，也就是文件数的偏移量
        // 3. 数据分区的偏移量范围的计算，偏移量不会被重复读取

        val rdd: RDD[String] = sc.textFile("spark/data/3.txt", 3)
        // 最小分区 minPartition = 3, 实际分区为 4 !!!??? 后边的是备用的，不工作
        // 4 个区分别为 [0,1,2] [3,4] [5,6] []

        rdd.saveAsTextFile("spark/output")

        // TODO 关闭环境
        sc.stop()

    }
}
