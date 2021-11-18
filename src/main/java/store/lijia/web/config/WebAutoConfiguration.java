package store.lijia.web.config;

import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import store.lijia.web.jackson.JacksonConfiguration;
import store.lijia.web.response.ResponseResultHandler;
import store.lijia.web.swagger2.SwaggerAutoConfiguration;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午2:29
*
 */
@Import({ResponseResultHandler.class, SwaggerAutoConfiguration.class, JacksonConfiguration.class})
public class WebAutoConfiguration implements WebMvcConfigurer {



}
