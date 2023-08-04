package com.bitc.full505_final_team4.controller;


import com.bitc.full505_final_team4.service.NovelDetailService;
import com.bitc.full505_final_team4.service.NovelSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class NovelSearchController {

  private final NovelSearchService novelSearchService;
  private final NovelDetailService novelDetailService;


  // 검색 결과 데이터 가져오기
  @RequestMapping(value = "/searchResult", method = RequestMethod.GET)
  public Object searchResultList() throws Exception {

    List<String> kakaoSearchIdList = new ArrayList<>();
    kakaoSearchIdList = novelSearchService.getKakaoSearchList("사람");

    return kakaoSearchIdList;
  }
}
