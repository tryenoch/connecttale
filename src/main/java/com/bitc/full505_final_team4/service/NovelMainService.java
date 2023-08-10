package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.NovelMainDto;
import org.json.simple.JSONArray;

import java.util.List;

public interface NovelMainService {

  /* 리디북스 카테고리 별 순위 리스트 불러오기 */
  List<NovelMainDto> getRidiRankList(String category, int startNum) throws Exception;

  /*  카카오 순위 리스트 불러오기 */
  List<NovelMainDto> getKakaoList(String urlId) throws Exception;

  /* 카카오 특정 작품 개별 정보 가져오기 */
  NovelMainDto getKakaoNovel(String novelId) throws Exception;

  /* 리디 별점 계산기 */
  String getStarRate(JSONArray ratings) throws Exception;
}
