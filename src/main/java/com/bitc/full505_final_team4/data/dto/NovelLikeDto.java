package com.bitc.full505_final_team4.data.dto;

import com.bitc.full505_final_team4.data.entity.NovelLikeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class NovelLikeDto {
  private MemberDto memberId;
  private NovelDto novelIdx;
  private char likeYn;


  @Builder
  public NovelLikeDto(MemberDto memberDto, NovelDto novelDto, char likeYn) {
    this.memberId = memberDto;
    this.novelIdx = novelDto;
    this.likeYn = likeYn;
  }

//   entity -> dto
//  public static NovelLikeDto toDto(NovelLikeEntity entity) {
//
//    return NovelLikeDto.builder()
//      .memberId(MemberDto.toDto(entity.getMemberId()))
//      .novelIdx(NovelDto.toDto(entity.getNovelIdx()))
//      .likeYn(entity.getLikeYn())
//      .build();
//  }
}

