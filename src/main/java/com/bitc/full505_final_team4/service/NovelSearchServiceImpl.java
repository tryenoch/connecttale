package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.entity.NovelCateEntity;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@RequiredArgsConstructor
@Service
public class NovelSearchServiceImpl implements NovelSearchService {
  private final NovelEntity novelEntity;
  private final NovelPlatformEntity novelPlatformEntity;
  private final NovelCateEntity novelCateEntity;

  // 셀레니움을 통한 크롤링(카카오페이지)
  private WebDriver driver;
  private WebElement element;
  private String url;

  public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
  public static String WEB_DRIVER_PATH = "C:/chromedriver/chromedriver.exe";


  @Override
  public Map<String, Object> getKakaoSearchList(String searchWord) throws Exception {
    // 카카오 페이지 크롤링 - 검색 결과에 따른 데이터리스트에서 id값들 가져오기
    
    return null;
  }
}





























