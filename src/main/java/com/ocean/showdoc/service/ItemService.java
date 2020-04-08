package com.ocean.showdoc.service;

import com.ocean.showdoc.controller.param.item.AddReq;
import com.ocean.showdoc.dao.entity.Item;

import java.util.List;

/**
 * @author oceanBin on 2020/3/31
 */
public interface ItemService {
    List<Item> getById(String uid);

    void add(AddReq addReq, String uid);

    Item getOneById(Integer item_id);

    int delOneById(String uid, Integer item_id);
}
