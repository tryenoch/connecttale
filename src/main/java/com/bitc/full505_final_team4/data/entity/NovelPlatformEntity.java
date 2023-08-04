package com.bitc.full505_final_team4.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;


@IdClass(NovelPlatFormIdx.class)
@Entity
@Table(name = "platform")
@IdClass(NovelPlatformGroupKey.class)
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
<<<<<<< HEAD
  @JoinColumn(name = "novel_idx")
  @ToString.Exclude
  private int novelIdx;
=======
  @ManyToOne
  @JoinColumn(name = "novel_idx")
  private NovelEntity novelEntity;
>>>>>>> origin/chanmi

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
