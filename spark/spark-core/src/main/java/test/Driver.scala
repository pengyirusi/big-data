package test

import java.io.{ObjectOutputStream, OutputStream}
import java.net.Socket

/**
 * Create by weiyupeng on 2021/8/7 10:52
 */
object Driver {
    def main(args: Array[String]): Unit = {
        // 连接服务器
        val client = new Socket("localhost", 9999)

        val out: OutputStream = client.getOutputStream
        val objOut = new ObjectOutputStream(out)

        val task = new Task()

        objOut.writeObject(task)
        objOut.flush()
        objOut.close()
        client.close()

        println("客户端数据发送完毕！")
    }
}
