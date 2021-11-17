package store.lijia.web.redis.template;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
@ConditionalOnBean(RedisConnectionFactory.class)
public class RedisTemplateConfiguration extends CachingConfigurerSupport {

    /**
     * 选择redis作为默认缓存工具
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheManager rcm = RedisCacheManager.create(connectionFactory);
        return rcm;
    }

    @Bean
    public <K,V> RedisBaseTemplate<K,V> redisBaseTemplate(RedisConnectionFactory redisConnectionFactory){
        return new RedisBaseTemplate<K,V>(redisConnectionFactory);
    }

    @Bean
    public <K,V> RedisListOperations<K,V> redisListOperations(RedisBaseTemplate<K,V> redisBaseTemplate){
        return new RedisListOperations<K,V>(redisBaseTemplate);
    }

    @Bean
    public <K,V> RedisValueOperations<K,V> redisValueOperations(RedisBaseTemplate<K,V> redisBaseTemplate){
        return new RedisValueOperations<K,V>(redisBaseTemplate);
    }

}

