package notesWithCode

import java.io.{File, PrintWriter}
import scala.io.{Source, StdIn}

/**
 * Create by weiyupeng on 2021/8/3 22:44
 */
object N21_FileIO {
    def main(args: Array[String]): Unit = {
        // IO 直接用的 java.io._
        // 写入文件
        val writer = new PrintWriter(new File("Z:\\IDEA Project\\big-data\\scala\\src\\notesWithCode\\a.txt"));
        writer.write("weiyupeng\n");
        writer.close();

        // 控制台输入
        val a: String = StdIn.readLine();
        println(a + a);

        // 读文件
        Source.fromFile("Z:\\IDEA Project\\big-data\\scala\\src\\notesWithCode\\a.txt").foreach{
            println;
        }
    }
}
