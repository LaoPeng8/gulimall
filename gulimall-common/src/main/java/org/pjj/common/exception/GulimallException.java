package org.pjj.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常
 * @author PengJiaJun
 * @Date 2022/06/05 00:12
 */
@Data // get set toString
public class GulimallException extends RuntimeException {

    private BizCodeEnum bizCodeEnum;

    public GulimallException() {
    }
    public GulimallException(BizCodeEnum bizCodeEnum) {
        this.bizCodeEnum = bizCodeEnum;
    }


    @Override
    public String toString() {
        return "GulimallException{" +
                "bizCodeEnum=" + bizCodeEnum +
                '}';
    }
}
