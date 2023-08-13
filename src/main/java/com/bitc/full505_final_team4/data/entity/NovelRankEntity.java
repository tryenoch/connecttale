package com.bitc.full505_final_team4.data.entity;

import com.bitc.full505_final_team4.data.dto.NovelRankDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "novel_rank")
@Entity
@Data
@NoArgsConstructor
@IdClass(PlatformRankIdx.class)
public class NovelRankEntity {
  @Id
  @Column(name = "platform")
  private int platform; // 1 카카오, 2 네이버, 3 리디북스

  @Id
  @Column(name = "rank_num")
  private int rankNum;

  @Column(nullable = false, length = 100)
  private String title;

  @Column(length = 45)
  private String author;

  @Column(nullable = false, length = 200)
  private String thumbnail;

  private int category; // 1750 : 판타지 / 1650 : 로맨스 / 6050 : 로판 / 4150: BL

  @Column(nullable = false, length = 45)
  private String platformId;

  // 현재 날짜 입력, 로컬 시스템 기준
  @Column(nullable = false)
  private LocalDate updateDate = LocalDate.now();

  @Column(nullable = false)
  private int adultsOnly = 0;

  @Builder
  public NovelRankEntity(int platform, int rankNum, String title, String author, String thumbnail, String platformId, LocalDate updateDate, int category, int adultsOnly){
    this.platform = platform;
    this.rankNum = rankNum;
    this.title = title;
    this.author = author;
    this.thumbnail = thumbnail;
    this.category = category;
    this.platformId = platformId;
    this.updateDate = updateDate;
    this.adultsOnly = adultsOnly;
  }


}
