package acc

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.util.{AccumulatorV2, LongAccumulator}

import scala.collection.mutable

/**
 * Create by weiyupeng on 2021/8/13 19:28
 */
object Spark04_RddAcc {
    def main(args: Array[String]): Unit = {
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("ACC")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[String] = sc.makeRDD(List("hello", "spark", "hello", "scala"))

        // rdd.map((_,1)).reduceByKey(_+_) // 原来的 wordCount

        // TODO 累加器实现 wordCount
        // 1. 创建累加器对象
        val wcAcc = new MyAccumulator

        // 2. 向 Spark 注册
        sc.register(wcAcc, "wordCountAcc")

        rdd.foreach(
            (word: String) => {
                // 数据的累加
                wcAcc.add(word)
            }
        )

        println(wcAcc.value)

        sc.stop()
    }

    /**
     * 自定义累加器
     * 1. 继承 AccumulatorV2
     * 2. 定义泛型 [IN, OUT]
     * 3. 重写方法
     */
    class MyAccumulator extends AccumulatorV2[String, mutable.Map[String, Long]]{
        private var wcMap = mutable.Map[String, Long]() // 一个空的集合

        // isZero 判断是否为初始状态
        override def isZero: Boolean = wcMap.isEmpty

        override def copy(): AccumulatorV2[String, mutable.Map[String, Long]] = new MyAccumulator

        override def reset(): Unit = wcMap.clear()

        // 获取累加器需要计算的值
        override def add(word: String): Unit = {
            val newCount: Long = wcMap.getOrElse(word, 0L) + 1
            wcMap.update(word, newCount)
        }

        // 合并累加器
        // Driver 合并从不同 Executor 返回的 map
        // 这里的逻辑写两个 map 的合并即可
        override def merge(other: AccumulatorV2[String, mutable.Map[String, Long]]): Unit = {
            val map: mutable.Map[String, Long] = other.value

            map.foreach {
                case (word, count) => {
                    val newCount: Long = wcMap.getOrElse(word, 0L) + count
                    wcMap.update(word, newCount)
                }
            }
        }

        override def value: mutable.Map[String, Long] = wcMap
    }
}
