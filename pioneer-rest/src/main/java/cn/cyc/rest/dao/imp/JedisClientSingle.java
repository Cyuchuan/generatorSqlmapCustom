package cn.cyc.rest.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;

import cn.cyc.rest.dao.JedisClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisClientSingle implements JedisClient {

	@Autowired
	JedisPool jedisPool;

	@Override
	public String get(String key) {
		Jedis resource = jedisPool.getResource();
		String string = resource.get(key);
		resource.close();
		return string;
	}

	@Override
	public String set(String key, String value) {
		Jedis resource = jedisPool.getResource();
		String string = resource.set(key, value);
		resource.close();
		return string;
	}

	@Override
	public String hget(String hash, String key) {
		Jedis resource = jedisPool.getResource();
		String string = resource.hget(hash, key);
		resource.close();
		return string;
	}

	@Override
	public Long hset(String hash, String key, String value) {
		Jedis resource = jedisPool.getResource();
		Long hset = resource.hset(hash, key, value);
		resource.close();
		return hset;
	}

	@Override
	public long incr(String key) {
		Jedis resource = jedisPool.getResource();
		Long incr = resource.incr(key);
		resource.close();
		return incr;
	}

	@Override
	public long expire(String key, int seconds) {
		Jedis resource = jedisPool.getResource();
		Long expire = resource.expire(key, seconds);
		resource.close();
		return expire;
	}

	@Override
	public long ttl(String key) {
		Jedis resource = jedisPool.getResource();
		Long ttl = resource.ttl(key);
		resource.close();
		return ttl;
	}

	@Override
	public long del(String key) {
		Jedis resource = jedisPool.getResource();
		Long del = resource.del(key);
		resource.close();
		return del;
	}

	@Override
	public long hdel(String hash, String key) {
		Jedis resource = jedisPool.getResource();
		Long del = resource.hdel(hash, key);
		resource.close();
		return del;
	}
}
