package com.bitc.full505_final_team4.data.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "novel_reply")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class NovelReplyEntity {
  @Id
  @Column(name = "reply_idx")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int replyIdx;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "novel_idx", nullable = false)
  @ToString.Exclude
  private NovelEntity novelIdx;

  @Column(nullable = false, length = 300)
  private String replyContent;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "create_id", nullable = false)
  @ToString.Exclude
  private MemberEntity createId;

  @Column(nullable = false)
  private LocalDateTime createDt = LocalDateTime.now();

  @Column(nullable = false)
  @ColumnDefault("N")
  private String deletedYn;

  @Column(nullable = false)
  @ColumnDefault("N")
  private String spoilerYn;
}
