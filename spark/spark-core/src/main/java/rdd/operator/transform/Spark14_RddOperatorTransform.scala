package rdd.operator.transform

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/9 21:24
 */
object Spark14_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - key-value 类型
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
        // rdd 找不到 .partitionBy() 方法，因为它不是 kv 类型

        val mapRDD: RDD[(Int, String)] = rdd.map(
            (num: Int) => (num, num.toString)
        )

        // 根据指定的分区规则对数据进行重分区
        mapRDD.partitionBy(new HashPartitioner(2)).saveAsTextFile("spark/output")
        // [(2,2) (4,4)]  [(1,1) (3,3)]
        // 源码：
        // def getPartition(key: Any): Int = key match {
        //     case null => 0
        //     case _ => Utils.nonNegativeMod(key.hashCode, numPartitions)
        // }
        // def nonNegativeMod(x: Int, mod: Int): Int = {
        //     val rawMod = x % mod
        //     rawMod + (if (rawMod < 0) mod else 0)
        // }

        mapRDD.partitionBy(new HashPartitioner(2)).partitionBy(new HashPartitioner(2))
        // 如果分区规则一样，就不会执行了，他会先比较，源码：
        // def partitionBy(partitioner: Partitioner): RDD[(K, V)] = self.withScope {
        //     //...
        //     if (self.partitioner == Some(partitioner)) {
        //         self
        //     }
        //     //...
        // }
        // override def equals(other: Any): Boolean = other match {
        //     case h: HashPartitioner =>
        //         h.numPartitions == numPartitions
        //     case _ =>
        //         false
        // }

        // 也可以自己重写分区器 Partitioner

        sc.stop()
    }
}
