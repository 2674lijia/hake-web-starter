package store.lijia.web.redis.queue;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/11 下午2:06
*
 */
public abstract class RedisQueueAbstractListener {

    /**
     * 消息
     *
     * @param body json格式字符串
     */
    public abstract void onMessage(String  body);

    /**
     *
     * @return
     */
    public abstract String getTopic();

}
