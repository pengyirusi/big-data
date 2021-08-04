package notesWithCode

import scala.collection.mutable.Set

/**
 * Create by weiyupeng on 2021/8/1 20:42
 */
object N12_Collection {

    // collection contains:
    // list set map tuple option iterator

    def main(args: Array[String]): Unit = {

        val a = List(1, 1, 2, 3, 4);

        // 集合基本操作 : 头 尾 判空 最小 最大
        println(a.head)
        println(a.tail)
        println(a.isEmpty)
        println(a.min)
        println(a.max)

        /**
         * list
         */

        // 连接
        val a1 = List(5, 6, 7);
        val a2 = a ::: a1; // a 在前边
        val a3 = a.:::(a1); // a1 在前边，为啥呢
        val a4 = List.concat(a, a1); // a 在前边
        println(a2);
        println(a3);
        println(a4);

        // fill
        val a5 = List.fill(5)(2); // 5 个 2
        println(a5);

        // tabulate
        val a6 = {
            List.tabulate(6)(i => i * i * i);
        };
        println(a6);

        val a7 = {
            List.tabulate(4, 5)(_ * _);
        };
        println(a7);

        // reverse
        println(a6.reverse);
        println(a7.reverse);

        /**
         * set
         */
        val b = Set(1, 2, 2, 3, 4);
        println(b) // only 一个 2

        println(b.contains(3)); // true
        println(b.exists(_ % 3 == 1)); // true
        println(b.remove(4)); // true, remove 4
        println(b.drop(2)); // remove 2 elements, and return set

        // add & remove
        b.add(4);
        b.remove(1);
        b += 5;
        b -= 2;
        println(b);

        // other collection to set
        println(a.toSet);

        // 连接
        val b1 = Set(5, 6, 7);
        val b2 = b ++ b1;
        val b3 = b.++(b1);
        println(b2);
        println(b3); // b2 b3 输出一样，because Set 是无序的

        // 交集
        val b4 = Set(1, 2, 3);
        val b5 = Set(2, 3, 4);
        println(b4.&(b5))
        println(b4.intersect(b5))

        // 并集
        println(b4.|(b5));

        /**
         * map
         */
        val c: Map[Int, String] = Map(1 -> "one", 2 -> "two");
        for (elem <- c.keys) {
            println(c.get(elem));
        }
        for (elem <- c.values) {
            println(elem);
        }

        // 使用 ++ 运算符 或 Map.++() 方法来连接两个 Map，Map 合并时会移除重复的 key
        val c1: Map[Int, String] = Map(1 -> "one_one", 3 -> "three");
        println((c ++ c1).get(1)); // 后边覆盖前边的

        /**
         * tuple
         */
        val d = ("hello", 1, "scala");
        println(d._1 + "" + d._3);
        println(d.toString());

        for (elem <- d.productIterator) {
            print(elem + "\t");
        }
        println()

        /**
         * option
         */
        val e : Option[Int] = Some(5); // 估计 e 是个 Int, 但不保证
        val e1 : Option[Int] = None;

        /**
         * iterator
         */
        val f = Iterator("MI", "Baidu", "Alibaba");
        while (f.hasNext) {
            println(f.next());
        }
    }
}
