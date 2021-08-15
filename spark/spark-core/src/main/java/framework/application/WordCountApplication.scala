package framework.application

import framework.common.TApplication
import framework.controller.WordCountController

/**
 * Create by weiyupeng on 2021/8/15 18:00
 */
object WordCountApplication extends App with TApplication{

    start("local[*]", "wordCount") {
        val controller = new WordCountController
        controller.execute()
    }
}
