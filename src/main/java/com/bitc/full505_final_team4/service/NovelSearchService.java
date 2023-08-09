package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.NovelSearchDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface NovelSearchService {
  Map<String, Object> getKakaoSearchIdList(String searchWord) throws Exception;

  Map<String, Object> getNaverSearchList(String searchWord) throws Exception;
}
