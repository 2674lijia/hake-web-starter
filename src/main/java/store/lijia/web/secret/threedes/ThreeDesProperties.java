package store.lijia.web.secret.threedes;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午4:59
*
 */
@Data
@ConfigurationProperties(prefix = "hake.secret.three-des")
public class ThreeDesProperties {
    /**
     * 秘钥
     */
    private String secretKey;

    /**
     * 向量、加盐
     */
    private String desSalt;
    /**
     * key算法
     */
    private String keyAlgorithm="DESede";

    /**
     * 密码算法
     */
    private String cipherAlgorithm="DESede/CBC/PKCS5Padding";

}
