package store.lijia.web.exception;

import lombok.Builder;
import lombok.Data;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午1:26
*
 */
@Data
@Builder
public class InfoException extends RuntimeException {

    private String message;

    private Integer code;

}
