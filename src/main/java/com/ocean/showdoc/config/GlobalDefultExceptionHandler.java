package com.ocean.showdoc.config;

import com.ocean.showdoc.common.Rsp;
import com.ocean.showdoc.common.RspCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author oceanBin on 2020/4/8
 */
@ControllerAdvice
@Slf4j
public class GlobalDefultExceptionHandler {
    //声明要捕获的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Rsp<Void> defultExcepitonHandler(HttpServletRequest request, Exception e) {
        log.error("系统异常:",e);
	    return Rsp.buildFail(RspCode.SYSTEM_ERR, RspCode.SYSTEM_ERR.getMessage());
    }
}
