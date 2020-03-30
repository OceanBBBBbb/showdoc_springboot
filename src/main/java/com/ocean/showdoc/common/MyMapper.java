package com.ocean.showdoc.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author oceanBin on 2020/3/30
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
