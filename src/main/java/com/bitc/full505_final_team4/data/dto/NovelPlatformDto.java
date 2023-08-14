package com.bitc.full505_final_team4.data.dto;

import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NovelPlatformDto {
  private int platform; // 1 카카오 2 네이버 3 리디북스
//  private int novelIdx;
  private NovelKeyDto novelKeyDto;
  private String platformId;
  private String novelTitle;
  private String novelThumbnail;
  private String novelIntro;
  private String novelIntroImg;
  private String novelAuthor;
  private String novelPubli;
  private int novelCount;
  private String novelCompleteYn;
  private int novelPrice;
  private double novelStarRate;
  private String novelUpdateDate;
  private String novelRelease;
  private String cateList;
  private String novelOrEbook;
  private String novelAdult;

  @Builder
  public NovelPlatformDto(int platform, NovelKeyDto novelKeyDto, String platformId, String novelTitle, String novelThumbnail, String novelIntro, String novelIntroImg, String novelAuthor, String novelPubli, int novelCount, String novelCompleteYn, int novelPrice, double novelStarRate, String novelUpdateDate, String novelRelease, String cateList, String novelOrEbook, String novelAdult) {
    this.platform = platform;
    this.novelKeyDto = novelKeyDto;
    this.platformId = platformId;
    this.novelTitle = novelTitle;
    this.novelThumbnail = novelThumbnail;
    this.novelIntro = novelIntro;
    this.novelIntroImg = novelIntroImg;
    this.novelAuthor = novelAuthor;
    this.novelPubli = novelPubli;
    this.novelCount = novelCount;
    this.novelCompleteYn = novelCompleteYn;
    this.novelPrice = novelPrice;
    this.novelStarRate = novelStarRate;
    this.novelUpdateDate = novelUpdateDate;
    this.novelRelease = novelRelease;
    this.cateList = cateList;
    this.novelOrEbook = novelOrEbook;
    this.novelAdult = novelAdult;
  }


  // dto 를 entity 로 변환
  public NovelPlatformEntity toEntity(NovelRankDto dto){

    return NovelPlatformEntity.builder()
      .platform(platform)
      .platformId(platformId)
      .novelEntity(novelKeyDto.toEntity(novelKeyDto))
      .novelTitle(novelTitle)
      .novelThumbnail(novelThumbnail)
      .novelIntro(novelIntro)
      .novelIntroImg(novelIntroImg)
      .novelAuthor(novelAuthor)
      .novelCount(novelCount)
      .novelCompleteYn(novelCompleteYn)
      .novelPrice(novelPrice)
      .novelStarRate(novelStarRate)
      .novelUpdateDate(novelUpdateDate)
      .novelRelease(novelRelease)
      .cateList(cateList)
      .novelOrEbook(novelOrEbook)
      .novelAdult(novelAdult)
      .build();
  }

  // entity 를 dto 로 변환
  public static NovelPlatformDto toDto(NovelPlatformEntity entity){

    return NovelPlatformDto.builder()
      .platform(entity.getPlatform())
      .platformId(entity.getPlatformId())
      .novelKeyDto(NovelKeyDto.toDto(entity.getNovelEntity()))
      .novelTitle(entity.getNovelTitle())
      .novelThumbnail(entity.getNovelThumbnail())
      .novelIntro(entity.getNovelIntro())
      .novelIntroImg(entity.getNovelIntroImg())
      .novelAuthor(entity.getNovelAuthor())
      .novelCount(entity.getNovelCount())
      .novelCompleteYn(entity.getNovelCompleteYn())
      .novelPrice(entity.getNovelPrice())
      .novelStarRate(entity.getNovelStarRate())
      .novelUpdateDate(entity.getNovelUpdateDate())
      .novelRelease(entity.getNovelRelease())
      .cateList(entity.getCateList())
      .novelOrEbook(entity.getNovelOrEbook())
      .novelAdult(entity.getNovelAdult())
      .build();
  }

}
