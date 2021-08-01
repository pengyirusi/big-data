package notesWithCode

/**
 * Create by weiyupeng on 2021/8/1 14:48
 */
object N03_Variable {
    def main(args: Array[String]): Unit = {
        var a : Integer = 1; // 变量
        val B : Integer = 2; // 常量

        a = 3; // 变量能修改
        // B = 5; // 常量不能修改

        var i = 'a';
        // i = "hello" // 报错！i 没指定，但是已经被推断出是个 Char，虽然没显式写明

        val j, k = 1; // 声明 j k 都为 1

        val tuple1 = (100, "hello") // 声明一个元组

        println(tuple1)
        println(tuple1._1)
        println(tuple1._2)
    }
}
