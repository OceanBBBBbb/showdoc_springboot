package com.ocean.showdoc.controller.param.catalogs;

import com.ocean.showdoc.controller.param.Constant;
import com.ocean.showdoc.controller.param.page.PageRsp;
import com.ocean.showdoc.dao.entity.Catalogs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author oceanBin on 2020/3/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogsRsp {
    private int cat_id;
    private String cat_name;
    private int item_id;
    private int s_number;
    private String addtime;
    private int parent_cat_id;
    private int level;
    private List<CatalogsRsp> catalogs;
    private List<PageRsp> pages;


    public static CatalogsRspBuilder buildByCatalogs(Catalogs c){

    return CatalogsRsp.builder()
        .cat_id(c.getId())
        .cat_name(c.getName())
        .item_id(c.getItemId())
        .s_number(c.getSNumber())
        .addtime(Constant.DATE_FORMAT.format(c.getCreatedAt()))
        .parent_cat_id(c.getParentCatId())
        .level(c.getLevel());
    }
}
