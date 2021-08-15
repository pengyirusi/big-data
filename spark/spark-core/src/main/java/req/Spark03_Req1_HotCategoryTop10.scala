package req

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/15 14:03
 */
object Spark03_Req1_HotCategoryTop10 {
    def main(args: Array[String]): Unit = {
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10")
        val sc = new SparkContext(sparkConf)

        // TODO : top 10 热门品类

        // Spark02_Req1_HotCategoryTop10 的问题：
        // 很多 reduceByKey 存在大量 shuffle 操作
        // 虽然，reduceByKey 聚合算子 Spark 会提供优化 缓存
        // 但是，当前数据的 reduceByKey 针对的都是不同数据 所以全是 shuffle

        // 1. 读取原始日志数据
        val actionRDD: RDD[String] = sc.textFile("Z:\\IDEA Project\\big-data\\spark\\data\\user_visit_action.txt")
        actionRDD.cache()

        // 2. 将数据转换结构
        // 点击场合 : (品类 ID, (1, 0, 0))
        // 下单场合 : (品类 ID, (0, 1, 0))
        // 支付场合 : (品类 ID, (0, 0, 1))
        val flatRDD: RDD[(String, (Int, Int, Int))] = actionRDD.flatMap(
            (action: String) => {
                val datas: Array[String] = action.split("_")
                if (datas(6) != "-1") {
                    List((datas(6), (1, 0, 0)))
                } else if (datas(8) != "null") {
                    val ids: Array[String] = datas(8).split(",")
                    ids.map(id => (id, (0, 1, 0)))
                } else if (datas(10) != "null") {
                    val ids: Array[String] = datas(10).split(",")
                    ids.map(id => (id, (0, 0, 1)))
                } else {
                    Nil
                }
            }
        )

        // 3. 将相同的品类 ID 的数量进行分组聚合
        // (品类 ID, (点击数量, 下单数量, 支付数量)
        val statisticsRDD: RDD[(String, (Int, Int, Int))] = flatRDD.reduceByKey(
            (t1, t2) => {
                (t1._1 + t2._1, t1._2 + t2._2, t1._3 + t2._3)
            }
        )

        // 4. 排列取 top 10
        val resultRDD: Array[(String, (Int, Int, Int))] = statisticsRDD
                .sortBy((_: (String, (Int, Int, Int)))._2, ascending = false).take(10)

        // 6. 将结果采集到 console 打印出来
        resultRDD.foreach(println)

        sc.stop()
    }
}
