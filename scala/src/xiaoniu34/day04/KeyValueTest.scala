package xiaoniu34.day04

/**
  * Created by Huge
  * Desc: 
  */

object KeyValueTest {


  def main(args: Array[String]): Unit = {

    // key - values   key-
    val lst = List("Id1-The Spark", "Id2-The Hadoop", "Id3-The Spark")

    // 1,map 每一条数据 都需要进行两次分割
    //    val map1: List[Array[(String, String)]] = lst.map(t => {
    val data: List[(String, String)] = lst.flatMap(t => {
      val split: Array[String] = t.split("-") // 数组长度为2
      val oldKey = split(0)
      val oldValues: String = split(1)
      // 对values 进行切分
      val newKeys: Array[String] = oldValues.split(" ") // 集合

      // 2 (oldValue,oldKey)
      // 最重要的一步  把新的key和oldkey（newValues）组装起来
      val map: Array[(String, String)] = newKeys.map(newKey => {
        (newKey, oldKey)
      })
      map
    })
    //    val flatten: List[(String, String)] = map1.flatten


    // groupBy  把所有的value进行聚合
    val grouped: Map[String, List[(String, String)]] = data.groupBy(_._1)

    grouped.foreach(println)


    //    val tp2 = List(("","a","","12"),("","b","","22"),("","c","","33"),("","d","","44"))
    //    tp2.map(t=>(t._2,t._4))
    val values: Map[String, List[String]] = grouped.mapValues(lst => {
      val lst2: List[String] = lst.map(_._2)
      lst2
    })
    // 把value中的list  转换成 字符串
    val values1: Map[String, String] = values.mapValues(lst => {
      // 按照指定的空格拼接字符串
      lst.mkString(" ")
    })
//   println(values1.mkString("-"))
    values1.map(t=>{
      t._1 + "-" + t._2
//      t._1.concat("-").concat(t._2)
//      t._1 ++ "-" ++ t._2
    }).foreach(println)



  }
}
