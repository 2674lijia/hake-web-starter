package store.lijia.web.redis.queue;

import lombok.*;

import java.io.Serializable;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/11 下午2:04
*
 */
@NoArgsConstructor
@Data
public class RedisQueueBody  implements Serializable {

    private static final long serialVersionUID = 8841433872811285796L;

    private String topic;

    private String data;


    @Builder
    public RedisQueueBody( String topic, String data) {
        this.topic = topic;
        this.data = data;
    }
}
