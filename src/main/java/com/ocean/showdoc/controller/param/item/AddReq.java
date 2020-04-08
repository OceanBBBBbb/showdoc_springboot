package com.ocean.showdoc.controller.param.item;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

/** @author oceanBin on 2020/3/31 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddReq {

  @Length(min = 1, max = 11)
  private Integer item_type;

  @Length(min = 1, max = 255)
  private String item_name;

  @Length(min = 1, max = 255)
  private String item_description;

  @Nullable
  @Length(max = 128)
  private String password;

  @Nullable
  private int item_id;
}
