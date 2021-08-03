package notesWithCode

/**
 * Create by weiyupeng on 2021/8/2 21:54
 */
class N14_CompanionObject {

}

// 私有构造方法
class Marker private(val color : String) {

    println("create " + this);

    override def toString: String = "color : " + color;
}

// 伴生对象，与类名字相同，可以访问类的私有属性和方法
object Marker {

    // 构造函数 ?
    private val markers : Map[String, Marker] = Map(
        "red" -> new Marker("red"),
        "blue" -> new Marker("blue"),
        "green" -> new Marker("green"),
    )

    def apply(color : String) = {
        if (markers.contains(color)) markers(color) else null;
    }

    def getMarker(color : String) = {
        if (markers.contains(color)) markers(color) else null;
    }

    def main(args: Array[String]): Unit = {
        println(Marker("red"));
        println(Marker getMarker "blue");
    }
}