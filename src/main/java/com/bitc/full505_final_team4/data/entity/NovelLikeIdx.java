package com.bitc.full505_final_team4.data.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NovelLikeIdx implements Serializable {
  // db 컬럼명 그대로 가져와야됨
  @EqualsAndHashCode.Include
  private String id;

  @EqualsAndHashCode.Include
  private int novelIdx;
}
