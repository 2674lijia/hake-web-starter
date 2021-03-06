package store.lijia.web.secret;

import java.lang.annotation.*;

/**
 * 响应结果加密注解
 * 被该注解修饰的类或方法则会将相应结果数据进行加密操作
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
public @interface Encrypt {
}
