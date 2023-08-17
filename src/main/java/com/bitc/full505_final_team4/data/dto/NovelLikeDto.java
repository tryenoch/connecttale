package com.bitc.full505_final_team4.data.dto;

import com.bitc.full505_final_team4.data.entity.NovelLikeEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Builder
public class NovelLikeDto {
  private MemberDto id;
  private NovelDto novelIdx;
  private String likeYn;


  @Builder
  public NovelLikeDto(MemberDto id, NovelDto novelIdx, String likeYn) {
    this.id = id;
    this.novelIdx = novelIdx;
    this.likeYn = likeYn;
  }

  public static NovelLikeDto toDto(NovelLikeEntity novel) {
    return NovelLikeDto.builder()
        .id(MemberDto.toDto(novel.getId()))
        .novelIdx(NovelDto.toDto(novel.getNovelIdx()))
        .likeYn(novel.getLikeYn())
        .build();
  }

//   entity -> dto
//  public static NovelLikeDto toDto(NovelLikeEntity entity) {
//
//    return NovelLikeDto.builder()
//      .id(MemberDto.toDto(entity.getId()))
//      .novelIdx(NovelDto.toDto(entity.getNovelIdx()))
//      .likeYn(entity.getLikeYn())
//      .build();
//  }
}

