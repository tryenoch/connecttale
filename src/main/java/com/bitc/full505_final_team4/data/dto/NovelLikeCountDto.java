package com.bitc.full505_final_team4.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NovelLikeCountDto {

  private int novel_idx;
  // jpa count로 반환 되는 건 Long 타입이다
  private Long count;

}
