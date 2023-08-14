package com.bitc.full505_final_team4.data.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

  @Column(nullable = false, length = 500, unique = true)
  private String novelThumbnail;

  @Column(nullable = false, length = 1)
  @ColumnDefault("N")
  private char novelAdult;

  @Builder
  public NovelEntity(String novelTitle, String novelThumbnail, char novelAdult){
    this.novelTitle = novelTitle;
    this.novelThumbnail = novelThumbnail;
    this.novelAdult = novelAdult;
  }

}
