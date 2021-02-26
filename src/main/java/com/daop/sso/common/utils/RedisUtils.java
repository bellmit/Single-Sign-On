package com.daop.sso.common.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: iot
 * @BelongsPackage: com.xinye.iot.common.utils.redis
 * @Description: Redis工具类
 * @DATE: 2020-09-23
 * @AUTHOR: Administrator
 **/
@Component
@Configuration
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /*=============================common=============================*/

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间（秒）
     * @return 返回True/False
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取Key的过期时间
     *
     * @param key 键
     * @return 时间（秒） 0为永久有效
     */
    public long getExpire(String key) {
        return Objects.requireNonNull(redisTemplate.getExpire(key, TimeUnit.SECONDS));
    }

    /**
     * 判断Key是否存在
     *
     * @param key 键
     * @return true 存在；false 不存在
     */
    public boolean hasKey(String key) {
        try {
            return Objects.requireNonNull(redisTemplate.hasKey(key));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除指定缓存
     *
     * @param key 一个或多个键名
     * @return true：成功；false：失败
     */
    public boolean del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                return  Objects.requireNonNull(redisTemplate.delete(key[0]));
            } else {
                return  Objects.requireNonNull(redisTemplate.delete(Arrays.toString(key)));
            }
        }
        return false;
    }

    public long del(Set<String> keys) {
        return  Objects.requireNonNull(redisTemplate.delete(keys));
    }

    /*=============================String=============================*/

    /**
     * 缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true 成功；false 失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 缓存放入并设置有效时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间（秒） time大于0，若time小于0，将设置无限期
     * @return true：成功；false：失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加多少（大于0）
     * @return 返回值
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return Objects.requireNonNull(redisTemplate.opsForValue().increment(key, delta));
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少多少（大于0）
     * @return 返回值
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return Objects.requireNonNull(redisTemplate.opsForValue().increment(key, -delta));
    }
    /*=============================Map================================*/

    /**
     * HashSet：将Hash表放入缓存中，如果没有则创建。
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true：成功；false：失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet：向Hash中存放数据并添加有效时间
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间（秒） 注意：如已存在在的Hash表中又时间，将会覆盖原有的
     * @return true：成功；false：失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashMapSet：将一个Map集合添加到缓存中
     *
     * @param key 值
     * @param map 对应的多个键值
     * @return true：成功；false：失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashMapSet：将一个Map集合添加到缓存中并添加过期时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间（秒）
     * @return true：成功；false：失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashGet:根据字段和项获取Hash表指定的值
     *
     * @param key  键 不能为Null
     * @param item 项 不能为Null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * HashMapGet：获取HashKey对应的所有Map集合键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashDelete：删除Hash表中的值
     *
     * @param key  键 不能为Null
     * @param item 项 可以是多个，但不能为Null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * HashHasKey：判断Hash表中是否有该项的值
     *
     * @param key  值 不能为Null
     * @param item 项 不能为Null
     * @return true：存在；false：不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * Hash递增：如果没有就会创建一个并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加的数量（大于0）
     * @return 返回值
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * Hash递减：
     *
     * @param key  键
     * @param item 项
     * @param by   要减少的数量（小于0）
     * @return 返回值
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }


    /*=============================Set================================*/

    /**
     * SetGet：根据Key获取Set中所有值
     *
     * @param key 键
     * @return 返回值
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * SetHasKey：查询指定键中是否存在指定的值
     *
     * @param key   键
     * @param value 值
     * @return true：存在；false：不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return Objects.requireNonNull(redisTemplate.opsForSet().isMember(key, value));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * SetSet：把数据放入到Set缓存中
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return Objects.requireNonNull(redisTemplate.opsForSet().add(key, values));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * SetSet：把数据放入到Set缓存中并设置有效时间
     *
     * @param key    键
     * @param time   时间（秒）有效时间
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, long time, Object... values) {
        try {
            Long count = Objects.requireNonNull(redisTemplate.opsForSet().add(key, values));
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * SetGetSetSize：根据键查询Set缓存的长度
     *
     * @param key 键
     * @return Set缓存的长度
     */
    public long sGetSetSize(String key) {
        try {
            return Objects.requireNonNull(redisTemplate.opsForSet().size(key));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * SetRemove：移除键中的值
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            return Objects.requireNonNull(redisTemplate.opsForSet().remove(key, values));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
    /*=============================List===============================*/

    /**
     * ListGet：获取List缓存中的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0到-1代表所有值
     * @return 返回值
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ListGetListSize：获取List缓存的长度
     *
     * @param key 键
     * @return 长度
     */
    public long lGetListSize(String key) {
        try {
            return Objects.requireNonNull(redisTemplate.opsForList().size(key));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * ListGetIndex：根据索引获取List的值
     *
     * @param key   键
     * @param index 索引
     * @return 返回值
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ListSet：将值放入List缓存
     *
     * @param key   键
     * @param value 值
     * @return 返回值
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ListSet：将值放入List缓存并设置有效时间
     *
     * @param key   键
     * @param value 值
     * @return 返回值
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ListSet：将List放入缓存
     *
     * @param key    键
     * @param values 值
     * @return 返回值
     */
    public boolean lSet(String key, List<Object> values) {
        try {
            redisTemplate.opsForList().rightPushAll(key, values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ListSet：把List放入到缓存中并设置有效时间
     *
     * @param key    键
     * @param values 值
     * @param time   有效时间（秒）
     * @return 返回值
     */
    public boolean lSet(String key, List<Object> values, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ListUpdateIndex：根据索引修改List中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return 返回值
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ListRemove：移除N个value值
     *
     * @param key   键
     * @param count count>0：从List头部向尾部搜索并移除{@code count}个{@code value}；
     *              count<0：从List尾部向头部搜索并移除{@code count}个{@code value}，；
     *              count=0：移除表中所有的{@code value}；
     * @param value 要移除的值
     * @return 返回值
     */
    public long lRemove(String key, long count, Object value) {
        try {
            return Objects.requireNonNull(redisTemplate.opsForList().remove(key, count, value));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true 成功；false 失败
     */
    public boolean setAndDB(String key, Object value, Integer dbIndex) {
        try {
            set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Set<String> keys(String prefix) {
        return redisTemplate.keys(prefix);
    }


}
