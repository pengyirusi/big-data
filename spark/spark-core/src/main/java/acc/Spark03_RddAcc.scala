package acc

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator

/**
 * Create by weiyupeng on 2021/8/13 19:23
 */
object Spark03_RddAcc {
    def main(args: Array[String]): Unit = {
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("ACC")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

        // 获取系统累加器
        // Spark 默认提供了简单数据聚合的累加器
        val sumAcc: LongAccumulator = sc.longAccumulator("sum")

        // sc.doubleAccumulator - Double类型
        // sc.collectionAccumulator - 集合类型

        rdd.map( // foreach 换成 map
            (num: Int) => {
                // 使用累加器
                println("***") // 并没有执行，因为没有行动算子，map 不会被执行
                // 如果有两个行动算子，会执行两次，导致累加器多加，因为累加器是全局的
                // 一般情况下，累加器会放在行动算子中
                sumAcc.add(num)
                num
            }
        )

        // 获取累加器的值
        println(sumAcc.value) // 0
        println(sumAcc)

        sc.stop()
    }
}
