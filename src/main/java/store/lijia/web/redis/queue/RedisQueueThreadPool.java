package store.lijia.web.redis.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/11 下午2:52
 * @company 杭州车凌网络科技有限公司
 * @address 杭州市滨江区聚光中心B座705
 */
public class RedisQueueThreadPool {


    private static final Integer CORE_POOL_SIZE = 30;
    private static final Integer MAXIMUM_POOL_SIZE = 60;
    private static final Long KEEP_ALIVE_TIME_SECOND = 30L;
    private static final Integer QUEUE_CAPACITY = 1000;

    private final static ThreadPoolExecutor threadPoolExecutor;

    private static RedisQueueThreadPool redisQueueThreadPool;

    public static RedisQueueThreadPool getInstance() {
        synchronized (RedisQueueThreadPool.class) {
            if (redisQueueThreadPool == null) {
                return new RedisQueueThreadPool();
            }
        }
        return redisQueueThreadPool;

    }

    static {
        threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME_SECOND, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(QUEUE_CAPACITY),
                (r, executor) -> {
                    throw new RuntimeException("redis queue pool is rejected!");
                }
        );
    }

    private RedisQueueThreadPool() {

    }

    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }
}
