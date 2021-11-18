package store.lijia.web.swagger2;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午3:48
*
 */
@Data
@ConfigurationProperties("hake.swagger")
public class SwaggerProperties {

    /**
     * 是否开启
     */
    private Boolean enable=true;

    /**
     * 组名
     */
    private String groupName="swagger";

    /**
     * 标题
     */
    private String title="swagger";

    /**
     * 作者
     */
    private SwaggerProperties.Author author=new SwaggerProperties.Author();

    /**
     * 构造自定义全局参数
     */
    private SwaggerProperties.Parameter parameter=new SwaggerProperties.Parameter();

    /**
     * 描述
     */
    private String description="API";

    /**
     * 版本
     */
    private String version="1.0.0";

    @Data
    public static class Author {
        /**
         * 作者名称
         */
        private String name;
        /**
         * 作者博客
         */
        private String url;
        /**
         * 作者邮件
         */
        private String email;
    }

    @Data
    public static class Parameter {
        /**
         * 参数类型
         */
        private String parameterType="header";

        /**
         * 是否为必填
         */
        private Boolean required=false;

        /**
         * 描述
         */
        private String description="请求头token";

        /**
         * 参数名称
         */
        private String parameterName="authorization";
    }

}
