package com.bitc.full505_final_team4.common;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonUtils {

  /* 작성자 : chanmi
  * jsonUrlParser 목적 : 완성된 url을 입력하면 java class 내에서 사용 가능한 json 객체로 만들어준다
  * build.gradle 에 json-simple 라이브러리가 존재해야함.
  * Get 방식의 Url만 가능
  * url 작성 예)
  * String urlStr = "https://book-api.ridibooks.com/books/";
      urlStr += novelId; // parameter 로 받아온 값 추가
      urlStr += "/notices"; // 최종 완성된 url을 넣을 것

  * 결과 ) JSONObject jsonObject = JsonUtils.jsonUrlParser(urlStr);
  *
  * 결과 가공 방식 예) NovelMainController testJson 메소드 참고
  * JSONArray noticeList = (JSONArray) resultJson.get("notices"); // 배열 변환 예
    JSONObject notice = (JSONObject) noticeList.get(0); // 객체 변환 예
    String noticeTitle = (String) notice.get("title"); // 문자열 변환 예
  * */

  public static JSONObject jsonUrlParser(String jsonUrl) throws Exception {

    // 최종 리턴 될 Json 객체
    // 스프링 내장 객체 JSONObject 이 아닌, import org.json.simple.JSONObject
    JSONObject resultJson = null;

    // Json 을 받아올 변수 초기화
    URL url = null;
    HttpURLConnection con = null;
    StringBuilder sb = new StringBuilder();

    try {
      // URL 객체 생성하여, json url 연결
      url = new URL(jsonUrl);
      con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("Content-type", "application/json");
      con.setDoOutput(true);

      BufferedReader br = new BufferedReader(
        new InputStreamReader(con.getInputStream(), "UTF-8")
      );

      while (br.ready()){
        // url 객체에서 가져온 값들이 모두 sb 에 저장이 된다.
        sb.append(br.readLine());
      }

      // 최종 변환된 Json 객체
      resultJson = (JSONObject) new JSONParser().parse(sb.toString());

    } catch (Exception e) {
      // 예외 발생 시
      System.out.println("error : " + e.getMessage());
      e.printStackTrace();

    }
    finally {
      con.disconnect();
    }

    return resultJson;
  }

}
