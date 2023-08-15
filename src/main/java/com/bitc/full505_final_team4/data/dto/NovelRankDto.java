package com.bitc.full505_final_team4.data.dto;

import com.bitc.full505_final_team4.data.entity.NovelRankEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class NovelRankDto {
  private int platform; // 1 카카오, 2 네이버, 3 리디북스
  private int rankNum;
  private String title;
  private String author;
  private String thumbnail;
  private int category; // 1750 : 판타지 / 1650 : 로맨스 / 6050 : 로판 / 4150: BL
  private String platformId;
  private String updateDate;
  private int adultsOnly;

  @Builder
  public NovelRankDto(int platform, int rankNum, String title, String author, String thumbnail, int category, String platformId, String updateDate, int adultsOnly){
    this.platform = platform;
    this.rankNum = rankNum;
    this.title = title;
    this.thumbnail = thumbnail;
    this.category = category;
    this.platformId = platformId;
    this.updateDate = updateDate;
    this.adultsOnly = adultsOnly;
    this.author = author;
  }

  // dto 를 entity 로 변환
  public NovelRankEntity toEntity(NovelRankDto dto){
    return NovelRankEntity.builder()
      .platform(platform)
      .rankNum(rankNum)
      .title(title)
      .author(author)
      .thumbnail(thumbnail)
      .category(category)
      .platformId(platformId)
      .updateDate(LocalDate.parse(updateDate)) // 현재 날짜 구하기(시스템 시계, 시스템 타임존 기준)
      .adultsOnly(adultsOnly)
      .build();
  }

  // entity 를 dto 로 변환
  public static NovelRankDto toDto(NovelRankEntity entity){
    return NovelRankDto.builder()
      .platform(entity.getPlatform())
      .rankNum(entity.getRankNum())
      .author(entity.getAuthor())
      .title(entity.getTitle())
      .thumbnail(entity.getThumbnail())
      .category(entity.getCategory())
      .platformId(entity.getPlatformId())
      .updateDate(entity.getUpdateDate().toString())
      .adultsOnly(entity.getAdultsOnly())
      .build();
  }

}
