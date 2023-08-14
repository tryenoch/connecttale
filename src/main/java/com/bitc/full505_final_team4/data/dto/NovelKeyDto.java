package com.bitc.full505_final_team4.data.dto;

import com.bitc.full505_final_team4.data.entity.NovelEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// NovelEntity 에 해당하는 dto
@Data
@NoArgsConstructor
public class NovelKeyDto {
  private int novelIdx;
  private String novelTitle;
  private String novelThumbnail;
  private String novelAdult;

  @Builder
  public NovelKeyDto(int novelIdx, String novelTitle, String novelThumbnail, String novelAdult){
    this.novelIdx = novelIdx;
    this.novelTitle = novelTitle;
    this.novelThumbnail = novelThumbnail;
    this.novelAdult = novelAdult;
  }

  // dto 를 entity 로 변환
  public NovelEntity toEntity(NovelKeyDto dto){
    return NovelEntity.builder()
      .novelTitle(novelTitle)
      .novelThumbnail(novelThumbnail)
      .novelAdult(novelAdult)
      .build();
  }

  // entity 를 dto 로 변환
  public static NovelKeyDto toDto(NovelEntity entity){
    return NovelKeyDto.builder()
      .novelIdx(entity.getNovelIdx())
      .novelTitle(entity.getNovelTitle())
      .novelThumbnail(entity.getNovelThumbnail())
      .novelAdult(entity.getNovelAdult())
      .build();
  }

}
