package test2

import java.io.{InputStream, ObjectInputStream}
import java.net.{ServerSocket, Socket}

/**
 * Create by weiyupeng on 2021/8/7 10:52
 */
object Executor2 {
    def main(args: Array[String]): Unit = {
        // 启动服务器，接收数据
        val server = new ServerSocket(8888)
        println("服务器启动，等待接收数据")

        // 等待 client 的连接
        val client: Socket = server.accept()
        val in: InputStream = client.getInputStream
        val objIn = new ObjectInputStream(in)
        val task: SubTask = objIn.readObject().asInstanceOf[SubTask]
        val ints: List[Int] = task.compute()

        println("节点[8888]计算结果为：" + ints)

        objIn.close()
        in.close()
        client.close()
        server.close()
    }
}
