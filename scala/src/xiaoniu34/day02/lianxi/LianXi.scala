package xiaoniu34.day02.lianxi

object LianXi {
    def getValues(value:Array[Int],v:Int)={
        val i = value.count(_ >v)
        val j = value.count(_ < v)
        val k = value.count(_== v)
        (i,k,j)
    }
    def jiaoHuan(ints: Array[Int])={
        for (i <- 0 until(ints.length-1,2)){
            var a =0
            a=ints(i)
            ints(i)=ints(i+1)
            ints(i+1)=a

        }
        ints
    }
    def maxMin(array: Array[Double])={
        (array.max,array.min)
    }
    def main(args: Array[String]): Unit = {
        println("--------------计算平均数值--------------------")
        val doubles = Array(1.2,56.3,3.3)
        //保留两位小数formatted("%.2f")
        val d = (doubles.sum/doubles.length).formatted("%.2f")
        println(d)
        println("--------------------最大值最小值-----------")
        println(maxMin(doubles))
        val ints = Array(1,2,5,9,8,6,55,44,22,11,5,88,662,200,48)
        println(getValues(ints,15))
        //每两个数前后交换
        jiaoHuan(ints).foreach(println)

        //还可用迭代器进行交换
        println("-------------------------------------------------")
        ints.grouped(2).flatMap(_.reverse).foreach(println)
        //练习:生成一个映射集合是名字对应一个年龄,从中找出最大最小的,再把名字年龄打印出来
        println("-------------映射练习---------------------")
        val array = Array(("张三",25),("李四",26),("王五",36),("赵六",19),("小柒",56))
        println(array.maxBy(_._2))
        val map = array.toMap

        println(map.maxBy(_._2))
        println(map.minBy(_._2))
        // 练习 求平均值
        	 val d1 = Array(("bj",28.1), ("sh",28.7), ("gz",32.0), ("sz", 33.1))
        	val d2 = Array(("bj",27.3), ("sh",30.1), ("gz",33.3))
        	val d3 = Array(("bj",28.2), ("sh",29.1), ("gz",32.0), ("sz", 30.5))
       // 把数据聚合起来//并集
        val tuples = d1 union d2 union d3
        //d1 ++ d2 ++ d3
        //按照城市分组
        val st = tuples.groupBy(_._1)
        //计算每组平均值
//        val unit=for (i <- st){
//            val value = i._2
//            var k=0d
//            for (j <- value){
//                k+= j._2
//            }
    //        ( i._1,k  )
//        }
//        println(unit)
        val mapm = st.map(x => {
            val sum = x._2.map(_._2).sum
            val length = x._2.length
            (x._1, (sum / length).formatted("%.2f"))
        })
        //mapvalues
       val values = st.mapValues(x=>{
            val sum = x.map(_._2).sum
           val length = x.length
           val d = sum/length
           d
           })
        //使用reduce求平均值
       st.map(x=>{
           val value = x._2.map(_._2).reduce(_+_)
           (x._1,value)
       })
        //使用fold求平均值
        val map2 = st.map(x => {
            //println(arrayk.foldLef0t(2)(_ - _))//((((2)-1)-3)-4)  与foldyiyang  类型可以不一致
            val d = x._2.map(_._2).foldLeft(0d)(_ + _)
            (x._1, d)
        })
        //使用par.aggregate
        val map3 = st.map(x => {
            //println(arrayk.foldLef0t(2)(_ - _))//((((2)-1)-3)-4)  与foldyiyang  类型可以不一致
            val d = x._2.aggregate(0d)(_+_._2,_ + _)
            val length = x._2.length
            val avg =d/length
            (x._1, avg)
        })
        map3.foreach(println)
    }
}
