package com.bitc.full505_final_team4.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
  private String id;
  private String pw;
  private String name;
  private String nickname;
  private int gender;
  private String birthday;
}
