package com.bitc.full505_final_team4.data.dto;

import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelReplyEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class NovelReplyDto2 {
  private int replyIdx;
  private int novelIdx;
  private String novelTitle;
  private String novelThumbnail;
  private String novelAdult;
  private String ebookCheck;
  private String replyContent;
  private String id;
  private String createDt;
  private String deletedYn;
  private String spoilerYn;


  @Builder
  public NovelReplyDto2(int replyIdx, int novelIdx, String novelTitle, String novelThumbnail, String novelAdult, String ebookCheck, String replyContent, String id, LocalDateTime createDt, String deletedYn, String spoilerYn) {
    this.replyIdx = replyIdx;
    this.novelIdx = novelIdx;
    this.novelTitle = novelTitle;
    this.novelThumbnail = novelThumbnail;
    this.novelAdult = novelAdult;
    this.ebookCheck = ebookCheck;
    this.replyContent = replyContent;
    this.id = id;
    if (createDt != null) {
      this.createDt = createDt.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
    this.deletedYn = deletedYn;
    this.spoilerYn = spoilerYn;
  }

  public static NovelReplyDto2 toDto(NovelReplyEntity entity) {
    return NovelReplyDto2.builder()
        .replyIdx(entity.getReplyIdx())
        .novelIdx(entity.getNovelIdx().getNovelIdx())
        .novelTitle(entity.getNovelIdx().getNovelTitle())
        .novelThumbnail(entity.getNovelIdx().getNovelThumbnail())
        .novelAdult(entity.getNovelIdx().getNovelAdult())
        .ebookCheck(entity.getNovelIdx().getEbookCheck())
        .replyContent(entity.getReplyContent())
        .id(entity.getId().getId())
        .createDt(entity.getCreateDt())
        .deletedYn(entity.getDeletedYn())
        .spoilerYn(entity.getSpoilerYn())
        .build();
  }

}
