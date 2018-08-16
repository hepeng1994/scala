package day05.work

import utils.MySpark

object PvDay {
    def main(args: Array[String]): Unit = {
        val sc = MySpark(this.getClass.getSimpleName)
        val pv = sc.textFile("E:\\x\\spark资料\\spark-05\\spark-05\\作业题\\pvuv.txt")
        pv

    }
}
