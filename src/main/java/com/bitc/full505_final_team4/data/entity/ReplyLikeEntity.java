package com.bitc.full505_final_team4.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reply_like")
@IdClass(ReplyLikeIdx.class)
@Getter
@Setter
@ToString
@NoArgsConstructor

public class ReplyLikeEntity {
  @Id
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "id")
  @ToString.Exclude
  private MemberEntity id;

  @Id
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "reply_idx")
  @ToString.Exclude
  private NovelReplyEntity replyIdx;

  @Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private String likeYn;

  @Builder
  public ReplyLikeEntity(MemberEntity id, NovelReplyEntity replyIdx, String likeYn) {
    this.id = id;
    this.replyIdx = replyIdx;
    this.likeYn = likeYn;
  }
}
