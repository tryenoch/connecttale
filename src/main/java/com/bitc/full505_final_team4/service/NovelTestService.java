package com.bitc.full505_final_team4.service;

import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class NovelTestService {

  public Object getGraphqlTest(){

    String urlString = "https://page.kakao.com/graphql";

    try {
      URL url = new URL(urlString);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");

      String qurey = "";



    } catch (Exception e){

    }

    return null;
  }

}
