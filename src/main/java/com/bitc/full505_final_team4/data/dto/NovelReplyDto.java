package com.bitc.full505_final_team4.data.dto;

import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelReplyEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NovelReplyDto {
  private int replyIdx;
  private NovelEntity novelIdx;
  private String replyContent;
  private MemberEntity id;
  private LocalDateTime createDt = LocalDateTime.now();
  private String deletedYn;
  private String spoilerYn;


  @Builder
  public NovelReplyDto(int replyIdx, NovelEntity novelIdx, String replyContent, MemberEntity id, LocalDateTime createDt, String deletedYn, String spoilerYn) {
    this.replyIdx = replyIdx;
    this.novelIdx = novelIdx;
    this.replyContent = replyContent;
    this.id = id;
    this.createDt = createDt;
    this.deletedYn = deletedYn;
    this.spoilerYn = spoilerYn;
  }

  public static NovelReplyDto toDto(NovelReplyEntity entity) {
    return NovelReplyDto.builder()
      .replyIdx(entity.getReplyIdx())
      .novelIdx(entity.getNovelIdx())
      .replyContent(entity.getReplyContent())
      .id(entity.getId())
      .createDt(entity.getCreateDt())
      .deletedYn(entity.getDeletedYn())
      .spoilerYn(entity.getSpoilerYn())
      .build();
  }

}
