package com.bitc.full505_final_team4.data.dto;

<<<<<<< HEAD
=======
import com.bitc.full505_final_team4.data.entity.BoardEntity;
>>>>>>> origin/main
import com.bitc.full505_final_team4.data.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
<<<<<<< HEAD
@NoArgsConstructor
@Builder
=======
>>>>>>> origin/main
public class MemberDto {
  private String id;
  private String pw;
  private String name;
  private String nickname;
  private int gender;
  private String birthday;
<<<<<<< HEAD
  private String oFile;
  private String sFile;
  private int grade;
  private String deletedYn;


  @Builder
  public MemberDto(String id, String pw, String name, String nickname, int gender, String birthday, String oFile, String sFile, int grade, String deletedYn) {
=======
  private String sFile;
  private String oFile;
  private int grade;
  private String deletedYn;

  @Builder
  public MemberDto(String id, String pw, String name, String nickname, int gender, String birthday, String sFile, String oFile, int grade, String deletedYn) {
>>>>>>> origin/main
    this.id = id;
    this.pw = pw;
    this.name = name;
    this.nickname = nickname;
    this.gender = gender;
    this.birthday = birthday;
<<<<<<< HEAD
    this.oFile = oFile;
    this.sFile = sFile;
=======
    this.sFile = sFile;
    this.oFile = oFile;
>>>>>>> origin/main
    this.grade = grade;
    this.deletedYn = deletedYn;
  }

<<<<<<< HEAD
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
=======
  public static MemberDto toDto(MemberEntity entity) {
    return MemberDto.builder()
        .id(entity.getId())
        .pw(entity.getPw())
        .name(entity.getName())
        .nickname(entity.getNickname())
        .gender(entity.getGender())
        .birthday(entity.getBirthday())
        .sFile(entity.getSFile())
        .oFile(entity.getOFile())
        .grade(entity.getGrade())
        .deletedYn(entity.getDeletedYn())
        .build();
>>>>>>> origin/main
  }
}


