### hake-web-starter (java web快速启动)

### 整合secret参数响应加密
使用3DES对称加密算法 \
yaml配置说明
```yaml
hake:
  secret:
    enable: true #是否开启 默认为false
    three-des:
      secret-key: 123456789101212121212121212 #秘钥
      des-salt: 12345678 #向量、盐值。需8位
      key-algorithm: DESede #秘钥key算法 默认为 DESede
      cipher-algorithm: DESede/CBC/PKCS5Padding #秘钥加密算法 默认为：DESede/CBC/PKCS5Padding
```
使用实例
```java
/**
 * 参数解密注解
 * 被该注解修饰的类或方法则会将请求参数进行解密操作
 * @author lijia
 * @version 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Decrypt {

}
```
#### swagger整合
yaml配置说明
```yaml
hake:
  swagger:
    enable: true #是否开启
    group-name: swagger #组名
    version: 1.0 #版本
    description: 描述 #描述
    title: api #标题
    author:
      name: lijia #作者信息
      email: 177771@qq.com
    parameter:
      parameter-name: authorization #增加参数 ，默认为authorization
      parameter-type: header #参数类型 默认为header
      required: false #是否必须 默认为false
      description: 请求头token #参数描述 默认为`请求头token`
```


#### jackson全局配置
默认时间日期格式为 ``yyyy-MM-dd HH:mm:ss``\
Long类型转换 ``Long ---> String``

#### redis队列

yaml配置说明
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
