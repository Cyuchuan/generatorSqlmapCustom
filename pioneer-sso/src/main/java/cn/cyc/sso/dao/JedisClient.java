package cn.cyc.sso.dao;

public interface JedisClient {
	String get(String key);

	String set(String key, String value);

	String hget(String hash, String key);

	Long hset(String hash, String key, String value);

	long incr(String key);

	long expire(String key, int seconds);

	long ttl(String key);
	
	long del(String key);

	long hdel(String hash, String key);
}
