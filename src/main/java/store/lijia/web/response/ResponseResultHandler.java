package store.lijia.web.response;

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

/**
 * @author lijia
 * @version 1.0.0
 * @description 请求相应通知处理
 * @createTime 2021/11/9 下午1:17
 * @company 杭州车凌网络科技有限公司
 * @address 杭州市滨江区聚光中心B座705
 */
@EnableConfigurationProperties(value = ResponseConfigProperties.class)
@RestControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object>, Ordered {

    private ResponseConfigProperties responseConfigProperties;

    public ResponseResultHandler(ResponseConfigProperties responseConfigProperties) {
        this.responseConfigProperties = responseConfigProperties;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        /**
         * 只处理带有JsonResultController注解的方法
         */
        Class<?> declaringClass = methodParameter.getDeclaringClass();
        JsonResultController jsonResultController =declaringClass.getAnnotation(JsonResultController.class);
        return jsonResultController != null;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        /**
         * 处理提示性异常
         */
        if (o instanceof InfoException) {
            InfoException infoException = (InfoException) o;
            return ResponseResult.message(infoException.getCode(), infoException.getMessage());
        }
        return ResponseResult.success(o);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
