package notesWithCode

/**
 * Create by weiyupeng on 2021/8/1 14:23
 */
object N02_DataType {
    def main(args: Array[String]): Unit = {
        // 数据类型
        // Byte	    8 位有符号补码整数。数值区间为 -128 到 127
        // Short	16 位有符号补码整数。数值区间为 -32768 到 32767
        // Int	    32 位有符号补码整数。数值区间为 -2147483648 到 2147483647
        // Long	    64 位有符号补码整数。数值区间为 -9223372036854775808 到 9223372036854775807
        // Float	32 位, IEEE 754 标准的单精度浮点数
        // Double	64 位, IEEE 754 标准的双精度浮点数
        // Char	    16 位, 无符号 Unicode 字符, 区间值为 U+0000 到 U+FFFF
        // String	字符序列
        // Boolean	true 或 false
        // Unit	    表示无值, 和其他语言中 void 等同, 用作不返回任何结果的方法的结果类型, Unit只有一个实例值，写成()
        // Null	    null 或 空引用
        // Nothing	Nothing 类型在 Scala 的类层级的最底端；它是任何其他类型的子类型。
        // Any	    Any 是所有其他类的超类, 相当于 java 中的 Object ?
        // AnyRef	AnyRef 类是 Scala 里所有引用类 (reference class) 的基类
        var a = 1
        var b = 2L // 默认 int, long 要在末尾加 L
        var c = 2.5
        var d = 3.5F // 默认 double, float 要在末尾加 F
        var e = true | false & true
        val f: String = "hello"
        val g: Char = 'a'
        val h: String =
            """h
              |e
              |l
              |l
              |o
              |""".stripMargin
        println(h)
        val i: String = """h
                          e
l
    l
o"""
        println(i) //
        val j: Null = null

        println("----------------------------------")

        /* 转义字符
        // \b	\u0008	 退格(BS), 将当前位置移到前一列
        // \t	\u0009	 水平制表(HT), 跳到下一个TAB位置
        // \n	\u000a	 换行(LF) 将当前位置移到下一行开头
        // \f	\u000c	 换页(FF), 将当前位置移到下页开头
        // \r	\u000d	 回车(CR) ，将当前位置移到本行开头
        // \"	\u0022	 代表一个双引号 (") 字符
        // \'	\u0027	 代表一个单引号 (') 字符
        // '	\u005c	 代表一个反斜线字符 '\' */

        println("\"hello\"\t\'world\'\n\nscala //")
    }




}
