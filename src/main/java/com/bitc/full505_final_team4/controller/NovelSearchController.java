package com.bitc.full505_final_team4.controller;


import com.bitc.full505_final_team4.data.dto.NovelSearchDTO;
import com.bitc.full505_final_team4.service.NovelDetailService;
import com.bitc.full505_final_team4.service.NovelSearchService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.lang.model.util.ElementScanner6;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class NovelSearchController {

  private final NovelSearchService novelSearchService;
  private final NovelDetailService novelDetailService;


  // 카카오 검색 결과 데이터 가져오기
  @RequestMapping(value = "/searchKakao", method = RequestMethod.GET)
  public Object searchKakaoResult(@RequestParam("searchWord") String searchWord) throws Exception {

    // 카카오 검색 결과에 대한 작품 id 리스트 가져오기
    List<String> kakaoSearchIdList = new ArrayList<>();
    kakaoSearchIdList = novelSearchService.getKakaoSearchIdList(searchWord);

    return kakaoSearchIdList;
  }



  // 네이버 검색 결과 데이터 가져오기
  @RequestMapping(value = "/searchNaver", method = RequestMethod.GET)
  public Object searchNaverResult(@RequestParam("searchWord") String searchWord) throws Exception {

    // jsoup을 이용해서 naver시리즈 검색결과 리스트 가져오기
    Map<String, Object> naverSearchList = novelSearchService.getNaverSearchList(searchWord);

    return naverSearchList;
  }

//  @RequestMapping(value = "/searchRidi", method = RequestMethod.GET)
//  public Object searchRidiResult(@RequestParam("searchWord") String searchWord) throws Exception {
//    Map<String, Object> ridiSearchList = novelSearchService.getRidiSearchList(searchWord);
//
//    return ridiSearchList;
//  }

}
