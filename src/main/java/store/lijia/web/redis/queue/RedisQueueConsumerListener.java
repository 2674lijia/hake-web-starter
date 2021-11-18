package store.lijia.web.redis.queue;

import java.lang.annotation.*;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/12 下午2:54
*
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisQueueConsumerListener {

    /**
     * 监听目标
     */
    String topic();
}
