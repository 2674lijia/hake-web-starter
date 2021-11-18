package store.lijia.web.secret;

import org.springframework.context.annotation.Import;
import store.lijia.web.secret.threedes.DecryptRequestHandler;
import store.lijia.web.secret.threedes.EncryptResponseHandler;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午5:33
*
 */
@Import({DecryptRequestHandler.class, EncryptResponseHandler.class})
public class SecretAutoConfiguration {
}
