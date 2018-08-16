package xiaoniu34.day02.YuXi

import scala.collection.mutable

object MapTest {
    def main(args: Array[String]): Unit = {
        //map创建
        val map = Map[String, Int]("xyz" -> 99, "ldh" -> 102)
        val map1 = new mutable.HashMap[String,Int]()
        val map3 = map ++ map1
        //map的取值
        //val mp1: Int = map("zxy2")
        // 如果可以不存在就报错
        // Option 类 有两个子类 Some(v)  None
        println(map.get("xyz").get)
        // 如果key 不存在 ，则给定默认值
        val orElse: Int = map.getOrElse("zxy2",10000)
        println(orElse)
        println("----------------------可变map-------------------------")
        import scala.collection.mutable.Map
        val map4 = Map[String,Int]()
        val map5 = new mutable.HashMap[String,Int]()
        //添加元素
        map4.put("hf",55)
        map4 += ("nihao" -> 23)
        map4 += (("hp",25),("zb",28))
        map4("zgq")=27
        // 删除元素  根据key
        map4 -= "zs"
        //    mMap -= ("zxy","ldh")
        map4 --= List("xx")
        // 统计长度
        map4.size
        //6.5.	map迭代key和value
        for (i <- map4){
            println(i._1,i._2)
        }
        println("-----------------")
        for ((k, v) <- map4) {
            println(k, v)
        }
        println("--------------------")
        // 只打印key
        for ((k, _) <- map4) {
            println(k)
        }
        println("--------------------")
        // 只打印 value
        for ((_, v) <- map4) {
            println(v)
        }
        // 判空
       println( map4.isEmpty)
        val ks = map4.keysIterator
        while (ks.hasNext){
            ks.next()
        }
        println("-------------------------------")
        map4.keys
        map4.keySet
        val values: Iterable[Int] = map4.values
        values.foreach(println)
        println("---------------------------")
        //同时使用可变和不可变的Map
        //可以导入包，不指定具体的类
        import scala.collection.mutable
        // 不可变的map
        val mp = Map[String,Int]()

        val mp6 = mutable.Map[String,Int]()

        //Map和Array等其他的集合，存储 元组的区别：
        val mp2 = mutable.Map[String,Int]()
        //val arr = Array[(String,Int)]()

        //存储的对偶元组的集合，可以转化成Map
        val arr = Array[(String,Int)]()
        arr.toMap

    }
}
