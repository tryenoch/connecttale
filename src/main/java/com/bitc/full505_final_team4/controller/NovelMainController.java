package com.bitc.full505_final_team4.controller;

import com.bitc.full505_final_team4.common.JsonUtils;
import com.bitc.full505_final_team4.data.dto.NovelMainDto;
import com.bitc.full505_final_team4.service.NovelMainService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/novel")
public class NovelMainController {

  private final NovelMainService novelMainService;

  // jpa 테스트용
  @GetMapping("/testJpa")
  public Object testJpa(@RequestParam("date") String date) throws Exception{
    Map<String ,Object> result = new HashMap<>();

    LocalDate javaDate = LocalDate.now();
    String convertDate = javaDate.toString();
    String res = "";

    if (date.equals(convertDate)){
      res = "날짜가 같습니다.";
    } else {
      res = "날짜가 틀리네요";
      novelMainService.storeRidiCategoryRankList(1750, 1);
      novelMainService.storeRidiCategoryRankList(1650, 1);
      novelMainService.storeRidiCategoryRankList(6050, 1);
      novelMainService.storeRidiCategoryRankList(4150, 1);
    }

    result.put("result", res);

    return result;
  }


  /* Json 데이터 변환 및 가져오기 테스트 */
  @GetMapping("/testJson")
  public Object testJson(@RequestParam("novelId") String novelId) throws Exception {

    // 최종 결과값을 전달할 Object
    Map<String, Object> result = new HashMap<>();

    String urlStr = "https://book-api.ridibooks.com/books/";
    urlStr += novelId;
    urlStr += "/notices";

    JSONObject resultJson = JsonUtils.jsonUrlParser(urlStr);

    JSONArray noticeList = (JSONArray) resultJson.get("notices");
    JSONObject notice = (JSONObject) noticeList.get(0);
    String noticeTitle = (String) notice.get("title");

    if(resultJson != null){
      result.put("result", "success");
      result.put("notice", noticeTitle);
    } else {
      result.put("result", "fail");
    }

    return result;
  }

  /* 리디북스 순위 리스트 가져오기 */
  @GetMapping("/ridiRankList")
  public Object getRidiRankList(@RequestParam("category") String category) throws Exception {

    Map<String, Object> result = new HashMap<>();

    /* 실시간 순위 1위부터 20위까지 리스트 반환 */
    List<NovelMainDto> ridiNovelList = novelMainService.getRidiRankList(category, 1);

    if(ridiNovelList != null){
      result.put("result", "success");
      result.put("ridiNovelList", ridiNovelList);
    } else {
      result.put("result", "Backend error");
    }

    return result;
  }

  /* 네이버 순위 리스트 가져오기 */
  @GetMapping("/naverRankList")
  public Object getNaverRankList(@RequestParam("startNum") String startNum, @RequestParam("endNum") String endNum) throws Exception{
    Map<String, Object> result = new HashMap<>();

    List<NovelMainDto> naverNovelList = novelMainService.getNaverRankList(startNum, endNum, 1);

    if(!ObjectUtils.isEmpty(naverNovelList)){
      result.put("naverNovelList", naverNovelList);
      result.put("result", "success");
    } else {
      result.put("result", "Backend error");
    }

    return result;
  }

  @GetMapping("/kakaoRankList")
  public Object getKakaoRankList(@RequestParam("urlId") String urlId) throws Exception {
    Map<String, Object> result = new HashMap<>();

    List<NovelMainDto> kakaoNovelList = novelMainService.getKakaoList(urlId);
    if (!ObjectUtils.isEmpty(kakaoNovelList)){
      result.put("kakaoNovelList", kakaoNovelList);
      result.put("result", "success");
    } else {
      result.put("result", "Backend error");
    }

    return result;
  }

}
