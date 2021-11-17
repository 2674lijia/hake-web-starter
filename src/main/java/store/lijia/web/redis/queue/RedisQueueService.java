package store.lijia.web.redis.queue;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/11 下午2:16
 * @company 杭州车凌网络科技有限公司
 * @address 杭州市滨江区聚光中心B座705
 */
public interface RedisQueueService {

    /**
     * 加入队列
     *
     * @param redisQueueBody 处理数据
     * @return
     */
    boolean joinQueue(RedisQueueBody redisQueueBody);

    /**
     * 移除队列
     *
     * @param topic 主题
     */
    @Deprecated
    default void removeQueue(String topic) {
    }

}
