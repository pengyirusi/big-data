package notesWithCode

import java.io.{FileNotFoundException, FileReader, IOException}

/**
 * Create by weiyupeng on 2021/8/3 22:12
 */
object N19_Exception {

    def main(args: Array[String]): Unit = {
        try {
            val file = new FileReader("a.txt");
        } catch {
            /**
             * 使用 case, 狠 !
             */
            case e : FileNotFoundException => {
                println("[ERROR] " + e);
            }
            case e : IOException => {
                println("[ERROR] " + e);
            }
        } finally {
            println("[INFO] " + "exec finally")
            println("[INFO] " + "exit")
        }
    }
}
