package tools

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.JedisPool
import utils.ConfigHandler

/**
  * author: sheep.Old 
  * qq: 64341393
  * Created 2018/6/14
  */
object Jpools {
    private lazy val jedisPool = new JedisPool(ConfigHandler.redisHost, ConfigHandler.redisPort)
    private lazy val poolConfig = new GenericObjectPoolConfig()
    poolConfig.setMaxTotal(1000) // 支持最大的连接数
    poolConfig.setMaxIdle(5)// 支持最大的空闲连接
    // ...
    //private lazy val jedisPool = new JedisPool(poolConfig, "192.168.212.100")

    def getJedis = {
        val jedis = jedisPool.getResource
        jedis.select(8)
        jedis
    }




}
