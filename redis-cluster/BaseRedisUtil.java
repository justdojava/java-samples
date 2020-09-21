package com.coocaa.ad.common.tools.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 类AbRedisConfiguration实现描述:
 *
 * @author siberxu
 * @date 2019年05月11 09:31
 */
@Slf4j
@Getter
public abstract class BaseRedisUtil {

    protected RedisTemplate template;

    /**
     * 动态切换 database
     * @param dbIndex
     */
    public void selectDatabase(int dbIndex){

        LettuceConnectionFactory connectionFactory = (LettuceConnectionFactory)getTemplate().getConnectionFactory();
        connectionFactory.setDatabase(dbIndex);
        connectionFactory.resetConnection();
        getTemplate().setConnectionFactory(connectionFactory);
    }

    /**
     * 指定缓存失效时间
     *
     * @param key
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                getTemplate().expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("redis expire() is error");
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {

        return getTemplate().getExpire(key, TimeUnit.SECONDS);
    }


    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return getTemplate().hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("redis hasKey() is error");
            return false;
        }
    }

    /**
     *  删除KEY
     * @param key
     * @return
     */
    public boolean delete(String key) {
        try {
            return getTemplate().delete(key);
        } catch (Exception e) {
            log.error("redis hasKey() is error");
            return false;
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {

        return key == null ? null : getTemplate().opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {

        try {
            getTemplate().opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("redis set() is error");
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                getTemplate().opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("redis set() is error");
            return false;
        }
    }

    /**
     * 计数器
     *
     * @param key 键
     * @return 值
     */
    public Long incr(String key) {

        return getTemplate().opsForValue().increment(key);
    }

    public Long incrBy(String key, long step) {

        return getTemplate().opsForValue().increment(key, step);
    }

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {

        return getTemplate().opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {

        return getTemplate().opsForHash().entries(key);
    }

    /**
     * 获取hashKey对应的批量键值
     * @param key
     * @param values
     * @return
     */
    public List<Object> hmget(String key, List<String> values) {

        return getTemplate().opsForHash().multiGet(key, values);
    }


    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            getTemplate().opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("redis hmset() is error");
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            getTemplate().opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("redis hmset() is error");
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            getTemplate().opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("redis hset() is error");
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            getTemplate().opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("redis hset() is error");
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {

        getTemplate().opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {

        return getTemplate().opsForHash().hasKey(key, item);
    }

    /**
     * hash递增1 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @return
     */
    public double hincr(String key, String item) {

        return getTemplate().opsForHash().increment(key, item, 1);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by) {

        return getTemplate().opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return getTemplate().opsForHash().increment(key, item, -by);
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return getTemplate().opsForSet().members(key);
        } catch (Exception e) {
            log.error("redis sGet() is error");
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return getTemplate().opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("redis sHasKey() is error");
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return getTemplate().opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("redis sSet() is error");
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = getTemplate().opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            log.error("redis sSetAndTime() is error");
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return getTemplate().opsForSet().size(key);
        } catch (Exception e) {
            log.error("redis sGetSetSize() is error");
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = getTemplate().opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            log.error("redis setRemove() is error");
            return 0;
        }
    }

    /**
     * 实现命令：LPOP key，移除并返回列表 key的头元素。
     *
     * @param key KEY
     * @return 列表key的头元素。
     */
    public String lpop(String key) {

        return (String) getTemplate().opsForList().leftPop(key);
    }

    /**
     * 实现命令：LPOP key，移除并返回列表 key的尾元素。
     *
     * @param key KEY
     * @return 列表key的尾元素。
     */
    public String rpop(String key) {

        return (String) getTemplate().opsForList().rightPop(key);
    }

    /**
     * 实现命令：LPUSH key value，将一个值 value插入到列表 key的表头
     *
     * @param key   KEY
     * @param value VALUE
     * @return 执行 LPUSH命令后，列表的长度
     */
    public long lpush(String key, String value) {

        return getTemplate().opsForList().leftPush(key, value);
    }

    /**
     * 实现命令：RPUSH key value，将一个值 value插入到列表 key的表尾(最右边)。
     *
     * @param key   key
     * @param value VALUE
     * @return 执行 LPUSH命令后，列表的长度
     */
    public long rpush(String key, Object value) {

        return getTemplate().opsForList().rightPush(key, value);
    }

    /**
     * 从列表尾移动列表头,并返回该值
     * @param source
     * @param destination
     * @return
     */
    public Object RPOPLPUSH(String source, String destination) {

        return getTemplate().opsForList().rightPopAndLeftPush(source, destination);
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return getTemplate().opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("redis lGet() is error");
            return null;
        }
    }

    /**
     * 获取list缓存的内容
     *
     * @param key 键
     * @return
     */
    public List<Object> lGet(String key) {
        return lGet(key, 0, -1);
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return getTemplate().opsForList().size(key);
        } catch (Exception e) {
            log.error("redis lGetListSize() is error");
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return getTemplate().opsForList().index(key, index);
        } catch (Exception e) {
            log.error("redis lGetIndex() is error");
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            getTemplate().opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("redis lSet() is error");
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            getTemplate().opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("redis lSet() is error");
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            getTemplate().opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("redis lSet() is error");
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            getTemplate().opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("redis lSet() is error");
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            getTemplate().opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("redis lUpdateIndex() is error");
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = getTemplate().opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            log.error("redis lRemove() is error");
            return 0;
        }
    }


    /**
     * 原子自增
     *
     * @param key
     * @return
     */
    public long generate(String key) {
        RedisAtomicLong counter = new RedisAtomicLong(key, getTemplate().getConnectionFactory());
        return counter.incrementAndGet();
    }

    /**
     * A原子自增
     *
     * @param key
     * @param expireTime
     * @return
     */
    public long generate(String key, Date expireTime) {
        RedisAtomicLong counter = new RedisAtomicLong(key, getTemplate().getConnectionFactory());
        counter.expireAt(expireTime);
        return counter.incrementAndGet();
    }

    /**
     * 原子自增
     *
     * @param key
     * @param increment
     * @return
     */
    public long generate(String key, int increment) {
        RedisAtomicLong counter = new RedisAtomicLong(key, getTemplate().getConnectionFactory());
        return counter.addAndGet(increment);
    }

    /**
     * 原子自增
     *
     * @param key
     * @param increment
     * @param expireTime
     * @return
     */
    public long generate(String key, int increment, Date expireTime) {
        RedisAtomicLong counter = new RedisAtomicLong(key, getTemplate().getConnectionFactory());
        counter.expireAt(expireTime);
        return counter.addAndGet(increment);
    }
}
