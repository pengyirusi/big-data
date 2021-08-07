package test2

/**
 * Create by weiyupeng on 2021/8/7 11:40
 */
class SubTask extends Serializable {
    var data : List[Int] = _

    var logic : Int=>Int = _

    def compute() = {
        data.map(logic)
    }
}
