package com.ocean.showdoc.service;

import com.ocean.showdoc.controller.param.catalogs.CatalogsGroupRsp;
import com.ocean.showdoc.controller.param.catalogs.CatalogsRsp;
import com.ocean.showdoc.controller.param.catalogs.SaveReq;

import java.util.List;

/**
 * @author oceanBin on 2020/4/5
 */
public interface CatalogService {
    void save(SaveReq req, int level);

    int deleteById(int cat_id);

    List<CatalogsRsp> getByItemId(int item_id);

    List<CatalogsRsp> getByItemIdLevel(int item_id, int level);

    List<CatalogsRsp> getByParentcatId(int cat_id);

    List<CatalogsGroupRsp> catListGroup(Integer item_id);
}
