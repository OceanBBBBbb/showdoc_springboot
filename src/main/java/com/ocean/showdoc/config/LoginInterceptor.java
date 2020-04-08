package com.ocean.showdoc.config;

import com.ocean.showdoc.common.GlobalConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author oceanBin on 2020/3/30
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate redisTemplate;

    public LoginInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        log.info(request.getRequestURI());
        String token = request.getHeader(GlobalConst.USER_SESSION_KEY);
        log.info("token={}",token);
        String uid = redisTemplate.opsForValue().get(token);

        if (StringUtils.isEmpty(uid))  {
            response.sendError(403,"未登陆");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        log.info("postHandle...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        log.info("afterCompletion...");
    }
}
