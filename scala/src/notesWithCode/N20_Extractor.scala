package notesWithCode

/**
 * Create by weiyupeng on 2021/8/3 22:21
 */
object N20_Extractor {
    /**
     * Extractor 提取器, 类似 java 里的 toString 和 它 的反操作
     */

    // 注入
    def apply(name: String, age: String) = {
        name + "-" + age;
    }

    // 提取
    def unapply(str: String): Option[(String, String)] = {
        val parts = str.split("#");
        if (parts.length == 2) { // 必须正好分隔开才行
            Some(parts(0), parts(1));
        } else {
            None; // 解不出来就返回 None
        }
    }

    def main(args: Array[String]): Unit = {
        println(apply("p1","28"));
        println(unapply("p2#18"));
        println(unapply("p 3"));
        println(unapply("p#4#25"));
    }

}
