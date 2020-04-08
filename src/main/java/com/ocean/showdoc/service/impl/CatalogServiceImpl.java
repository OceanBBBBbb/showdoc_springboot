package com.ocean.showdoc.service.impl;

import com.ocean.showdoc.controller.param.catalogs.CatalogsGroupRsp;
import com.ocean.showdoc.controller.param.catalogs.CatalogsRsp;
import com.ocean.showdoc.controller.param.catalogs.SaveReq;
import com.ocean.showdoc.controller.param.page.PageRsp;
import com.ocean.showdoc.dao.CatalogsMapper;
import com.ocean.showdoc.dao.PageMapper;
import com.ocean.showdoc.dao.entity.Catalogs;
import com.ocean.showdoc.dao.entity.Page;
import com.ocean.showdoc.service.CatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/** @author oceanBin on 2020/4/5 */
@Slf4j
@Service
public class CatalogServiceImpl implements CatalogService {

  private final CatalogsMapper catalogsMapper;
  private final PageMapper pageMapper;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  public CatalogServiceImpl(CatalogsMapper catalogsMapper, PageMapper pageMapper) {
    this.catalogsMapper = catalogsMapper;
    this.pageMapper = pageMapper;
  }

  @Override
  public void save(SaveReq req, int level) {
      int sNum;
      try {
          String s_number = req.getS_number();
          sNum = Integer.parseInt(s_number);
      } catch (Exception e) {
          log.error("传的req={}",req);
          sNum= 99;
      }
      Catalogs build =
        Catalogs.builder()
            .createdAt(new Date())
            .itemId(req.getItem_id())
            .name(req.getCat_name())
            .sNumber(sNum)
            .level(level)
            .parentCatId(req.getParent_cat_id())
            .build();
    if (null == req.getCat_id() || 0 == req.getCat_id()) {
      catalogsMapper.insert(build);
    } else {
      build.setId(req.getCat_id());
      catalogsMapper.updateByPrimaryKeySelective(build);
    }
  }

  @Override
  public int deleteById(int cat_id) {
    Catalogs catalogs = catalogsMapper.selectByPrimaryKey(cat_id);
    if (null != catalogs && 2 == catalogs.getLevel()) {
      catalogsMapper.delete(Catalogs.builder().parentCatId(cat_id).build());
    }
    return catalogsMapper.deleteByPrimaryKey(cat_id);
  }

  @Override
  public List<CatalogsRsp> getByItemId(int item_id) {
    List<CatalogsRsp> result = new LinkedList<>();
    // 先查2级目录
    List<Catalogs> level2 =
        catalogsMapper.select(Catalogs.builder().itemId(item_id).level(2).build());
    // 先把2级的信息和page拿到
    List<CatalogsRsp> catalogsRsps2 = getCatalogsRsps(level2);
    // 再遍历二级，二级下没有三级，就加入结果，有则转3级
    catalogsRsps2.forEach(
        cr2 -> {
          List<Catalogs> level3 =
              catalogsMapper.select(
                  Catalogs.builder().itemId(item_id).parentCatId(cr2.getCat_id()).level(3).build());
          List<CatalogsRsp> catalogsRsps = getCatalogsRsps(level3);
          cr2.setCatalogs(catalogsRsps);
          result.add(cr2);
        });
    return result;
  }

  @Override
  public List<CatalogsRsp> getByItemIdLevel(int item_id, int level) {
    List<Catalogs> select =
        catalogsMapper.select(Catalogs.builder().itemId(item_id).level(level).build());
    return getCatalogsRsps(select);
  }

  @Override
  public List<CatalogsRsp> getByParentcatId(int cat_id) {
    List<Catalogs> select = catalogsMapper.select(Catalogs.builder().parentCatId(cat_id).build());
    return getCatalogsRsps(select);
  }

  @Override
  public List<CatalogsGroupRsp> catListGroup(Integer item_id) {
    List<CatalogsGroupRsp> result = new LinkedList<>();
    // 先查2级目录
    List<Catalogs> level2 =
        catalogsMapper.select(Catalogs.builder().itemId(item_id).level(2).build());
    level2.forEach(
        level2Cate -> {
          CatalogsGroupRsp.CatalogsGroupRspBuilder cgrb =
              CatalogsGroupRsp.buildByCatalogs(level2Cate);
          List<Catalogs> level3 =
              catalogsMapper.select(
                  Catalogs.builder()
                      .itemId(item_id)
                      .parentCatId(level2Cate.getId())
                      .level(3)
                      .build());
          if (0 < level3.size()) {
            List<CatalogsGroupRsp> cgr2 = new LinkedList<>();
            level3.forEach(
                l3 -> {
                  cgr2.add(CatalogsGroupRsp.buildByCatalogs(l3).build());
                });
            cgrb.sub(cgr2);
          }
          result.add(cgrb.build());
        });
    return result;
  }

  private List<CatalogsRsp> getCatalogsRsps(List<Catalogs> select) {
    List<CatalogsRsp> result = new LinkedList<>();
    select.forEach(
        catalogs -> {
          CatalogsRsp.CatalogsRspBuilder catalogsRspBuilder = CatalogsRsp.buildByCatalogs(catalogs);
          List<Page> pages = pageMapper.select(Page.builder().catId(catalogs.getId()).build());
          List<PageRsp> prsp =
              pages.stream().map(PageRsp::buildByPage).collect(Collectors.toList());
          result.add(catalogsRspBuilder.pages(prsp).build());
        });
    return result;
  }
}
