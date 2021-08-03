package notesWithCode

/**
 * Create by weiyupeng on 2021/8/3 21:41
 */
object N17_PatternMatch {

    // match 相当于 java 里的 switch，但是不用自己写 break
    private def matchTest(x: Int): String = x match {
        case 1 => "one";
        case 2 => "two";
        case _ => "many";
    }

    // Any 十分灵活的 match
    private def matchTest1(x: Any): Any = x match {
        case 1 => "one";
        case "two" => 2;
        case y: Int => "scala.Int";
        case _ => "others";
    }

    def main(args: Array[String]): Unit = {
        println(matchTest(3))

        println(matchTest1("two"));
        println(matchTest1("test"));
        println(matchTest1(1));
        println(matchTest1(6));

        // 样例类
        case class Person(name: String, age: Int)

        val p1 = new Person("p1", 28);
        val p2 = Person("p2", 18); // 可以 省略 new
        val p3 = Person("p3", 25);

        for (p <- List(p1, p2, p3)) {
            p match {
                case Person("p1", 28) => println("hello p1 !");
                case Person("p2", 18) => println("hello p2 !");
                case Person(name, age) => println("name: " + name + ", age: " + age);
            }
        }
    }

}
