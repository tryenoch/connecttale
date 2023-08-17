package com.bitc.full505_final_team4.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "novel_like")
@IdClass(NovelLikeIdx.class)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class NovelLikeEntity {

  @Id
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "id")
  @ToString.Exclude
  private MemberEntity id;

  @Id
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "novel_idx")
  @ToString.Exclude
  private NovelEntity novelIdx;

  @Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private char likeYn;

  @Builder
  public NovelLikeEntity(MemberEntity id, NovelEntity novelIdx, char likeYn) {
    this.id = id;
    this.novelIdx = novelIdx;
    this.likeYn = likeYn;
  }
}
