package com.test.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;
import redis.clients.util.Pool;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

/**
 * Redis操作接口
 */

@Component
@Scope("singleton")
@Slf4j
public class RedisUtil {


    /**single node jedis pool*/
    private JedisPool pool = null;
    
    /**slave redis pools*/
    private JedisPool[] slavePools = null;


    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
//    @Value("${spring.redis.slave.hostlist}")
//    private String slaveHosts;
//    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.pool.max-active}")
    private String maxTotal;
    @Value("${spring.redis.pool.max-idle}")
    private String maxIdle;
    @Value("${spring.redis.pool.max-wait}")
    private String maxWaitMillis;
    @Value("${spring.redis.testOnBorrow}")
    private String testOnBorrow;
//    @Value("${spring.redis.mymaster.masterName}")
//    private String masterName;
//    @Value("${spring.redis.mymaster.sentinel}")
//    private String sentinel;
//    @Value("${spring.redis.mode:single}")
//    private String mode;
    
    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        initJedisSingleNodePool();

    }


	/**
	 * 初始化从redis节点连接池
	 */
	private void initSlavePools() {
		JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.valueOf(maxTotal));
        config.setMaxIdle(Integer.valueOf(maxIdle));
        config.setMaxWaitMillis(Integer.valueOf(maxWaitMillis));
        config.setTestOnBorrow(Boolean.valueOf(testOnBorrow));
        

//        //初始化从库用于读取
//        if( slaveHosts != null ) {
//        	String[] hostList = slaveHosts.split(",");
//        	if( hostList != null && hostList.length > 0 ) {
//        		slavePools = new JedisPool[hostList.length];
//	            for (int i = 0; i <= hostList.length - 1; i++) {
//	            	String[] hostAndPort = hostList[i].split(":");
//	            	String host = hostAndPort[0];
//	            	String port = hostAndPort[1];
//	            	slavePools[i] = new JedisPool(config, host, Integer.valueOf(port), Protocol.DEFAULT_TIMEOUT, null);
//	            }
//        	}
//        }
//
//        slavePoolsLength = slavePools.length;
	}
    

	
	
	/**
	 * 单点模式连接池初始化方法
	 */
	private void initJedisSingleNodePool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.valueOf(maxTotal));
        config.setMaxIdle(Integer.valueOf(maxIdle));
        config.setMaxWaitMillis(Integer.valueOf(maxWaitMillis));
        config.setTestOnBorrow(Boolean.valueOf(testOnBorrow));
        int timeout = 5000;
        pool = new JedisPool(config, host, Integer.valueOf(port), timeout, password);
	}

    @PreDestroy
    public void destroy() {

    	if( pool != null ) {
    		pool.destroy();
    	}
    	
    	if( slavePools != null ) {
    		for( JedisPool slavePool : slavePools ) {
    			slavePool.destroy();
    		}
    	}
    	
    }

    /**
     * 构建redis连接池
     *
     * @return JedisPool
     */
    public Pool<Jedis> getPool() {

        if( pool == null ) {
            initJedisSingleNodePool();
        }
        return pool;

    	
    }

    
    private Jedis getConnection() {
		return getPool().getResource();
	}
	
	int i = 0;
	private Jedis getReadConnection() {
		return getConnection();
	}
    
    /**
     * 返回资源
     * @param pool
     * @param redis
     */
    public void returnResource(Jedis redis) {
                redis.close();
    }

    /**
     * 关闭资源
     * @param pool
     * @param redis
     */
    public void returnBrokenResource(Jedis redis) {
            redis.close();
    }

    /**
     * 删除给定的一个或多个 key
     * @param db 库号
     * @param key 键
     * @return boolean
     */
