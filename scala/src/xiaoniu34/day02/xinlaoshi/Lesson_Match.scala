package xiaoniu34.day02.xinlaoshi

object Lesson_Match {
    def main(args: Array[String]): Unit = {
        var tuple=(1,25,"niaho",1.0,'c')
        val ite = tuple.productIterator
        while(ite.hasNext){
            MatchTest(ite.next())
        }
    }
    //匹配的是值是否相等  1.0与1一样
    def MatchTest(x:Any)=
        x match {
            case 1=>println("value is 1")
            case 25=>println("value is 25")
            case x:Double=>println("value is 小数")
            case x:String=>println("value is 字符串")
            case _=>println("no match")

        }

}
