package com.cvc.crm.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BitOP;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.SortingParams;

public final class RDSUtils {

	private volatile static JedisPool jedisPool;

	private static String host;
	private static int port;
	private static String password;
	private static int timeout;
	private static int database = 1;// 默认存放数据库

	static {
		try {
			Properties p = new Properties();
			p.load(RDSUtils.class.getResourceAsStream("/redis.properties"));
			host = p.getProperty("host");
			port = Integer.parseInt(p.getProperty("port"));
			password = p.getProperty("password");
			timeout = Integer.parseInt(p.getProperty("timeout"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private RDSUtils() {
	}

	public static JedisPool getJedisPool() {
		if (jedisPool == null) {
			synchronized (RDSUtils.class) {
				if (jedisPool == null) {
					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxTotal(1000);
					config.setMaxIdle(50);
					config.setMaxWaitMillis(1000 * 100);
					config.setTestOnBorrow(true);
					jedisPool = new JedisPool(config, host, port, timeout, password, database);
				}
			}
		}
		return jedisPool;
	}

	public static Jedis getJedis() {

		// Jedis jedis = new Jedis("127.0.0.1", 6379, 60000);
		// jedis.auth("dab935edb3064773aacaf811ee83fc15");
		// jedis.select(1);
		// return jedis;

		return getJedisPool().getResource();

	}

	/* --- Hashes ----------------------- */

	public static Long hdel(String key, String... fields) {
		Jedis jedis = getJedis();
		Long result = jedis.hdel(key, fields);
		jedis.close();
		return result;
	}

	public static Boolean hexists(String key, String field) {
		Jedis jedis = getJedis();
		Boolean result = jedis.hexists(key, field);
		jedis.close();
		return result;
	}

	public static String hget(String key, String field) {
		Jedis jedis = getJedis();
		String result = jedis.hget(key, field);
		jedis.close();
		return result;
	}

	public static Map<String, String> hgetAll(String key) {
		Jedis jedis = getJedis();
		Map<String, String> result = jedis.hgetAll(key);
		jedis.close();
		return result;
	}

	public static Long hincrBy(String key, String field, long value) {
		Jedis jedis = getJedis();
		Long result = jedis.hincrBy(key, field, value);
		jedis.close();
		return result;
	}

	public static Double hincrByFloat(String key, String field, double value) {
		Jedis jedis = getJedis();
		Double result = jedis.hincrByFloat(key, field, value);
		jedis.close();
		return result;
	}

	public static Set<String> hkeys(String key) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.hkeys(key);
		jedis.close();
		return result;
	}

	public static Long hlen(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.hlen(key);
		jedis.close();
		return result;
	}

	public static List<String> hmget(String key, String... fields) {
		Jedis jedis = getJedis();
		List<String> result = jedis.hmget(key, fields);
		jedis.close();
		return result;
	}

	public static String hmset(String key, Map<String, String> hash) {
		Jedis jedis = getJedis();
		String result = jedis.hmset(key, hash);
		jedis.close();
		return result;
	}

	public static Long hset(String key, String field, String value) {
		Jedis jedis = getJedis();
		Long result = jedis.hset(key, field, value);
		jedis.close();
		return result;
	}

	public static Long hsetnx(String key, String field, String value) {
		Jedis jedis = getJedis();
		Long result = jedis.hsetnx(key, field, value);
		jedis.close();
		return result;
	}

	public static List<String> hvals(String key) {
		Jedis jedis = getJedis();
		List<String> result = jedis.hvals(key);
		jedis.close();
		return result;
	}

	/* --- Keys ----------------------- */

	public static Long del(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.del(key);
		jedis.close();
		return result;
	}

	public static Long del(String... keys) {
		Jedis jedis = getJedis();
		Long result = jedis.del(keys);
		jedis.close();
		return result;
	}

	public static byte[] dump(String key) {
		Jedis jedis = getJedis();
		byte[] result = jedis.dump(key);
		jedis.close();
		return result;
	}

	public static Boolean exists(String key) {
		Jedis jedis = getJedis();
		Boolean result = jedis.exists(key);
		jedis.close();
		return result;
	}

/*	public static Long exists(String... keys) {
		Jedis jedis = getJedis();
		Long result = jedis.exists(keys);
		jedis.close();
		return result;
	}
*/
	public static Long expire(String key, int seconds) {
		Jedis jedis = getJedis();
		Long result = jedis.expire(key, seconds);
		jedis.close();
		return result;
	}

	public static Long expireAt(String key, long unixTime) {
		Jedis jedis = getJedis();
		Long result = jedis.expireAt(key, unixTime);
		jedis.close();
		return result;
	}

	public static Set<String> keys(String pattern) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.keys(pattern);
		jedis.close();
		return result;
	}

	public static String migrate(String host, int port, String key, int destinationDb, int timeout) {
		Jedis jedis = getJedis();
		String result = jedis.migrate(host, port, key, destinationDb, timeout);
		jedis.close();
		return result;
	}

	public static Long move(String key, int dbIndex) {
		Jedis jedis = getJedis();
		Long result = jedis.move(key, dbIndex);
		jedis.close();
		return result;
	}

	public static Long persist(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.persist(key);
		jedis.close();
		return result;
	}

	public static Long pexpire(String key, long milliseconds) {
		Jedis jedis = getJedis();
		Long result = jedis.pexpire(key, milliseconds);
		jedis.close();
		return result;
	}

	public static Long pexpireAt(String key, long millisecondsTimestamp) {
		Jedis jedis = getJedis();
		Long result = jedis.pexpireAt(key, millisecondsTimestamp);
		jedis.close();
		return result;
	}

	public static Long pttl(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.pttl(key);
		jedis.close();
		return result;
	}

	public static String randomKey() {
		Jedis jedis = getJedis();
		String result = jedis.randomKey();
		jedis.close();
		return result;
	}

	public static String rename(String oldkey, String newkey) {
		Jedis jedis = getJedis();
		String result = jedis.rename(oldkey, newkey);
		jedis.close();
		return result;
	}

	public static Long renamenx(String oldkey, String newkey) {
		Jedis jedis = getJedis();
		Long result = jedis.renamenx(oldkey, newkey);
		jedis.close();
		return result;
	}

	public static String restore(String key, int ttl, byte[] serializedValue) {
		Jedis jedis = getJedis();
		String result = jedis.restore(key, ttl, serializedValue);
		jedis.close();
		return result;
	}

	public static List<String> sort(String key) {
		Jedis jedis = getJedis();
		List<String> result = jedis.sort(key);
		jedis.close();
		return result;
	}

	public static Long sort(String key, String dstkey) {
		Jedis jedis = getJedis();
		Long result = jedis.sort(key, dstkey);
		jedis.close();
		return result;
	}

	public static List<String> sort(String key, SortingParams sortingParameters) {
		Jedis jedis = getJedis();
		List<String> result = jedis.sort(key, sortingParameters);
		jedis.close();
		return result;
	}

	public static Long sort(String key, SortingParams sortingParameters, String dstkey) {
		Jedis jedis = getJedis();
		Long result = jedis.sort(key, sortingParameters, dstkey);
		jedis.close();
		return result;
	}

	public static Long ttl(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	public static String type(String key) {
		Jedis jedis = getJedis();
		String result = jedis.type(key);
		jedis.close();
		return result;
	}

	/* --- Lists ----------------------- */

	public static List<String> blpop(String... args) {
		Jedis jedis = getJedis();
		List<String> result = jedis.blpop(args);
		jedis.close();
		return result;
	}

	public static List<String> blpop(int timeout, String key) {
		Jedis jedis = getJedis();
		List<String> result = jedis.blpop(timeout, key);
		jedis.close();
		return result;
	}

	public static List<String> blpop(int timeout, String... keys) {
		Jedis jedis = getJedis();
		List<String> result = jedis.blpop(timeout, keys);
		jedis.close();
		return result;
	}

	public static List<String> brpop(String... args) {
		Jedis jedis = getJedis();
		List<String> result = jedis.brpop(args);
		jedis.close();
		return result;
	}

	public static List<String> brpop(int timeout, String key) {
		Jedis jedis = getJedis();
		List<String> result = jedis.brpop(timeout, key);
		jedis.close();
		return result;
	}

	public static List<String> brpop(int timeout, String... keys) {
		Jedis jedis = getJedis();
		List<String> result = jedis.brpop(timeout, keys);
		jedis.close();
		return result;
	}

	public static String brpoplpush(String source, String destination, int timeout) {
		Jedis jedis = getJedis();
		String result = jedis.brpoplpush(source, destination, timeout);
		jedis.close();
		return result;
	}

	public static String lindex(String key, long index) {
		Jedis jedis = getJedis();
		String result = jedis.lindex(key, index);
		jedis.close();
		return result;
	}

	public static Long lindex(String key, LIST_POSITION where, String pivot, String value) {
		Jedis jedis = getJedis();
		Long result = jedis.linsert(key, where, pivot, value);
		jedis.close();
		return result;
	}

	public static Long llen(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.llen(key);
		jedis.close();
		return result;
	}

	public static String lpop(String key) {
		Jedis jedis = getJedis();
		String result = jedis.lpop(key);
		jedis.close();
		return result;
	}

	public static Long lpush(String key, String... strings) {
		Jedis jedis = getJedis();
		Long result = jedis.lpush(key, strings);
		jedis.close();
		return result;
	}

	public static Long lpushx(String key, String... string) {
		Jedis jedis = getJedis();
		Long result = jedis.lpushx(key, string);
		jedis.close();
		return result;
	}

	public static List<String> lrange(String key, long start, long end) {
		Jedis jedis = getJedis();
		List<String> result = jedis.lrange(key, start, end);
		jedis.close();
		return result;
	}

	public static Long lrem(String key, long count, String value) {
		Jedis jedis = getJedis();
		Long result = jedis.lrem(key, count, value);
		jedis.close();
		return result;
	}

	public static String lset(String key, long index, String value) {
		Jedis jedis = getJedis();
		String result = jedis.lset(key, index, value);
		jedis.close();
		return result;
	}

	public static String ltrim(String key, long start, long end) {
		Jedis jedis = getJedis();
		String result = jedis.ltrim(key, start, end);
		jedis.close();
		return result;
	}

	public static String rpop(String key) {
		Jedis jedis = getJedis();
		String result = jedis.rpop(key);
		jedis.close();
		return result;
	}

	public static String rpoplpush(String srckey, String dstkey) {
		Jedis jedis = getJedis();
		String result = jedis.rpoplpush(srckey, dstkey);
		jedis.close();
		return result;
	}

	public static Long rpush(String key, String... strings) {
		Jedis jedis = getJedis();
		Long result = jedis.rpush(key, strings);
		jedis.close();
		return result;
	}

	public static Long rpushx(String key, String... string) {
		Jedis jedis = getJedis();
		Long result = jedis.rpushx(key, string);
		jedis.close();
		return result;
	}

	/* --- Server ----------------------- */

	public static Long dbSize() {
		Jedis jedis = getJedis();
		Long result = jedis.dbSize();
		jedis.close();
		return result;
	}

	public static String flushAll() {
		Jedis jedis = getJedis();
		String result = jedis.flushAll();
		jedis.close();
		return result;
	}

	public static String flushDB() {
		Jedis jedis = getJedis();
		String result = jedis.flushDB();
		jedis.close();
		return result;
	}

	public static String info() {
		Jedis jedis = getJedis();
		String result = jedis.info();
		jedis.close();
		return result;
	}

	public static List<String> time() {
		Jedis jedis = getJedis();
		List<String> result = jedis.time();
		jedis.close();
		return result;
	}

	/* --- Sets ----------------------- */

	public static Long sadd(String key, String... members) {
		Jedis jedis = getJedis();
		Long result = jedis.sadd(key, members);
		jedis.close();
		return result;
	}

	public static Long scard(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.scard(key);
		jedis.close();
		return result;
	}

	public static Set<String> sdiff(String... keys) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.sdiff(keys);
		jedis.close();
		return result;
	}

	public static Long sdiffstore(String dstkey, String... keys) {
		Jedis jedis = getJedis();
		Long result = jedis.sdiffstore(dstkey, keys);
		jedis.close();
		return result;
	}

	public static Set<String> sinter(String... keys) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.sinter(keys);
		jedis.close();
		return result;
	}

	public static Long sinterstore(String dstkey, String... keys) {
		Jedis jedis = getJedis();
		Long result = jedis.sinterstore(dstkey, keys);
		jedis.close();
		return result;
	}

	public static Boolean sismember(String key, String member) {
		Jedis jedis = getJedis();
		Boolean result = jedis.sismember(key, member);
		jedis.close();
		return result;
	}

	public static Set<String> smembers(String key) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.smembers(key);
		jedis.close();
		return result;
	}

