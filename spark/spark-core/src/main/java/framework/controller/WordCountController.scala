package framework.controller

import framework.common.TController
import framework.service.WordCountService

/**
 * Create by weiyupeng on 2021/8/15 18:01
 */
class WordCountController extends TController{

    private val wordCountService = new WordCountService

    // 调度
    def execute() = {
        val array: Array[(String, Int)] = wordCountService.dataAnalysis()
        array.foreach(println)
    }
}
