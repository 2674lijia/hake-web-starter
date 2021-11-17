### hake-web-starter (java web快速启动)

#### redis队列

配置文件
```yaml
#使用reids队列需要连接redis服务
spring:
  redis:
    host: 192.168.0.178
    password: 123456
    database: 0
    
#redis队列配置
hake:
  queue:
    enable: true #是否开启  默认为true
    queue-prefix: REDIS_QUEUE #队列前缀KEY  默认为REDIS_QUEUE
    scan-package: store.lijia #注解、监听器扫描包  默认为store.lijia
```
使用实例
```java
@Component
public class RedisTest {
    
    @Autowired
    private RedisQueueService redisQueueService;

    public void joinQueue() {
        //加入队列
        redisQueueService.joinQueue(RedisQueueBody.builder().

                topic(ConsumerListener.class.getName()).

                data("new Date()").

                build());
    }
}


/**
 * 定义队列消费监听器
 * 实现RedisQueueAbstractListener 接口的方式消费数据
 */

@Component
public class ConsumerListener extends RedisQueueAbstractListener {

    public void onMessage(String str) {
        System.out.println(ConsumerListener.class.getName()+"开始消费：" + str);
    }

    public String getTopic() {
        return ConsumerListener.class.getName();
    }
}

/**
 * 使用注解``@RedisQueueConsumerListener``消费队列数据
 */
@Component
public class ConsumerListener {


    @RedisQueueConsumerListener(topic = "AAAA")
    public void onMessage(String data) {

        System.out.println("onMessage消费：" + data);
    }
}

```
