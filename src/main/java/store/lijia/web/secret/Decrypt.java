package store.lijia.web.secret;

import java.lang.annotation.*;

/**
 * 参数解密注解
 * 被该注解修饰的类或方法则会将请求参数进行解密操作
 *
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午2:29
*
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Decrypt {

}
