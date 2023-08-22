package com.bitc.full505_final_team4.data.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.openqa.selenium.devtools.Reply;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "novel_reply")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
  @JoinColumn(name = "id", nullable = false)
  @ToString.Exclude
  private MemberEntity id;

  @Column(nullable = false)
  private LocalDateTime createDt = LocalDateTime.now();

  @Column(nullable = false,  columnDefinition = "CHAR(1) DEFAULT 'N'")
  private String deletedYn;

  @Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private String spoilerYn;

  @JsonIgnore
  @OneToMany(mappedBy = "replyIdx", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<ReplyLikeEntity> replyLikeList = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "replyIdx", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<ReportEntity> reportList = new ArrayList<>();

  @Builder NovelReplyEntity(int replyIdx, NovelEntity novelIdx, String replyContent, MemberEntity id, LocalDateTime createDt, String deletedYn, String spoilerYn) {
    this.replyIdx = replyIdx;
    this.novelIdx = novelIdx;
    this.replyContent = replyContent;
    this.id = id;
    this.createDt = createDt;
    this.deletedYn = deletedYn;
    this.spoilerYn = spoilerYn;
  }

}
