package store.lijia.web.redis.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.lijia.web.redis.template.RedisListOperations;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/11 下午2:21
 * @company 杭州车凌网络科技有限公司
 * @address 杭州市滨江区聚光中心B座705
 */
public class RedisQueueServiceImpl implements RedisQueueService {
    private final static Logger logger = LoggerFactory.getLogger(RedisQueueServiceImpl.class);

    private RedisListOperations<String, RedisQueueBody> redisListOperations;

    private String queuePrefix;


    public RedisQueueServiceImpl(String queuePrefix, RedisListOperations<String, RedisQueueBody> redisListOperations) {
        this.queuePrefix = queuePrefix;
        this.redisListOperations = redisListOperations;
    }


    @Override
    public boolean joinQueue(RedisQueueBody redisQueueBody) {
        AtomicLong atomicLong = new AtomicLong();
        RedisQueueThreadPool.getInstance().execute(() -> atomicLong.set(redisListOperations.rightPush(queuePrefix, redisQueueBody)));
        return atomicLong.get() > 0;
    }

}
