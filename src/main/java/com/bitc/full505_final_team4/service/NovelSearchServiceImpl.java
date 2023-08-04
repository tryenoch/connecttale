package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.entity.NovelCateEntity;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import com.bitc.full505_final_team4.data.repository.NovelCateRepository;
import com.bitc.full505_final_team4.data.repository.NovelPlatformRepository;
import com.bitc.full505_final_team4.data.repository.NovelRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*@EnableJpaRepositories(basePackages = {"com.bitc.full505_final_team4.data.entity"})*/
@Service
@RequiredArgsConstructor
public class NovelSearchServiceImpl implements NovelSearchService {
  private final NovelRepository novelRepository;
  private final NovelPlatformRepository novelPlatformRepository;
  private final NovelCateRepository novelCateRepository;

  // 셀레니움을 통한 크롤링(카카오페이지)
  private WebDriver driver;
  private WebElement element;
  private String url;

  public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
  public static String WEB_DRIVER_PATH = "C:/chromedriver/chromedriver.exe";


  @Override
  public List<String> getKakaoSearchList(String searchWord) throws Exception {
    // 셀레니움을 통해 검색 결과에 따른 작품 id 리스트 가져오기
    String url = "https://page.kakao.com/search/result?keyword=" + searchWord + "&categoryUid=11";


    List<String> kakaoSearchIdList = new ArrayList<>();

    try {
      // webDriver 경로 설정
      System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

      // webDriver 옵션 설정
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--disable-popup-blocking"); // 팝업 안띄움
      options.addArguments("--enable-automation");
      options.addArguments("--window-position=-100000,-100000");
      options.addArguments("--window-size=0,0");
      options.addArguments("--lang=ko");
      options.addArguments("--disable-gpu");            //gpu 비활성화
      options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음

      driver = new ChromeDriver(options);
      driver.get(url);

      Duration duration = Duration.ofSeconds(20);
      WebDriverWait wait = new WebDriverWait(driver, duration);

//      wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector(".flex-1 .cursor-pointer")));
//
      List<WebElement> elements = driver.findElements(By.cssSelector(".flex-1 cursor-pointer"));

      if (elements.isEmpty()) {
        System.out.println("css선택자를 못찾은 것 같음");
      }
      else {
        for (WebElement element : elements) {
          String kakaoSearchId = element.getAttribute("href").substring(9);
          kakaoSearchIdList.add(kakaoSearchId);
        }
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      driver.close();
    }
    return kakaoSearchIdList;
  }
}





























