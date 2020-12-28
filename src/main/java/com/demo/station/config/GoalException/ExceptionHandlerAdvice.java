package com.demo.station.config.GoalException;

import com.demo.station.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : lipb
 * @date : 2020-12-28 18:05
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result businessException(BusinessException e) {
        Result result = new Result();
        result.setCode(e.getCode());
        result.setMessage(e.getMessage());
        e.printStackTrace();
        return result;
    }

}
