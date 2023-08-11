package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.repository.NovelCateRepository;
import com.bitc.full505_final_team4.data.repository.NovelPlatformRepository;
import com.bitc.full505_final_team4.data.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import javax.swing.text.Element;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.datatransfer.*;

/*@EnableJpaRepositories(basePackages = {"com.bitc.full505_final_team4.data.entity"})*/
@Service
@RequiredArgsConstructor
public class NovelSearchServiceImpl implements NovelSearchService {
  private final NovelRepository novelRepository;
  private final NovelPlatformRepository novelPlatformRepository;
  private final NovelCateRepository novelCateRepository;

  // 셀레니움을 통한 크롤링(카카오페이지)
  public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
  public static String WEB_DRIVER_PATH = "C:\\chromedriver\\chromedriver.exe";

// --------------------- 카카오페이지 작품 id 가져오기 ------------------------------
  // 셀레니움을 통해 검색 결과에 따른 카카오페이지 작품 id 리스트 가져오기
  @Override
  public List<String> getKakaoSearchIdList(String searchWord) throws Exception {
    WebDriver driver;
    // ------------- 카카오페이지 셀레니움을 통한 로그인 시도 --------------
//    Map<String, Object> kakaoSearchList = new HashMap<>();
//
//    String kakaoLoginUrl = "https://accounts.kakao.com/login/?continue=https%3A%2F%2Fkauth.kakao.com%2Foauth%2Fauthorize%3Fis_popup%3Dfalse%26ka%3Dsdk%252F2.1.0%2520os%252Fjavascript%2520sdk_type%252Fjavascript%2520lang%252Fko%2520device%252FWin32%2520origin%252Fhttps%25253A%25252F%25252Fpage.kakao.com%26auth_tran_id%3D4NVAWH9Y2fxSMEHpozuV3fE29BE-34edN65QVdy5wjDQd1tk2GYAa4Je-rJW%26response_type%3Dcode%26state%3Dhttps%25253A%25252F%25252Fpage.kakao.com%25252F%26redirect_uri%3Dhttps%253A%252F%252Fpage.kakao.com%252Frelay%252Flogin%26through_account%3Dtrue%26client_id%3D49bbb48c5fdb0199e5da1b89de359484&talk_login=hidden#login";
//
//    String searchUrl = "https://page.kakao.com/search/result?keyword=" + searchWord + "&categoryUid=11";
//
//    List<String> kakaoSearchIdList = new ArrayList<>();
//    List<String> kakaoSearchStarRateList = new ArrayList<>();
//
//    System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
//
//    // webDriver 옵션 설정
//    ChromeOptions options = new ChromeOptions();
////    options.addArguments("--disable-popup-blocking"); // 팝업 안띄움
//////    options.addArguments("--headless"); // 브라우저 창 숨기고 실행
////    options.addArguments("--enable-automation");
////    options.addArguments("--window-position=-100000,-100000");
////    options.addArguments("--window-size=0,0");
////    options.addArguments("--lang=ko");
////    options.addArguments("--disable-gpu");            //gpu 비활성화
////    options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음
//
//    driver = new ChromeDriver(options);
//    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//
//    try {
//      driver.get(kakaoLoginUrl);
//      Thread.sleep(1000);
//      driver.findElement(By.name("loginId")).sendKeys("");
//      Thread.sleep(1000);
//      driver.findElement(By.name("password")).sendKeys("");
//
//      driver.findElement(By.cssSelector(".btn_g.highlight.submit")).click();
//      Thread.sleep(2000);
//
//      Set<Cookie> cookies = driver.manage().getCookies();
//
//      driver.get(searchUrl);
//
//      // 페이지 끝까지 스크롤 내리는 로직
////      while (true) {
//////        long lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
////
////        long lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.getElementById('__next').scrollHeight");
////
////
////        // Scroll down to bottom
////        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.getElementById('__next').scrollHeight);");
////
////        // Wait for new content to load
////        Thread.sleep(1000);
////
////        long newHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.getElementById('__next').scrollHeight");
////
////        // 새로운 콘텐츠가 로드되지 않을 때, 스크롤이 끝났다고 판단하여 반복문 종료
////        if (newHeight == lastHeight) {
////          break;
////        }
////      }
//
//      //      wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector(".flex-1.cursor-pointer")));
//
//      // css선택자를 활용하여 해당 태그찾기(같은 클래스값을 가진 태그가 반복되는 형태라 list<WebElement>타입으로 설정함
//      List<WebElement> elements = driver.findElements(By.cssSelector(".flex-1.cursor-pointer"));
//
//      if (elements.isEmpty()) {
//        System.out.println("작품 아이디가 적힌 a태그를 못찾음");
//      }
//      else {
//        for (WebElement element : elements) {
//          // 태그 요소들 각각의 작품id값만 String으로 받아오기
//          String kakaoSearchHref = element.getAttribute("href");
//
//          int kakaoSearchIdIndex = kakaoSearchHref.lastIndexOf("/");
//          String kakaoSearchId = kakaoSearchHref.substring(kakaoSearchIdIndex + 1);
//          kakaoSearchIdList.add(kakaoSearchId);
//        }
//
//        driver.get("https://page.kakao.com/content/51332154");
////          for (Cookie cookie : cookies) {
////            driver.manage().addCookie(cookie);
////          }
//
//        WebElement starRateEle = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[1]/span[2]"));
//        String starRate = starRateEle.getText();
//
//        kakaoSearchStarRateList.add(starRate);
//
//        kakaoSearchList.put("kakaoSearchIdList", kakaoSearchIdList);
//        kakaoSearchList.put("kakaoSearchStarRateList", kakaoSearchStarRateList);
//      }
//
//    }
//    catch (Exception e) {
//      e.printStackTrace();
//    }
//    finally {
//      driver.quit();
//    }
//    return kakaoSearchList;

    String searchUrl = "https://page.kakao.com/search/result?keyword=" + searchWord + "&categoryUid=11";

    List<String> kakaoSearchIdList = new ArrayList<>();

    System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

    // webDriver 옵션 설정
    ChromeOptions options = new ChromeOptions();
//    options.addArguments("--disable-popup-blocking"); // 팝업 안띄움
//    options.addArguments("--headless"); // 브라우저 창 숨기고 실행
//    options.addArguments("--enable-automation");
//    options.addArguments("--window-position=-100000,-100000");
//    options.addArguments("--window-size=0,0");
//    options.addArguments("--lang=ko");
//    options.addArguments("--disable-gpu");            //gpu 비활성화
//    options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음

    driver = new ChromeDriver(options);
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    try {

      driver.get(searchUrl);

      // 페이지 끝까지 스크롤 내리는 로직
      while (true) {

        long lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.getElementById('__next').scrollHeight");


        // Scroll down to bottom
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.getElementById('__next').scrollHeight);");

        // Wait for new content to load
        Thread.sleep(250);

        long newHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.getElementById('__next').scrollHeight");

        // 새로운 콘텐츠가 로드되지 않을 때, 스크롤이 끝났다고 판단하여 반복문 종료
        if (newHeight == lastHeight) {
          break;
        }
      }


      // css선택자를 활용하여 해당 태그찾기(같은 클래스값을 가진 태그가 반복되는 형태라 list<WebElement>타입으로 설정함
      List<WebElement> elements = driver.findElements(By.cssSelector(".flex-1.cursor-pointer"));

      if (elements.isEmpty()) {
        System.out.println("작품 아이디가 적힌 a태그를 못찾음");
      }
      else {
        for (WebElement element : elements) {
          // 태그 요소들 각각의 작품id값만 String으로 받아오기
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


  // ---------------------------- 네이버 검색결과 가져오기--------------------------------
  @Override
  public Map<String, Object> getNaverSearchList(String searchWord) throws Exception {
    WebDriver driver;
    Map<String, Object> naverSearchObj = new HashMap<>();

//    System.setProperty("java.awt.headless", "false");
    System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

    ChromeOptions options = new ChromeOptions();
//    options.addArguments("--disable-popup-blocking"); // 팝업 안띄움
    options.addArguments("--headless"); // 브라우저 창 숨기고 실행
    options.addArguments("--start-minimized");
    options.addArguments("--enable-automation");
    options.addArguments("--window-position=1980,1050");
    Dimension windowSize = new Dimension(800, 600); // 원하는 크기로 설정
    options.addArguments("--window-size=" + windowSize.width + "," + windowSize.height);
    options.addArguments("--lang=ko");
    options.addArguments("--disable-gpu");            //gpu 비활성화
    options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음

    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(300, TimeUnit.MILLISECONDS);

    String naverLoginUrl = "https://nid.naver.com/nidlogin.login?mode=form&url=https://www.naver.com/";

    String naverSearchUrl = "https://series.naver.com/search/search.series?t=novel&fs=default&q=" + searchWord + "&page=1";

    String naverId = "bitcteam4";
    String naverPw = "qntks505!";

    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    StringSelection id = new StringSelection(naverId);
    StringSelection pw = new StringSelection(naverPw);

      // -------------------------------- selenium (로그인) -------------------------------------

    try {
      // 네이버 로그인 페이지 접속하기
      driver.get(naverLoginUrl);

      // 아이디 입력
      WebElement idInput = driver.findElement(By.name("id"));
      clipboard.setContents(id, null);
      idInput.click();
      idInput.sendKeys(Keys.CONTROL + "v");

      Thread.sleep(300);

      // 비밀번호 입력
      WebElement pwInput = driver.findElement(By.name("pw"));
      clipboard.setContents(pw, null);
      pwInput.click();
      pwInput.sendKeys(Keys.CONTROL + "v");

      Thread.sleep(300);

      // 로그인 버튼 클릭
      WebElement btnLogin = driver.findElement(By.cssSelector(".btn_login"));
      btnLogin.click();


      // 검색 작품 개수 찾을 사이트 접속하기
      driver.get(naverSearchUrl);

      // 검색 작품 개수 찾기
      WebElement findAllCount = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[2]/h3/em"));
      int si = findAllCount.getText().indexOf("(");
      int ei = findAllCount.getText().indexOf(")");
      int searchCount = Integer.parseInt(findAllCount.getText().substring(si + 1, ei));

      // 검색 작품 개수가 25개 이하일때
      if (searchCount <= 25) {
        List<String> platformIdList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        List<String> thumbnailList = new ArrayList<>();
        List<Double> starRateList = new ArrayList<>();
        List<String> authorList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        List<String> completeYnList = new ArrayList<>();
        List<String> descriptionList = new ArrayList<>();
        List<String> ageGradeList = new ArrayList<>();

        // 작품 id가 적힌 a태그들 찾기
        List<WebElement> aLinks = driver.findElements(By.className("N=a:nov.img"));

        for (WebElement platformIdEl : aLinks) {
          // a태그 href 속성값 중 platformId값 추출하기
          int platformIdIndex = platformIdEl.getAttribute("href").indexOf("=");
          String platformId = platformIdEl.getAttribute("href").substring(platformIdIndex + 1);
          platformIdList.add(platformId);

          // 썸네일 주소 가져오기
          String thumbnail = platformIdEl.findElement(By.cssSelector("img")).getAttribute("src");
          thumbnailList.add(thumbnail);
        }


        List<WebElement> titles = driver.findElements(By.className("N=a:nov.title"));
        for (WebElement titleEl : titles) {
          // 제목 가져오기
          int titleIndex = titleEl.getText().indexOf("(");
          String title = titleEl.getText().substring(0, titleIndex - 1);
          titleList.add(title);

          // count 가져오기
          int countStartIndex = titleEl.getText().indexOf("총");
          int countEndIndex = titleEl.getText().indexOf("/");
          int count = Integer.parseInt(titleEl.getText().substring(countStartIndex + 2, countEndIndex - 1));
          countList.add(count);

        }

        // 작가 정보 가져오기
        List<WebElement> infos = driver.findElement(By.cssSelector(".lst_list")).findElements(By.className("info"));
        for (WebElement e : infos) {
          String p = e.getText();
          String[] parts = p.split("\\s*\\|\\s*");

          String author = parts[1];
          authorList.add(author);

          // 완결 유무 가져오기
          String f = parts[3];
          int completeYnStartIndex = f.indexOf("/");

          String completeYn = f.substring(completeYnStartIndex + 1);
          completeYnList.add(completeYn);
        }

        // 별점 정보 가져오기
        List<WebElement> starRates = driver.findElements(By.className("score_num"));
        for (WebElement starRateEl : starRates) {
          double starRate = Double.parseDouble(starRateEl.getText());
          starRateList.add(starRate);
        }

        // 작품 소개글 가져오기
        List<WebElement> descriptions = driver.findElements(By.className("dsc"));
        for (WebElement descriptionEl : descriptions) {
          String description = descriptionEl.getText();
          descriptionList.add(description);
        }

        // 성인 작품 여부 가져오기
        List<WebElement> conts = driver.findElements(By.cssSelector("div.cont"));
        for (WebElement contEl : conts) {
          String h3 = contEl.findElement(By.cssSelector("h3")).getText();
          String ageGrade = h3.contains("19금") ? "adult" : "all";

          ageGradeList.add(ageGrade);
        }

        // 객체에 속성명으로 해당 리스트(배열) 형태로 저장
        naverSearchObj.put("platformId", platformIdList);
        naverSearchObj.put("title", titleList);
        naverSearchObj.put("thumbnail", thumbnailList);
        naverSearchObj.put("starRate", starRateList);
        naverSearchObj.put("author", authorList);
        naverSearchObj.put("count", countList);
        naverSearchObj.put("completeYn", completeYnList);
        naverSearchObj.put("dsc", descriptionList);
        naverSearchObj.put("ageGrade", ageGradeList);
      }

      // 결과가 25개 초과인 경우(2페이지 이상 있는 경우)
      else {
        List<String> platformIdList = new ArrayList<>();  // o
        List<String> titleList = new ArrayList<>(); // o
        List<String> thumbnailList = new ArrayList<>(); // o
        List<Double> starRateList = new ArrayList<>(); // o
        List<String> authorList = new ArrayList<>(); // o
        List<Integer> countList = new ArrayList<>(); // o
        List<String> completeYnList = new ArrayList<>(); //
        List<String> descriptionList = new ArrayList<>();
        List<String> ageGradeList = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
          // 1~10페이지의 페이지에 접속하기
          String searchDetailUrl = "https://series.naver.com/search/search.series?t=novel&q=" + searchWord + "&page=" + i;

          driver.get(searchDetailUrl);

          // 작품 id가 적힌 a태그들 찾기
          List<WebElement> aLinks = driver.findElements(By.className("N=a:nov.img"));

          for (WebElement platformIdEl : aLinks) {
            // a태그 href 속성값 중 platformId값 추출하기
            int platformIdIndex = platformIdEl.getAttribute("href").indexOf("=");
            String platformId = platformIdEl.getAttribute("href").substring(platformIdIndex + 1);
            platformIdList.add(platformId);

            // 썸네일 주소 가져오기
            String thumbnail = platformIdEl.findElement(By.cssSelector("img")).getAttribute("src");
            thumbnailList.add(thumbnail);
          }

          List<WebElement> titles = driver.findElements(By.className("N=a:nov.title"));

          for (WebElement titleEl : titles) {
            // 제목 가져오기
            int titleIndex = titleEl.getText().indexOf("(");
            String title = titleEl.getText().substring(0, titleIndex - 1);
            titleList.add(title);

            // count 가져오기
            int countStartIndex = titleEl.getText().indexOf("총");
            int countEndIndex = titleEl.getText().indexOf("/");
            int count = Integer.parseInt(titleEl.getText().substring(countStartIndex + 2, countEndIndex - 1));
            countList.add(count);

          }

          // 작가 정보 가져오기
          List<WebElement> infos = driver.findElement(By.cssSelector(".lst_list")).findElements(By.className("info"));
          for (WebElement e : infos) {
            String p = e.getText();
            String[] parts = p.split("\\s*\\|\\s*");

            String author = parts[1];
            authorList.add(author);

            // 완결 유무 가져오기
            String f = parts[3];
            int completeYnStartIndex = f.indexOf("/");

            String completeYn = f.substring(completeYnStartIndex + 1);
            completeYnList.add(completeYn);
          }

          // 별점 정보 가져오기
          List<WebElement> starRates = driver.findElements(By.className("score_num"));
          for (WebElement starRateEl : starRates) {
            double starRate = Double.parseDouble(starRateEl.getText());
            starRateList.add(starRate);
          }

          // 작품소개글 가져오기
          List<WebElement> descriptions = driver.findElements(By.className("dsc"));
          for (WebElement descriptionEl : descriptions) {
            String description = descriptionEl.getText();
            descriptionList.add(description);
          }

          // 성인 작품 여부 가져오기
          List<WebElement> conts = driver.findElements(By.cssSelector("div.cont"));
          for (WebElement contEl : conts) {
            String h3 = contEl.findElement(By.cssSelector("h3")).getText();
            String ageGrade = h3.contains("19금") ? "adult" : "all";

            ageGradeList.add(ageGrade);
          }


        }
        // 객체에 속성명으로 해당 리스트(배열) 형태로 저장
        naverSearchObj.put("platformId", platformIdList);
        naverSearchObj.put("title", titleList);
        naverSearchObj.put("thumbnail", thumbnailList);
        naverSearchObj.put("starRate", starRateList);
        naverSearchObj.put("author", authorList);
        naverSearchObj.put("count", countList);
        naverSearchObj.put("completeYn", completeYnList);
        naverSearchObj.put("dsc", descriptionList);
        naverSearchObj.put("ageGrade", ageGradeList);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      driver.quit();
    }

      // -------------- jsoup(비로그인) -----------------

      // Jsoup 활용하여 페이지에 접속하기
    // 검색한 작품 결과의 총 개수를 구하는 jsoup findMaxPageNumUrl 변수 선언
//    String findMaxPageNumUrl = "https://series.naver.com/search/search.series?t=novel&q=" + searchWord;

    //    try {
//      Document findPageNum = Jsoup.connect(findMaxPageNumUrl).get();

      // 검색 결과로 나온 총작품수로 max pageNum 구하기 => 자료가 너무 많아 페이지 max pageNum 10개로 고정
//      Elements pageNumEle = findPageNum.select("div[class=lst_header]>h3>em");
//      if (!pageNumEle.isEmpty()) {
//        int allCountStartIndex = pageNumEle.text().indexOf("(");
//        int allCountEndIndex = pageNumEle.text().indexOf(")");
//        int allCount = Integer.parseInt(pageNumEle.text().substring(allCountStartIndex + 1, allCountEndIndex));
//
////        double maxPageDot = (double) allCount / 25.0;
////        int maxPageNum = (int) Math.ceil(maxPageDot);
//
//        // 리스트 타입으로 저장할 변수 선언
//        List<String> platformIdList = new ArrayList<>();
//        List<String> titleList = new ArrayList<>();
//        List<String> thumbnailList = new ArrayList<>();
//        List<String> countList = new ArrayList<>();
//        List<String> completeList = new ArrayList<>();
//        List<String> starRateList = new ArrayList<>();
//        List<String> authorList = new ArrayList<>();
//        List<String> lastUpdateList = new ArrayList<>();
//        List<String> descriptionList = new ArrayList<>();
//        List<String> publiList = new ArrayList<>();
//        List<String> categoryList = new ArrayList<>();
//        List<String> priceList = new ArrayList<>();
//        List<String> ageGradeList = new ArrayList<>();
//
//        // 검색 결과가 25개 이하인 경우(1페이지만 있는 경우)
//        if (allCount <= 25) {
//          // 페이지 별로 데이터 찾아올 url
//          String searchUrl = "https://series.naver.com/search/search.series?t=novel&q=" + searchWord + "&page=1";
//
//          Document doc = Jsoup.connect(searchUrl).get();
//
//          Elements platformIds = doc.getElementsByClass("N=a:nov.img");
//          Elements titles = doc.getElementsByClass("N=a:nov.title");
//          Elements thumbnails = doc.select("img[width=79]");
//          Elements starRates = doc.getElementsByClass("score_num");
//          Elements info = doc.select("body p[class=info]");
//          Elements conts = doc.select("div[class=cont]");
//
//
//          // 동일한 클레스값을 가진 반복되는 태그(엘리먼트)들의 데이터를 list타입에 저장하기
//          for (Element e : platformIds) {
//            String platformIdFind = e.attr("href");
//            int platformIdIndex = platformIdFind.indexOf("=");
//            platformIdList.add(platformIdFind.substring(platformIdIndex + 1));
//
////         네이버시리즈 디테일 페이지로가서 카테고리, 출판사, 가격, 연령 정보 가져오기
//            // ※ 성인 컨텐츠의 경우 로그인 상태에서만 사이트로 접속할 수 있기 때문에 데이터를 못가져옴(null 값으로 가져옴)
////        String detailUrl = "https://series.naver.com/novel/detail.series?productNo=" + platformIdFind.substring(platformIdIndex + 1);
////
////        Document doc2 = Jsoup.connect(detailUrl).get();
////        Elements thumbnails = doc2.select("img[width=173]");
////        String thumbnail = thumbnails.attr("src");
////        thumbnailList.add(thumbnail);
//
//            // 디테일 페이지의 출판사 정보 가져오기
////        Elements publis = doc2.select("li[class=info_lst]>ul>li:nth-child(4)>a");
////        publiList.add(publis.text());
////
////        // 디테일 페이지의 카테고리 정보 가져오기
////        Elements categorys = doc2.select("li[class=info_lst]>ul>li:nth-child(2)>span>a");
////        categoryList.add(categorys.text());
////
////        // 성인컨텐츠 유무 정보 가져오기
////        Elements ageGrades = doc2.select("li[class=info_lst]>ul>li:nth-child(5)");
////        ageGradeList.add(ageGrades.text());
////
////        // 가격 정보 가져오기
////        Elements prices = doc2.select("div[class=area_price] span[class=point_color]");
////        priceList.add(prices.text());
//
//          }
//
//          for (Element e : titles) {
//            int titleIndex = e.text().indexOf("(");
//            int completeStartIndex = e.text().indexOf("/");
//            int completeEndIndex = e.text().indexOf(")");
//            int countStartIndex = e.text().indexOf("총");
//
//            // 제목 가져오기
//            titleList.add(e.text().substring(0, titleIndex - 1));
////      completeList.add(e.text().substring(completeStartIndex + 1, completeEndIndex));
//            // 총화수 가져오기
//            countList.add(e.text().substring(countStartIndex + 2, completeStartIndex - 1));
//          }
//
//          // 썸네일 주소 가져오기
//          for (Element e : thumbnails) {
//            String thumbnailSrc = e.attr("src");
//            thumbnailList.add(thumbnailSrc);
//          }
//
//          // 별점 가져오기
//          for (Element e : starRates) {
//            starRateList.add(e.text());
//          }
//
//          for (Element e : info) {
//            String p = e.text();
//            String[] parts = p.split("\\s*\\|\\s*");  // ' | ' 앞뒤로 공백이 있을 수 있으므로 공백을 포함한 정규표현식 사용
//
//            // 작가 정보 가져오기
//            String authorPart = parts[1];
//            authorList.add(authorPart);
//
//            // 최신업데이트 날짜 가져오기
//            String datePart = parts[2];  // 날짜는 세 번째 요소에 위치
//            String datePartNoDot = datePart.substring(0, datePart.length() - 1);
//            lastUpdateList.add(datePartNoDot);
//
//            // 완결여부 가져오기
//            String completePart = parts[3];
//            int completeIndex = completePart.indexOf("/");
//            String completeYn = completePart.substring(completeIndex + 1);
//            completeList.add(completeYn);
//          }
//
//          for (Element e : conts) {
//            String description = "";
//            Element descriptionElement = e.getElementsByClass("dsc").first();
//            if (descriptionElement != null) {
//              description = descriptionElement.text();
//            }
//            descriptionList.add(description);
//          }
//          // 작품소개글 가져오기
////        for (Element e : descriptions) {
////          String description = e.text();
////          descriptionList.add(description);
////        }
//        }
//        // 검색 결과가 25개 초과인 경우(2페이지가 있는 경우)
//        else {
//          for (int i = 1; i <= 10; i++) {
//            // 페이지 별로 데이터 찾아올 url
//            String searchUrl = "https://series.naver.com/search/search.series?t=novel&q=" + searchWord + "&page=" + i;
//
//            Document doc = Jsoup.connect(searchUrl).get();
//
//            Elements platformIds = doc.getElementsByClass("N=a:nov.img");
//            Elements titles = doc.getElementsByClass("N=a:nov.title");
//            Elements thumbnails = doc.select("img[width=79]");
//            Elements starRates = doc.getElementsByClass("score_num");
//            Elements info = doc.select("body p[class=info]");
//            Elements conts = doc.select("div[class=cont]");
//
//
//            // 동일한 클레스값을 가진 반복되는 태그(엘리먼트)들의 데이터를 list타입에 저장하기
//            for (Element e : platformIds) {
//              String platformIdFind = e.attr("href");
//              int platformIdIndex = platformIdFind.indexOf("=");
//              platformIdList.add(platformIdFind.substring(platformIdIndex + 1));
//
////         네이버시리즈 디테일 페이지로가서 카테고리, 출판사, 가격, 연령 정보 가져오기
//              // ※ 성인 컨텐츠의 경우 로그인 상태에서만 사이트로 접속할 수 있기 때문에 데이터를 못가져옴(null 값으로 가져옴)
////        String detailUrl = "https://series.naver.com/novel/detail.series?productNo=" + platformIdFind.substring(platformIdIndex + 1);
////
////        Document doc2 = Jsoup.connect(detailUrl).get();
////        Elements thumbnails = doc2.select("img[width=173]");
////        String thumbnail = thumbnails.attr("src");
////        thumbnailList.add(thumbnail);
//
//              // 디테일 페이지의 출판사 정보 가져오기
////        Elements publis = doc2.select("li[class=info_lst]>ul>li:nth-child(4)>a");
////        publiList.add(publis.text());
////
////        // 디테일 페이지의 카테고리 정보 가져오기
////        Elements categorys = doc2.select("li[class=info_lst]>ul>li:nth-child(2)>span>a");
////        categoryList.add(categorys.text());
////
////        // 성인컨텐츠 유무 정보 가져오기
////        Elements ageGrades = doc2.select("li[class=info_lst]>ul>li:nth-child(5)");
////        ageGradeList.add(ageGrades.text());
////
////        // 가격 정보 가져오기
////        Elements prices = doc2.select("div[class=area_price] span[class=point_color]");
////        priceList.add(prices.text());
//
//            }
//
//            for (Element e : titles) {
//              int titleIndex = e.text().indexOf("(");
//              int completeStartIndex = e.text().indexOf("/");
//              int completeEndIndex = e.text().indexOf(")");
//              int countStartIndex = e.text().indexOf("총");
//
//              // 제목 가져오기
//              titleList.add(e.text().substring(0, titleIndex - 1));
////      completeList.add(e.text().substring(completeStartIndex + 1, completeEndIndex));
//              // 총화수 가져오기
//              countList.add(e.text().substring(countStartIndex + 2, completeStartIndex - 1));
//            }
//
//            // 썸네일 주소 가져오기
//            for (Element e : thumbnails) {
//              String thumbnailSrc = e.attr("src");
//              thumbnailList.add(thumbnailSrc);
//            }
//
//            // 별점 가져오기
//            for (Element e : starRates) {
//              starRateList.add(e.text());
//            }
//
//            for (Element e : info) {
//              String p = e.text();
//              String[] parts = p.split("\\s*\\|\\s*");  // ' | ' 앞뒤로 공백이 있을 수 있으므로 공백을 포함한 정규표현식 사용
//
//              // 작가 정보 가져오기
//              String authorPart = parts[1];
//              authorList.add(authorPart);
//
//              // 최신업데이트 날짜 가져오기
//              String datePart = parts[2];  // 날짜는 세 번째 요소에 위치
//              String datePartNoDot = datePart.substring(0, datePart.length() - 1);
//              lastUpdateList.add(datePartNoDot);
//
//              // 완결여부 가져오기
//              String completePart = parts[3];
//              int completeIndex = completePart.indexOf("/");
//              String completeYn = completePart.substring(completeIndex + 1);
//              completeList.add(completeYn);
//            }
//
//            for (Element e : conts) {
//              String description = "";
//              Element descriptionElement = e.getElementsByClass("dsc").first();
//              if (descriptionElement != null) {
//                description = descriptionElement.text();
//              }
//              descriptionList.add(description);
//            }
//            // 작품소개글 가져오기
////        for (Element e : descriptions) {
////          String description = e.text();
////          descriptionList.add(description);
////        }
//          }
//        }
//        // Map타입 의 naverSearchList에 리스트 추가
//        naverSearchList.put("platformId", platformIdList);
//        naverSearchList.put("title", titleList);
//        naverSearchList.put("thumbnail", thumbnailList);
//        naverSearchList.put("completeYn", completeList);
//        naverSearchList.put("count", countList);
//        naverSearchList.put("author", authorList);
//        naverSearchList.put("starRate", starRateList);
//        naverSearchList.put("lastUpdate", lastUpdateList);
//        naverSearchList.put("description", descriptionList);
//        naverSearchList.put("publi", publiList);
//        naverSearchList.put("category", categoryList);
//        naverSearchList.put("price", priceList);
//        naverSearchList.put("ageGrade", ageGradeList);
////        return naverSearchList;
//      }
//    }
//    catch (IOException e) {
//      e.printStackTrace();
//    }
    return naverSearchObj;
  }
}


























