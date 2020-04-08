package com.ocean.showdoc.controller.param.item;

import com.ocean.showdoc.controller.param.catalogs.CatalogsRsp;
import com.ocean.showdoc.controller.param.page.PageRsp;
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
public class Menu {

    List<PageRsp> pages;
    List<CatalogsRsp> catalogs;
}
