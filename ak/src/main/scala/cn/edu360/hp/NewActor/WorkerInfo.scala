package cn.edu360.hp.NewActor

/**
  * Created by Huge
  * Desc:  定义一个workerInfo的类  用于封装worker信息
  */

class WorkerInfo (workerId:String,cores:Int,memory:Int){

  // 保存worker的最后一次心跳时间
  var lastHeartBeatTime:Long = _
}
