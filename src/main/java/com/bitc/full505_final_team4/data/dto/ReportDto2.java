package com.bitc.full505_final_team4.data.dto;

import com.bitc.full505_final_team4.data.entity.NovelReplyEntity;
import com.bitc.full505_final_team4.data.entity.ReportEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReportDto2 {
  private int reportIdx;
  private int replyIdx;
  private String replyContent;
  private String novelTitle;
  private String reportContent;
  private String reporter;
  private String suspect;
  private LocalDateTime reportDt = LocalDateTime.now();


  @Builder
  ReportDto2(int reportIdx, int replyIdx, String replyContent, String novelTitle, String reportContent, String reporter, String suspect, LocalDateTime reportDt) {
    this.reportIdx = reportIdx;
    this.replyIdx = replyIdx;
    this.replyContent = replyContent;
    this.novelTitle = novelTitle;
    this.reportContent = reportContent;
    this.reporter = reporter;
    this.suspect = suspect;
    this.reportDt = reportDt;
  }

  public static ReportDto2 toDto(ReportEntity entity) {
    return ReportDto2.builder()
      .reportIdx(entity.getReportIdx())
      .replyIdx(entity.getReplyIdx().getReplyIdx())
      .replyContent(entity.getReplyIdx().getReplyContent())
      .novelTitle(entity.getReplyIdx().getNovelIdx().getNovelTitle())
      .reportContent(entity.getReportContent())
      .reporter(entity.getReporter())
      .suspect(entity.getSuspect())
      .reportDt(entity.getReportDt())
      .build();
  }
}
