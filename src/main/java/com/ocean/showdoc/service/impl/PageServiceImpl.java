package com.ocean.showdoc.service.impl;

import com.ocean.showdoc.controller.param.page.PageRsp;
import com.ocean.showdoc.dao.PageMapper;
import com.ocean.showdoc.dao.entity.Page;
import com.ocean.showdoc.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** @author oceanBin on 2020/4/5 */
@Slf4j
@Service
public class PageServiceImpl implements PageService {
  private final PageMapper pageMapper;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  public PageServiceImpl(PageMapper pageMapper) {
    this.pageMapper = pageMapper;
  }

  @Override
  public void save(Page build) {
    int i = pageMapper.selectCount(Page.builder().id(build.getId()).build());
    if (i > 0) {
      pageMapper.updateByPrimaryKeySelective(build);
    } else {
      pageMapper.insert(build);
    }
  }

  @Override
  public Page getById(int page_id) {
    return pageMapper.selectByPrimaryKey(page_id);
  }

  @Override
  public int delById(int page_id) {
    return pageMapper.deleteByPrimaryKey(page_id);
  }

  @Override
  public List<PageRsp> getByItemId(Integer item_id) {
    List<Page> select = pageMapper.select(Page.builder().itemId(item_id).build());
    return select.stream().map(PageRsp::buildByPage).collect(Collectors.toList());
  }

  @Override
  public List<PageRsp> getByItemIdCatId(Integer item_id, int cat_id) {
    List<Page> select = pageMapper.select(Page.builder().itemId(item_id).catId(cat_id).build());
    return select.stream().map(PageRsp::buildByPage).collect(Collectors.toList());
  }
}
