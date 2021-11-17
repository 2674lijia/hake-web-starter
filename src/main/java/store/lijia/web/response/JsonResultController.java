package store.lijia.web.response;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 *
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午1:11
 * @company 杭州车凌网络科技有限公司
 * @address 杭州市滨江区聚光中心B座705
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@RestController
public @interface JsonResultController {

}
