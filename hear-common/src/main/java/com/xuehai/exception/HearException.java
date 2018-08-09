package com.xuehai.exception;

import com.xuehai.utils.ConstantUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author ：周黎钢.
 * @date ：Created in 18:45 2018/7/9
 * @description:
 */
@Accessors(chain = true)
@Data
public class HearException extends RuntimeException {
    /**
     * 异常code,默认为1000失败
     */
    private Long code;

    /**
     * 异常信息
     */
    private String errMsg;

    public HearException(Long code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    public HearException(String errMsg) {
        this.code = ConstantUtil.FAILED;
        this.errMsg = errMsg;
    }
}
