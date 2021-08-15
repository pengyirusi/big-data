package req

import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
 * Create by weiyupeng on 2021/8/15 15:22
 */
object Spark05_Req2_ProductTop10InEachCategory {
    def main(args: Array[String]): Unit = {
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10")
        val sc = new SparkContext(sparkConf)

        val actionRDD: RDD[String] = sc.textFile("Z:\\IDEA Project\\big-data\\spark\\data\\user_visit_action.txt")
        actionRDD.cache()
        val top10Categories: List[String] = top10Category(actionRDD, sc).map(_.cid)
        println(top10Categories.mkString(","))

        // TODO top10 热门品类中每个品类的 top10 活跃 session 点击统计
        // 在需求一的基础上增加每个品类用户 session 的点击统计

        // 1. 过滤原始数据，保留点击和前 10 品类 ID
        val filterActionRDD: RDD[String] = actionRDD.filter(
            (action: String) => {
                val data: Array[String] = action.split("_")
                // if (data(6) != "-1") {
                //     top10Categories.contains(data(6))
                // } else {
                //     false
                // }
                data(6) != "-1" && top10Categories.contains(data(6))
            }
        )

        // 2. 根据品类 ID 和 sessionID 进行点击量的统计
        val reduceRDD: RDD[((String, String), Int)] = filterActionRDD.map(
            action => {
                val data: Array[String] = action.split("_")
                ((data(6), data(2)), 1)
            }
        ).reduceByKey(_ + _)

        // 3. 将统计的结果进行结构转换
        val mapRDD: RDD[(String, (String, Int))] = reduceRDD.map(
            (t: ((String, String), Int)) => (t._1._1, (t._1._2, t._2))
        )

        // 4. 相同的品类分在一个组中
        val groupRDD: RDD[(String, Iterable[(String, Int)])] = mapRDD.groupByKey()

        // 5. 将分组后的数据进行排序，取 top 10
        val resultRDD: RDD[(String, List[(String, Int)])] = groupRDD.mapValues(
            iter => {
                iter.toList.sortBy(_._2)(Ordering.Int.reverse).take(10)
            }
        )

        resultRDD.collect().foreach(println)

        sc.stop()
    }

    def top10Category(actionRDD: RDD[String], sc: SparkContext) = {
        val acc = new HotCategoryAccumulator
        sc.register(acc, "hotCategory")

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

        resultRDD
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
