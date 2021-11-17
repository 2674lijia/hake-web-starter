package store.lijia.web.secret.threedes;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import store.lijia.web.exception.InfoException;
import store.lijia.web.secret.Decrypt;
import store.lijia.web.secret.IgnoreDecrypt;
import store.lijia.web.secret.SecretProperties;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * 3desc参数解密处理器
 *
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
public class DecryptRequestHandler extends RequestBodyAdviceAdapter {

    private ThreeDesProperties threeDesProperties;

    @Resource
    public void setThreeDesProperties(ThreeDesProperties threeDesProperties) {
        this.threeDesProperties = threeDesProperties;
    }

    private SecretProperties secretProperties;

    @Resource
    public void setSecurityProperties(SecretProperties secretProperties) {
        this.secretProperties = secretProperties;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        /**
         * 判断是否需要解密
         */
        Decrypt annotation = methodParameter.getDeclaringClass().getAnnotation(Decrypt.class);
        return (annotation != null && !methodParameter.hasMethodAnnotation(IgnoreDecrypt.class))
                || methodParameter.hasMethodAnnotation(Decrypt.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try {
            InputStream encryptStream = inputMessage.getBody();
            String encryptBody = StreamUtils.copyToString(encryptStream, Charset.defaultCharset());
            String decodeStr = ThreeDesUtil.decode(encryptBody, threeDesProperties.getSecretKey(), threeDesProperties.getDesSalt());
            return new HttpInputMessage() {
                @Override
                public InputStream getBody() {
                    return new ByteArrayInputStream(decodeStr.getBytes());
                }

                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
            throw InfoException.builder().message("参数异常").build();
        }
    }
}
