package test2

import java.io.{ObjectOutputStream, OutputStream}
import java.net.Socket

/**
 * Create by weiyupeng on 2021/8/7 10:52
 */
object Driver {
    def main(args: Array[String]): Unit = {
        // 连接服务器
        val client1 = new Socket("localhost", 9999)
        val client2 = new Socket("localhost", 8888)

        val task = new Task()
        val len = task.data.length

        val out1: OutputStream = client1.getOutputStream
        val objOut1 = new ObjectOutputStream(out1)

        val subTask1 = new SubTask
        subTask1.logic = task.logic
        subTask1.data = task.data.take(len / 2)

        objOut1.writeObject(subTask1)
        objOut1.flush()
        objOut1.close()
        client1.close()

        val out2: OutputStream = client2.getOutputStream
        val objOut2 = new ObjectOutputStream(out2)

        val subTask2 = new SubTask
        subTask2.logic = task.logic
        subTask2.data = task.data.takeRight((len + 1) / 2)

        objOut2.writeObject(subTask2)
        objOut2.flush()
        objOut2.close()
        client2.close()

        println("客户端数据发送完毕！")
    }
}
