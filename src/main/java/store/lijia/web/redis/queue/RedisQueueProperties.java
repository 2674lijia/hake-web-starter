package store.lijia.web.redis.queue;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/11 下午4:58
*
 */
@Data
@ConfigurationProperties("hake.queue")
public class RedisQueueProperties {

    /**
     * 队列前缀key
     */
    private String queuePrefix = "REDIS_QUEUE";

    /**
     * 是否开启队列
     */
    private boolean enable = true;

    /**
     * 扫描包，使用注解时必要
     */
    private String scanPackage = "store.lijia";
}
