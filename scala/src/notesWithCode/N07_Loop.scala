package notesWithCode

import scala.util.control.Breaks

/**
 * Create by weiyupeng on 2021/8/1 16:25
 */
object N07_Loop {
    def main(args: Array[String]): Unit = {
        var a = 10;

        /**
         * for loop
         */
        for (i <- 1 to 5) { // <- 相当于 java foreach 中的冒号: for (Integer i : list) {}
            println("i = " + i);
        }

        val numList = List(1,2,3,4,5);
        for (a <- numList;
             if a > 0; if a%2 == 1) { // filter 筛选出大于 0 的奇数
            println("a = " + a);
        }

        val newList = for {
            a <- numList;
            if a > 0; if a%2 == 1
        } yield a * 10; // 遍历还有返回值 !?
        for (a <- newList) {
            println("Value of a: " + a);
        }

        /**
         * while loop
         */
        var b = 5;
        while (b > 0) {
            println("Value of b: " + b);
            b -= 1;
        }

        // while (true) { // 无限循环
        //     println("a = " + a);
        // }

        val loop = new Breaks;
        loop.breakable {
            while (true) {
                if (b > 5) {
                    loop.break();
                }
                println("b = " + b);
                b += 2;
            }
        }

        /**
         * do while loop
         */
        var c = 5;
        do {
            println("Value of c: " + c);
            c += 3;
        } while (c < 20);
    }
}
