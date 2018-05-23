package cn.cyc.order.dao.imp;


import org.springframework.beans.factory.annotation.Autowired;

import cn.cyc.order.dao.JedisClient;
import redis.clients.jedis.JedisCluster;

public class JedisClientCluster implements JedisClient {

	@Autowired
	JedisCluster jedisCluster;

	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public String hget(String hash, String key) {
		return jedisCluster.hget(hash, key);
	}

	@Override
	public Long hset(String hash, String key, String value) {
		return jedisCluster.hset(hash, key, value);
	}

	@Override
	public long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public long expire(String key, int seconds) {
		return jedisCluster.expire(key, seconds);
	}

	@Override
	public long ttl(String key) {
		return jedisCluster.ttl(key);
	}

	@Override
	public long hdel(String hash, String key) {
		return jedisCluster.hdel(hash, key);
	}
	
	@Override
	public long del(String key) {
		return jedisCluster.del(key);
	}
}
