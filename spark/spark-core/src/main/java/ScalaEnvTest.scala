/**
 * Create by weiyupeng on 2021/7/31 20:42
 */
object ScalaEnvTest {
    def main(args: Array[String]): Unit = {
        println("hello scala !")

        val s: String = "qwe"
        val i: Int = 1
        println(s != i) // 不同类型都能比较 ！

        val list1 = List(1, 2, 3, 4)
        val tail: List[Int] = list1.tail
        println(tail.mkString(","))

        println(list1.zip(tail))
    }
}