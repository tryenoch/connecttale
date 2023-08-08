package com.bitc.full505_final_team4.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
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
  private String Ofile;

  @Column(length = 100)
  private String Sfile;

  @Column(nullable = false)
  @ColumnDefault("1")
  private int grade;

  @Column(nullable = false)
  @ColumnDefault("N")
  private char deletedYn;
}
