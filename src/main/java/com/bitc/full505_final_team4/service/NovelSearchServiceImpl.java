package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.NovelSearchDTO;
import com.bitc.full505_final_team4.data.entity.NovelCateEntity;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import com.bitc.full505_final_team4.data.repository.NovelCateRepository;
import com.bitc.full505_final_team4.data.repository.NovelPlatformRepository;
import com.bitc.full505_final_team4.data.repository.NovelRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.Duration;
import java.util.*;

/*@EnableJpaRepositories(basePackages = {"com.bitc.full505_final_team4.data.entity"})*/
@Service
@RequiredArgsConstructor
public class NovelSearchServiceImpl implements NovelSearchService {
  private final NovelRepository novelRepository;
  private final NovelPlatformRepository novelPlatformRepository;
  private final NovelCateRepository novelCateRepository;

  // 셀레니움을 통한 크롤링(카카오페이지)
  private WebDriver driver;

  public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
  public static String WEB_DRIVER_PATH = "C:\\chromedriver\\chromedriver.exe";


  // 셀레니움을 통해 검색 결과에 따른 카카오페이지 작품 id 리스트 가져오기
  @Override
  public List<String> getKakaoSearchIdList(String searchWord) throws Exception {
    String url = "https://page.kakao.com/search/result?keyword=" + searchWord + "&categoryUid=11";

    List<String> kakaoSearchIdList = new ArrayList<>();
    kakaoSearchIdList.add("kakaoIdList");

    System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

    // webDriver 옵션 설정
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--disable-popup-blocking"); // 팝업 안띄움
//    options.addArguments("--headless"); // 브라우저 창 숨기고 실행
    options.addArguments("--enable-automation");
    options.addArguments("--window-position=-100000,-100000");
    options.addArguments("--window-size=0,0");
    options.addArguments("--lang=ko");
    options.addArguments("--disable-gpu");            //gpu 비활성화
    options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음

    driver = new ChromeDriver(options);

    try {
      // webDriver 경로 설정

      driver.get(url);

      // 페이지 끝까지 스크롤 내리는 로직
//      while (true) {
////        long lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
//
//        long lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.getElementById('__next').scrollHeight");
//
//
//        // Scroll down to bottom
//        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.getElementById('__next').scrollHeight);");
//
//        // Wait for new content to load
//        Thread.sleep(1000);
//
//        long newHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.getElementById('__next').scrollHeight");
//
//        // 새로운 콘텐츠가 로드되지 않을 때, 스크롤이 끝났다고 판단하여 반복문 종료
//        if (newHeight == lastHeight) {
//          break;
//        }
//      }

      //      wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector(".flex-1.cursor-pointer")));
//
      List<WebElement> elements = driver.findElements(By.cssSelector(".flex-1.cursor-pointer"));

      if (elements.isEmpty()) {
        System.out.println("아이디가 적힌 a태그를 못찾음");
      }
      else {
        for (WebElement element : elements) {
          String kakaoSearchHref = element.getAttribute("href");
          int kakaoSearchIdIndex = kakaoSearchHref.lastIndexOf("/");
          String kakaoSearchId = kakaoSearchHref.substring(kakaoSearchIdIndex + 1);

          kakaoSearchIdList.add(kakaoSearchId);
        }
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      driver.quit();
    }
    return kakaoSearchIdList;
  }

  @Override
  public Map<String, Object> getNaverSearchList(String searchWord) throws Exception {
    Map<String, Object> naverSearchList = new HashMap<>();

    List<String> platform = new ArrayList<>();
    List<String> platformIdList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();
    List<String> thumbnailList = new ArrayList<>();
    List<String> countList = new ArrayList<>();
    List<String> completeList = new ArrayList<>();
    List<String> starRateList = new ArrayList<>();
    List<String> authorList = new ArrayList<>();
    List<String> lastUpdateList  = new ArrayList<>();
    List<String> descriptionList = new ArrayList<>();
    List<String> publiList = new ArrayList<>();
    List<String> categoryList = new ArrayList<>();
    List<String> priceList = new ArrayList<>();
    List<String> ageGradeList = new ArrayList<>();

    NovelSearchDTO novelSearchDTO = new NovelSearchDTO();

    String searchUrl = "https://series.naver.com/search/search.series?t=novel&q=" + searchWord;
    Document doc = Jsoup.connect(searchUrl).get();

    Elements titles = doc.getElementsByClass("N=a:nov.title");
    Elements starRates = doc.getElementsByClass("score_num");
    Elements authors = doc.getElementsByClass("author");
    Elements lastUpdates = doc.getElementsByClass("info");




    for (Element e : titles) {
      int titleIndex = e.text().indexOf("(");
      int completeStartIndex = e.text().indexOf("/");
      int completeEndIndex = e.text().indexOf(")");
      int countStartIndex = e.text().indexOf("총");
      int countEndIndex = e.text().indexOf("화");

      titleList.add(e.text().substring(0, titleIndex - 1));
      completeList.add(e.text().substring(completeStartIndex + 1, completeEndIndex));

      if (countEndIndex == -1) {
        countEndIndex = e.text().indexOf("권");
      }
      countList.add(e.text().substring(countStartIndex + 1, countEndIndex));

    }


    for (Element e : starRates) {
      starRateList.add(e.text());
    }

    for (Element e : authors) {
      authorList.add(e.text());
    }

    for (Element e : lastUpdates) {
      String[] mobNum = e.text().split("|");
      lastUpdateList.add(mobNum[2]);
    }


    naverSearchList.put("title", titleList);
    naverSearchList.put("completeYn", completeList);
    naverSearchList.put("count", countList);
    naverSearchList.put("author", authorList);
    naverSearchList.put("starRate", starRateList);
    naverSearchList.put("lastUpdate", lastUpdateList);



    return naverSearchList;
  }



}





























