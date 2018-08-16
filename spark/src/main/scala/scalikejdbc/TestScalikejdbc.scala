package scalikejdbc

import scalikejdbc.config.DBs

/**
  * Author: sheep.Old  
  * WeChat: JiaWei-YANG
  * QQ: 64341393 
  * Created 2018/6/20
  */

case class Users(province: String, cnts: String)

object TestScalikejdbc {

    DBs.setup() // application.conf ->application.json -> application.properties

    // 插入
    def testInsert() = {
        DB.autoCommit { implicit session => SQL("insert into access_log(province,cnts) values(?,?)").bind("海南", "998").update().apply() }
    }

    // 删除
    def testDelete = {
        DB.autoCommit { implicit session =>
            SQL("delete from access_log where province=?").bind("海南").update().apply()
        }
    }

    def testUpdate = {
        DB.autoCommit { implicit session =>
            SQL("update access_log set cnts=28 where province='云南'").update().apply()
        }
    }

    def testSelect = {
        DB.readOnly{ implicit session =>
            SQL("select * from access_log").map(rs => {
                Users(
                    rs.string("province"),
                    rs.string("cnts")

                )
            }).list().apply()
        }
    }

    def testTransaction = {
        DB.localTx{ implicit session =>
            SQL("insert into access_log(province, cnts) values(?,?)").bind("云南", "666").update().apply()

           val i = 1/0

            SQL("insert into access_log(province, cnts) values(?,?)").bind("台湾", "777").update().apply()
        }
    }


    def main(args: Array[String]): Unit = {

//        testInsert()
//        testDelete
//        testUpdate

        val userList = testSelect
        userList.foreach(println)

//        testTransaction
    }

}
