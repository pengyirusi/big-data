package notesWithCode

/**
 * Create by weiyupeng on 2021/8/1 16:46
 */
object N08_Method {
    def m(x : Int): Int = x + 3; // 前边 Int 是 输入类型 后边 Int 是 输出类型
    val f = (x: Int) => x + 3 : Int;

    def main(args: Array[String]): Unit = {
        println(m(1));
        println(f(2));

        val a = new A;
        println(a.addThree(3));
        println(a.addThree2(4));
    }
}

class A {

    def addThree(x : Int) : Int = {
        var sum : Int = x + 3;
        return sum;
    }

    def addThree2(x : Int) : Int = {
        x + 3;
    }
}
