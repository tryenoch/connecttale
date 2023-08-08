package com.bitc.full505_final_team4.controller;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/novel")
public class NovelMainController {

  /* 리디북스 테스트 */
  @GetMapping("/testRidi")
  public Object testRidi() throws Exception {

    // 최종 결과값을 전달할 Object
    Map<String, Object> resultList = new HashMap<>();

    URL url = null;
    HttpURLConnection con = null;
    JSONObject result = null;
    StringBuilder sb = new StringBuilder();

    String novelId = "3049037206";

    try {
      String urlStr = "https://book-api.ridibooks.com/books/";
      urlStr += novelId;
      urlStr += "/notices";

      // URL 객체 생성, json ur 연결
      url = new URL(urlStr);
      con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("Content-type", "application/json");
      con.setDoOutput(true);

      BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

      while (br.ready()){
        // url 에서 가져온 값들이 모두 sb 에 저장이 된다.
        sb.append(br.readLine());
      }

      con.disconnect();
      result = (JSONObject) new JSONParser().parse(sb.toString());

      resultList.put("result", "success");
      System.out.println(result);


    } catch (Exception e){
      e.printStackTrace();
      resultList.put("result", "fail");
    }


    return resultList;
  }

}
