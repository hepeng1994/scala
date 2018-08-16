package xiaoniu34.day02.xinlaoshi
//样例类自动重写equals() , hashCode()  ,,copy ,toString
case class Person(xname:String,xage:Int)
object Lesson_CaseClass {
    def main(args: Array[String]): Unit = {
        //可new 可不new  因为重写了以上  所以 person 与person1相等
        val person = new Person("hepeng",5)
        val person1 = Person("hepeng",5)
        val person2 = Person("张斌",25)
        val person3 = Person("郑贵强",10)
        println(person.xname)
        println(person1.xname)
        println(person==person1)
        val persons = List(person,person1,person2,person3)
        persons.foreach{x=>{
            x match{
                case Person("hepeng",5)=>println("自己")
                case Person("张斌",25)=>println("大笔个")
                case p:Person=>println("person")
                case _ =>println("no match")
            }
        }

        }
    }
}
