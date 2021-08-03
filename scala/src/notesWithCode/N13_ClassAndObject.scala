package notesWithCode

/**
 * Create by weiyupeng on 2021/8/2 21:20
 */
class N13_ClassAndObject(xc: Int, yc: Int){
    var x = xc;
    var y = yc;

    def move(dx: Int, dy: Int): Unit = {
        x += dx;
        y += dy;
        println("(" + x + ", " + y + ")");
    }
}

// 继承
class Point3( val xc: Int,  val yc: Int,
              val zc: Int) extends N13_ClassAndObject(xc: Int, yc: Int) {
    var z: Int = zc;

    def move(dx: Int, dy: Int, dz: Int): Unit = {
        x += dx;
        y += dy;
        z += dz
        println("(" + x + ", " + y + ", " + z + ")");
    }
}

object N13_ClassAndObject {
    def main(args: Array[String]): Unit = {
        val point = new N13_ClassAndObject(1, 2);
        point.move(2, 1);

        val point3 = new Point3(0, 0, 0);
        point3.move(1, 2, 3);
    }
}
