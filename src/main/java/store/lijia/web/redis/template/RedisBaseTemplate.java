package store.lijia.web.redis.template;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/12 上午10:52
*
 */
public class RedisBaseTemplate<K, V> extends RedisTemplate<K, V> {

    public RedisBaseTemplate(RedisConnectionFactory redisConnectionFactory) {
        setConnectionFactory(redisConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        // key序列化
        setKeySerializer(stringSerializer);
        // value序列化
        setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // Hash key序列化
        setHashKeySerializer(stringSerializer);
        // Hash value序列化
        setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
    }
}
