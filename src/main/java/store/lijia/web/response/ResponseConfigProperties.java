package store.lijia.web.response;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午2:04
*
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "hake.web.response")
public class ResponseConfigProperties {

    private String[] scanPackages;


}
