package com.ocean.showdoc.controller.param.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/** @author oceanBin on 2020/4/5 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveReq {
  private int page_id;
  private int item_id;
  private int s_number;

  @Length(min = 1, max = 255, message = "标题1-255")
  private String page_title;

  @Length(min = 1, max = 65535, message = "内容最多65535字")
  private String page_content;
  private int cat_id;
}
