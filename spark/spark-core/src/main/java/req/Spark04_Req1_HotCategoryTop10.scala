package req

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable

/**
 * Create by weiyupeng on 2021/8/15 14:21
 */
object Spark04_Req1_HotCategoryTop10 {
    def main(args: Array[String]): Unit = {
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10")
        val sc = new SparkContext(sparkConf)

        // TODO : top 10 热门品类
        // Spark03_Req1_HotCategoryTop10 继续优化：
        // 使用累加器 彻底不用 reduceByKey 没有 shuffle 了

        val acc = new HotCategoryAccumulator
        sc.register(acc, "hotCategory")

        // 1. 读取原始日志数据
        val actionRDD: RDD[String] = sc.textFile("Z:\\IDEA Project\\big-data\\spark\\data\\user_visit_action.txt")

        // 2. 将数据转换结构
        // 点击场合 : (品类 ID, (1, 0, 0))
        // 下单场合 : (品类 ID, (0, 1, 0))
        // 支付场合 : (品类 ID, (0, 0, 1))
        actionRDD.foreach(
            (action: String) => {
                val data: Array[String] = action.split("_")
                if (data(6) != "-1") {
                    acc.add((data(6), "click"))
                } else if (data(8) != "null") {
                    data(8).split(",").foreach(
                        id => acc.add((id, "order"))
                    )
                } else if (data(10) != "null") {
                    data(10).split(",").foreach(
                        id => acc.add((id, "pay"))
                    )
                }
            }
        )

        val accValue: mutable.Map[String, HotCategory] = acc.value
        val categories: mutable.Iterable[HotCategory] = accValue.map((_: (String, HotCategory))._2)

        val resultRDD: List[HotCategory] = categories.toList.sortWith(
            (left: HotCategory, right: HotCategory) => {
                if (left.clickCount > right.clickCount) {
                    true
                } else if (left.clickCount < right.clickCount) {
                    false
                } else {
                    if (left.orderCount > right.orderCount) {
                        true
                    } else if (left.orderCount < right.orderCount) {
                        false
                    } else {
                        left.payCount > right.payCount
                    }
                }
            }
        ).take(10)

        resultRDD.foreach(println)

        sc.stop()
    }

    case class HotCategory(cid:String, var clickCount:Int, var orderCount:Int, var payCount:Int)

    /**
     * 自定义累加器
     * 1. extends AccumulatorV2
     * 2. 定义泛型
     *      IN: (品类ID, 行为类型)
     *      OUT: mutable.Map[String, HotCategory]
     * 3. 重写方法
     */
    class HotCategoryAccumulator extends AccumulatorV2[(String, String), mutable.Map[String, HotCategory]]{
        private val hotMap = mutable.Map[String, HotCategory]()

        override def isZero: Boolean = hotMap.isEmpty

        override def copy(): AccumulatorV2[(String, String), mutable.Map[String, HotCategory]] = new HotCategoryAccumulator

        override def reset(): Unit = hotMap.clear()

        override def add(v: (String, String)): Unit = {
            val cid: String = v._1
            val actionType: String = v._2
            val category: HotCategory = hotMap.getOrElse(cid, HotCategory(cid, 0, 0, 0))
            if (actionType == "click") {
                category.clickCount += 1
            } else if (actionType == "order") {
                category.orderCount += 1
            } else if (actionType == "pay") {
                category.payCount += 1
            }
            hotMap.update(cid, category)
        }

        override def merge(other: AccumulatorV2[(String, String), mutable.Map[String, HotCategory]]): Unit = {
            val map: mutable.Map[String, HotCategory] = other.value;
            map.foreach{
                case (cid, hc) => {
                    val category: HotCategory = hotMap.getOrElse(cid, HotCategory(cid, 0, 0, 0))
                    category.clickCount += hc.clickCount
                    category.orderCount += hc.orderCount
                    category.payCount += hc.payCount
                    hotMap.update(cid, category)
                }
            }
        }

        override def value: mutable.Map[String, HotCategory] = hotMap
    }
}
