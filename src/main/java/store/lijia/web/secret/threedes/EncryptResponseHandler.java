package store.lijia.web.secret.threedes;

import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import store.lijia.web.exception.InfoException;
import store.lijia.web.secret.Encrypt;
import store.lijia.web.secret.IgnoreEncrypt;
import store.lijia.web.secret.SecretProperties;

import javax.annotation.Resource;

/**
 * 参数加密注解
 * 被该注解修饰的类或方法则会将相应结果数据进行加密操作
 *
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午2:29
 * @company 杭州车凌网络科技有限公司
 * @address 杭州市滨江区聚光中心B座705
 */
@EnableConfigurationProperties({SecretProperties.class, ThreeDesProperties.class})
@RestControllerAdvice
@ConditionalOnProperty(name = "hake.secret.enable", havingValue = "true")
public class EncryptResponseHandler implements ResponseBodyAdvice<Object>, Ordered {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ThreeDesProperties threeDesProperties;

    @Resource
    public void setThreeDesProperties(ThreeDesProperties threeDesProperties) {
        this.threeDesProperties = threeDesProperties;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        /**
         * 判断是否需要加密
         */
        Encrypt annotation = methodParameter.getDeclaringClass().getAnnotation(Encrypt.class);
        return (annotation != null && !methodParameter.hasMethodAnnotation(IgnoreEncrypt.class))
                || methodParameter.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        try {
            return ThreeDesUtil.encode(JSONUtil.toJsonStr(o), threeDesProperties.getSecretKey(), threeDesProperties.getDesSalt());
        } catch (Exception e) {
            e.printStackTrace();
            throw InfoException.builder().message("服务器异常").build();
        }
    }


    @Override
    public int getOrder() {
        return -200;
    }
}
