package com.bitc.full505_final_team4.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReportEntity {
  @Id
  @Column(name = "report_idx")
  private int reportIdx;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "reply_idx")
  @ToString.Exclude
  private NovelReplyEntity replyIdx;

  @Column(nullable = false, length = 300)
  private String reportContent;

  @Column(nullable = false, length = 20)
  private String reporter;

  @Column(nullable = false, length = 20)
  private String suspect;

  @Column(nullable = false)
  private LocalDateTime reportDt = LocalDateTime.now();

  @Builder ReportEntity (int reportIdx, NovelReplyEntity replyIdx, String reportContent, String reporter, String suspect, LocalDateTime reportDt) {
    this.reportIdx = reportIdx;
    this.replyIdx = replyIdx;
    this.reportContent = reportContent;
    this.reporter = reporter;
    this.suspect = suspect;
    this.reportDt = reportDt;
  }
}
