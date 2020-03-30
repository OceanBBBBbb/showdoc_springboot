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
@Table(name = "`user`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`email`")
    private String email;

    @Column(name = "`password`")
    private String password;

    @Column(name = "`created_at`")
    private Date createdAt;

    @Column(name = "`updated_at`")
    private Date updatedAt;
}