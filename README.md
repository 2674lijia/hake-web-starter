### hake-web-starter (java web快速启动)

#### 请求响应数据封装

使用`@JsonResultController`注解来代替`@RestController`，数据会自动封装到data下。\
`@JsonResultController`是一个聚合注解，包含了`@RestController`注解。\

Controller示例

```java

@RequestMapping("/info")
@JsonResultController
public class InfoController {

    @GetMapping("/massage")
    public String massage(@RequestParam("message") String message) {
        return "data:" + message;
    }
}

```

响应数据格式

```json
{
  "code": 200,
  "message": "",
  "data": "data:+message",
  "success": true,
  "excMessage": "错误异常信息"
}
```

#### 参数响应加密

使用3DES对称加密算法 ，并通过Base64进行字符串转换。\
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

注解介绍\
`@Decrypt`: 参数解密注解，作用在方法上和类上。被该注解修饰的类或方法则会将请求参数进行解密操作。\
`@Encrypt`: 响应数据加密，作用在方法上和类上。被该注解修饰的类或方法则会将相应结果数据进行加密操作。\
`@IgnoreDecrypt`: 忽略解密，作用在方法上。\
`@IgnoreEncrypt`: 忽略加密，作用在方法上。

使用示例

```java

import org.springframework.web.bind.annotation.RequestParam;
import store.lijia.web.secret.Decrypt;

/**
 * 使用了加密注解，该响应数据会被加密
 * 如果发送请求是将参数进行了加密则需要相应的解密
 */
@RequestMapping("/info")
@JsonResultController
@Encrypt
@Decrypt
public class InfoController {

    /**
     *该请求响应数据会加密.
     *该请求参数会自动解密
     */
    @GetMapping("/massage")
    public String massage(@RequestParam("message") String message) {
        return "data:" + message;
    }

    /**
     *
     * 使用了忽略加密注解，则该请求响应数据不会加密
     */
    @IgnoreEncrypt
    @GetMapping("/massage2")
    public String massage2() {
        return "data";
    }
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

#### redis队列使用

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

使用示例

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
 * 方式一
 * 定义队列消费监听器
 * 实现RedisQueueAbstractListener 接口的方式消费数据
 */

@Component
public class ConsumerListener extends RedisQueueAbstractListener {

    public void onMessage(String str) {
        System.out.println(ConsumerListener.class.getName() + "开始消费：" + str);
    }

    public String getTopic() {
        return ConsumerListener.class.getName();
    }
}

/**
 * 方式二
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
