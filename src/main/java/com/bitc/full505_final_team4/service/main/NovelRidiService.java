package com.bitc.full505_final_team4.service.main;

import com.bitc.full505_final_team4.data.dto.NovelMainDto;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public interface NovelRidiService {

  // 오늘 날짜의 리디북스 카테고리 별 순위 리스트 저장하기
  boolean storeRidiCategoryRankList (int category, int startNum) throws Exception;

  /* 리디북스 카테고리 별 순위 리스트 불러오기 */
  List<NovelMainDto> getRidiRankList(String category, int startNum) throws Exception;

  // 리디북스 최신작 리스트 불러온 후 테이블에 없는 작품 저장하기(특정 카테고리별)
  boolean starRidiRecentNovel(int category) throws Exception;

  // json object 에 있는 novel table 관련 데이터 entity 형태로 들고오기
  NovelEntity getCateNovelEntityFromJson(JSONObject novel) throws Exception;

  // json object 에 있는 platform table 관련 데이터 entity 형태로 들고오기
  NovelPlatformEntity getCatePlatformEntityFromJson(NovelEntity novelEntity, JSONObject novelData) throws Exception;

  /* 리디 별점 계산기 */
  String getStarRate(JSONArray ratings) throws Exception;

  // 리디 카테고리 pk 생성 관련
  int ridiCategoryRankNum(int category) throws Exception;
}
