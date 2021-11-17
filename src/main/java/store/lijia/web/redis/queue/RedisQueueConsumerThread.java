package store.lijia.web.redis.queue;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.lijia.web.redis.template.RedisListOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/11 下午3:06
 * @company 杭州车凌网络科技有限公司
 * @address 杭州市滨江区聚光中心B座705
 */
public class RedisQueueConsumerThread implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(RedisQueueServiceImpl.class);

    private RedisListOperations<String, RedisQueueBody> redisListOperations;

    private String queueKey;

    private Map<String, ? extends List<? extends RedisQueueAbstractListener>> mapListener1;

    private Map<String, List<Consumer<Object>>> mapListener2;

    public RedisQueueConsumerThread(String queueKey
            , RedisListOperations<String, RedisQueueBody> redisListOperations
            , List<? extends RedisQueueAbstractListener> listeners
            , Map<String, List<Consumer<Object>>> mapListener2) {
        this.queueKey = queueKey;
        this.redisListOperations = redisListOperations;
        this.mapListener1 = Optional.ofNullable(listeners.stream().collect(Collectors.groupingBy(RedisQueueAbstractListener::getTopic)))
                .orElse(Collections.emptyMap());
        this.mapListener2 = Optional.ofNullable(mapListener2).orElse(Collections.emptyMap());

    }


    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            RedisQueueBody body = redisListOperations.leftPop(queueKey, 5, TimeUnit.SECONDS);
            if (null == body) {
                logger.info("Redis队列心跳");
                continue;
            }
            boolean flag = true;
            /**
             * 类
             */
            if (!mapListener1.isEmpty() && mapListener1.get(body.getTopic()) != null) {
                mapListener1.get(body.getTopic()).forEach(item -> item.onMessage(body.getData()));
                flag = false;
            }
            /**
             * 注解
             */
            if (!mapListener2.isEmpty() && mapListener2.get(body.getTopic()) != null) {
                mapListener2.get(body.getTopic()).forEach(item -> item.accept(body.getData()));
                flag = false;
            }
            if (flag) {
                logger.warn("没有找到topic:[{}]的监听器！", body.getTopic());
            }
        }
    }


}
