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
import java.util.concurrent.TimeUnit;

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

// --------------------- 카카오페이지 작품 id 가져오기 ------------------------------
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




  // ---------------------------- 네이버 검색결과 가져오기--------------------------------
  @Override
  public Map<String, Object> getNaverSearchList(String searchWord) throws Exception {
    Map<String, Object> naverSearchList = new HashMap<>();

    // 검색한 작품 결과의 총 수를 구하는 jsoup findMaxPageNumUrl 설정
    String findMaxPageNumUrl = "https://series.naver.com/search/search.series?t=novel&q=" + searchWord;

    try {

      // Jsoup 활용하여 페이지에 접속하기
      Document findPageNum = Jsoup.connect(findMaxPageNumUrl).get();

      // 검색 결과로 나온 총작품수로 max pageNum 구하기
      Elements pageNumEle = findPageNum.select("div[class=lst_header]>h3>em");
      int allCountStartIndex = pageNumEle.text().indexOf("(");
      int allCountEndIndex = pageNumEle.text().indexOf(")");
      int allCount = Integer.parseInt(pageNumEle.text().substring(allCountStartIndex + 1, allCountEndIndex));

      double maxPageDot = allCount / 25;
      int maxPageNum = (int) Math.ceil(maxPageDot);

      // 리스트 타입으로 저장할 변수 선언
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

      // 1페이지부터 maxPageNum 까지 반복문으로 사이트 크롤링하기
      for (int i = 1; i <= maxPageNum; i++) {
        // 페이지 별로 데이터 찾아올 url
        String searchUrl = "https://series.naver.com/search/search.series?t=novel&q=" + searchWord + "&page=" + i;

        Document doc = Jsoup.connect(searchUrl).get();

        Elements platformIds = doc.getElementsByClass("N=a:nov.img");
        Elements titles = doc.getElementsByClass("N=a:nov.title");
        Elements thumbnails = doc.select("img[width=79]");
        Elements starRates = doc.getElementsByClass("score_num");
        Elements info = doc.select("body p[class=info]");
        Elements conts = doc.select("div[class=cont]");


        // 동일한 클레스값을 가진 반복되는 태그(엘리먼트)들의 데이터를 list타입에 저장하기
        for (Element e : platformIds) {
          String platformIdFind = e.attr("href");
          int platformIdIndex = platformIdFind.indexOf("=");
          platformIdList.add(platformIdFind.substring(platformIdIndex + 1));

//         네이버시리즈 디테일 페이지로가서 카테고리, 출판사, 가격, 연령 정보 가져오기
          // ※ 성인 컨텐츠의 경우 데이터를 못가져옴(null 값으로 가져옴)
//        String detailUrl = "https://series.naver.com/novel/detail.series?productNo=" + platformIdFind.substring(platformIdIndex + 1);
//
//        Document doc2 = Jsoup.connect(detailUrl).get();
//        Elements thumbnails = doc2.select("img[width=173]");
//        String thumbnail = thumbnails.attr("src");
//        thumbnailList.add(thumbnail);

          // 디테일 페이지의 출판사 정보 가져오기
//        Elements publis = doc2.select("li[class=info_lst]>ul>li:nth-child(4)>a");
//        publiList.add(publis.text());
//
//        // 디테일 페이지의 카테고리 정보 가져오기
//        Elements categorys = doc2.select("li[class=info_lst]>ul>li:nth-child(2)>span>a");
//        categoryList.add(categorys.text());
//
//        // 성인컨텐츠 유무 정보 가져오기
//        Elements ageGrades = doc2.select("li[class=info_lst]>ul>li:nth-child(5)");
//        ageGradeList.add(ageGrades.text());
//
//        // 가격 정보 가져오기
//        Elements prices = doc2.select("div[class=area_price] span[class=point_color]");
//        priceList.add(prices.text());

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

        for (Element e : conts) {
          String description = "";
          Element descriptionElement = e.getElementsByClass("dsc").first();
          if (descriptionElement != null) {
            description = descriptionElement.text();
          }
          descriptionList.add(description);
        }
        // 작품소개글 가져오기
//        for (Element e : descriptions) {
//          String description = e.text();
//          descriptionList.add(description);
//        }
      }


      // Map타입 의 naverSearchList에 리스트 추가
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

    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return naverSearchList;
  }


  // 셀레니움으로 네이버시리즈 로그인 시도하여 데이터 가져오기 (로그인이 안됨)

//  @Override
//  public Map<String, Object> getNaverSearchList(String searchWord) throws Exception {
//    Map<String, Object> naverSearchList = null;
//    List<String> thumbnailList = null;
//
//    System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
//    // 셀레니움 사용하여 네이버 로그인 후 데이터 가져오기 시도
//    // Chrome WebDriver 인스턴스 생성
//    WebDriver driver = new ChromeDriver();
//
//    // 타임아웃 설정
//    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//
//
//    try {
//      // 네이버 시리즈 로그인 페이지로 이동
//      driver.get("https://nid.naver.com/nidlogin.login");
//
//      Thread.sleep(3000);
//
//      // 아이디와 비밀번호 입력
//      driver.findElement(By.name("id")).sendKeys("bitcteam4");
//      driver.findElement(By.name("pw")).sendKeys("qntks505!");
//
//      // 로그인 버튼 클릭
//      driver.findElement(By.className("btn_login")).click();
//
//      // 로그인 후 페이지가 로드될 때까지 대기
//      Thread.sleep(1000);
//
//      // 네이버 시리즈 검색 페이지로 이동
//      driver.get("https://series.naver.com/novel/detail.series?productNo=514942");
//
//      // 페이지가 로드될 때까지 대기
//      Thread.sleep(1000);
//
//      // 검색 결과에서 썸네일을 크롤링하여 출력
//
////      List<WebElement> elements = driver
////        .findElements(By.xpath("/html/body/div/div[2]/div[2]/div[2]/div[3]/ul/li[1]/a/img"));
//      WebElement thumbnail = driver.findElement(By.xpath("//*[@id=\"container\"]/div[1]/a/img"));
//      String thumbnailUrl = thumbnail.getAttribute("src");
//
//      Thread.sleep(1000);
//
//
////      for (WebElement e : elements) {
////        thumbnailList.add(e.getAttribute("src"));
////        String author = e.findElement(By.cssSelector(".author")).getText();
//////        System.out.println("Title: " + title + ", Author: " + author);
////
////      }
//
//      naverSearchList.put("thumbnail", thumbnailUrl);
//    } catch (Exception e) {
//      e.printStackTrace();
//    } finally {
//      // 작업이 끝난 후 WebDriver 종료
//      driver.quit();
//    }
//
//    return naverSearchList;
//  }
}


























