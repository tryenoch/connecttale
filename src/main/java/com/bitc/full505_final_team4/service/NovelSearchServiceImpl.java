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
import org.jsoup.Connection;
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
      } else {
        for (WebElement element : elements) {
          String kakaoSearchHref = element.getAttribute("href");
          int kakaoSearchIdIndex = kakaoSearchHref.lastIndexOf("/");
          String kakaoSearchId = kakaoSearchHref.substring(kakaoSearchIdIndex + 1);

          kakaoSearchIdList.add(kakaoSearchId);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      driver.quit();
    }
    return kakaoSearchIdList;
  }

  @Override
  public Map<String, Object> getNaverSearchList(String searchWord) throws Exception {
    Map<String, Object> naverSearchList = new HashMap<>();

    // 로그인 상태에서 정보를 가져와야함(19금 썸네일 이미지 가져오기 위함)
//    String loginUrl = "https://nid.naver.com/nidlogin.login";
    String searchUrl = "https://series.naver.com/search/search.series?t=novel&q=" + searchWord;

//    String username = "bitcteam4";
//    String password = "qntks505!";

    try {
      // 로그인 페이지에 접속하여 필요한 정보 가져오기
//      Connection.Response loginPageResponse = Jsoup.connect(loginUrl)
//        .method(Connection.Method.GET)
//        .execute();
//
//      Document loginPageDocument = loginPageResponse.parse();
//      String csrfToken = loginPageDocument.select("input[name=csrf_token]").attr("value");
//
//      // 로그인 정보 설정
//      Map<String, String> loginData = new HashMap<>();
//      loginData.put("id", username);
//      loginData.put("pw", password);
//      loginData.put("csrf_token", csrfToken);
//
//      // 로그인 요청 및 세션 유지
//      Connection.Response loginResponse = Jsoup.connect(loginUrl)
//        .data(loginData)
//        .cookies(loginPageResponse.cookies())
//        .method(Connection.Method.POST)
//        .execute();
//
//      // 검색 페이지에 접속하여 데이터 가져오기
//      Connection.Response searchPageResponse = Jsoup.connect(searchUrl)
//        .cookies(loginResponse.cookies())
//        .method(Connection.Method.GET)
//        .execute();
//
//      Document doc = searchPageResponse.parse();

      // 로그인 정보 없이 페이지에 접속하기
      Document doc = Jsoup.connect(searchUrl).get();


      List<String> platformIdList = new ArrayList<>();
      List<String> titleList = new ArrayList<>();
      List<String> thumbnailList = new ArrayList<>();
      List<String> countList = new ArrayList<>();
      List<String> completeList = new ArrayList<>();
      List<String> starRateList = new ArrayList<>();
      List<String> authorList = new ArrayList<>();
      List<String> lastUpdateList = new ArrayList<>();
      List<String> descriptionList = new ArrayList<>();
      List<String> publiList = new ArrayList<>();
      List<String> categoryList = new ArrayList<>();
      List<String> priceList = new ArrayList<>();
      List<String> ageGradeList = new ArrayList<>();

      NovelSearchDTO novelSearchDTO = new NovelSearchDTO();


      // 엘리먼츠 파싱
      Elements platformIds = doc.getElementsByClass("N=a:nov.img");
      Elements titles = doc.getElementsByClass("N=a:nov.title");
      Elements thumbnails = doc.select("img[width=79]");
      Elements starRates = doc.getElementsByClass("score_num");
      Elements info = doc.select("body p[class=info]");
      Elements descriptions = doc.getElementsByClass("dsc");

      for (Element e : platformIds) {
        String platformIdFind = e.attr("href");
        int platformIdIndex = platformIdFind.indexOf("=");
        platformIdList.add(platformIdFind.substring(platformIdIndex + 1));

//         네이버시리즈 디테일 페이지로가서 카테고리, 출판사, 가격, 연령 정보 가져오기
        String detailUrl = "https://series.naver.com/novel/detail.series?productNo=" + platformIdFind.substring(platformIdIndex + 1);

        Document doc2 = Jsoup.connect(detailUrl).get();
//        Elements thumbnails = doc2.select("img[width=173]");
//        String thumbnail = thumbnails.attr("src");
//        thumbnailList.add(thumbnail);

        // 디테일 페이지의 출판사 정보 가져오기
        Elements publis = doc2.select("li[class=info_lst]>ul>li:nth-child(4)>a");
        publiList.add(publis.text());

        // 디테일 페이지의 카테고리 정보 가져오기
        Elements categorys = doc2.select("li[class=info_lst]>ul>li:nth-child(2)>span>a");
        categoryList.add(categorys.text());

        // 성인컨텐츠 유무 정보 가져오기
        Elements ageGrades = doc2.select("li[class=info_lst]>ul>li:nth-child(5)");
        ageGradeList.add(ageGrades.text());

        // 가격 정보 가져오기
        Elements prices = doc2.select("div[class=area_price] span[class=point_color]");
        priceList.add(prices.text());


      }

      for (Element e : titles) {
        int titleIndex = e.text().indexOf("(");
        int completeStartIndex = e.text().indexOf("/");
        int completeEndIndex = e.text().indexOf(")");
        int countStartIndex = e.text().indexOf("총");

        // 제목 가져오기
        titleList.add(e.text().substring(0, titleIndex - 1));
//      completeList.add(e.text().substring(completeStartIndex + 1, completeEndIndex));
        // 총화수 가져오기
        countList.add(e.text().substring(countStartIndex + 2, completeStartIndex - 1));
      }

      // 썸네일 주소 가져오기
      for (Element e : thumbnails) {
        String thumbnailSrc = e.attr("src");
        thumbnailList.add(thumbnailSrc);
      }

      // 별점 가져오기
      for (Element e : starRates) {
        starRateList.add(e.text());
      }


      for (Element e : info) {
        String p = e.text();
        String[] parts = p.split("\\s*\\|\\s*");  // ' | ' 앞뒤로 공백이 있을 수 있으므로 공백을 포함한 정규표현식 사용

        // 작가 정보 가져오기
        String authorPart = parts[1];
        authorList.add(authorPart);

        // 최신업데이트 날짜 가져오기
        String datePart = parts[2];  // 날짜는 세 번째 요소에 위치
        String datePartNoDot = datePart.substring(0, datePart.length() - 1);
        lastUpdateList.add(datePartNoDot);

        // 완결여부 가져오기
        String completePart = parts[3];
        int completeIndex = completePart.indexOf("/");
        String completeYn = completePart.substring(completeIndex + 1);
        completeList.add(completeYn);
      }

      // 작품소개글 가져오기
      for (Element e : descriptions) {
        String description = e.text();
        descriptionList.add(description);
      }


      naverSearchList.put("platformId", platformIdList);
      naverSearchList.put("title", titleList);
      naverSearchList.put("thumbnail", thumbnailList);
      naverSearchList.put("completeYn", completeList);
      naverSearchList.put("count", countList);
      naverSearchList.put("author", authorList);
      naverSearchList.put("starRate", starRateList);
      naverSearchList.put("lastUpdate", lastUpdateList);
      naverSearchList.put("description", descriptionList);
      naverSearchList.put("publi", publiList);
      naverSearchList.put("category", categoryList);
      naverSearchList.put("price", priceList);
      naverSearchList.put("ageGrade", ageGradeList);


      // 가져온 데이터 처리
//      System.out.println(doc);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return naverSearchList;
  }


}





























