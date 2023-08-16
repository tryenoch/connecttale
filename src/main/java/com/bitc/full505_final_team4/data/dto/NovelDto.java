package com.bitc.full505_final_team4.data.dto;

import com.bitc.full505_final_team4.data.entity.NovelEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// NovelEntity 에 해당하는 dto
@Data
@NoArgsConstructor
public class NovelDto {
  private int novelIdx;
  private String novelTitle;
  private String novelThumbnail;
  private String novelAdult;
  private String ebookCheck;

  @Builder
  public NovelDto(int novelIdx, String novelTitle, String novelThumbnail, String novelAdult, String ebookCheck){
    this.novelIdx = novelIdx;
    this.novelTitle = novelTitle;
    this.novelThumbnail = novelThumbnail;
    this.novelAdult = novelAdult;
    this.ebookCheck = ebookCheck;
  }

  // dto 를 entity 로 변환
  public NovelEntity toEntity(NovelDto dto){
    return NovelEntity.builder()
      .novelTitle(novelTitle)
      .novelThumbnail(novelThumbnail)
      .novelAdult(novelAdult)
      .ebookCheck(ebookCheck)
      .build();
  }

  // entity 를 dto 로 변환
  public static NovelDto toDto(NovelEntity entity){
    return NovelDto.builder()
      .novelIdx(entity.getNovelIdx())
      .novelTitle(entity.getNovelTitle())
      .novelThumbnail(entity.getNovelThumbnail())
      .novelAdult(entity.getNovelAdult())
      .ebookCheck(entity.getEbookCheck())
      .build();
  }

}
