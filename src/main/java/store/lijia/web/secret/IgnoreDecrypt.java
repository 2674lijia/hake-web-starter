package store.lijia.web.secret;

import java.lang.annotation.*;

/**
 * 忽略参数解密注解
 *
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午2:29
*
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface IgnoreDecrypt {
}
