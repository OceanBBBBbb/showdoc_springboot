package com.ocean.showdoc.dao.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by Mybatis Generator on 2020/03/30
*/
@Table(name = "`catalogs`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Catalogs {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`item_id`")
    private Integer itemId;

    /**
     * 排序
     */
    @Column(name = "`s_number`")
    private Integer sNumber;

    @Column(name = "`created_at`")
    private Date createdAt;

    @Column(name = "`parent_cat_id`")
    private Integer parentCatId;

    @Column(name = "`level`")
    private Integer level;
}