package com.bitc.full505_final_team4.service;

import java.util.HashMap;
import java.util.Map;

public interface NovelSearchService {
  Map<String, Object> getKakaoSearchList(String searchWord) throws Exception;
}
