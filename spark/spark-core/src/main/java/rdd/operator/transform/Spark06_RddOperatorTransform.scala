package rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/8 21:59
 */
object Spark06_RddOperatorTransform {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        // TODO 算子 - groupBy
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

        // groupBy 每个数据算出一个 key，相同的 key 放在一组
        def groupByFunction(num: Int) = {
            num % 2
        }

        // groupBy 会将数据打乱并重新组合 shuffle 但分区默认不变

        val groupRDD: RDD[(Int, Iterable[Int])] = rdd.groupBy(groupByFunction)

        groupRDD.collect().foreach(println)

        sc.stop()
    }
}
