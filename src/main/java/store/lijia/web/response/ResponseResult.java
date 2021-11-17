package store.lijia.web.response;

import lombok.*;

/**
 * @author lijia
 * @version 1.0.0
 * @description
 * @createTime 2021/11/9 上午11:46
 * @company 杭州车凌网络科技有限公司
 * @address 杭州市滨江区聚光中心B座705
 */
@Getter
@Setter
@AllArgsConstructor
public class ResponseResult<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 数据
     */
    private T data;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 错误异常信息
     */
    private String excMessage;

    /**
     * 成功标记
     */
    private Boolean success;


    public static <T> ResponseResult<T> success() {
        return success(null);
    }
    public static <T> ResponseResult<T> success(T data) {
        return success(data, 200);
    }

    public static <T> ResponseResult<T> success(T data, Integer code) {
        return success(data, code, "成功");
    }

    public static <T> ResponseResult<T> success(T data, Integer code, String message) {
        return new ResponseResult<>(code, data, message, null, true);
    }

    public static <T> ResponseResult<T> error() {
        return error(1000);
    }

    public static <T> ResponseResult<T> error(Integer code) {
        return error(code, "失败");
    }

    public static <T> ResponseResult<T> error(Integer code, String message) {
        return new ResponseResult<>(code, null, message, null, true);
    }

    public static <T> ResponseResult<T> message(Integer code, String message) {
        return success(null, code, message);
    }
}
