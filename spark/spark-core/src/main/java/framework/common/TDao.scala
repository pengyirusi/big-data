package framework.common

import framework.util.EnvUtil

/**
 * Create by weiyupeng on 2021/8/15 18:30
 */
trait TDao {

    def readFile(path: String) = {
        EnvUtil.take().textFile(path)
    }

}
