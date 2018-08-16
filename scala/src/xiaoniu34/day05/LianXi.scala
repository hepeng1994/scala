package xiaoniu34.day05

import scala.io.Source

object LianXi {
    def main(args: Array[String]): Unit = {
        val strings = Source.fromFile("E:\\x\\scala\\30期scala--赵老师\\day-04\\作业\\impclick.txt").getLines()

        //数据切分  1010,华语剧场|剧情|当代|类型,1,0       目标1010 华语剧场 1 0
        //1010 剧情	3	2
        val tuples = strings.map(strings => {
            val sp = strings.split(",")
            val bianhao = sp(0)
            val str = sp(1)
            val zhanShiLiang = sp(2).toInt
            val dianJiLiang = sp(3).toInt
            val strin = str.split("\\|")
            val leixin = strin(0)
            (bianhao, leixin, zhanShiLiang, dianJiLiang)
        })
       // 分组
       // val map = tuples.map({case(k,v1,v2,v3)=>((k,v1),v2,v3)}).toList.groupBy(_._1)
        val map = tuples.toList.groupBy(x=>(x._1,x._2))
        val tuple = map.mapValues(x => {
            val sum3 = x.map(_._3).sum
            //val sum3 = x.map({case (_,_,imp,_)=>imp}).sum
            val sum4 = x.map(_._4).sum
            (sum3, sum4)
        })
       val tuples2 = tuple.map({case ((k1,k2),(v1,v2))=>(k1,k2,v1,v2)})
        println("------------------------------------------------")
        tuples2.toList.sortBy(x=>(x._1,-x._4)).foreach(println)
    }
}
