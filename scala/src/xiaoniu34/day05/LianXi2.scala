package xiaoniu34.day05

import java.text.SimpleDateFormat

object LianXi2 {
    //元组作为参数
    def shijian2(b:(Int,String,Int,String))={
        val format = new SimpleDateFormat("yyyy-MM-dd")
        val value = format.parse(b._2)
        val value2 = format.parse(b._4)
        val Long = value.getTime - value2.getTime
    }
    def shiJian(b:String,d:String)={
        val format = new SimpleDateFormat("yyyy-MM-dd")
        val value = format.parse(b)
        val value2 = format.parse(d)
        val Long = value.getTime - value2.getTime
        Long
    }
    def main(args: Array[String]): Unit = {
    val tuples = Array((1, "2018-04-06", 5646, "2017-04-26"), (2, "2018-04-15", 46, "2017-04-26"),
        (3, "2018-04-21", 564, "2017-03-26"), (4, "2018-05-06", 566, "2017-06-26"))
        val units = tuples.map(x => {
            val Long = shiJian(x._2,x._4)
            val sum = tuples.map(_._3).sum
            (Long/1000/60/60/24,sum)
        })
            units.foreach(println)
    }
}
