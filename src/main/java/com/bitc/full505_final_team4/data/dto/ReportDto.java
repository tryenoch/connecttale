package com.bitc.full505_final_team4.data.dto;

import com.bitc.full505_final_team4.data.entity.NovelReplyEntity;
import com.bitc.full505_final_team4.data.entity.ReportEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReportDto {
  private int reportIdx;
  private NovelReplyEntity replyIdx;
  private String reportContent;
  private String reporter;
  private String suspect;
  private LocalDateTime reportDt = LocalDateTime.now();


  @Builder ReportDto (int reportIdx, NovelReplyEntity replyIdx, String reportContent, String reporter, String suspect, LocalDateTime reportDt) {
    this.reportIdx = reportIdx;
    this.replyIdx = replyIdx;
    this.reportContent = reportContent;
    this.reporter = reporter;
    this.suspect = suspect;
    this.reportDt = reportDt;
  }

  public static ReportDto toDto(ReportEntity entity) {
    return ReportDto.builder()
      .reportIdx(entity.getReportIdx())
      .replyIdx(entity.getReplyIdx())
      .reportContent(entity.getReportContent())
      .reporter(entity.getReporter())
      .suspect(entity.getSuspect())
      .reportDt(entity.getReportDt())
      .build();
  }
}
