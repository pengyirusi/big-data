package framework.util

import org.apache.spark.SparkContext

/**
 * Create by weiyupeng on 2021/8/15 18:35
 * sc 环境变量 放到线程公共区域 ThreadLocal 里 大家都能取
 */
object EnvUtil {

    private val scLocal = new ThreadLocal[SparkContext]

    def put(sc: SparkContext) = {
        scLocal.set(sc)
    }

    def take(): SparkContext = {
        scLocal.get()
    }

    def clear() = {
        scLocal.remove()
    }
}
