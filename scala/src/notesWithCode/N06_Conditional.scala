package notesWithCode

/**
 * Create by weiyupeng on 2021/8/1 16:20
 */
object N06_Conditional {
    def main(args: Array[String]): Unit = {
        var x = 10;
        if (x < 20) {
            println("x < 20");
            if (x < 15) {
                println("x < 15");
            }
        } else if (x == 20){
            println("x == 20");
        } else {
            println("x > 20");
        }
    }
}
