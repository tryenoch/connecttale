package com.bitc.full505_final_team4.data.dto;

import com.bitc.full505_final_team4.data.entity.NovelReplyEntity;
import com.bitc.full505_final_team4.data.entity.ReplyLikeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReplyLikeDto {
  private MemberDto id;
  private NovelReplyDto replyIdx;
  private String likeYn;


  @Builder
  public ReplyLikeDto(MemberDto id, NovelReplyDto replyIdx, String likeYn) {
    this.id = id;
    this.replyIdx = replyIdx;
    this.likeYn = likeYn;
  }

  public static ReplyLikeDto toDto(ReplyLikeEntity entity) {
    return ReplyLikeDto.builder()
      .id(MemberDto.toDto(entity.getId()))
      .replyIdx(NovelReplyDto.toDto(entity.getReplyIdx()))
      .likeYn(entity.getLikeYn())
      .build();
  }


}
