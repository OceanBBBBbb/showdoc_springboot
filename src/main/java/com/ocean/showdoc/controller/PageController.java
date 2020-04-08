package com.ocean.showdoc.controller;

import com.ocean.showdoc.common.GlobalConst;
import com.ocean.showdoc.common.Rsp;
import com.ocean.showdoc.common.RspCode;
import com.ocean.showdoc.controller.param.page.PageRsp;
import com.ocean.showdoc.controller.param.page.SaveReq;
import com.ocean.showdoc.controller.param.page.UploadRsp;
import com.ocean.showdoc.dao.entity.Page;
import com.ocean.showdoc.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.UUID;

/** @author oceanBin on 2020/3/30 */
@RestController
@RequestMapping("/v1/api/page")
@Slf4j
public class PageController {

  @Value("${upload.forbidden}")
  private boolean uploadFbd;

  private final PageService pageService;
  private final StringRedisTemplate redisTemplate;

  public PageController(PageService pageService, StringRedisTemplate redisTemplate) {
    this.pageService = pageService;
    this.redisTemplate = redisTemplate;
  }

  @PostMapping("/save")
  public Rsp<Void> save(@Validated SaveReq req, HttpServletRequest request) {
    String token = request.getHeader(GlobalConst.USER_SESSION_KEY);
    String uid = redisTemplate.opsForValue().get(token);
    pageService.save(
        Page.builder()
            .catId(req.getCat_id())
            .authorUid(Integer.valueOf(uid))
            .createdAt(new Date())
            .itemId(req.getItem_id())
            .id(req.getPage_id())
            .sNumber(req.getS_number())
            .pageTitle(req.getPage_title())
            .pageContent(req.getPage_content())
            .build());

    return Rsp.buildSuccessEmpty();
  }

  @PostMapping("/info")
  public Rsp<PageRsp> Info(int page_id, HttpServletRequest request) {
    Page page = pageService.getById(page_id);
    return Rsp.buildSuccess(PageRsp.buildByPage(page));
  }

  @PostMapping("/delete")
  public Rsp<Void> delete(int page_id, HttpServletRequest request) {
    int del = pageService.delById(page_id);
    return Rsp.buildSuccessEmpty();
  }

  @PostMapping("/uploadImg")
  public Rsp<UploadRsp> uploadImg(
      MultipartFile editormdImageFile, String authorization, HttpServletRequest request) {

    // 线上不开放
    if (uploadFbd) {
      return Rsp.buildFail(RspCode.PARAM_ERR, "暂未开放");
    }

    if (StringUtils.isEmpty(authorization)
        || StringUtils.isEmpty(redisTemplate.opsForValue().get(authorization))) {
      return Rsp.buildFail(RspCode.PARAM_ERR, "需要登陆");
    }
    String contentType = editormdImageFile.getContentType();
    log.info("file contentType:" + contentType);
    // 存到一个可以直接访问的位置后返回这个访问url
    String path = "upload/" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
    File file = new File(new File("src/main/resources/static/" + path).getAbsolutePath());
    try {
      editormdImageFile.transferTo(file);
    } catch (Exception e) {
      log.error("存失败", e);
      return Rsp.buildFail(RspCode.PARAM_ERR, "这图片存不了");
    }
    String url = "http://localhost:8989/showdoc/" + path;
    return Rsp.buildSuccess(UploadRsp.builder().url(url).build());
  }
}
