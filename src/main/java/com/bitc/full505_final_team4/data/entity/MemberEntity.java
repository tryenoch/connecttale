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
  private int grade = 1;

  @Column(nullable = false)
  private String deletedYn = "N";

}
