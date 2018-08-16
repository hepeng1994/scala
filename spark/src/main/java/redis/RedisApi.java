package redis;


import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * author: sheep.Old
 * qq: 64341393
 * Created 2018/6/14
 */
public class RedisApi {


    static final String HOST = "10.172.50.63";


    @Test
    public void createKV() {
        // 一个连接
        Jedis jedis = new Jedis(HOST, 6379);

//        jedis.set("name", "oldsheep");
        jedis.del("name");
        jedis.incrBy("a", 10);

        jedis.close();

    }


    @Test
    public void createHashes(){

        // 一个连接
        Jedis jedis = new Jedis(HOST, 6379);

//        jedis.hset("qiannvyouhun", "nicaicheng", "20");
//        jedis.hset("qiannvyouhun", "yanchixia", "40");

        jedis.expire("qiannvyouhun", 10);

        jedis.close();

    }


}
