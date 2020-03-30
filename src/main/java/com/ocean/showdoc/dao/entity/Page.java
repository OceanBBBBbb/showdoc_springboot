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
@Table(name = "`page`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`item_id`")
    private Integer itemId;

    @Column(name = "`author_uid`")
    private Integer authorUid;

    @Column(name = "`cat_id`")
    private Integer catId;

    @Column(name = "`page_title`")
    private String pageTitle;

    @Column(name = "`s_number`")
    private Integer sNumber;

    @Column(name = "`created_at`")
    private Date createdAt;

    @Column(name = "`page_content`")
    private String pageContent;

    @Column(name = "`page_comments`")
    private String pageComments;
}