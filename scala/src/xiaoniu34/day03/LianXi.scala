package xiaoniu34.day03

object LianXi {
    def main(args: Array[String]): Unit = {
        val lst = List("Id1-The Spark", "Id2-The Hadoop", "Id3-The Spark")
        //对以上进操作  使之形成 Spark-Id1 Id3    Hadoop-Id2 The-Id1 Id2 Id3
        val list = lst.flatMap(x => {
            //首次切割
            val sp = x.split("-")
            val oldKey = sp(0)
            val oldValue = sp(1)
            //二次切割  把The Spark切割
            val newKeys = oldValue.split(" ")
            //把切割后的进行切分  (Spark,Id1)(The,Id1)(Hadoop,Id2)(Spark,Id3)(The,Id2)
            val tuples = newKeys.map(x => {
                (x, oldKey)
            })
            tuples
    })
        //把上步操作进行分组
        val gr = list.groupBy(_._1)
        //(Hadoop,List((Hadoop,Id2)))
        //(Spark,List((Spark,Id1), (Spark,Id3)))
        //(The,List((The,Id1), (The,Id2), (The,Id3)))
        //截取value中的Id2 Id1 Id3
        val map = gr.mapValues(x => {
            x.map(_._2)
        })
        //(Hadoop,List(Id2))
        //(Spark,List(Id1, Id3))
        //(The,List(Id1, Id2, Id3))
       //把后边value(list)进行拼接
        val unit = map.mapValues(x => {
           x.mkString(" ")
        })
       //(Hadoop,Id2)
        //(Spark,Id1 Id3)
        //(The,Id1 Id2 Id3)
       unit.map(x => {
            x._1 ++ "-" ++ x._2
        }).foreach(println)
        //Hadoop-Id2
        //Spark-Id1 Id3
        //The-Id1 Id2 Id3
    }
}
