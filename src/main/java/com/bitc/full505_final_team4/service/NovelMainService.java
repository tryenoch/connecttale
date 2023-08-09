package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.NovelMainDto;
import org.json.simple.JSONArray;

import java.util.List;

public interface NovelMainService {

  /* 리디북스 카테고리 별 순위 리스트 불러오기 */
  List<NovelMainDto> getRidiRankList(String category, int startNum) throws Exception;

  String getStarRate(JSONArray ratings) throws Exception;
}
