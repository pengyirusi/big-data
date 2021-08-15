package req

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/15 16:59
 */
object Spark06_Req3_Pageflow {
    def main(args: Array[String]): Unit = {
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10")
        val sc = new SparkContext(sparkConf)

        val actionRDD: RDD[String] = sc.textFile("Z:\\IDEA Project\\big-data\\spark\\data\\user_visit_action.txt")

        val actionDateRDD: RDD[UserVisitAction] = actionRDD.map(
            action => {
                val data: Array[String] = action.split("_")
                UserVisitAction(
                    data(0),
                    data(1).toLong,
                    data(2),
                    data(3).toLong,
                    data(4),
                    data(5),
                    data(6).toLong,
                    data(7).toLong,
                    data(8),
                    data(9),
                    data(10),
                    data(11),
                    data(12).toLong,
                )
            }
        )
        actionDateRDD.cache()

        // TODO 对指定的页面连续跳转进行统计
        // 加一个 filter 操作

        // TODO 计算分母
        val pageIDToCountMap: Map[Long, Long] = actionDateRDD.map(
            action => {
                (action.page_id, 1L)
            }
        ).reduceByKey(_ + _).collect().toMap

        // TODO 计算分子

        // 根据 session 进行分组
        val sessionRDD: RDD[(String, Iterable[UserVisitAction])] = actionDateRDD.groupBy(_.session_id)

        // 分组后根据访问时间进行排序（升序）
        val mvRDD: RDD[(String, List[((Long, Long), Int)])] = sessionRDD.mapValues(
            (iter: Iterable[UserVisitAction]) => {
                val sortList: List[UserVisitAction] = iter.toList.sortBy((_: UserVisitAction).action_time)

                // [1 2 3 4] => [1-2 2-3 3-4]
                // [1 2] [2 3] [3 4]
                // sliding 滑窗
                // zip 拉链
                val flowIDs: List[Long] = sortList.map((_: UserVisitAction).page_id)
                val pageFlowIDs: List[(Long, Long)] = flowIDs.zip(flowIDs.tail)
                pageFlowIDs.map((_, 1))
            }
        )

        val flatRDD: RDD[((Long, Long), Int)] = mvRDD.map(_._2).flatMap(list => list)

        val dateRDD: RDD[((Long, Long), Int)] = flatRDD.reduceByKey(_ + _)

        // TODO 计算单跳转换率
        // 分子 / 分母
        dateRDD.foreach{
            case ((pageID1, pageID2), sum) => {
                val long: Long = pageIDToCountMap.getOrElse(pageID1, 0L)

                println(s"页面${pageID1}跳转到${pageID2}的单跳转换率为: " + sum.toDouble / long)
            }
        }

        sc.stop()
    }
}

case class UserVisitAction (
        date: String,
        user_id: Long,
        session_id: String,
        page_id: Long,
        action_time: String,
        search_keyword: String,
        click_category_id: Long,
        click_product_id: Long,
        order_category_ids: String,
        order_product_ids: String,
        pay_category_ids: String,
        pay_product_ids: String,
        city_id: Long
)