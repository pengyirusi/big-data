package notesWithCode

import scala.util.matching.Regex

/**
 * Create by weiyupeng on 2021/8/3 22:00
 */
object N18_Regex {

    // scala 正则 和 java 正则 基本一致

    def main(args: Array[String]): Unit = {
        val pattern = "Scala".r; // .r 表示是 Regex 对象
        val str ="Scala, hello, scala !";
        println(pattern.findFirstIn(str));

        val pattern1 = new Regex("(S|s)cala");
        println(pattern1.findAllIn(str).mkString("\t"));

        println(pattern1.replaceFirstIn(str, "Java"));
        println(pattern1.replaceFirstIn(str, "Java"));
        println(pattern1.replaceAllIn(str, "Java"));
    }
}