	public static Long smove(String srckey, String dstkey, String member) {
		Jedis jedis = getJedis();
		Long result = jedis.smove(srckey, dstkey, member);
		jedis.close();
		return result;
	}

	public static String spop(String key) {
		Jedis jedis = getJedis();
		String result = jedis.spop(key);
		jedis.close();
		return result;
	}

	public static String srandmember(String key) {
		Jedis jedis = getJedis();
		String result = jedis.srandmember(key);
		jedis.close();
		return result;
	}

	public static List<String> srandmember(String key, int count) {
		Jedis jedis = getJedis();
		List<String> result = jedis.srandmember(key, count);
		jedis.close();
		return result;
	}

	public static Long srem(String key, String... members) {
		Jedis jedis = getJedis();
		Long result = jedis.srem(key, members);
		jedis.close();
		return result;
	}

	public static Set<String> sunion(String... keys) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.sunion(keys);
		jedis.close();
		return result;
	}

	public static Long sunionstore(String dstkey, String... keys) {
		Jedis jedis = getJedis();
		Long result = jedis.sunionstore(dstkey, keys);
		jedis.close();
		return result;
	}

	/* --- Strings ----------------------- */

	public static Long append(String key, String value) {
		Jedis jedis = getJedis();
		Long result = jedis.append(key, value);
		jedis.close();
		return result;
	}

	public static Long bitcount(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.bitcount(key);
		jedis.close();
		return result;
	}

	public static Long bitcount(String key, long start, long end) {
		Jedis jedis = getJedis();
		Long result = jedis.bitcount(key, start, end);
		jedis.close();
		return result;
	}

	public static Long bitop(BitOP op, String destKey, String... srcKeys) {
		Jedis jedis = getJedis();
		Long result = jedis.bitop(op, destKey, srcKeys);
		jedis.close();
		return result;
	}

	public static Long bitpos(String key, boolean value) {
		Jedis jedis = getJedis();
		Long result = jedis.bitpos(key, value);
		jedis.close();
		return result;
	}

	public static Long bitpos(String key, boolean value, BitPosParams params) {
		Jedis jedis = getJedis();
		Long result = jedis.bitpos(key, value, params);
		jedis.close();
		return result;
	}

	public static Long decr(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.decr(key);
		jedis.close();
		return result;
	}

	public static Long decrBy(String key, long integer) {
		Jedis jedis = getJedis();
		Long result = jedis.decrBy(key, integer);
		jedis.close();
		return result;
	}

	public static String get(String key) {
		Jedis jedis = getJedis();
		String result = jedis.get(key);
		jedis.close();
		return result;
	}

	public static Boolean getbit(String key, long offset) {
		Jedis jedis = getJedis();
		Boolean result = jedis.getbit(key, offset);
		jedis.close();
		return result;
	}

	public static String getrange(String key, long startOffset, long endOffset) {
		Jedis jedis = getJedis();
		String result = jedis.getrange(key, startOffset, endOffset);
		jedis.close();
		return result;
	}

	public static String getSet(String key, String value) {
		Jedis jedis = getJedis();
		String result = jedis.getSet(key, value);
		jedis.close();
		return result;
	}

	public static Long incr(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	public static Long incrBy(String key, long integer) {
		Jedis jedis = getJedis();
		Long result = jedis.incrBy(key, integer);
		jedis.close();
		return result;
	}

	public static Double incrByFloat(String key, double value) {
		Jedis jedis = getJedis();
		Double result = jedis.incrByFloat(key, value);
		jedis.close();
		return result;
	}

	public static List<String> mget(String... keys) {
		Jedis jedis = getJedis();
		List<String> result = jedis.mget(keys);
		jedis.close();
		return result;
	}

	public static String mset(String... keysvalues) {
		Jedis jedis = getJedis();
		String result = jedis.mset(keysvalues);
		jedis.close();
		return result;
	}

	public static Long msetnx(String... keysvalues) {
		Jedis jedis = getJedis();
		Long result = jedis.msetnx(keysvalues);
		jedis.close();
		return result;
	}

	/*public static String msetnx(String key, long milliseconds, String value) {
		Jedis jedis = getJedis();
		String result = jedis.psetex(key, milliseconds, value);
		jedis.close();
		return result;
	}*/

	public static String set(String key, String value) {
		Jedis jedis = getJedis();
		String result = jedis.set(key, value);
		jedis.close();
		return result;
	}

	public static String set(String key, String value, String nxxx) {
		Jedis jedis = getJedis();
		String result = jedis.set(key, value, nxxx);
		jedis.close();
		return result;
	}

	public static String set(String key, String value, String nxxx, String expx, long time) {
		Jedis jedis = getJedis();
		String result = jedis.set(key, value, nxxx, expx, time);
		jedis.close();
		return result;
	}

	public static String set(String key, String value, String nxxx, String expx, int time) {
		Jedis jedis = getJedis();
		String result = jedis.set(key, value, nxxx, expx, time);
		jedis.close();
		return result;
	}

	public static Boolean setbit(String key, long offset, String value) {
		Jedis jedis = getJedis();
		Boolean result = jedis.setbit(key, offset, value);
		jedis.close();
		return result;
	}

	public static Boolean setbit(String key, long offset, boolean value) {
		Jedis jedis = getJedis();
		Boolean result = jedis.setbit(key, offset, value);
		jedis.close();
		return result;
	}

	public static String setex(String key, int seconds, String value) {
		Jedis jedis = getJedis();
		String result = jedis.setex(key, seconds, value);
		jedis.close();
		return result;
	}

	public static Long setnx(String key, String value) {
		Jedis jedis = getJedis();
		Long result = jedis.setnx(key, value);
		jedis.close();
		return result;
	}

	public static Long setrange(String key, long offset, String value) {
		Jedis jedis = getJedis();
		Long result = jedis.setrange(key, offset, value);
		jedis.close();
		return result;
	}

	public static Long strlen(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.strlen(key);
		jedis.close();
		return result;
	}

	/* --- Sorted Sets ----------------------- */

	public static Long zadd(String key, double score, String member) {
		Jedis jedis = getJedis();
		Long result = jedis.zadd(key, score, member);
		jedis.close();
		return result;
	}

	public static Long zadd(String key, Map<String, Double> scoreMembers) {
		Jedis jedis = getJedis();
		Long result = jedis.zadd(key, scoreMembers);
		jedis.close();
		return result;
	}

	public static Long zcard(String key) {
		Jedis jedis = getJedis();
		Long result = jedis.zcard(key);
		jedis.close();
		return result;
	}

	public static Long zcount(String key, String min, String max) {
		Jedis jedis = getJedis();
		Long result = jedis.zcount(key, min, max);
		jedis.close();
		return result;
	}

	public static Long zcount(String key, double min, double max) {
		Jedis jedis = getJedis();
		Long result = jedis.zcount(key, min, max);
		jedis.close();
		return result;
	}

	public static Double zincrby(String key, double score, String member) {
		Jedis jedis = getJedis();
		Double result = jedis.zincrby(key, score, member);
		jedis.close();
		return result;
	}

	public static Set<String> zrange(String key, long start, long end) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.zrange(key, start, end);
		jedis.close();
		return result;
	}

	public static Set<String> zrangeByScore(String key, String min, String max) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.zrangeByScore(key, min, max);
		jedis.close();
		return result;
	}

	public static Set<String> zrangeByScore(String key, double min, double max) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.zrangeByScore(key, min, max);
		jedis.close();
		return result;
	}

	public static Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.zrangeByScore(key, min, max, offset, count);
		jedis.close();
		return result;
	}

	public static Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.zrangeByScore(key, min, max, offset, count);
		jedis.close();
		return result;
	}

	public static Long zrank(String key, String member) {
		Jedis jedis = getJedis();
		Long result = jedis.zrank(key, member);
		jedis.close();
		return result;
	}

	public static Long zrem(String key, String... members) {
		Jedis jedis = getJedis();
		Long result = jedis.zrem(key, members);
		jedis.close();
		return result;
	}

	public static Long zremrangeByRank(String key, long start, long end) {
		Jedis jedis = getJedis();
		Long result = jedis.zremrangeByRank(key, start, end);
		jedis.close();
		return result;
	}

	public static Long zremrangeByScore(String key, double start, double end) {
		Jedis jedis = getJedis();
		Long result = jedis.zremrangeByScore(key, start, end);
		jedis.close();
		return result;
	}

	public static Long zremrangeByScore(String key, String start, String end) {
		Jedis jedis = getJedis();
		Long result = jedis.zremrangeByScore(key, start, end);
		jedis.close();
		return result;
	}

	public static Set<String> zrevrange(String key, long start, long end) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.zrevrange(key, start, end);
		jedis.close();
		return result;
	}

	public static Set<String> zrevrangeByScore(String key, String max, String min) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.zrevrangeByScore(key, max, min);
		jedis.close();
		return result;
	}

	public static Set<String> zrevrangeByScore(String key, double max, double min) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.zrevrangeByScore(key, max, min);
		jedis.close();
		return result;
	}

	public static Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.zrevrangeByScore(key, max, min, offset, count);
		jedis.close();
		return result;
	}

	public static Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.zrevrangeByScore(key, max, min, offset, count);
		jedis.close();
		return result;
	}

	public static Long zrevrank(String key, String member) {
		Jedis jedis = getJedis();
		Long result = jedis.zrevrank(key, member);
		jedis.close();
		return result;
	}

	public static Double zscore(String key, String member) {
		Jedis jedis = getJedis();
		Double result = jedis.zscore(key, member);
		jedis.close();
		return result;
	}

	public static Long zunionstore(String dstkey, String... sets) {
		Jedis jedis = getJedis();
		Long result = jedis.zunionstore(dstkey, sets);
		jedis.close();
		return result;
	}

	public static Long zinterstore(String dstkey, String... sets) {
		Jedis jedis = getJedis();
		Long result = jedis.zinterstore(dstkey, sets);
		jedis.close();
		return result;
	}

	public static Long zlexcount(String key, String min, String max) {
		Jedis jedis = getJedis();
		Long result = jedis.zlexcount(key, min, max);
		jedis.close();
		return result;
	}

	public static Long zremrangeByLex(String key, String min, String max) {
		Jedis jedis = getJedis();
		Long result = jedis.zremrangeByLex(key, min, max);
		jedis.close();
		return result;
	}

}
