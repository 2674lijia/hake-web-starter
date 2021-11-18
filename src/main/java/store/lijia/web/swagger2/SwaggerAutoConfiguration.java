package store.lijia.web.swagger2;

import com.google.common.base.Predicates;
import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Swagger配置启动
 *
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午3:40
 */
@EnableSwagger2
@EnableConfigurationProperties(value = SwaggerProperties.class)
@ConditionalOnProperty(name = "hake.swagger.enable", havingValue = "true")
public class SwaggerAutoConfiguration {


    private SwaggerProperties swaggerProperties;

    @Resource
    public void setSwaggerProperties(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    /**
     * swagger文档
     *
     * @return
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .globalOperationParameters(parameters())
                .apiInfo(apiInfo())
                .groupName(swaggerProperties.getGroupName());
    }


    /**
     * 构建全局参数
     */
    public List<Parameter> parameters() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        final Parameter tokenParameter = parameterBuilder.name(swaggerProperties.getParameter().getParameterName())
                .description(swaggerProperties.getParameter().getDescription())
                .modelRef(new ModelRef("String"))
                .parameterType(swaggerProperties.getParameter().getParameterType())
                .required(swaggerProperties.getParameter().getRequired())
                .build();
        return new ArrayList() {
            {
                add(tokenParameter);
            }
        };

    }


    /**
     * api基本信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .contact(new Contact(swaggerProperties.getAuthor().getName(), swaggerProperties.getAuthor().getUrl(), swaggerProperties.getAuthor().getEmail()))
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .build();
    }


}
