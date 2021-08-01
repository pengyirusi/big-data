package notesWithCode

/**
 * Create by weiyupeng on 2021/8/1 14:09
 */
object N01_Basis {
    def main(args: Array[String]): Unit = { // main函数，程序入口
        val i: Int = 1 + 1
        println("hello scala " + i + " !")
    }

// 语法特点：区分大小写，类名大驼峰，方法小驼峰，扩展名为`.scala`

// package 也可以这样写：
// package a {
//     object {
//         // ...
//     }
// }

// import 引用包
// import java.awt.Color : 引入 Color
// import java.awt._ : 引入包内所有成员
// import java.awt.{Color, Font} : 引入多个成员
// import java.util.{HashMap => JavaHashMap} : 相当于 python 里的 as

}
