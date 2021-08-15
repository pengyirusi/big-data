package framework.common

import framework.util.EnvUtil
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Create by weiyupeng on 2021/8/15 18:16
 */
trait TApplication {
    def start(master: String = "local[*]", app: String = "application")(op: => Unit): Unit = {
        val sparkConf: SparkConf = new SparkConf().setMaster(master).setAppName(app)
        val sc = new SparkContext(sparkConf)
        EnvUtil.put(sc)

        try {
            op
        } catch {
            case ex => println(ex.getMessage)
        }

        sc.stop()
        EnvUtil.clear()
    }
}
