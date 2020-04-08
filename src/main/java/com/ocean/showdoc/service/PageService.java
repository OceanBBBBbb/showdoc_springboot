package com.ocean.showdoc.service;

import com.ocean.showdoc.controller.param.page.PageRsp;
import com.ocean.showdoc.dao.entity.Page;

import java.util.List;

/**
 * @author oceanBin on 2020/4/5
 */
public interface PageService {
    void save(Page build);

    Page getById(int page_id);

    int delById(int page_id);

    List<PageRsp> getByItemId(Integer item_id);

    List<PageRsp> getByItemIdCatId(Integer item_id, int cat_id);
}
