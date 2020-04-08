package com.ocean.showdoc.controller;

import com.ocean.showdoc.common.GlobalConst;
import com.ocean.showdoc.common.Rsp;
import com.ocean.showdoc.controller.param.catalogs.CatalogsGroupRsp;
import com.ocean.showdoc.controller.param.catalogs.CatalogsRsp;
import com.ocean.showdoc.controller.param.catalogs.SaveReq;
import com.ocean.showdoc.controller.param.item.AddReq;
import com.ocean.showdoc.controller.param.page.Temp;
import com.ocean.showdoc.service.CatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/** @author oceanBin on 2020/3/30 */
@RestController
@RequestMapping("/v1/api/catalog")
@Slf4j
public class CatalogController {

  private final CatalogService catalogService;
  private final StringRedisTemplate redisTemplate;

  public CatalogController(CatalogService catalogService, StringRedisTemplate redisTemplate) {
    this.catalogService = catalogService;
    this.redisTemplate = redisTemplate;
  }

  @PostMapping("/save")
  public Rsp<Void> save(@Validated SaveReq req, HttpServletRequest request) {
    int parent_cat_id = req.getParent_cat_id();
    int parent_id;
    if (parent_cat_id == 0) {
      parent_id = 2;
    } else {
      parent_id = 3;
    }
    catalogService.save(req, parent_id);
    return Rsp.buildSuccessEmpty();
  }

  @PostMapping("/delete")
  public Rsp<Void> save(int cat_id, HttpServletRequest request) {
    catalogService.deleteById(cat_id);
    return Rsp.buildSuccessEmpty();
  }

  @PostMapping("/catListGroup")
  public Rsp<List<CatalogsGroupRsp>> catListGroup(Integer item_id, HttpServletRequest request) {
    if(null==item_id){
      return Rsp.buildSuccess(new ArrayList<>());
    }
    List<CatalogsGroupRsp> rsp = catalogService.catListGroup(item_id);
    return Rsp.buildSuccess(rsp);
  }

  @PostMapping("/secondCatList")
  public Rsp<List<CatalogsRsp>> secondCatList(Integer item_id, HttpServletRequest request) {
    if(null==item_id){
      return Rsp.buildSuccess(new ArrayList<>());
    }
    List<CatalogsRsp> rsp = catalogService.getByItemIdLevel(item_id, 2);
    return Rsp.buildSuccess(rsp);
  }

  @PostMapping("/childCatList")
  public Rsp<List<CatalogsRsp>> childCatList(Integer cat_id, HttpServletRequest request) {
    if(null==cat_id){
      return Rsp.buildSuccess(new ArrayList<>());
    }
    List<CatalogsRsp> rsp = catalogService.getByParentcatId(cat_id);
    return Rsp.buildSuccess(rsp);
  }

  // 这接口意义何在???
  @PostMapping("/getDefaultCat")
  public Rsp<Temp> getDefaultCat(Integer page_id, String copy_page_id, HttpServletRequest request) {
    Temp build;
    build = Temp.builder().default_cat_id2("").default_cat_id3("").build();
    return Rsp.buildSuccess(build);
  }
}
