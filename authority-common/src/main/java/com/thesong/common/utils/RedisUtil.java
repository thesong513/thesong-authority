package com.thesong.common.utils;

/**
 * @Author thesong
 * @Date 2020/7/22 14:45
 * @Version 1.0
 */

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class RedisUtil {

    private Jedis jedis;

    public RedisUtil(){

        String ip = Config.get("redis");
        int port = Integer.parseInt(Config.get("port"));
        String password = Config.get("password");
        JedisPool pool = getPool(ip, port, password);
        jedis=pool.getResource();
    }

    public static JedisPool getPool(String ip, int port, String password){
        JedisPool pool=null;
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(Integer.parseInt("50"));
        config.setMaxTotal(Integer.parseInt("1000"));
        config.setMaxWaitMillis((long)Integer.parseInt("3000"));
        config.setTestOnBorrow(Boolean.parseBoolean("true"));
        config.setTestOnReturn(Boolean.parseBoolean("true"));
        config.setTimeBetweenEvictionRunsMillis(-1L);
        pool=new JedisPool(config,ip,port,2000,password);
        return pool;
    }

    public String get(String key){
        return jedis.get(key);
    }
    public String set(String key,String value){
        return jedis.set(key,value);
    }
    public Long del(String... keys){
        return jedis.del(keys);
    }

    /**
     * 通过key向指定的value追加值
     * @param key
     * @param str
     * @return
     */
    public Long append(String key,String str){
        return jedis.append(key,str);
    }
    public Boolean exist(String key){
        return jedis.exists(key);
    }

    /**
     * 设置key-value，若key已存在返回0 则返回1
     * @param key
     * @param value
     * @return
     */
    public Long setnx(String key,String value){
        return jedis.setnx(key,value);
    }

    /**
     *  设置key-value并指定键值的有效期
     * @param key
     * @param seconds 以秒为单位
     * @param value
     * @return
     */
    public String setex(String key,int seconds,String value){
        return jedis.setex(key,seconds,value);
    }

    /**
     * 从指定的位置替换原先value  aa 1 tt ->atta
     * 值不存在也会有空格   ee 1 uu->" uu"
     * @param key
     * @param offset
     * @param str
     * @return
     */
    public Long setrange(String key,int offset,String str){
        return jedis.setrange(key,offset,str);
    }

    /**
     * 通过批量keys获取批量values
     * @param keys
     * @return
     */
    public List<String> mget(String... keys){
        return jedis.mget(keys);
    }

    /**
     * 批量设置keyvalues，也可以一个 "key","value",..
     * @param keyvalues
     * @return
     */
    public String mset(String... keyvalues){
        return jedis.mset(keyvalues);
    }

    /**
     * 批量设置keyvalues，也可以一个 如果key存在则会失败，操作回滚
     * @param keyvalues
     * @return
     */
    public Long msetnx(String... keyvalues){
        return jedis.msetnx(keyvalues);
    }

    /**
     * 设置key的值，返回一个旧值，key不存在，也会设置
     * @param key
     * @param value
     * @return
     */
    public String getSet(String key,String value){
        return jedis.getSet(key,value);
    }

    /**
     * 通过key和指定下标获取指定下标的值
     * @param key
     * @param startOffset
     * @param endOffset
     * @return
     */
    public String getrange(String key,int startOffset,int endOffset){
        return jedis.getrange(key,startOffset,endOffset);
    }

    /**
     * 通过key对value加1操作，如果value不是int类型会返回错误，如果key不存在，value为1
     * @param key
     * @return
     */
    public Long incr(String key){
        return jedis.incr(key);
    }

    /**
     * 通过key对value加指定的值，如果value不是int类型会返回错误，若key不存在，value为该值
     * @param key
     * @param integer
     * @return
     */
    public Long incrBy(String key,int integer){
        return jedis.incrBy(key,integer);
    }
    /**
     * 通过key对value减去1操作，如果value不是int类型会返回错误，如果key不存在，value为-1
     * @param key
     * @return
     */
    public Long decr(String key){
        return jedis.decr(key);
    }

    /**
     * 通过key对value减去指定的值，如果value不是int类型会返回错误，若key不存在，value为该值
     * @param key
     * @param integer
     * @return
     */
    public Long decrBy(String key,int integer){
        return jedis.decrBy(key,integer);
    }

    /**
     * 通过key获取value的长度
     * @param key
     * @return
     */
    public Long StrLen(String key){
        return jedis.strlen(key);
    }

    /**
     * 通过key给field设置指定的值，若key不存在则先创建，若field已存在，返回0
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hsetnx(String key,String field,String value){
        return jedis.hsetnx(key,field,value);
    }

    /**
     * 通过key给field设置指定的值，若key不存在则先创建
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hset(String key,String field,String value){
        return jedis.hset(key,field,value);
    }

    /**
     * 通过key同时设置多个field
     * @param key
     * @param hash
     * @return
     */
    public String hmset(String key, Map<String,String> hash){
        return jedis.hmset(key,hash);
    }

    /**
     * 通过key和field获取指定的值
     * @param key
     * @param field
     * @return
     */
    public String hget(String key,String field){
        return jedis.hget(key,field);
    }

    /**
     * 设置key的过期时间秒
     * @param key
     * @param seconds
     * @return
     */
    public Long expirt(String key,int seconds){
        return jedis.expire(key,seconds);
    }

    /**
     * 通过key和fields获取指定的value，如果没有value，返回null
     * @param key
     * @param field
     * @return
     */
    public List<String> hmget(String key,String... field){
        return jedis.hmget(key,field);
    }

    /**
     * 通过key给指定的field加指定的值
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hincBy(String key,String field,Long value){
        return jedis.hincrBy(key,field,value);
    }

    /**
     * 通过key和field判断指定的value是否存在
     * @param key
     * @param field
     * @return
     */
    public Boolean hexist(String key,String field){
        return jedis.hexists(key,field);
    }

    /**
     * 通过key返回field数量
     * @param key
     * @return
     */
    public Long hlen(String key){
        return jedis.hlen(key);
    }
    /**
     * 通过key删除指定field
     * @param key
     * @param field 一个 也可以是数组
     * @return
     */
    public Long hdel(String key,String... field){
        return jedis.hdel(key,field);
    }

    /**
     * 通过field获取所有field
     * @param key
     * @return
     */
    public Set<String> hkeys(String key){
        return jedis.hkeys(key);
    }

    /**
     * 通过key获取和key有关的value
     * @param key
     * @return
     */
    public List<String> hvals(String key){
        return jedis.hvals(key);
    }

    /**
     * 通过key获取有关的field和value
     * @param key
     * @return
     */
    public Map<String,String> hgetall(String key){
        return jedis.hgetAll(key);
    }

    /**
     * 通过key向list头部添加字符串
     * @param key
     * @param strs
     * @return
     */
    public Long lpush(String key,String... strs){
        return jedis.lpush(key,strs);
    }

    /**
     * 通过key向list尾部添加字符串
     * @param key
     * @param strs
     * @return
     */
    public Long rpush(String key,String... strs){
        return jedis.rpush(key,strs);
    }

    /**
     * 通过key向list指定位置添加字符串
     * @param key
     * @param where LIST_POSITION 枚举类型
     * @param pivot list内的value
     * @param value
     * @return
     */
    public Long linsert(String key, BinaryClient.LIST_POSITION where,String pivot,String value){
        return jedis.linsert(key,where,pivot,value);
    }

    /**
     * 通过key设置list指定下标位置value，越界报错
     * @param key
     * @param index
     * @param value
     * @return
     */
    public String lset(String key,Long index,String value){
        return jedis.lset(key,index,value);
    }

    /**
     * 通过key删除count个与value相同的元素
     * @param key
     * @param count
     * @param value
     * @return
     */
    public Long lrem(String key,long count,String value){
        return jedis.lrem(key,count,value);
    }

    /**
     * 通过key保留下标start到end的value
     * @param key
     * @param start
     * @param end
     * @return
     */
    public String ltrim(String key,long start,long end){
        return jedis.ltrim(key,start,end);
    }

    /**
     * 通过key删除头部一个value，返回value
     * @param key
     * @return
     */
    public String lpop(String key){
        return jedis.lpop(key);
    }
    /**
     * 通过key删除尾部一个value，返回value
     * @param key
     * @return
     */
    public String rpop(String key){
        return jedis.rpop(key);
    }

    /**
     * 通过key删除list尾部一个value，添加到另一个list的头部，返回该value
     * list为空或不存在返回null
     * @param srckey
     * @param dstkey
     * @return
     */
    public String rpoplpush(String srckey,String dstkey){
        return jedis.rpoplpush(srckey,dstkey);
    }

    /**
     * 通过key获取list指定下标的value
     * @param key
     * @param index
     * @return
     */
    public String lindex(String key,long index){
        return jedis.lindex(key,index);
    }

    /**
     * 通过key获取list的长度
     * @param key
     * @return
     */
    public Long llen(String key){
        return jedis.llen(key);
    }

    /**
     * 通过key获取指定下标位置的value
     * 若start为0，end为-1，返回所有value
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key,long start,long end){
        return jedis.lrange(key,start,end);
    }

    /**
     * 通过key向set添加value
     * @param key
     * @param members
     * @return
     */
    public Long sadd(String key,String... members){
        return jedis.sadd(key,members);
    }

    /**
     * 通过key删除set对应的value值
     * @param key
     * @param members
     * @return
     */
    public Long srem(String key,String... members){
        return jedis.srem(key,members);
    }

    /**
     * 通过key随机删除set一个value
     * @param key
     * @return
     */
    public String spop(String key){
        return jedis.spop(key);
    }

    /**
     * 通过key获取set中的差集
     * 以第一个为标准
     * @param keys
     * @return
     */
    public Set<String> sdiff(String... keys){
        return jedis.sdiff(keys);
    }

    /**
     * 通过key获取set中差集并存入另一set中
     * @param dstkey
     * @param keys
     * @return
     */
    public Long sdiffstore(String dstkey,String... keys){
        return jedis.sdiffstore(dstkey,keys);
    }

    /**
     * 通过key获取set中交集
     * @param keys
     * @return
     */
    public Set<String> sinter(String... keys){
        return jedis.sinter(keys);
    }
    /**
     * 通过key获取set中交集并存入另一set中
     * @param dstkey
     * @param keys
     * @return
     */
    public Long sinterstore(String dstkey,String... keys){
        return jedis.sinterstore(dstkey,keys);
    }
    /**
     * 通过key获取set中并集
     * @param keys
     * @return
     */
    public Set<String> sunion(String... keys){
        return jedis.sunion(keys);
    }
    /**
     * 通过key获取set中并集并存入另一set中
     * @param dstkey
     * @param keys
     * @return
     */
    public Long sunionstore(String dstkey,String... keys){
        return jedis.sunionstore(dstkey,keys);
    }

    /**
     * 通过key将set中的value移除并把该value值添加到第二个set中
     * @param srckey
     * @param dstkey
     * @param member
     * @return
     */
    public Long smove(String srckey,String dstkey,String member){
        return jedis.smove(srckey,dstkey,member);
    }

    /**
     * 通过key获取set中value个数
     * @param key
     * @return
     */
    public Long scard(String key){
        return jedis.scard(key);
    }

    /**
     * 通过key判断value是否是set中元素
     * @param key
     * @param member
     * @return
     */
    public Boolean sismember(String key,String member){
        return jedis.sismember(key,member);
    }

    /**
     * 通过key随机获取set中的值
     * @param key
     * @return
     */
    public String srandmember(String key){
        return jedis.srandmember(key);
    }

    /**
     * 通过key获取set所有元素
     * @param key
     * @return
     */
    public Set<String> smembers(String key){
        return jedis.smembers(key);
    }

    /**
     * 通过key的zset中添加value，score，其中score是用来排序的
     * 若该value已存在则根据score更新元素
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Long zadd(String key,double score,String member){
        return jedis.zadd(key,score,member);
    }

    /**
     * 通过key删除在zset中指定的value
     * @param key
     * @param member
     * @return
     */
    public Long zrem(String key,String... member){
        return jedis.zrem(key,member);
    }

    /**
     * 通过key增加该value中score值
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Double zincrby(String key,double score,String member){
        return jedis.zincrby(key,score,member);
    }

    /**
     * 通过key返回zset中的排名
     * 下标从小到大排序
     * @param key
     * @param member
     * @return
     */
    public Long zrank(String key,String member){
        return jedis.zrank(key,member);
    }

    /**
     * 通过key返回zset中的排名
     *  下标从大道小排序
     * @param key
     * @param member
     * @return
     */
    public Long zrevrank(String key,String member){
        return jedis.zrevrank(key,member);
    }
    /**
     * 通过key获取zset中start到end的value，score从大到小排序
     *  start为0 end为-1返回全部
     * @param key
     * @param start
     * @return
     */
    public Set<String> zrevrange(String key,long start,long end){
        return jedis.zrevrange(key,start,end);
    }
    /**
     * 通过key获取zset中指定score内的value
     * @param key
     * @param max
     * @return
     */
    public Set<String> zrangeByScore(String key,String max,String min){
        return jedis.zrangeByScore(key,max,min);
    }
    /**
     * 通过key获取zset中指定score内的value
     * @param key
     * @param max
     * @return
     */
    public Set<String> zrangeByScore(String key,double max,double min){
        return jedis.zrangeByScore(key,max,min);
    }
    /**
     * 通过key获取zset中指定区间内的value数量
     * @param key
     * @param max
     * @return
     */
    public Long zcount(String key,String max,String min){
        return jedis.zcount(key,max,min);
    }
    /**
     * 通过key获取zset中value数量
     * @param key
     * @return
     */
    public Long zcard(String key){
        return jedis.zcard(key);
    }

    /**
     * 通过key获取zset中value的score值
     * @param key
     * @param member
     * @return
     */
    public Double zscore(String key,String member){
        return jedis.zscore(key,member);
    }
    /**
     * 通过key删除指定区间的元素
     * @param key
     * @param start
     * @return
     */
    public Long zremrangeByRank(String key,long start,long end){
        return jedis.zremrangeByRank(key,start,end);
    }
    /**
     * 通过key删除指定score的元素
     * @param key
     * @param start
     * @return
     */
    public Long zremrangeByScore(String key,double start,double end){
        return jedis.zremrangeByScore(key,start,end);
    }

    /**
     * 通过key存储是什么类型
     * @param key
     * @return
     */
    public String type(String key){
        return jedis.type(key);
    }
    /**
     * 返回满足pattern表达式的所有key
     * keys("*") 返回所有的key
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern){
        return jedis.keys(pattern);
    }
}
