package store.lijia.web.response;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午2:04
 * @company 杭州车凌网络科技有限公司
 * @address 杭州市滨江区聚光中心B座705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "hake.web.response")
public class ResponseConfigProperties {

    private String[] scanPackages;


}
