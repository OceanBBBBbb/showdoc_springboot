package com.ocean.showdoc.service.impl;

import com.ocean.showdoc.controller.param.item.AddReq;
import com.ocean.showdoc.dao.ItemMapper;
import com.ocean.showdoc.dao.entity.Item;
import com.ocean.showdoc.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author oceanBin on 2020/3/31
 */
@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemMapper itemMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ItemServiceImpl(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Override
    public List<Item> getById(String uid) {
        return itemMapper.select(Item.builder().userId(Integer.valueOf(uid)).build());
    }

    @Override
    public void add(AddReq addReq, String uid) {
        Item build = Item.builder().userId(Integer.valueOf(uid))
                .type(addReq.getItem_type())
                .description(addReq.getItem_description())
                .title(addReq.getItem_name())
                .password("")
                .createdAt(new Date())
                .build();
        itemMapper.insertSelective(build);
    }

    @Override
    public Item getOneById(Integer item_id) {
        return itemMapper.selectByPrimaryKey(item_id);
    }

    @Override
    public int delOneById(String uid, Integer item_id) {
        return itemMapper.delete(Item.builder().id(item_id).userId(Integer.valueOf(uid)).build());
    }
}
