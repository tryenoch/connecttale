package com.bitc.full505_final_team4.data.dto;


import com.bitc.full505_final_team4.data.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MemberDto {
  private String id;
  private String pw;
  private String name;
  private String nickname;
  private int gender;
  private String birthday;
  private String oFile;
  private String sFile;
  private int grade;
  private String deletedYn;


  @Builder
  public MemberDto(String id, String pw, String name, String nickname, int gender, String birthday, String sFile, String oFile, int grade, String deletedYn) {
    this.id = id;
    this.pw = pw;
    this.name = name;
    this.nickname = nickname;
    this.gender = gender;
    this.birthday = birthday;
    this.sFile = sFile;
    this.oFile = oFile;
    this.grade = grade;
    this.deletedYn = deletedYn;
  }


  public MemberEntity tnEntity(MemberDto dto) {
    return MemberEntity.builder()
      .id(id)
      .pw(pw)
      .name(name)
      .nickname(nickname)
      .gender(gender)
      .birthday(birthday)
      .oFile(oFile)
      .sFile(sFile)
      .grade(grade)
      .deletedYn(deletedYn)
      .build();
  }

  public static MemberDto toDto(MemberEntity entity) {
    return MemberDto.builder()
      .id(entity.getId())
      .pw(entity.getPw())
      .name(entity.getName())
      .nickname(entity.getNickname())
      .gender(entity.getGender())
      .birthday(entity.getBirthday())
      .oFile(entity.getOFile())
      .sFile(entity.getSFile())
      .grade(entity.getGrade())
      .deletedYn(entity.getDeletedYn())
      .build();
  }
}


