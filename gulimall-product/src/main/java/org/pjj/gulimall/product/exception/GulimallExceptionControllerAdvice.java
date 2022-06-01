package org.pjj.gulimall.product.exception;

import lombok.extern.slf4j.Slf4j;
import org.pjj.common.exception.BizCodeEnum;
import org.pjj.common.utils.R;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 集中处理所有异常
 * @author PengJiaJun
 * @Date 2022/05/31 19:43
 */
@Slf4j
@RestControllerAdvice(basePackages = "org.pjj.gulimall.product.controller")
public class GulimallExceptionControllerAdvice {

    // 该方法处理所有 MethodArgumentNotValidException 异常 (也就是 JSR303检验失败后 抛出的异常)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public R handleValueException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{}, 异常类型: {}", e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();

        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return R.error(BizCodeEnum.VAILD_EXCEPTION.getCode(), BizCodeEnum.VAILD_EXCEPTION.getMsg()).put("data", errorMap);
    }


    // 处理总异常
    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {
        log.error(throwable.getMessage(), "_", throwable.getClass());

        return R.error(BizCodeEnum.UNKNOW_EXCEPTION.getCode(), BizCodeEnum.UNKNOW_EXCEPTION.getMsg());
    }



}
