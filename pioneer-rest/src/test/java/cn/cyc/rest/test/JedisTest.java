package cn.cyc.rest.test;

import java.util.Date;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	@Test
	public void test() {
		Jedis jedis = new Jedis("192.168.32.129", 6379);
		System.out.println(jedis.get("a"));
		jedis.close();
	}

	@Test
	public void testPool() {
		JedisPool jedisPool = new JedisPool("192.168.32.129", 6379);
		Jedis resource = jedisPool.getResource();
		System.out.println(resource.get("a"));
		resource.close();
	}

	@Test
	public void testTime() {
		
	}
}
