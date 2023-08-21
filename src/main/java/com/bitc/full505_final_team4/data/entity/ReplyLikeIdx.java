package com.bitc.full505_final_team4.data.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
public class ReplyLikeIdx implements Serializable {
  @EqualsAndHashCode.Exclude
  private String id;

  @EqualsAndHashCode.Exclude
  private int replyIdx;
}
