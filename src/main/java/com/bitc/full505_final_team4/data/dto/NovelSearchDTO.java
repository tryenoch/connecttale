package com.bitc.full505_final_team4.data.dto;

import lombok.Data;

@Data
public class NovelSearchDTO {
  private int platform;
  private String novelThumbnail;
  private String novelTitle;
  private String novelAuthor;
  private double novelStarRate;
  private String novelPubli;
  private String cateList;
  private int novelCount;
  private String novelCompleteYn;
  private String novelUpdateDate;
  private String novelIntro;
  private int novelPrice;
  private String novelRelease;
}
