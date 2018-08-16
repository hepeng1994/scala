package xiaoniu34.day02.YuXi

object WordCount {
    def main(args: Array[String]): Unit = {
        val lst = List[String]("hello world hello tom tom jim", "hello spark scala")
        //数据切分
//        val stringses = lst.map(_.split(" "))
//        val flatten = stringses.flatten
        val strings = lst.flatMap(_.split(" "))
        //单词组装  单词1
        //  val words: List[String] = lst.flatMap(str => str.split(" "))
        val tuples = strings.map((_,1))
        //按照单词来分组
        // 生成Map   map的key  就是自定义的分组的条件  value 是 原有的数据类型
        val map = tuples.groupBy(x =>x._1)
        //聚合
        val unit = map.map(x => {
            val value = x._2
            val ints = value.map(_._2)
            (x._1, ints.sum)
        })
        // 排序  单词出现的次数的降序  sortBy  Map 上没有sortBy方法   集合转换  Array  List   默认是升序
        val list = unit.toList.sortBy(-_._2)
        // 利用reverse 也可以实现降序
       // result.toList.sortBy(tp => tp._2).reverse
        list.foreach(println)

        val lst1 = List[String]("hello world hello tom tom jim", "hello spark scala")
        println("--------------------------------")
        val map1 = lst1.flatMap(_.split(" ")).map((_,1)).groupBy(_._1).map(x=>(x._1,((x._2).map(_._2)).sum))
        map1.foreach(println)


    }
}
