package com.bitc.full505_final_team4.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "category")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class NovelCateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int cateIdx;

  @Column(nullable = false, unique = true, length = 100)
  private String cateName;
}
