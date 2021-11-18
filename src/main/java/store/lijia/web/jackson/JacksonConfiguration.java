package store.lijia.web.jackson;

import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 处理json格式
 * 将Long类型转为String,并格式化日期时间
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午6:37
*
 */
@Configuration
@ConfigurationProperties(prefix = "spring.jackson")
public class JacksonConfiguration {
    /**
     * 时间格式
     */
    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String dateFormat;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(){
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.dateFormat(new SimpleDateFormat(dateFormat));
            jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateFormat)));
            jacksonObjectMapperBuilder.serializerByType(Date.class,new DateSerializer(true,new SimpleDateFormat(dateFormat)));
        };
    }
}
