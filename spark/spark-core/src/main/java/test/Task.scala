package test

/**
 * Create by weiyupeng on 2021/8/7 11:03
 */
class Task extends Serializable {
    val data = List(1, 2, 3, 4)

    // val logic = (num: Int) => {num * 2}
    val logic : Int=>Int = (_: Int) * 2

    def compute() = {
        data.map(logic)
    }
}
