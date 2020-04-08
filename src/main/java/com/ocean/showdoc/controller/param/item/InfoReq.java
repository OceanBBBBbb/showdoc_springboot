package com.ocean.showdoc.controller.param.item;

import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * @author oceanBin on 2020/3/31
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InfoReq {
    @Length(min = 1,max=11)
    private Integer item_id;
    @Length(min = 1,max=255)
    private String keyword;
}
