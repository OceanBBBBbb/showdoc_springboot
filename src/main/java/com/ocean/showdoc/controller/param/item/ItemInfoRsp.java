package com.ocean.showdoc.controller.param.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by Mybatis Generator on 2020/03/30
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemInfoRsp {
    private String item_id;
    private String item_title;
    private String is_archived;
    private String default_page_id;
    private String default_cat_id2;
    private String default_cat_id3;
    private String unread_count;
    private int item_type;
    private boolean Is_login = true;
    private boolean itemCreator =true;
    private boolean itemPermn =true;
    private Menu menu;


}