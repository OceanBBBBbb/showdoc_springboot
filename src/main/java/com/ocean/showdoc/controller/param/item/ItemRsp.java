package com.ocean.showdoc.controller.param.item;

import com.ocean.showdoc.dao.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
* Created by Mybatis Generator on 2020/03/30
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRsp {
    private String item_id;
    private String item_name;
    private String item_description;
    private String password;
    private String userId;
    private String type;
    private String addtime;
    private String updatedAt;

    public static ItemRsp buildByItem(Item item){
        return ItemRsp.builder()
                .item_id(String.valueOf(item.getId()))
                .item_name(item.getTitle())
                .item_description(item.getDescription())
                .type(String.valueOf(item.getType()))
                .userId(String.valueOf(item.getUserId()))
                .build();
    }
}