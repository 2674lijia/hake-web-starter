package store.lijia.web.redis;

import lombok.SneakyThrows;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import store.lijia.web.redis.queue.*;
import store.lijia.web.redis.template.RedisListOperations;
import store.lijia.web.redis.template.RedisTemplateConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/11 下午2:02
*
 */
@EnableConfigurationProperties(RedisQueueProperties.class)
@ConditionalOnBean({org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class})
@ConditionalOnProperty(name = "hake.queue.enable", havingValue = "true")
@Import({RedisTemplateConfiguration.class})
public class RedisAutoConfiguration {

//    private RedisQueueProperties redisQueueProperties;
//
//    public void setRedisQueueProperties(RedisQueueProperties redisQueueProperties) {
//        this.redisQueueProperties = redisQueueProperties;
//    }

    @Bean
    public RedisQueueService redisQueueService(RedisQueueProperties redisQueueProperties, RedisListOperations<String, RedisQueueBody> redisListOperations) {
        return new RedisQueueServiceImpl(redisQueueProperties.getQueuePrefix(), redisListOperations);
    }

    @Bean
    public RedisQueueConsumerThread runQueuePullThread(RedisQueueProperties redisQueueProperties, List<? extends RedisQueueAbstractListener> listeners, RedisListOperations<String, RedisQueueBody> redisListOperations) {

        RedisQueueConsumerThread redisQueueConsumerThread = new RedisQueueConsumerThread(redisQueueProperties.getQueuePrefix(), redisListOperations, listeners, scanListener(redisQueueProperties));
        new Thread(redisQueueConsumerThread, "队列消费Thread").start();
        return redisQueueConsumerThread;
    }


    /**
     * 扫描注解监听器
     *
     * @return
     */
    @SneakyThrows
    private Map<String, List<Consumer<Object>>> scanListener(RedisQueueProperties redisQueueProperties) {
        Map<String, List<Consumer<Object>>> mapListener2 = new ConcurrentHashMap<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory();
        /**
         * 获取该包及其子包下的资源信息
         */
        Resource[] resources = resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + redisQueueProperties.getScanPackage().replace(".", "/") + "/**/*.class");
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        for (Resource resource : resources) {
            MetadataReader reader = metaReader.getMetadataReader(resource);
            String className = reader.getClassMetadata().getClassName();
            Class<?> clazz = loader.loadClass(className);
            /**
             * 排除不需要的clazz信息
             */
            if (clazz.getAnnotation(Component.class) == null)
                continue;
            Object instance = clazz.newInstance();
            /**
             * 获取所有方法
             */
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                /**
                 * 只处理带有RedisQueueConsumerListener注解的方法
                 */
                RedisQueueConsumerListener annotation = method.getAnnotation(RedisQueueConsumerListener.class);
                if (annotation != null) {
                    if (mapListener2.get(annotation.topic()) == null) {
                        List<Consumer<Object>> target = new ArrayList<>();
                        target.add(data -> {
                            try {
                                method.invoke(instance, data);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        mapListener2.put(annotation.topic(), target);
                    } else {

                        mapListener2.get(annotation.topic()).add(data -> {
                            try {
                                method.invoke(instance, data);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }
        }
        return mapListener2;
    }

}
