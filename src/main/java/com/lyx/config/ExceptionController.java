package com.lyx.config;

import cn.hutool.core.util.StrUtil;
import com.lyx.common.CommonResult;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController
{
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public CommonResult<String> foo(Exception e)
    {
        if (e instanceof BindException)
        {
            BindException bindException = (BindException) e;
            List<ObjectError> allErrors = bindException.getBindingResult().getAllErrors();
            String errorMsg = allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("、"));
            return CommonResult.errorMsg(errorMsg);
        }

        if (e instanceof ConstraintViolationException)
        {
            ConstraintViolationException exception = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violationSet = exception.getConstraintViolations();
            String errorMsg = violationSet.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("、"));
            return CommonResult.errorMsg(errorMsg);
        }

        return CommonResult.errorMsg(StrUtil.format("全局捕获异常：{}", e.getMessage()));
    }
}
