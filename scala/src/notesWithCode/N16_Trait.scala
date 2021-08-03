package notesWithCode

/**
 * Create by weiyupeng on 2021/8/3 20:33
 */
object N16_Trait {
    def main(args: Array[String]): Unit = {
        val p1 = new Point(2, 3);
        val p2 = new Point(2, 4);
        val p3 = new Point(3, 3);

        println(p1.isEqual(p2));
        println(p1.isEqual(p3));
        println(p3.isNotEqual(p2));
    }
}

// trait 本意: 特征, 相当于 java 中的接口, 所以能被多重继承
trait Equal {
    def isEqual(x: Any): Boolean;
    def isNotEqual(x: Any): Boolean = !isEqual(x);
}

class Point(xc: Int, yc: Int) extends Equal {
    var x: Int = xc;
    var y: Int = yc;

    override def isEqual(obj: Any): Boolean =
        obj.isInstanceOf[Point] && obj.asInstanceOf[Point].x == x;

}

// 特征构造顺序
// 顺序：
// 调用超类的构造器；
// 特征构造器在超类构造器之后、类构造器之前执行；
// 特征由左到右被构造；
// 每个特征当中，父特征先被构造；
// 如果多个特征共有一个父特征，父特征不会被重复构造
// 所有特征被构造完毕，子类被构造。