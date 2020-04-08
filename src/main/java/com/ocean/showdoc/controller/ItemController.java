package com.ocean.showdoc.controller;

import com.ocean.showdoc.common.GlobalConst;
import com.ocean.showdoc.common.Rsp;
import com.ocean.showdoc.controller.param.catalogs.CatalogsRsp;
import com.ocean.showdoc.controller.param.item.*;
import com.ocean.showdoc.controller.param.page.PageRsp;
import com.ocean.showdoc.dao.entity.Item;
import com.ocean.showdoc.service.CatalogService;
import com.ocean.showdoc.service.ItemService;
import com.ocean.showdoc.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** @author oceanBin on 2020/3/30 */
@RestController
@RequestMapping("/v1/api/item")
@Slf4j
public class ItemController {

  private final ItemService itemService;
  private final StringRedisTemplate redisTemplate;
  private final PageService pageService;
  private final CatalogService catalogService;


  public ItemController(ItemService itemService, StringRedisTemplate redisTemplate, PageService pageService, CatalogService catalogService) {
    this.itemService = itemService;
    this.redisTemplate = redisTemplate;
    this.pageService = pageService;
    this.catalogService = catalogService;
  }

  @GetMapping("/myList")
  public Rsp<List<ItemRsp>> myList(HttpServletRequest request) {

    String token = request.getHeader(GlobalConst.USER_SESSION_KEY);
    String uid = redisTemplate.opsForValue().get(token);
    List<Item> items = itemService.getById(uid);
    List<ItemRsp> rsps =
        items.stream()
            .map(ItemRsp::buildByItem)
            .collect(Collectors.toList());
    return Rsp.buildSuccess(rsps);
  }

  @PostMapping("/add")
  public Rsp<Void> add(AddReq addReq, HttpServletRequest request) {

    String token = request.getHeader(GlobalConst.USER_SESSION_KEY);
    String uid = redisTemplate.opsForValue().get(token);
    itemService.add(addReq, uid);
    return Rsp.buildSuccessEmpty();
  }

  @PostMapping("/info")
  public Rsp<ItemInfoRsp> info(InfoReq infoReq, HttpServletRequest request) {

    String token = request.getHeader(GlobalConst.USER_SESSION_KEY);
    String uid = redisTemplate.opsForValue().get(token);
    boolean isMe = !StringUtils.isEmpty(uid);
    Item item = itemService.getOneById(infoReq.getItem_id());
    // 单独的pages
    List<PageRsp> pageRsps = pageService.getByItemIdCatId(infoReq.getItem_id(),0);
    // 查目录和目录下的目录和pages
    List<CatalogsRsp> byItemId = catalogService.getByItemId(infoReq.getItem_id());
    Menu menu = Menu.builder().pages(pageRsps).catalogs(byItemId).build();
    ItemInfoRsp build = ItemInfoRsp.builder()
            .item_id(item.getId().toString())
            .item_title(item.getTitle())
            .item_type(1)
            .is_archived("0")
            .default_page_id("0")
            .unread_count("0")
            .Is_login(isMe)
            .itemCreator(isMe)
            .itemPermn(isMe)
            .menu(menu)
            .build();
    return Rsp.buildSuccess(build);
  }


  @PostMapping("/detail")
  public ItemRsp detail(Integer item_id){
    Item item = itemService.getOneById(item_id);
    return ItemRsp.buildByItem(item);
  }

  @PostMapping("/delete")
  public Rsp<Void> delete(Integer item_id, HttpServletRequest request){
    String token = request.getHeader(GlobalConst.USER_SESSION_KEY);
    String uid = redisTemplate.opsForValue().get(token);
    // TODO 对应的子内容也需要删除
    int result = itemService.delOneById(uid,item_id);
    return Rsp.buildSuccessEmpty();
  }


  @PostMapping("/update")
  public ItemRsp Update(AddReq addReq){
    // TODO
    Item item = itemService.getOneById(addReq.getItem_id());
    return ItemRsp.buildByItem(item);
  }


}
