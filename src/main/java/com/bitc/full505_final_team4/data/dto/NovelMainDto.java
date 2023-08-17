package com.bitc.full505_final_team4.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NovelMainDto {
  /* platform table 기반 dto */
  private String platform;
  private String platformId; // 플랫폼에서 제공하는 아이디
  private int novelIndexNum; // 순위 정보로 주로 사용, platform 테이블에는 없음
  private int novelIdx; // 사이트 자체 아이디, novel 테이블과 연동
  private String novelTitle;
  private String novelThumbnail;
  private String novelIntro;
  private String novelAuthor;
  private String novelPubli; // 출판사
  private int novelCount; // 총 화수
  private String novelCompleteYn; // 완결 여부
  private String novelPrice;
  private double novelStarRate; // 별점
  private String novelUpdateDate; // 연재일
  private String novelRelease; // 소설 출시일
  private String novelRecentUpdate; // 최근 업데이트일
  private String cateList; // 소설 장르, arrayList로 변환 예정

  private String ebookCheck;
  private boolean adultsOnly; // 성인 여부, true 일 경우 세션 영역 나이 검사 및 19금 뱃지
}
