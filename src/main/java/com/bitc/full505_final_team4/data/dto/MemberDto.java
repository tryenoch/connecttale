package com.bitc.full505_final_team4.data.dto;

import lombok.Data;

@Data
public class MemberDto {
  private String id;
  private String pw;
  private String name;
  private String nickname;
  private int gender;
  private String birthday;
}
