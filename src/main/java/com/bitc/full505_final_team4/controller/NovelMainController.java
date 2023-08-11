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

import java.util.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/novel")
public class NovelMainController {

  private final NovelMainService novelMainService;

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
