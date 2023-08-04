package com.bitc.full505_final_team4.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "platform")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class NovelPlatformEntity {
  @Id
  private int platform;

  @Column(nullable = false, unique = true)
  private String platformId;

  @Id
  private int novelIdx;

  @Column(nullable = false, length = 100)
  private String novelTitle;

  @Column(nullable = false, length = 200)
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
  private String novelUpdateDate;

  @Column(length = 45)
  private String novelRelease;

  @Column(nullable = false, length = 100)
  public String cateList;




}
