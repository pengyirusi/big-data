package notesWithCode

/**
 * Create by weiyupeng on 2021/8/3 22:21
 */
object N20_Extractor2 {

    def apply(x: Int) = x * 2
    def unapply(z: Int): Option[Int] = if (z%2 == 0) Some(z/2) else None

    def main(args: Array[String]): Unit = {
        val x = N20_Extractor2(5);
        println(x);

        x match {
            case N20_Extractor2(num) => println(x + " = 2 * " + num);
            case _ => println("can't calculate ! ");
        }

        println(unapply(5)); //None
        println(unapply(10));
    }

}
