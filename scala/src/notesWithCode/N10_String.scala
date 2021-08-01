package notesWithCode

/**
 * Create by weiyupeng on 2021/8/1 17:10
 */
object N10_String {
    var str : String = "Hello Scala ! ";

    def main(args: Array[String]): Unit = {
        println(str);

        val buf = new StringBuilder;
        buf += 'a';
        buf ++= "bc";
        println(buf.toString());
        println(buf.length);

        // 拼接
        println(str.concat(buf.toString()));
        println(str + buf.toString());

        // 格式化输出
        var floatVar = 12.456;
        var intVar = 2000;
        var stringVar = "weiyupeng";
        printf("%f, %d, %s !\n", floatVar, intVar, stringVar)
        println(s"$floatVar, $intVar, $stringVar !\n")

        // String 方法
        // 和 java 基本一样
    }
}
