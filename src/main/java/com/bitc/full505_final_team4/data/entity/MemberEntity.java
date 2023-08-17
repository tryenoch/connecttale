package com.bitc.full505_final_team4.data.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@DynamicInsert
@NoArgsConstructor
//@AllArgsConstructor
public class MemberEntity {

  @Id
  @Column(name = "id", nullable = false, unique = true, length = 45)
  private String id;

  @Column(nullable = false, length = 100)
  private String pw;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true, length = 100)
  private String nickname;

  @Column(nullable = false)
  private int gender;

  @Column(nullable = false)
  private String birthday;

  @Column(length = 100)
  private String oFile;

  @Column(length = 100)
  private String sFile;

  @Column(nullable = false)
  @ColumnDefault("1")
  private int grade;

  @Column(nullable = false)
  @ColumnDefault("N")
  private String deletedYn;

  @JsonIgnore
  @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<NovelLikeEntity> memberList = new ArrayList<>();

  @Builder
  public MemberEntity(String id, String pw, String name, String nickname, int gender, String birthday, String oFile, String sFile, int grade, String deletedYn) {
    this.id = id;
    this.pw = pw;
    this.name = name;
    this.nickname = nickname;
    this.gender = gender;
    this.birthday = birthday;
    this.oFile = oFile;
    this.sFile = sFile;
    this.grade = grade;
    this.deletedYn = deletedYn;
  }

}
