package req

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/15 10:06
 */
object Spark01_Req1_HotCategoryTop10 {
    def main(args: Array[String]): Unit = {
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10")
        val sc = new SparkContext(sparkConf)

        // TODO : top 10 热门品类
        // 先看点击数 相同再看下单数 再相同则看支付数

        // 1. 读取原始日志数据
        val actionRDD: RDD[String] = sc.textFile("Z:\\IDEA Project\\big-data\\spark\\data\\user_visit_action.txt")

        // 2. 统计品类的点击数量 (品类ID, 点击数量)
        val clickActionRDD: RDD[String] = actionRDD.filter(
            (action: String) => {
                action.split("_")(6) != "-1"
            }
        )
        val clickCountRDD: RDD[(String, Int)] = clickActionRDD.map(
            (action: String) => {
                (action.split("_")(6), 1)
            }
        ).reduceByKey((_: Int) + (_: Int))

        // 3. 统计品类的下单数量 (品类ID, 下单数量)
        val orderActionRDD: RDD[String] = actionRDD.filter(
            (action: String) => {
                action.split("_")(8) != "null"
            }
        )
        val orderCountRDD: RDD[(String, Int)] = orderActionRDD.flatMap(
            (action: String) => {
                val c_id: String = action.split("_")(8)
                val c_ids: Array[String] = c_id.split(",")
                c_ids.map((id: String) => (id, 1))
            }
        ).reduceByKey((_: Int) + (_: Int))

        // 4. 统计品类的支付数量 (品类ID, 支付数量)
        val payActionRDD: RDD[String] = actionRDD.filter(
            (action: String) => {
                action.split("_")(10) != "null"
            }
        )
        val payCountRDD: RDD[(String, Int)] = payActionRDD.flatMap(
            (action: String) => {
                val c_id: String = action.split("_")(10)
                val c_ids: Array[String] = c_id.split(",")
                c_ids.map((id: String) => (id, 1))
            }
        ).reduceByKey((_: Int) + (_: Int))

        // 5. 将品类进行排序，并且取前 10 名
        // (category_id, (clickCount, orderCount, payCount))
        // cogroup = connect + group
        val cogroupRDD: RDD[(String, (Iterable[Int], Iterable[Int], Iterable[Int]))] =
            clickCountRDD.cogroup(orderCountRDD, payCountRDD)
        val statisticsRDD: RDD[(String, (Int, Int, Int))] = cogroupRDD.mapValues {
            case (clickIter, orderIter, payIter) => {
                var clickCount = 0
                val iterator1: Iterator[Int] = clickIter.iterator
                if (iterator1.hasNext) {
                    clickCount = iterator1.next()
                }
                var orderCount = 0
                val iterator2: Iterator[Int] = orderIter.iterator
                if (iterator2.hasNext) {
                    orderCount = iterator2.next()
                }
                var payCount = 0
                val iterator3: Iterator[Int] = payIter.iterator
                if (iterator3.hasNext) {
                    payCount = iterator3.next()
                }
                (clickCount, orderCount, payCount)
            }
        }

        // val resultRDD: Array[(String, (Int, Int, Int))] = statisticsRDD
        //         .sortBy(_._2, ascending = false).take(10)
        // _._2 表示通过元组排序 默认权重为元组中的顺序

        // 改为按权重排序
        val resultRDD: Array[(String, (Int, Int, Int))] = statisticsRDD.sortBy(
            (t: (String, (Int, Int, Int))) => {
                0.2 * t._2._1 + 0.3 * t._2._2 + 0.5 * t._2._3
            }
        ).take(10)

        // 6. 将结果采集到 console 打印出来
        resultRDD.foreach(println)

        sc.stop()
    }
}
