package com.ocean.showdoc.controller.param.catalogs;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

/**
 * @author oceanBin on 2020/4/5
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SaveReq {
    private Integer item_id;
    @Nullable
    private Integer cat_id;
    private Integer parent_cat_id;
    @Length(max = 255)
    private String cat_name;
    @Nullable
    private String s_number;
}
