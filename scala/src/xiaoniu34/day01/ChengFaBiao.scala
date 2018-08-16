package xiaoniu34.day01

object ChengFaBiao {
	def main(args: Array[String]): Unit = {
		for (i <-1 to 9;j <- 1 to i ) {
			if (i>=j){
				print(i+"*"+j+"="+i*j+"\t")
			}
			if (i==j){
				println
			}

		}
	}
}
