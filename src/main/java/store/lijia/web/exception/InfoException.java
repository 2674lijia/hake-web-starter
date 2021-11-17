package store.lijia.web.exception;

import lombok.Builder;
import lombok.Data;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 下午1:26
 * @company 杭州车凌网络科技有限公司
 * @address 杭州市滨江区聚光中心B座705
 */
@Data
@Builder
public class InfoException extends RuntimeException {

    private String message;

    private Integer code;

}
