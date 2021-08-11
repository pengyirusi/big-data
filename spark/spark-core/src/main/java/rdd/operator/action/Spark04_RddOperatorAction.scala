package rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/11 21:37
 */
object Spark04_RddOperatorAction {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 4), 2)

        // TODO - 行动算子
        // saveXxx
        rdd.saveAsTextFile("spark/output1")
        rdd.saveAsObjectFile("spark/output2") // 啥破玩意 乱码的 保存的对象信息 效率可能高点 ?
        rdd.map((_,1)).saveAsSequenceFile("spark/output3") // 同上 序列化 ?
        // saveAsSequenceFile 必须是键值对 才能用

        sc.stop()
    }
}
