package notesWithCode

/**
 * Create by weiyupeng on 2021/8/1 16:03
 */
object N05_Operator {
    def main(args: Array[String]): Unit = {
        var a = 10;
        var b = 20;
        var c = 25;
        var d = 25;
        println("算术运算符:");
        println("a + b = " + (a + b));
        println("a - b = " + (a - b));
        println("a * b = " + (a * b));
        println("b / a = " + (b / a));
        println("b / a = " + (b / a));
        println("b % a = " + (b % a));
        println("c % a = " + (c % a) + "\n");

        println("关系运算符:");
        println("a == b = " + (a == b) );
        println("a != b = " + (a != b) );
        println("a > b = " + (a > b) );
        println("a < b = " + (a < b) );
        println("b >= a = " + (b >= a) );
        println("b <= a = " + (b <= a) + "\n");

        var t = true;
        var f = false;
        println("逻辑运算符:");
        println("t && f = " + (t && f));
        println("t || f = " + (t || f));
        println("! (t && f) = " + !(t && f) + "\n");

        val A = 60; // 0011 1100
        val B = 13; // 0000 1101
        println("位运算符:");
        println("A & B = " + (A & B));
        println("A | B = " + (A | B));
        println("A ^ B = " + (A ^ B));
        println("~A = " + ~A + "\n");

        // 优先级
        // 1	() []	左到右
        // 2	! ~	右到左
        // 3	* / %	左到右
        // 4	+ -	左到右
        // 5	>> >>> <<	左到右
        // 6	> >= < <=	左到右
        // 7	== !=	左到右
        // 8	&	左到右
        // 9	^	左到右
        // 10	|	左到右
        // 11	&&	左到右
        // 12	||	左到右
        // 13	= += -= *= /= %= >>= <<= &= ^= |=	右到左
        // 14	,	左到右
    }
}
