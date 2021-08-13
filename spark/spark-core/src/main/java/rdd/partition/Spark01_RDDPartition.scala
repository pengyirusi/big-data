package rdd.partition

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/13 19:31
 */
object Spark01_RDDPartition {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[(String, String)] = sc.makeRDD(List(
            ("nba", "35211412141"),
            ("cba", "345343453453"),
            ("wba", "3548364345"),
            ("nba", "334534534541")
        ))

        // 需求：想把不同类型的消息分到不同的区，这里是分成 3 个分区
        val partRDD: RDD[(String, String)] = rdd.partitionBy(new MyPartition(3))

        partRDD.saveAsTextFile("spark/output")

        sc.stop()
    }

    /**
     * 自定义分区器:
     * 1. 继承 Partitioner
     * 2. 重写方法
     */
    class MyPartition(num: Int) extends Partitioner{
        // 分区数量
        override def numPartitions: Int = num

        // 返回数据的分区索引（从 0 开始）
        override def getPartition(key: Any): Int = {
            if (key == "nba") {
                0
            } else if (key == "cba") {
                1
            } else {
                2
            }
        }
    }
}
