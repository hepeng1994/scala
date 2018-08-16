package xiaoniu34.day01.xin

import java.text.SimpleDateFormat
import java.util.Date

/**
  * 1.函数的参数不能省略类型
  * 2.如果有return，必须显示声明返回的类型
  * 3.如果没有=，丢弃返回值。如果有函数有返回值，一定要写=
  * 4.如果逻辑很简单，可以一行搞定，{}可以省略
  */
object Lesson_Fun {
    def main(args: Array[String]): Unit = {
    //        def max (a:Int,b:Int)=if (a>b) a else b
    //        println(max(5,99))
    /**
      * 递归函数
      * 递归函数要显示声明返回值类型
      */
    //     def fun (n:Int):Int={
    //         if(n==1)
    //             1
    //         else
    //             n*fun(n-1)
    //     }
    //        println(fun(5))
    /**
      * 参数有默认值
      */
    //    def max (a:Int,b:Int=10,c:Int)={
    //        a
    //    }
    //        val i = max(a=2,c=10)
    //        println(i)
    /**
      * 可变长参数列表
      */
//        def sum(i:Int*)={
//            var a=0
//            for (j<-i){
//                a+=j
//            }
//            a
//        }
//        println(sum(1,2,3,4,5))
        /**
          * 匿名函数
          */
//        val f=(a:Int,b:Int)=>a+b
//        var v=f(1,2)
//        println(v)
        /**嵌套函数
          */
//        def fun1(a:Int)={
//         def fun2(b:Int)={
//             b+"********"+a
//         }
//        fun2(9)
//         }
//        println(fun1(5))
        /**
          * 偏应用函数
          */
//        def showLog(date:Date,s:String)={
//            println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date)+",log is "+s)
//        }
//        val date=new Date()
//        val f= showLog(date,_:String)
//
//        f("aaa")
//        f("bbb")
//        f("ccc")
        /**
          * 高阶函数
          *
          */
        //1\函数的参数是函数
//        def fun(f:(Int,Int)=>Int,a:Int,b:Int)={
//            f(a,b)
//        }
//        def fun1(a:Int,b:Int):Int={
//            a+b
//        }
//        println(fun(fun1,2,4))
        //2.函数的返回类型是函数
//        def fun(a:Int,b:Int):(String,String)=>String={
//            def fun1(s1:String,s2:String):String={
//                s1+"*"+s2+"%"+b+"&"+a
//            }
//            fun1
//        }
//        val str = fun(1,2)("hello","word")
//        println(str)
        //3.函数的参数和返回值都是函数
        def fun(f:(Int,Int)=>Int,s:String):(String,String)=>String={
            val v=f(1,2)
            def fun1(s1:String,s2:String):String={
                s1+"*"+s2+"!"+s+"&"+v
            }
            fun1
        }
        println(fun((a:Int,b:Int)=>{a*b},"hi")("aa","bb"))//aa*bb!hi&2
        //有多个参数列表，或者说多个小括号括起来的函数参数列表的函数就是柯里化函数。
       def fun3(f:(Int,Int)=>String,s:Int)(int: Int)={
               s+"*"+int+"!"
       }
    }
}
