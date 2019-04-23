package com.zzl.test.redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class TestRedis {
	
	
	@Test
	public void m1(){
		
//		Jedis jedis = new Jedis("118.24.194.103", 6379);
//		System.out.println(jedis.get("zyf"));
//		jedis.close();
	}
	
	@Test
	public void m2(){
//		// 第一步：创建一个JedisPool对象。需要指定服务端的ip及端口。
//		JedisPool jedisPool = new JedisPool("118.24.194.103", 6379);
//		// 第二步：从JedisPool中获得Jedis对象。
//		Jedis jedis = jedisPool.getResource();
//		// 第三步：使用Jedis操作redis服务器。
//		jedis.set("jedis", "test");
//		String result = jedis.get("jedis");
//		System.out.println(result);
//		// 第四步：操作完毕后关闭jedis对象，连接池回收资源。
//		jedis.close();
//		// 第五步：关闭JedisPool对象。
//		jedisPool.close();
	}
}
