package com.bitc.full505_final_team4.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "platform")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class NovelPlatformEntity {
  @Id
  private int platform;

}