//    public void flushDB(int db) {
//       
//        Jedis jedis = null;
//        try {
//            
//            jedis = getConnection();
//            jedis.select(db);
//            jedis.flushDB();
//        } catch (Exception e) {
//            returnBrokenResource(jedis);
//            log.error(e.getMessage(), e);
//        } finally {
//            returnResource(jedis);
//        }
//    }

    /**
     * 删除给定的一个或多个 key
     * @param db 库号
     * @param key 键
     * @return boolean
     */
    public boolean del(int db, String... keys) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            return jedis.del(keys) > 0;
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return true;
    }

    /**
     * 删除给定的一个或多个 key
     * @param db 库号
     * @param key 键
     * @return boolean
     */
    public boolean del(int db, Set<String> keys) {
        if(keys == null || keys.isEmpty()){
            return false;
        }
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            String[] arr = new String[keys.size()];
            int i=0;
            for(String str : keys){
                arr[i] = str;
                i++;
            }
            return jedis.del(arr) > 0;
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return true;
    }

    /**
     * 返回 key 所关联的字符串值
     * @param db 库号
     * @param key 键
     * @return String
     */
    public String get(int db, String key) {
       
        Jedis jedis = null;
        try {
            
            jedis = getReadConnection();
            jedis.select(db);
            return jedis.get(key);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    /**
     * 将字符串值 value 关联到 key
     * @param db 库号
     * @param key 键
     * @param value 值
     * @return boolean
     */
    public boolean set(int db, String key, String value) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            jedis.set(key, value);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return true;
    }

    /**
     * 移除并返回列表 key 的头元素
     * @param db 库号
     * @param key 键
     * @return String
     */
    public String lpop(int db, String key) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            return jedis.lpop(key);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)
     * @param db 库号
     * @param key 键
     * @param values 值
     * @return boolean
     */
    public boolean rpush(int db, String key, String... values) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            return jedis.rpush(key, values) > 0;
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表头(最左边)
     * @param db 库号
     * @param key 键
     * @param values 值
     * @return boolean
     */
    public boolean lpush(int db, String key, String... values) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            return jedis.lpush(key, values) > 0;
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    /**
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定
     * @param db 库号
     * @param key 键
     * @param start 0列表的第一个元素,1列表的第二个元素,-1列表的最后一个元素,-2列表的倒数第二个元素
     * @param end 0列表的第一个元素,1列表的第二个元素,-1列表的最后一个元素,-2列表的倒数第二个元素
     * @return List<String>
     */
    public List<String> lrange(int db, String key, long start, long end) {
       
        Jedis jedis = null;
        try {
            
            jedis = getReadConnection();
            jedis.select(db);
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    /**
     * 只保留指定区间内的元素，不在指定区间之内的元素都将被删除
     * LTRIM list 0 2
     * 只保留列表 list 的前三个元素，其余元素全部删除
     * @param db 库号
     * @param key 键
     * @param start 开始值
     * @param end 结束值
     * @return String
     */
    public String ltrim(int db, String key, long start, long end) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            return jedis.ltrim(key, start, end);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    /**
     * 参数 count 的值，移除列表中与参数 value 相等的元素。
     * @param db
     * @param key
     * @param count
     * count 的值可以是以下几种：
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
     * count = 0 : 移除表中所有与 value 相等的值
     * @param value
     * @return boolean
     */
    public boolean lrem(int db, String key, long count, String value) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            return jedis.lrem(key, count, value) > 0;
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return true;
    }

    /**
     * 返回列表 key 的长度
     * 如果 key 不存在，则 key 被解释为一个空列表，返回 0
     * 如果 key 不是列表类型，返回一个错误
     * @param db
     * @param key
     * @return long
     */
    public long llen(int db, String key) {
       
        Jedis jedis = null;
        long len = 0;
        try {
            
            jedis = getReadConnection();
            jedis.select(db);
            len = jedis.llen(key);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return len;
    }

    /**
     * 为给定 key 设置生存时间
     * @param db 库号
     * @param key 键
     * @param seconds 秒数
     * @return boolean
     */
    public boolean expire(int db, String key, int seconds) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            return jedis.expire(key, seconds) > 0;
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return true;
    }

    /**
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略
     * @param db
     * @param key
     * @param values
     * @return boolean
     */
    public boolean srem(int db, String key, String... values) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            return jedis.srem(key, values) > 0;
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return true;
    }

    /**
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略
     * @param db
     * @param key
     * @param members
     * @return boolean
     */
    public boolean sadd(int db, String key, String... members) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            jedis.sadd(key, members);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return true;
    }

    /**
     * 从set中pop出一个对象
     * @param db
     * @param key
     * @return string
     */
    public String spop(int db, String key) {
       
        Jedis jedis = null;
        String str = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            str = jedis.spop(key);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return str;
    }

    /**
     * 从set中pop出一批对象
     * @param db
     * @param key
     * @param count
     * @return
     */
    public Set<String> spopBatch(int db, String key, long count) {
       
        Jedis jedis = null;
        Set<String> set = new HashSet<String>();
        try {
            
            jedis = getConnection();
            jedis.select(db);
            long size = jedis.scard(key);
            long sizeCount = Math.min(size, count);
            for(int i=0;i<sizeCount;i++){
                set.add(jedis.spop(key));
            }
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return set;
    }

    /**
     * 返回集合 key 中的所有成员
     * @param db
     * @param key
     * @return Set<String>
     */
    public Set<String> smembers(int db, String key) {
       
        Jedis jedis = null;
        try {
            
            jedis = getReadConnection();
            jedis.select(db);
            return jedis.smembers(key);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    /**
     * 判断 member 元素是否集合 key 的成员
     * @param db
     * @param key
     * @param member
     * @return boolean
     */
    public boolean sismember(int db, String key, String member) {
       
        Jedis jedis = null;
        try {
            
            jedis = getReadConnection();
            jedis.select(db);
            return jedis.sismember(key, member);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    /**
     * 将哈希表 key 中的域 field 的值设为 value
     * @param db 库号
     * @param key 键
     * @param field 域
     * @param value 值
     * @return boolean
     */
    public boolean hset(int db, String key, String field, String value) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            jedis.hset(key, field, value);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return true;
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中
     * @param db
     * @param key
     * @param hash
     * @return boolean
     */
    public boolean hmset(int db, String key, Map<String, String> hash) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            jedis.hmset(key, hash);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return true;
    }

    /**
     * 以秒为单位，返回给定 key 的剩余生存时间
     * @param db 库号
     * @param key 键
     * @return int
     */
    public int ttl(int db, String key) {
       
        Jedis jedis = null;
        int result = 0;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            result = jedis.ttl(key).intValue();
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 返回哈希表 key 中给定域 field 的值
     * @param db 库号
     * @param key 键
     * @param field 域
     * @return String
     */
    public String hget(int db, String key, String field) {
       
        Jedis jedis = null;
        String value = null;
        try {
            
            jedis = getReadConnection();
            jedis.select(db);
            value = jedis.hget(key, field);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 查看哈希表 key 中，给定域 field 是否存在
     * @param db 库号
     * @param key 键
     * @param field 与
     * @return boolean
     */
    public boolean hexists(int db, String key, String field) {
       
        Jedis jedis = null;
        try {
            
            jedis = getReadConnection();
            jedis.select(db);
            return jedis.hexists(key, field);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    /**
     * 返回哈希表 key 中，所有的域和值
     * @param db
     * @param key
     * @return Map<String, String>
     */
    public Map<String, String> hgetAll(int db, String key) {
       
        Jedis jedis = null;
        Map<String, String> hash = null;
        try {
            
            jedis = getReadConnection();
            jedis.select(db);
            hash = jedis.hgetAll(key);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return hash;
    }

    /**
     * 返回哈希表 key 中，所有的域和值，设置过期时间
     * @param db
     * @param key
     * @param second 秒数
     * @return Map<String, String>
     */
    public Map<String, String> hgetAll(int db, String key, int second) {
       
        Jedis jedis = null;
        Map<String, String> hash = null;
        try {
            
            jedis = getReadConnection();
            jedis.select(db);

            Pipeline piple = jedis.pipelined();
            piple.expire(key, second);
            Map<String, Response<Map<String, String>>> responses;
            responses = new HashMap<String, Response<Map<String, String>>>();
            responses.put(key, piple.hgetAll( key));
            piple.sync();

            if (!responses.get(key).get().isEmpty()) {
                hash = responses.get(key).get();
            }
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return hash;
    }

    /**
     * 删除哈希表 key中多个指定域，不存在的域将被忽略
     * @param db 库号
     * @param key 键
     * @param field 域
     * @return boolean
     */
    public boolean hdel(int db, String key, String... field) {
       
        Jedis jedis = null;
        long value = 0;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            value = jedis.hdel(key, field);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return (value > 0);
    }

    /**
     * 为哈希表 key中的域 field 的值加上增量 increment
     * @param db 库号
     * @param key 键
     * @param field 域
     * @param increment 增量值
     * @return long
     */
    public long hincrBy(int db, String key, String field, long increment) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            return jedis.hincrBy(key, field, increment);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return 0;
    }

    /**
     * 自增 +1
     * @param db 库号
     * @param key 键
     * @return long
     */
    public long incr(int db, String key) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            return jedis.incr(key);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return 0;
    }

    /**
     * set hash key 可以有过期时间
     * @param db 库号
     * @param key 键
     * @param seconds 秒数
     * @param value 值
     * @return boolean
     */
    public boolean setex(int db, String key, String value, int seconds) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return true;
    }

    /**
     * 检查给定 key 是否存在
     * @param db 库号
     * @param key 件
     * @return boolean
     */
    public boolean exists(int db, String key) {
       
        Jedis jedis = null;
        try {
            
            jedis = getReadConnection();
            jedis.select(db);
            return jedis.exists(key);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    /**
     * 返回有序集 key 中成员 member 的排名
     * @param db
     * @param key
     * @param member
     * @return Long
     */
    public Long zrevrank(int db, String key, String member) {
       
        Jedis jedis = null;
        Long rank = 0L;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            rank = jedis.zrevrank(key, member);
            rank = rank == null ? 0 : rank.longValue();
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return rank;
    }

    /**
     * 返回有序集 key 中，指定区间内的成员
     * @param db 库号
     * @param key 键
     * @param start 0第一个元素 1 第二个元素-1最后一个元素 -2倒数第二个元素
     * @param end 0第一个元素 1 第二个元素-1最后一个元素 -2倒数第二个元素
     * @return Set<String>
     */
    public Set<String> zrevrange(int db, String key, long start, long end) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    /**
     * 批量处理：将多个 field-value (域-值)对设置到哈希表 key 中
     * @param db
     * @param key
     * @param value
     */
    public void batchHmset(int db, String key, List<Map<String, String>> value) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            Pipeline piple = jedis.pipelined();
            for (Map<String, String> map : value) {
                piple.hmset(key, map);
            }
            piple.sync();

        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 批量删除keys
     * @param db 库号
     * @param keys 键
     * @return boolean
     */
    public boolean batchDel(int db, List<String> keys) {
       
        Jedis jedis = null;
        try {
            
            jedis = getConnection();
            jedis.select(db);
            Pipeline piple = jedis.pipelined();
            for (String key : keys) {
                piple.del(key);
            }
            piple.sync();
            return true;
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    /**
     * 批量删除keys
     * @param db 库号
     * @param keys 键
     * @return boolean
     */
    public Set<String> keys(int db, String pattern) {
       
        Jedis jedis = null;
        try {
            
            jedis = getReadConnection();
            jedis.select(db);
            return jedis.keys(pattern);
        } catch (Exception e) {
            returnBrokenResource(jedis);
            log.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }

}

