package notesWithCode

/**
 * Create by weiyupeng on 2021/8/1 17:22
 */
object N11_Array {
    def main(args: Array[String]): Unit = {
        // 声明
        var strings : Array[String] = new Array[String](3);
        val ints = new Array[Int](5)

        // 设置元素
        strings(0) = "hello";
        strings(1) = "scala";
        strings(4/2) = "spark";

        var strings2 = Array("hello", "scala", "spark");
        var list = Array(1, 2, 3.5, 4.8);

        // 遍历
        for (x <- list) {
            println(x)
        }

        // 输出元素之和
        var sum = 0.0;
        for (x <- list) {
            sum += x;
        }
        println("sum = " + sum);

        // 输出最大值
        var max = list(0);
        for (x <- list
             if x > max) {
            max = x;
        }
        println("max = " + max);

        // 多维数组
        var matrix: Array[Array[Int]] = Array.ofDim[Int](3, 3);
        for (i <- 0 to 2) {
            for (j <- 0 to 2) {
                matrix(i)(j) = 3 * i + j;
            }
        }

        // 遍历
        for (i <- 0 to 2) {
            for (j <- 0 to 2) {
                print(matrix(i)(j) + "\t");
            }
            println();
        }

        // 合并数组
        var list1 = Array(0, 1, 2, 3)
        var list2 = Array(0, 1, 2, 3)
        var list3 = Array.concat(list1, list2)
        println(list3.mkString("", "\t", ""))

        // 区间数组
        var list4 = Array.range(0, 10); // 左闭右开区间
        var list5 = Array.range(0, 10, 2);
        println(list4.mkString("", "\t", ""))
        println(list5.mkString("", "\t", ""))
    }
}
