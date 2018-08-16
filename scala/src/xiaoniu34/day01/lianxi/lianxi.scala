package xiaoniu34.day01.lianxi

object lianxi {
    def sum (a:Int,b:Int)=a+b
    def main(args: Array[String]): Unit = {
        val x =10
        val unit = if (x==1) "a" else if(x==10)"b" else if(x==100) "c" else "d"
        println(unit)
        println(sum(5,6))
    }
}
