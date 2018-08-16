package xiaoniu34.day02.YuXi

object Method {
    val f =(x:Int) => x * 10
    def main(args: Array[String]): Unit = {
        //9.	集合上的常用方法
        /**map
          * 1, 映射  对集合中的每一个元素执行某一项操作
          * 2，返回值 正常情况下，如果原来的集合是List，map操作之后的集合还是List
          * 3，返回的集合的元素类型， 取决于 我们自定义的函数的返回值类型
          */
        val lst:List[Int] = List(2,3,4,5)
        val lst2: List[Int] = lst.map((t:Int) => t*10 )
        val lst3: List[Int] = lst.map(f)
        // 可以省略类型
        val lst4: List[Int] = lst.map(t=>t * 10)
        val lst5: List[Boolean] = lst.map(a=> a%2==0)
        lst2.foreach(println)
        println("-----------------------")
        lst3.foreach(println)
        println("-----------------------")
        lst4.foreach(println)
        println("-----------------------")
        lst5.foreach(println)
        println("-----------------------")
        //9.2.	filter
        val lst1:List[Int] = List(2,3,4,5)
        // 过滤出满足条件的所有内容  返回值类型和 原有的集合类型一致
        val filter: List[Int] = lst1.filter(a => a%2 == 0)
        // 过滤出不满足条件的元素
        val filter2 = lst1.filterNot(a => a%2 != 0)
        println(filter)
        println(filter2)
        //9.3.	count 和 find:
        //统计出满足条件的元素的个数
        val lst7: List[Int] = List(2, 3, 4, 5)

        // 求满足条件的元素的个数
        val count: Int = lst7.count(_ < 3)
        println(count)
        //    filter 过滤出所有满足条件的元素
        //    find  返回满足条件的第一个元素
        val find: Option[Int] = lst7.find(_ > 3)
        println(find) // Some(4)
        val find1: Option[Int] = lst7.find(_ > 10)
        println(find1) // None
        //9.4.	flatten：
       // 压平
        val lst6 = List(Array(1,2,4),Array(2,3,5))
        val list9 = List(Array(1, 2, 4), Array(2, 3, 5))
        lst6.flatten
        //9.5.	flatMap：
       // map  +  flatten
        val arr = Array("hello spark","hello world")
        val splitedArr = arr.map((str:String) => str.split(" "))
        splitedArr.flatten
        val result = arr.flatMap(str =>str.split(" "))
        //9.7.	groupBy
        println("------------------------------------")
        val arr1 = Array(("word",1),("hello",1),("word",2))
        val map = arr1.groupBy(tp => tp._1)
        map.foreach(x=>(println(x._1),x._2.foreach(println)))
        //sorted: 默认是升序
        //sortBy: 参数是一个函数，指定排序的规则  默认是升序
        //如果想要降序 t =>  - t._2   或者  reverse
        //sortWith: 接收两个参数，两个参数进行比较
        println("----------------------排序----------------------")
        val lst10:List[Int] = List(2,3,4,5,1,99,88,66)
        val sorted = lst10.sorted
        sorted.foreach(println)
        val lst11 = List[String]("hello world hello tom tom jim", "hello spark scala")

        // 单词计数的思路

        // 1,数据切分  分隔符是空格

        val words: List[String] = lst11.flatMap(_.split(" "))
        // 2 ,组装 单词和1
        val wordsAndOne: List[(String, Int)] = words.map((_, 1))

        // 3, 分组  按照单词来分组
        val grouped: Map[String, List[(String, Int)]] = wordsAndOne.groupBy(_._1)

        val result1: Map[String, Int]  = grouped.map(tp => {
            val lst: List[(String, Int)] = tp._2
            val intLst :List[Int] = lst.map(_._2)
            (tp._1,intLst.sum)
        })

        // 排序  单词出现的次数的降序  sortBy  Map 上没有sortBy方法   集合转换  Array  List   默认是升序
        val finalRes:List[(String,Int)] = result1.toList.sortBy(- _._2)
        // 利用reverse 也可以实现降序
        result1.toList.sortBy(tp => tp._2).reverse


        val foreach: Unit = finalRes.foreach(println)


        finalRes.foreach((tp:(String,Int)) =>println(tp))

        // 类型可以省略
        finalRes.foreach(tp =>println(tp))

        // 这里的_ 表示 每一条元素
        // 如果函数的参数只被使用一次，就可以用_替代
        finalRes.foreach(println(_))

        // 还可以省略下划线
        finalRes.foreach(println)
        println("-----------------交集 并集 差集 去重---------------------------")
        //9.10.	交集 并集 差集 去重
        val arr3 = Array(1,3,4)
        val arr2 =Array(1,2,5)
        //并集
        val array = (arr3 union arr2).distinct
        array.foreach(println)
        println("-------------------------------------------------------------")
        //交集
        val array2 = arr3 intersect arr2
        array2.foreach(println)
        println("-------------------------------------------------------------")
        //差集   a diff b  a是主要的,差集以A为主
        val array3 = arr3 diff arr2
        array3.foreach(println)
       // 9.11.	grouped(n:Int):按照指定的元素个数来分组：
       val arr4 = Array(1,3,4,5,6,7,8,98,90)
        val iterator = arr4.grouped(2)
        iterator.foreach(arr => println(arr.toList))
        //字符串拼接()里是用什么方式来拼接
        println("--------------------------")
        arr4.mkString(",").foreach(print)
        println()
        //reduce  元素归并  参数是一个函数，这个函数有两个参数   累加值  元素值   调用的就是reduceLeft
        println("----------------")
        println(arr4.reduce(_+_))   //此处相当于累加
        //reduce 元素归并  ()内为归并的规则
        //reduceLeft reduceRight
        //reduce底层调用的就是reduceLeft,只不过，reduce要求函数的输入类型和返回值类型必须一致，而reduceLeft,可以不一致。
        //val arr = Array(1,3,5,7,9)
        //
        //reduce(_ - _)        reduce((a,b)=>a-b)
        //
        //false   --->  true
        //		a：累加值     b:元素值
        //1		1
        //3		1			3      1-3    -2
        //5		-2			5	-2 - 5   -7
        //7		-7			7     -7-7       -14
        //9		-14 			9     -14 - 9   -23
        //
        //(((((1)-3)-5)-7)-9)      -23
        //reduce  reduceLeft
        //
        //(1-(3-(5-(7-(9)))))        5
        //reduce   -  reduceLeft
        //区别： reduce的2个输入参数类型，和输出值类型必须一致，
        //reduceLeft，可以支持两个不同类型的输入参数。
        println("-------fold折叠-----------------------")
        //fold(0)(_ + _)
        //fold有两个参数，第一个参数是默认值，第二个参数是一个函数，该函数有两个参数 累加
        //值  元素值   调用的就是foldLeft
        //fold 要求函数的2个输入参数类型必须一致，foldLeft 可以允许函数的2个输入参数类型
        //不一致
        //fold  foldLeft  foldRight  相比于 reduce  reduceLeft  reduceRight ，多了一个初始值参数运算。其他的都一样，+ ++ 取决于 元素的类型。
        val arrayk = Array(1,2,3,4)
        println(arrayk.fold(2)(_-_))//((((2)-1)-3)-4)  fold 种类型必须一致
        println(arrayk.foldLeft(2)(_ - _))//((((2)-1)-3)-4)  与foldyiyang  类型可以不一致
        println(arrayk.foldRight(2)(_ -_))//(1-(2-(3-(4-2))))
        println("--------------aggravate聚合--------------")
        val arrayx = Array(List(2),List(3,5),List(3,5,7))
        println(arrayx.par.aggregate(0)((a,b)=>math.max(a,b.max),_ + _))
        println(arrayx.aggregate(0)((a,b)=>math.max(a,b.max),_ + _))//_+_没有用到   只有并行化集合的时候才会用到,加上.par

        println("---------------------take取集合中的前几个元素------------------")
       //take 返回的是集合
        println(arrayk.take(1).toBuffer)
        //截取元素,提取元素表的from到until为止的元素[ )左臂右开
        println(arrayk.slice(0 ,2).toBuffer)

    }
}
