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
@Table(name = "`template`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Template {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`user_id`")
    private Integer userId;

    @Column(name = "`title`")
    private String title;

    @Column(name = "`created_at`")
    private Date createdAt;

    @Column(name = "`content`")
    private String content;
}