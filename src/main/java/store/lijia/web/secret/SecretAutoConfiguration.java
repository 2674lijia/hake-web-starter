package store.lijia.web.secret;

import org.springframework.context.annotation.Import;
import store.lijia.web.secret.threedes.DecryptRequestHandler;
import store.lijia.web.secret.threedes.EncryptResponseHandler;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午5:33
 * @company 杭州车凌网络科技有限公司
 * @address 杭州市滨江区聚光中心B座705
 */
@Import({DecryptRequestHandler.class, EncryptResponseHandler.class})
public class SecretAutoConfiguration {
}
