package com.ocean.showdoc.controller.param.page;

import com.ocean.showdoc.controller.param.Constant;
import com.ocean.showdoc.dao.entity.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author oceanBin on 2020/3/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRsp {
    private int page_id;
    private int author_uid;
    private int item_id;
    private int cat_id;
    private int s_number;
    private String page_title;
    private String page_content;
    private String addtime;

    public static PageRsp buildByPage(Page page){
        return PageRsp.builder().page_id(page.getId())
        .author_uid(page.getAuthorUid())
        .item_id(page.getItemId())
        .cat_id(page.getCatId())
        .s_number(page.getSNumber())
        .page_title(page.getPageTitle())
        .page_content(page.getPageContent())
        .addtime(Constant.DATE_FORMAT.format(page.getCreatedAt())).build();
    }
}
