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
  private char likeYn;


  @Builder
  public NovelLikeDto(MemberDto id, NovelDto novelIdx, char likeYn) {
    this.id = id;
    this.novelIdx = novelIdx;
    this.likeYn = likeYn;
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

