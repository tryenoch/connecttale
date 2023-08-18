package com.bitc.full505_final_team4.data.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "novel")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class NovelEntity {
  @Id
  @Column(name = "novel_idx")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int novelIdx;

  @Column(nullable = false, length = 100)
  private String novelTitle;

  @Column(nullable = false, length = 500)
  private String novelThumbnail;

  @Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private String novelAdult;

  @Column(nullable = false, length = 10)
  private String ebookCheck;

  @JsonIgnore
  @OneToMany(mappedBy = "novelIdx", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<NovelPlatformEntity> novelPlatformList = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "novelIdx", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<NovelLikeEntity> novelLikeList = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "novelIdx", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<NovelReplyEntity> novelReplyList = new ArrayList<>();

  @Builder
  public NovelEntity(String novelTitle, String novelThumbnail, String  novelAdult, String ebookCheck){
    this.novelTitle = novelTitle;
    this.novelThumbnail = novelThumbnail;
    this.novelAdult = novelAdult;
    this.ebookCheck = ebookCheck;
  }

}
