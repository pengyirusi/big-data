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

        // 集合基本操作 : 头 尾 判空
        println(a.head)
        println(a.tail)
        println(a.isEmpty)

        /**
         * list
         */

        // 连接
        val a1 = List(5, 6, 7);
        val a2 = a ::: a1;
        val a3 = a.:::(a1);
        val a4 = List.concat(a, a1);
        println(a2); // a 在前边
        println(a3); // a1 在前边，为啥呢
        println(a4); // a 在前边

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

        val c = Map(1 -> "one", 2 -> "two");
        val d = ("hello", 1, "scala");
        val e : Option[Int] = Some(5);
    }
}
