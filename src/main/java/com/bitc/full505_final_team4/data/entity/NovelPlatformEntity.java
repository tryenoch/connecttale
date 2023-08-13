package com.bitc.full505_final_team4.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Table(name = "platform")
@IdClass(NovelPlatFormIdx.class)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class NovelPlatformEntity {

  /* 복합키 구성 클래스 @IdClass(NovelPlatFormIdx.class) */
  @Id
  @Column(name = "platform")
  private int platform;

  @Column(nullable = false, unique = true)
  private String platformId;

  @Id
  @ManyToOne
  @JoinColumn(name = "novel_idx")
  private NovelEntity novelEntity;

  @Column(nullable = false, length = 100)
  private String novelTitle;

  @Column(nullable = false, length = 500)
  private String novelThumbnail;

  @Column(nullable = false, length = 500)
  private String novelIntro;

  @Column(length = 200)
  private String novelIntroImg;

  @Column(nullable = false, length = 45)
  private String novelAuthor;

  // 출판사
  @Column(length = 50)
  private String novelPubli;

  // 총화수
  @Column(nullable = false)
  private int novelCount;

  @Column(nullable = false, length = 45)
  @ColumnDefault("N")
  private String novelCompleteYn;

  @Column(nullable = false)
  private int novelPrice;

  @Column(nullable = false)
  private double novelStarRate;

  @Column(length = 50)
  private String novelUpdateDate; // 연재 요일

  @Column(length = 45)
  private String novelRelease;

  @Column(nullable = false, length = 100)
  public String cateList;

  @Column(length = 45)
  public String novelRecentUpdate;

  @Column(nullable = false, length = 50)
  public String novelOrEbook;

  @Column(nullable = false, length = 1)
  @ColumnDefault("N")
  private String novelAdult;

  @Builder
  public NovelPlatformEntity(int platform, String platformId, String novelTitle, NovelEntity novelEntity, String novelThumbnail, String novelIntro, String novelIntroImg, String novelAuthor, String novelPubli, int novelCount, String novelCompleteYn, int novelPrice, double novelStarRate, String novelUpdateDate, String novelRelease, String cateList, String novelOrEbook, String novelAdult) {
    this.platform = platform;
    this.novelEntity = novelEntity;
    this.platformId = platformId;
    this.novelTitle = novelTitle;
    this.novelThumbnail = novelThumbnail;
    this.novelIntro = novelIntro;
    this.novelIntroImg = novelIntroImg;
    this.novelAuthor = novelAuthor;
    this.novelCount = novelCount;
    this.novelCompleteYn = novelCompleteYn;
    this.novelPrice = novelPrice;
    this.novelStarRate = novelStarRate;
    this.novelUpdateDate = novelUpdateDate;
    this.novelRelease = novelRelease;
    this.cateList = cateList;
    this.novelOrEbook = novelOrEbook;
    this.novelAdult = novelAdult;
    this.novelPubli = novelPubli;
  }


}
