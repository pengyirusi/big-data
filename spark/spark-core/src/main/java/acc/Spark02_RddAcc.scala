package acc

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator

/**
 * Create by weiyupeng on 2021/8/13 19:10
 */
object Spark02_RddAcc {
    def main(args: Array[String]): Unit = {
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("ACC")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

        // 获取系统累加器
        // Spark 默认提供了简单数据聚合的累加器
        val sumAcc: LongAccumulator = sc.longAccumulator("sum")

        // sc.doubleAccumulator - Double类型
        // sc.collectionAccumulator - 集合类型

        rdd.foreach(
            (num: Int) => {
                // 使用累加器
                sumAcc.add(num)
            }
        )

        // 获取累加器的值
        println(sumAcc.value)
        println(sumAcc)

        sc.stop()
    }
}
