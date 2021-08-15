package req

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/15 10:56
 */
object Spark02_Req1_HotCategoryTop10 {
    def main(args: Array[String]): Unit = {
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10")
        val sc = new SparkContext(sparkConf)

        // TODO : top 10 热门品类

        // Spark01_Req1_HotCategoryTop10 存在的问题：
        // 1. actionRDD 重复使用：加存缓
        // 2. cogroupRDD 存在 shuffle 性能较低

        // 1. 读取原始日志数据
        val actionRDD: RDD[String] = sc.textFile("Z:\\IDEA Project\\big-data\\spark\\data\\user_visit_action.txt")
        actionRDD.cache()

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
        // (category_id, clickCount) => (category_id, (clickCount, 0, 0))
        // (category_id, orderCount) => (category_id, (0, orderCount, 0))
        // (category_id, payCount) => (category_id, (0, 0, payCount))
        // (category_id, (clickCount, orderCount, payCount))
        // 这样就不用 cogroup 了
        val rdd1: RDD[(String, (Int, Int, Int))] = clickCountRDD.map {
            case (cid, count) => {
                (cid, (count, 0, 0))
            }
        }
        val rdd2: RDD[(String, (Int, Int, Int))] = orderCountRDD.map {
            case (cid, count) => {
                (cid, (0, count, 0))
            }
        }
        val rdd3: RDD[(String, (Int, Int, Int))] = payCountRDD.map {
            case (cid, count) => {
                (cid, (0, 0, count))
            }
        }

        val statisticsRDD: RDD[(String, (Int, Int, Int))] = (rdd1 ++ rdd2 ++ rdd3).reduceByKey(
            (t1, t2) => {
                (t1._1 + t2._1, t1._2 + t2._2, t1._3 + t2._3)
            }
        )

        val resultRDD: Array[(String, (Int, Int, Int))] = statisticsRDD
                .sortBy((_: (String, (Int, Int, Int)))._2, ascending = false).take(10)

        // 6. 将结果采集到 console 打印出来
        resultRDD.foreach(println)

        sc.stop()
    }
}
