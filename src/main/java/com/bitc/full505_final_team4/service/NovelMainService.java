package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.NovelMainDto;
import com.bitc.full505_final_team4.data.dto.NovelPlatformDto;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import org.json.simple.JSONArray;

import java.time.LocalDate;
import java.util.List;

public interface NovelMainService {

  // 확인용 데이터 불러오기
  boolean checkTodayRankData(int platform, int rankNum, LocalDate date) throws Exception;

  // 오늘 날짜의 리디북스 카테고리 별 순위 리스트 저장하기
  boolean storeRidiCategoryRankList (int category, int startNum) throws Exception;

  /* 리디북스 카테고리 별 순위 리스트 불러오기 */
  List<NovelMainDto> getRidiRankList(String category, int startNum) throws Exception;

  /* 네이버 순위 리스트 불러오기 */
  List<NovelMainDto> getNaverRankList(String startNum, String endNum, int totalPageNum) throws Exception;

  /*  카카오 순위 리스트 불러오기 */
  List<NovelMainDto> getKakaoList(String urlId) throws Exception;

  /* 카카오 특정 작품 개별 정보 가져오기 */
  NovelMainDto getKakaoNovel(String novelId) throws Exception;

  // 최신 리스트 itemCount 수만큼 들고오기
  List<NovelPlatformDto> getRecentNovelList(int itemCount) throws Exception;

  List<NovelPlatformDto> getMaxLikeNovelList(int itemCount) throws Exception;

  List<NovelPlatformDto> getCateNovelList (String cateItem, String itemCount) throws Exception;

  /* 리디 별점 계산기 */
  double getStarRate(JSONArray ratings) throws Exception;

  // 리디 카테고리 pk 생성 관련
  int ridiCategoryRankNum(int category) throws Exception;
}
