package com.daop.sso.common.config.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @BelongsProject: demo
 * @BelongsPackage: com.daop.netty.common.config.redis
 * @Description:
 * @DATE: 2020-12-21
 * @AUTHOR: Administrator
 **/
@Configuration
public class RedisConfig {
    /**
     * 配置以String：Object类型的Redis缓存模板
     *
     * @param factory Redis连接工厂
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer=new FastJsonRedisSerializer<>(Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //key 采用 String 的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //hash 的 key 也采用 String 的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);

        //value 的序列化方式采用 JSON
        template.setValueSerializer(fastJsonRedisSerializer);
        //hash value 的序列化方式也采用 JSON
        template.setHashValueSerializer(fastJsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }


}
