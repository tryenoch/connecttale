package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.common.JsonUtils;
import com.bitc.full505_final_team4.data.dto.NovelMainDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NovelMainServiceImpl implements NovelMainService{

  /* 리디북스 카테고리 별 순위 리스트 불러오기
  * 시작번호로부터 20개 출력
  * 총 100위까지 출력되도록 함 */
  @Override
  public List<NovelMainDto> getRidiRankList(String category, int startNum) throws Exception {

    List<NovelMainDto> novelDtoList = new ArrayList<>();

    String url = "https://api.ridibooks.com/v2/bestsellers?category_includes=";
      url += category + "&offset=";
      url += (startNum - 1) + "&limit=20&period=DAILY";

    // 페이지 json 객체로 변환
    JSONObject novelResult = (JSONObject) JsonUtils.jsonUrlParser(url).get("data");
    ArrayList<JSONObject> novelList = (ArrayList<JSONObject>) novelResult.get("items"); // 배열 변환

    if (!novelList.isEmpty()){
      for(JSONObject novelItem : novelList){
        // 각각 item에 들어있는 book Object
        HashMap<String, Object > book = (HashMap<String, Object>) novelItem.get("book");

        // 값을 저장할 dto
        NovelMainDto novel = new NovelMainDto();

        // 소설 순위
        novel.setNovelIndexNum(novelList.indexOf(novelItem) + 1); // novelItem의 인덱스 번호

        // 플랫폼 제공 아이디
        novel.setPlatformId(book.get("book_id").toString());

        // 소설 제목 얻어오기
        JSONObject serial = (JSONObject) book.get("serial");
        novel.setNovelTitle(serial.get("title").toString());

        // 작가 이름 얻어오기
        ArrayList authorsList = (ArrayList) book.get("authors");
        HashMap<String, Object> authors = (HashMap<String, Object>) authorsList.get(0);
        novel.setNovelAuthor(authors.get("name").toString());

        // 소설 썸네일
        JSONObject cover = (JSONObject) book.get("cover");
        novel.setNovelThumbnail(cover.get("large").toString());

        // 소설 카테고리
        JSONArray categories = (JSONArray) book.get("categories");
        JSONObject categoryFirst = (JSONObject) categories.get(0);
        novel.setCateList(categoryFirst.get("name").toString());

        // 소설 별점
        JSONArray ratings = (JSONArray) book.get("ratings");
        novel.setNovelStarRate(getStarRate(ratings)); // 하위에 구현한 함수 사용

        // 성인 여부
        novel.setAdultsOnly((Boolean) book.get("adults_only"));

        novelDtoList.add(novel);
      }

      return novelDtoList;
    }

    return null;
  }

  /* Ridi Json 에서 들고온 ratings 별점으로 변환하기 */
  @Override
  public String getStarRate(JSONArray ratings) throws Exception {

    /* 계산식
    * ((1점 * 1점 count) + ... + (5점 * 5점 count)) / totalCount
    * */

    String starRate = "";

    double multiRating = 0;
    double totalCount = 0;

    /* count * rating 총합 구하기 */

    for (int i = 0; i < ratings.size(); i++) {

      JSONObject rateObject = (JSONObject) ratings.get(i);
      int rating = Integer.parseInt(rateObject.get("rating").toString()) ;
      int count = Integer.parseInt(rateObject.get("count").toString()) ;

      int multiRate = rating * count;

      multiRating += multiRate;
      totalCount += count;

    }

    double total = multiRating / totalCount;
    // 왜 한자리수 올림이 안되는 건지...
//    total = (double) Math.ceil((total * 100) / 100.0);

    // 소수점 한자리까지 보여주는 별점 반환
    starRate = String.format("%.1f", total);

    return starRate;
  }
}

