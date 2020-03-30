package com.ocean.showdoc.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;


@Configuration
@MapperScan({"com.ocean.showdoc.dao"})
public class MybatisConfig {

}
