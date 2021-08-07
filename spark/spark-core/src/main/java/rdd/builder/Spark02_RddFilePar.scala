package rdd.builder

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/7 15:48
 */
object Spark02_RddFilePar {
    def main(args: Array[String]): Unit = {

        // TODO 准备环境
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
        val sc = new SparkContext(sparkConf)

        // TODO 创建 RDD
        // Spark 底层读取文件的方式等同于 Hadoop
//         def textFile(
//                 path: String,
//                 minPartitions: Int = defaultMinPartitions): RDD[String] = withScope {
//             assertNotStopped()
//             hadoopFile(path, classOf[TextInputFormat], classOf[LongWritable], classOf[Text],
//                 minPartitions).map(pair => pair._2.toString).setName(path)
//         }
        // textFile 将文件作为数据处理的数据源，它也可以分区
        // minPartitions : 最小分区数量，默认为 min(2, 计算机核数)
        // 源码：math.min(defaultParallelism, 2)
        val rdd: RDD[String] = sc.textFile("spark/data/1.txt", 4)
        // 分成了 4 个区, 4 把 2 覆盖了
        // 一共 2 行数据，第 1、3 个分区有数据
        // 源码 FileInputFormat.getSplits(JobConf job, int numSplits)

        rdd.saveAsTextFile("spark/output")

        // TODO 关闭环境
        sc.stop()

    }
}
