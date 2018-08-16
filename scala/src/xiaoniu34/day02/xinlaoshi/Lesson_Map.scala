package xiaoniu34.day02.xinlaoshi

object Lesson_Map {
    def main(args: Array[String]): Unit = {
        var map =Map(
            1->"hello",
            2->"hi",
            (3,"word"),
            (3,"aaa")
        )
        println(map.get(100).getOrElse("no value"))
        //遍历
        //获取所有的key

        val keys=map.keys
        keys.foreach { key => {
            println(key+"-->"+map.get(key).get)
        } }
        //获取所有的value
        val values=map.values
        values.foreach { x => {
            println(x)
        }}
        val map1=Map((1,"hello"),(2,"hi"),(3,"tom"),(4,"john"))
        val map2=Map((1,"cccc"),(2,"ddd"),(5,"fff"),(6,"ggg"))

        // val result=map1.++:(map2)//map1加到map2
        val result=map1.++(map2)//map2加到map1
        result.foreach(println)
        // map1.filter(p=>{p._2.equals("tom")}).foreach(println)
        println("-------------------------------------------------")
        map1.filter(_._2.equals("tom")).foreach(println)
        println(map1.count(_._2.equals("hello")))



    }
}
