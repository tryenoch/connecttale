package com.bitc.full505_final_team4.data.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;


@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@DynamicInsert
@NoArgsConstructor
public class MemberEntity {

  @Id
  @Column(nullable = false, unique = true, length = 45)
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

  @Builder
  public MemberEntity(String id, String pw, String name, String nickname, int gender, String birthday, int grade, String deletedYn) {
    this.id = id;
    this.pw = pw;
    this.name = name;
    this.nickname = nickname;
    this.gender = gender;
    this.birthday = birthday;
    this.grade = grade;
    this.deletedYn = deletedYn;
  }
}
