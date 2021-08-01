package notesWithCode

/**
 * Create by weiyupeng on 2021/8/1 17:03
 */
object N09_Closure {
    // 闭包即一个依赖另一个数的函数变量
    val a = (i : Int) => i * 10;

    var j = 10;
    val b = (i : Int) => i * j;
    def main(args: Array[String]): Unit = {
        println(a(1));
        println(b(2));
    }
}
