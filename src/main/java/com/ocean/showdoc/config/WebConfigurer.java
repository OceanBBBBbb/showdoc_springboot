package com.ocean.showdoc.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** @author oceanBin on 2020/3/30 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

  private final StringRedisTemplate redisTemplate;

  public WebConfigurer(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }


  @Bean
  public FilterRegistrationBean corsConfigurer() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    org.springframework.web.cors.CorsConfiguration config =
        new org.springframework.web.cors.CorsConfiguration();
    config.setAllowCredentials(true);
    // 设置你要允许的网站域名，如果全允许则设为 *
    config.addAllowedOrigin("*");
    // 如果要限制 HEADER 或 METHOD 请自行更改
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
    bean.setOrder(0);
    return bean;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(new LoginInterceptor(redisTemplate))
        .addPathPatterns("/v1/api/**")
        .excludePathPatterns("/v1/api/item/info")
        .excludePathPatterns("/v1/api/page/info")
        .excludePathPatterns("/v1/api/page/uploadImg")
        .excludePathPatterns("/v1/api/item/detail")
        .excludePathPatterns("/v1/api/user/login")
        .excludePathPatterns("/v1/api/user/register");
  }
}
