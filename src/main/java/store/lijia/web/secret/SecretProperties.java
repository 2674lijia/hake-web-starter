package store.lijia.web.secret;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午5:12
 * @company 杭州车凌网络科技有限公司
 * @address 杭州市滨江区聚光中心B座705
 */
@Data
@ConfigurationProperties(prefix = "hake.secret")
public class SecretProperties {
    /**
     * 是否启用参数加解密
     */
    private Boolean enable = false;

    /**
     * 忽略地址
     */
    @Deprecated
    private List<String> ignoreUrl;
}
