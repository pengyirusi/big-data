package rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Create by weiyupeng on 2021/8/12 8:28
 */
object Spark06_RddOperatorAction {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
        val sc = new SparkContext(sparkConf)

        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

        val user = new User

        rdd.foreach(
            num => {
                println("age = " + (user.age + num))
            }
        )
        // age = 31
        // age = 33
        // age = 32
        // age = 34

        sc.stop()
    }

    class User extends Serializable { // case class User 样例类 自动实现序列化接口
        var age: Int = 30
    }

}
