package notesWithCode

/**
 * Create by weiyupeng on 2021/8/3 20:24
 */
class N15_Singolen(val xc: Int, val yc: Int) {
    var x: Int = xc;
    var y: Int = yc;
    def move(dx: Int, dy: Int): Unit = {
        x += dx;
        y += dy;
    }
}

// object 即为单例模式 ?
object Test {
    def main(args: Array[String]): Unit = {
        val n15_Singolen = new N15_Singolen(1, 2);

        def printPoint: Unit = {
            println("x: " + n15_Singolen.x + ", y: " + n15_Singolen.y);
        }

        printPoint;
    }


}