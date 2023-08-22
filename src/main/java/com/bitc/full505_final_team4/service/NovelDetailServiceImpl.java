package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.common.JsonUtils;
import com.bitc.full505_final_team4.data.dto.ReplyLikeInterface;
import com.bitc.full505_final_team4.data.dto.ReportDto;
import com.bitc.full505_final_team4.data.entity.*;
import com.bitc.full505_final_team4.data.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.openqa.selenium.json.Json;
import org.springframework.util.ObjectUtils;

import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class NovelDetailServiceImpl implements NovelDetailService {

  private final NovelCommonEditService novelCommonEditService;
  private final NovelRidiService novelRidiService;
  private final NovelPlatformRepository novelPlatformRepository;
  private final NovelRepository novelRepository;
  private final NovelLikeRepository novelLikeRepository;
  private final MemberRepository memberRepository;
  private final NovelReplyRepository novelReplyRepository;
  private final ReplyLikeRepository replyLikeRepository;
  private final ReportRepository reportRepository;


  // 셀레니움 사용을 위한 크롬드라이버 설정
  public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
  public static String WEB_DRIVER_PATH = "C:\\chromedriver\\chromedriver.exe";


  // 매개변수인 title, ebookCheck, novelAdult로 platform 테이블에서 가져오기
  @Override
  public List<NovelPlatformEntity> getNovelDetail(String title, String ebookCheck, String novelAdult) {

    List<NovelPlatformEntity> novelDetail = novelPlatformRepository.findAllByNovelTitleAndEbookCheckAndNovelAdult(title, ebookCheck, novelAdult);


    return novelDetail;
  }

  // 리디북스 디테일 정보 크롤링
  @Override
  public NovelPlatformEntity getRidiCrolling(String title, String ne, String novelAdult) throws Exception {
    NovelPlatformEntity ridiPlatformEntity = new NovelPlatformEntity();

    String encodeTitle = URLEncoder.encode(title, "UTF-8");
    // 매개변수로 받은 title을 키워드로 리디북스 api에서 json 데이터 가져오기
    String url1 = "https://ridibooks.com/api/search-api/search?adult_exclude=n&keyword=" + encodeTitle; // 대부분의 정보 찾기용
    JSONObject json = (JSONObject) JsonUtils.jsonUrlParser(url1);
    ArrayList<JSONObject> novelList = (ArrayList<JSONObject>) json.get("books");

    if (!novelList.isEmpty()) {
      // 제목, 성인여부, 종류가 일치하는 작품의 platformId 찾기
      for (int i = 0; i < novelList.size(); i++) {
        String searchTitle = novelCommonEditService.editTitleForNovelEntity(novelList.get(i).get("title").toString());
        if (searchTitle.equals(title)) {
          if (ne.equals("단행본") && novelAdult.equals("Y")) {
            if (novelList.get(i).get("web_title").toString().contains("e북") && Integer.parseInt(novelList.get(i).get("age_limit").toString()) == 19) {
              // 플랫폼 설정
              ridiPlatformEntity.setPlatform(3);

              String platformId = novelList.get(i).get("b_id").toString();
              ridiPlatformEntity.setPlatformId(platformId);

              // novelTitle 설정
              ridiPlatformEntity.setNovelTitle(title);

              // novelThumbnail 설정
              String novelThumbnail = "https://img.ridicdn.net/cover/" + platformId +"/xxlarge";
              ridiPlatformEntity.setNovelThumbnail(novelThumbnail);

              // novelIntro 설정
              JSONObject introDesc = (JSONObject) JsonUtils.jsonUrlParser("https://book-api.ridibooks.com/books/" + platformId + "/descriptions").get("descriptions");
              String novelIntro = introDesc.get("intro").toString();
              ridiPlatformEntity.setNovelIntro(novelIntro);

              // novelAuthor 설정
              ArrayList authorsList = (ArrayList) novelList.get(i).get("authors_info");
              HashMap<String, Object> authors = (HashMap<String, Object>) authorsList.get(0);
              String author = authors.get("name").toString();
              ridiPlatformEntity.setNovelAuthor(author);

              // novelPubli 설정
              String novelPubli = novelList.get(i).get("publisher").toString();
              ridiPlatformEntity.setNovelPubli(novelPubli);

              // novelCount
              int novelCount = Integer.parseInt(novelList.get(i).get("book_count").toString());
              ridiPlatformEntity.setNovelCount(novelCount);

              // novelPrice
              ArrayList priceList = (ArrayList) novelList.get(i).get("series_prices_info");
              HashMap<String, Object> prices = (HashMap<String, Object>) priceList.get(0);
              int novelPrice = Integer.parseInt(novelList.get(i).get("price").toString()) != 0 ? Integer.parseInt(novelList.get(i).get("price").toString()) : Integer.parseInt(prices.get("max_price").toString());
              ridiPlatformEntity.setNovelPrice(novelPrice);

              // starRate
              double novelStarRate = Double.parseDouble(novelList.get(i).get("buyer_rating_score").toString());
              ridiPlatformEntity.setNovelStarRate(novelStarRate);

              // completeYn
              String completeYn = Boolean.parseBoolean(novelList.get(i).get("is_series_complete").toString()) ? "Y" : "N";
              ridiPlatformEntity.setNovelCompleteYn(completeYn);

              // novelAdult
              ridiPlatformEntity.setNovelAdult(novelAdult);

              // novelRelease => 애매해서 생략

              // updateDate
              ArrayList updateDateList = (ArrayList) JsonUtils.jsonUrlParser("https://book-api.ridibooks.com/books/" + platformId + "/notices").get("notices");
              if (!updateDateList.isEmpty()) {
                HashMap<String, Object> updateDates = (HashMap<String, Object>) updateDateList.get(0);
                String updateDate = updateDates.get("title").toString();
                ridiPlatformEntity.setNovelUpdateDate(updateDate);
              }

              // cateList
              String cateItem = novelList.get(i).get("parent_category_name").toString();
              if (cateItem.contains("BL")) {
                ridiPlatformEntity.setCateList("7");
              }
              else if (cateItem.contains("로맨스")) {
                ridiPlatformEntity.setCateList("3");
              }
              else if (cateItem.contains("로판")) {
                ridiPlatformEntity.setCateList("4");
              }
              else if (cateItem.contains("판타지")) {
                ridiPlatformEntity.setCateList("1");
              }
              else {
                ridiPlatformEntity.setCateList("8");
              }

              // ebookCheck
              ridiPlatformEntity.setEbookCheck(ne);
              break;
            }
          }
          else if (ne.equals("단행본") && novelAdult.equals("N")) {
            if (novelList.get(i).get("web_title").toString().contains("e북") && Integer.parseInt(novelList.get(i).get("age_limit").toString()) != 19) {
              // 플랫폼 설정
              ridiPlatformEntity.setPlatform(3);

              String platformId = novelList.get(i).get("b_id").toString();
              ridiPlatformEntity.setPlatformId(platformId);

              // novelTitle 설정
              ridiPlatformEntity.setNovelTitle(title);

              // novelThumbnail 설정
              String novelThumbnail = "https://img.ridicdn.net/cover/" + platformId +"/xxlarge";
              ridiPlatformEntity.setNovelThumbnail(novelThumbnail);

              // novelIntro 설정
              JSONObject introDesc = (JSONObject) JsonUtils.jsonUrlParser("https://book-api.ridibooks.com/books/" + platformId + "/descriptions").get("descriptions");
              String novelIntro = introDesc.get("intro").toString();
              ridiPlatformEntity.setNovelIntro(novelIntro);

              // novelAuthor 설정
              ArrayList authorsList = (ArrayList) novelList.get(i).get("authors_info");
              HashMap<String, Object> authors = (HashMap<String, Object>) authorsList.get(0);
              String author = authors.get("name").toString();
              ridiPlatformEntity.setNovelAuthor(author);

              // novelPubli 설정
              String novelPubli = novelList.get(i).get("publisher").toString();
              ridiPlatformEntity.setNovelPubli(novelPubli);

              // novelCount
              int novelCount = Integer.parseInt(novelList.get(i).get("book_count").toString());
              ridiPlatformEntity.setNovelCount(novelCount);

              // novelPrice
              ArrayList priceList = (ArrayList) novelList.get(i).get("series_prices_info");
              HashMap<String, Object> prices = (HashMap<String, Object>) priceList.get(0);
              int novelPrice = Integer.parseInt(novelList.get(i).get("price").toString()) != 0 ? Integer.parseInt(novelList.get(i).get("price").toString()) : Integer.parseInt(prices.get("max_price").toString());
              ridiPlatformEntity.setNovelPrice(novelPrice);

              // starRate
              double novelStarRate = Double.parseDouble(novelList.get(i).get("buyer_rating_score").toString());
              ridiPlatformEntity.setNovelStarRate(novelStarRate);

              // completeYn
              String completeYn = Boolean.parseBoolean(novelList.get(i).get("is_series_complete").toString()) ? "Y" : "N";
              ridiPlatformEntity.setNovelCompleteYn(completeYn);

              // novelAdult
              ridiPlatformEntity.setNovelAdult(novelAdult);

              // novelRelease => 애매해서 생략

              // updateDate
              ArrayList updateDateList = (ArrayList) JsonUtils.jsonUrlParser("https://book-api.ridibooks.com/books/" + platformId + "/notices").get("notices");
              if (!updateDateList.isEmpty()) {
                HashMap<String, Object> updateDates = (HashMap<String, Object>) updateDateList.get(0);
                String updateDate = updateDates.get("title").toString();
                ridiPlatformEntity.setNovelUpdateDate(updateDate);
              }

              // cateList
              String cateItem = novelList.get(i).get("parent_category_name").toString();
              if (cateItem.contains("BL")) {
                ridiPlatformEntity.setCateList("7");
              }
              else if (cateItem.contains("로맨스")) {
                ridiPlatformEntity.setCateList("3");
              }
              else if (cateItem.contains("로판")) {
                ridiPlatformEntity.setCateList("4");
              }
              else if (cateItem.contains("판타지")) {
                ridiPlatformEntity.setCateList("1");
              }
              else {
                ridiPlatformEntity.setCateList("8");
              }

              // ebookCheck
              ridiPlatformEntity.setEbookCheck(ne);
              break;
            }
          }
          else if (ne.equals("웹소설") && novelAdult.equals("Y")) {
            if (!novelList.get(i).get("web_title").toString().contains("e북") && Integer.parseInt(novelList.get(i).get("age_limit").toString()) == 19) {
              // 플랫폼 설정
              ridiPlatformEntity.setPlatform(3);

              String platformId = novelList.get(i).get("b_id").toString();
              ridiPlatformEntity.setPlatformId(platformId);

              // novelTitle 설정
              ridiPlatformEntity.setNovelTitle(title);

              // novelThumbnail 설정
              String novelThumbnail = "https://img.ridicdn.net/cover/" + platformId +"/xxlarge";
              ridiPlatformEntity.setNovelThumbnail(novelThumbnail);

              // novelIntro 설정
              JSONObject introDesc = (JSONObject) JsonUtils.jsonUrlParser("https://book-api.ridibooks.com/books/" + platformId + "/descriptions").get("descriptions");
              String novelIntro = introDesc.get("intro").toString();
              ridiPlatformEntity.setNovelIntro(novelIntro);

              // novelAuthor 설정
              ArrayList authorsList = (ArrayList) novelList.get(i).get("authors_info");
              HashMap<String, Object> authors = (HashMap<String, Object>) authorsList.get(0);
              String author = authors.get("name").toString();
              ridiPlatformEntity.setNovelAuthor(author);

              // novelPubli 설정
              String novelPubli = novelList.get(i).get("publisher").toString();
              ridiPlatformEntity.setNovelPubli(novelPubli);

              // novelCount
              int novelCount = Integer.parseInt(novelList.get(i).get("book_count").toString());
              ridiPlatformEntity.setNovelCount(novelCount);

              // novelPrice
              ArrayList priceList = (ArrayList) novelList.get(i).get("series_prices_info");
              HashMap<String, Object> prices = (HashMap<String, Object>) priceList.get(0);
              int novelPrice = Integer.parseInt(novelList.get(i).get("price").toString()) != 0 ? Integer.parseInt(novelList.get(i).get("price").toString()) : Integer.parseInt(prices.get("max_price").toString());
              ridiPlatformEntity.setNovelPrice(novelPrice);

              // starRate
              double novelStarRate = Double.parseDouble(novelList.get(i).get("buyer_rating_score").toString());
              ridiPlatformEntity.setNovelStarRate(novelStarRate);

              // completeYn
              String completeYn = Boolean.parseBoolean(novelList.get(i).get("is_series_complete").toString()) ? "Y" : "N";
              ridiPlatformEntity.setNovelCompleteYn(completeYn);

              // novelAdult
              ridiPlatformEntity.setNovelAdult(novelAdult);

              // novelRelease => 애매해서 생략

              // updateDate
              ArrayList updateDateList = (ArrayList) JsonUtils.jsonUrlParser("https://book-api.ridibooks.com/books/" + platformId + "/notices").get("notices");
              if (!updateDateList.isEmpty()) {
                HashMap<String, Object> updateDates = (HashMap<String, Object>) updateDateList.get(0);
                String updateDate = updateDates.get("title").toString();
                ridiPlatformEntity.setNovelUpdateDate(updateDate);
              }

              // cateList
              String cateItem = novelList.get(i).get("parent_category_name").toString();
              if (cateItem.contains("BL")) {
                ridiPlatformEntity.setCateList("7");
              }
              else if (cateItem.contains("로맨스")) {
                ridiPlatformEntity.setCateList("3");
              }
              else if (cateItem.contains("로판")) {
                ridiPlatformEntity.setCateList("4");
              }
              else if (cateItem.contains("판타지")) {
                ridiPlatformEntity.setCateList("1");
              }
              else {
                ridiPlatformEntity.setCateList("8");
              }

              // ebookCheck
              ridiPlatformEntity.setEbookCheck(ne);
              break;
            }
          }
          else if (ne.equals("웹소설") && novelAdult.equals("N")) {
            if (!novelList.get(i).get("web_title").toString().contains("e북") && Integer.parseInt(novelList.get(i).get("age_limit").toString()) != 19) {
              // 플랫폼 설정
              ridiPlatformEntity.setPlatform(3);

              String platformId = novelList.get(i).get("b_id").toString();
              ridiPlatformEntity.setPlatformId(platformId);

              // novelTitle 설정
              ridiPlatformEntity.setNovelTitle(title);

              // novelThumbnail 설정
              String novelThumbnail = "https://img.ridicdn.net/cover/" + platformId +"/xxlarge";
              ridiPlatformEntity.setNovelThumbnail(novelThumbnail);

              // novelIntro 설정
              JSONObject introDesc = (JSONObject) JsonUtils.jsonUrlParser("https://book-api.ridibooks.com/books/" + platformId + "/descriptions").get("descriptions");
              String novelIntro = introDesc.get("intro").toString();
              ridiPlatformEntity.setNovelIntro(novelIntro);

              // novelAuthor 설정
              ArrayList authorsList = (ArrayList) novelList.get(i).get("authors_info");
              HashMap<String, Object> authors = (HashMap<String, Object>) authorsList.get(0);
              String author = authors.get("name").toString();
              ridiPlatformEntity.setNovelAuthor(author);

              // novelPubli 설정
              String novelPubli = novelList.get(i).get("publisher").toString();
              ridiPlatformEntity.setNovelPubli(novelPubli);

              // novelCount
              int novelCount = Integer.parseInt(novelList.get(i).get("book_count").toString());
              ridiPlatformEntity.setNovelCount(novelCount);

              // novelPrice
              ArrayList priceList = (ArrayList) novelList.get(i).get("series_prices_info");
              HashMap<String, Object> prices = (HashMap<String, Object>) priceList.get(0);
              int novelPrice = Integer.parseInt(novelList.get(i).get("price").toString()) != 0 ? Integer.parseInt(novelList.get(i).get("price").toString()) : Integer.parseInt(prices.get("max_price").toString());
              ridiPlatformEntity.setNovelPrice(novelPrice);

              // starRate
              double novelStarRate = Double.parseDouble(novelList.get(i).get("buyer_rating_score").toString());
              ridiPlatformEntity.setNovelStarRate(novelStarRate);

              // completeYn
              String completeYn = Boolean.parseBoolean(novelList.get(i).get("is_series_complete").toString()) ? "Y" : "N";
              ridiPlatformEntity.setNovelCompleteYn(completeYn);

              // novelAdult
              ridiPlatformEntity.setNovelAdult(novelAdult);

              // novelRelease => 애매해서 생략

              // updateDate
              ArrayList updateDateList = (ArrayList) JsonUtils.jsonUrlParser("https://book-api.ridibooks.com/books/" + platformId + "/notices").get("notices");
              if (!updateDateList.isEmpty()) {
                HashMap<String, Object> updateDates = (HashMap<String, Object>) updateDateList.get(0);
                String updateDate = updateDates.get("title").toString();
                ridiPlatformEntity.setNovelUpdateDate(updateDate);
              }

              // cateList
              String cateItem = novelList.get(i).get("parent_category_name").toString();
              if (cateItem.contains("BL")) {
                ridiPlatformEntity.setCateList("7");
              }
              else if (cateItem.contains("로맨스")) {
                ridiPlatformEntity.setCateList("3");
              }
              else if (cateItem.contains("로판")) {
                ridiPlatformEntity.setCateList("4");
              }
              else if (cateItem.contains("판타지")) {
                ridiPlatformEntity.setCateList("1");
              }
              else {
                ridiPlatformEntity.setCateList("8");
              }

              // ebookCheck
              ridiPlatformEntity.setEbookCheck(ne);
              break;
            }
          }
        }
      }
    }

    return ridiPlatformEntity;
  }

  // 네이버 디테일 페이지 정보 크롤링
  @Override
  public NovelPlatformEntity getNaverCrolling(String title, String ebookCheck, String ageGrade) {
    NovelPlatformEntity naverCrollingData = new NovelPlatformEntity();

    WebDriver driver;

    System.setProperty("java.awt.headless", "false");
    System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

    ChromeOptions options = new ChromeOptions();
    options.addArguments("--disable-popup-blocking"); // 팝업 안띄움
//    options.addArguments("--headless"); // 브라우저 창 숨기고 실행
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

    String naverSearchUrl = "https://series.naver.com/search/search.series?t=novel&q=" + title;


    String naverId = "bitcteam4";
    String naverPw = "qntks505!";

    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    StringSelection id = new StringSelection(naverId);
    StringSelection pw = new StringSelection(naverPw);

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


      // 해당 작품과 동일한 이름의 작품을 찾는 페이지 접속하기
      driver.get(naverSearchUrl);
      if (!driver.findElements(By.className("N=a:nov.title")).isEmpty()) {
        List<WebElement> titleEls = driver.findElements(By.className("N=a:nov.title"));
        if (ebookCheck.equals("단행본") && ageGrade.equals("Y")) {
          for (WebElement titleEl : titleEls) {
            String searchTitle = novelCommonEditService.editTitleForNovelEntity(titleEl.getText());
//            String testTitle = titleEl.getText();
            // 제목, ebookCheck, 성인여부가 일치하는 제목의 url 주소 얻기(같은 제목, 같은 ebookCheck인데 19세, 전체이용가 모두 있는 경우가 있기 때문)
            if (searchTitle.equals(title) && titleEl.getText().contains("[단행본]") && titleEl.findElement(By.xpath("..")).findElements(By.cssSelector(".ico.n19")).isEmpty()) {
              int platformIdIndex = titleEl.getAttribute("href").indexOf("=");

              // 검색결과 페이지에서 가져올 정보
              // platform 구분하기
              naverCrollingData.setPlatform(2);

              // platformId 가져오기
              naverCrollingData.setPlatformId(titleEl.getAttribute("href").substring(platformIdIndex + 1));

              // novelIntro 가져오기
              WebElement titleParentEl = titleEl.findElement(By.xpath("..")).findElement(By.xpath(".."));
              naverCrollingData.setNovelIntro(titleParentEl.findElement(By.className("dsc")).getText());
              // -------------------------------


              // 작품 디테일 페이지 접속하기
              driver.get(titleEl.getAttribute("href"));

              // novelTitle 설정하기
              naverCrollingData.setNovelTitle(title);

              // novelThumbnail 가져오기
              WebElement leftSide = driver.findElement(By.xpath("//*[@id=\"container\"]/div[1]"));
              WebElement thumbnailEl = leftSide.findElement(By.tagName("img"));

              naverCrollingData.setNovelThumbnail(thumbnailEl.getAttribute("src"));


              // novelAuthor 가져오기
              naverCrollingData.setNovelAuthor(driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[3]/a")).getText());

              // novelPubli 가져오기
              naverCrollingData.setNovelPubli(driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[4]/a")).getText());

              // novelCount 가져오기
              naverCrollingData.setNovelCount(Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"content\"]/h5/strong")).getText()));

              // novelCompleteYn 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[1]/span")).getText().equals("완결")) {
                naverCrollingData.setNovelCompleteYn("Y");
              } else {
                naverCrollingData.setNovelCompleteYn("N");
              }

              // novelPrice 가져오기
              naverCrollingData.setNovelPrice(Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"content\"]/div[4]/div/dl/dd/div/div[1]/span/span")).getText()));

              // novelStarRate 가져오기
              naverCrollingData.setNovelStarRate(Double.parseDouble(driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/div[1]/em")).getText()));

              // novelRelease 가져오기
              int novelReleaseStartIndex = driver.findElement(By.xpath("//*[@id=\"volumeList\"]/tr[1]/td[1]/div/em")).getText().indexOf("(");
              int novelReleaseEndIndex = driver.findElement(By.xpath("//*[@id=\"volumeList\"]/tr[1]/td[1]/div/em")).getText().indexOf(")");

              naverCrollingData.setNovelRelease(driver.findElement(By.xpath("//*[@id=\"volumeList\"]/tr[1]/td[1]/div/em")).getText().substring(novelReleaseStartIndex + 1, novelReleaseEndIndex - 1));

              // cateList 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("판타지")) {
                naverCrollingData.setCateList("1");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("현판")) {
                naverCrollingData.setCateList("2");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("로맨스")) {
                naverCrollingData.setCateList("3");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("로판")) {
                naverCrollingData.setCateList("4");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("무협")) {
                naverCrollingData.setCateList("5");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("드라마")) {
                naverCrollingData.setCateList("6");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("BL")) {
                naverCrollingData.setCateList("7");
              }
              else {
                naverCrollingData.setCateList("8");
              }

              // ebookCheck 가져오기 : 이거는 상세페이지로 이동할 때 이미 정해져있음
              naverCrollingData.setEbookCheck(ebookCheck);

              // novelAdult 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[5]")).getText().equals("청소년 이용불가")) {
                naverCrollingData.setNovelAdult("Y");
              } else {
                naverCrollingData.setNovelAdult("N");
              }
              break;
            }
          }
        }
        else if (ebookCheck.equals("단행본") && ageGrade.equals("N")) {
          for (WebElement titleEl : titleEls) {
            String searchTitle = novelCommonEditService.editTitleForNovelEntity(titleEl.getText());
//            String testTitle = titleEl.getText();
            // 제목, ebookCheck, 성인여부가 일치하는 제목의 url 주소 얻기(같은 제목, 같은 ebookCheck인데 19세, 전체이용가 모두 있는 경우가 있기 때문)
            if (searchTitle.equals(title) && titleEl.getText().contains("[단행본]") && titleEl.findElement(By.xpath("..")).findElements(By.cssSelector(".ico.n19")).isEmpty()) {
              int platformIdIndex = titleEl.getAttribute("href").indexOf("=");

              // 검색결과 페이지에서 가져올 정보
              // platform 구분하기
              naverCrollingData.setPlatform(2);

              // platformId 가져오기
              naverCrollingData.setPlatformId(titleEl.getAttribute("href").substring(platformIdIndex + 1));

              // novelIntro 가져오기
              WebElement titleParentEl = titleEl.findElement(By.xpath("..")).findElement(By.xpath(".."));
              naverCrollingData.setNovelIntro(titleParentEl.findElement(By.className("dsc")).getText());
              // -------------------------------


              // 작품 디테일 페이지 접속하기
              driver.get(titleEl.getAttribute("href"));

              // novelTitle 설정하기
              naverCrollingData.setNovelTitle(title);

              // novelThumbnail 가져오기
              WebElement leftSide = driver.findElement(By.xpath("//*[@id=\"container\"]/div[1]"));
              WebElement thumbnailEl = leftSide.findElement(By.tagName("img"));

              naverCrollingData.setNovelThumbnail(thumbnailEl.getAttribute("src"));


              // novelAuthor 가져오기
              naverCrollingData.setNovelAuthor(driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[3]/a")).getText());

              // novelPubli 가져오기
              naverCrollingData.setNovelPubli(driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[4]/a")).getText());

              // novelCount 가져오기
              naverCrollingData.setNovelCount(Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"content\"]/h5/strong")).getText()));

              // novelCompleteYn 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[1]/span")).getText().equals("완결")) {
                naverCrollingData.setNovelCompleteYn("Y");
              } else {
                naverCrollingData.setNovelCompleteYn("N");
              }

              // novelPrice 가져오기
              naverCrollingData.setNovelPrice(Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"content\"]/div[4]/div/dl/dd/div/div[1]/span/span")).getText()));

              // novelStarRate 가져오기
              naverCrollingData.setNovelStarRate(Double.parseDouble(driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/div[1]/em")).getText()));

              // novelRelease 가져오기
              int novelReleaseStartIndex = driver.findElement(By.xpath("//*[@id=\"volumeList\"]/tr[1]/td[1]/div/em")).getText().indexOf("(");
              int novelReleaseEndIndex = driver.findElement(By.xpath("//*[@id=\"volumeList\"]/tr[1]/td[1]/div/em")).getText().indexOf(")");

              naverCrollingData.setNovelRelease(driver.findElement(By.xpath("//*[@id=\"volumeList\"]/tr[1]/td[1]/div/em")).getText().substring(novelReleaseStartIndex + 1, novelReleaseEndIndex - 1));

              // cateList 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("판타지")) {
                naverCrollingData.setCateList("1");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("현판")) {
                naverCrollingData.setCateList("2");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("로맨스")) {
                naverCrollingData.setCateList("3");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("로판")) {
                naverCrollingData.setCateList("4");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("무협")) {
                naverCrollingData.setCateList("5");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("드라마")) {
                naverCrollingData.setCateList("6");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("BL")) {
                naverCrollingData.setCateList("7");
              }
              else {
                naverCrollingData.setCateList("8");
              }

              // ebookCheck 가져오기 : 이거는 상세페이지로 이동할 때 이미 정해져있음
              naverCrollingData.setEbookCheck(ebookCheck);

              // novelAdult 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[5]")).getText().equals("청소년 이용불가")) {
                naverCrollingData.setNovelAdult("Y");
              } else {
                naverCrollingData.setNovelAdult("N");
              }
              break;
            }
          }
        }

        else if (ebookCheck.equals("웹소설") && ageGrade.equals("Y")) {
          for (WebElement titleEl : titleEls) {
            String searchTitle = novelCommonEditService.editTitleForNovelEntity(titleEl.getText());
//            String testTitle = titleEl.getText();
            // 제목, ebookCheck, 성인여부가 일치하는 제목의 url 주소 얻기(같은 제목, 같은 ebookCheck인데 19세, 전체이용가 모두 있는 경우가 있기 때문)
            if (searchTitle.equals(title) && !titleEl.getText().contains("[단행본]") && !titleEl.findElement(By.xpath("..")).findElements(By.cssSelector(".ico.n19")).isEmpty()) {
              int platformIdIndex = titleEl.getAttribute("href").indexOf("=");

              // 검색결과 페이지에서 가져올 정보
              // platform 구분하기
              naverCrollingData.setPlatform(2);

              // platformId 가져오기
              naverCrollingData.setPlatformId(titleEl.getAttribute("href").substring(platformIdIndex + 1));

              // novelIntro 가져오기
              WebElement titleParentEl = titleEl.findElement(By.xpath("..")).findElement(By.xpath(".."));
              naverCrollingData.setNovelIntro(titleParentEl.findElement(By.className("dsc")).getText());
              // -------------------------------


              // 작품 디테일 페이지 접속하기
              driver.get(titleEl.getAttribute("href"));

              // novelTitle 설정하기
              naverCrollingData.setNovelTitle(title);

              // novelThumbnail 가져오기
              WebElement leftSide = driver.findElement(By.xpath("//*[@id=\"container\"]/div[1]"));
              WebElement thumbnailEl = leftSide.findElement(By.tagName("img"));

              naverCrollingData.setNovelThumbnail(thumbnailEl.getAttribute("src"));


              // novelAuthor 가져오기
              naverCrollingData.setNovelAuthor(driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[3]/a")).getText());

              // novelPubli 가져오기
              naverCrollingData.setNovelPubli(driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[4]/a")).getText());

              // novelCount 가져오기
              naverCrollingData.setNovelCount(Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"content\"]/h5/strong")).getText()));

              // novelCompleteYn 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[1]/span")).getText().equals("완결")) {
                naverCrollingData.setNovelCompleteYn("Y");
              } else {
                naverCrollingData.setNovelCompleteYn("N");
              }

              // novelPrice 가져오기
              naverCrollingData.setNovelPrice(Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"content\"]/div[4]/div/dl/dd/div/div[1]/span/span")).getText()));

              // novelStarRate 가져오기
              naverCrollingData.setNovelStarRate(Double.parseDouble(driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/div[1]/em")).getText()));

              // novelRelease 가져오기
              int novelReleaseStartIndex = driver.findElement(By.xpath("//*[@id=\"volumeList\"]/tr[1]/td[1]/div/em")).getText().indexOf("(");
              int novelReleaseEndIndex = driver.findElement(By.xpath("//*[@id=\"volumeList\"]/tr[1]/td[1]/div/em")).getText().indexOf(")");

              naverCrollingData.setNovelRelease(driver.findElement(By.xpath("//*[@id=\"volumeList\"]/tr[1]/td[1]/div/em")).getText().substring(novelReleaseStartIndex + 1, novelReleaseEndIndex - 1));

              // cateList 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("판타지")) {
                naverCrollingData.setCateList("1");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("현판")) {
                naverCrollingData.setCateList("2");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("로맨스")) {
                naverCrollingData.setCateList("3");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("로판")) {
                naverCrollingData.setCateList("4");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("무협")) {
                naverCrollingData.setCateList("5");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("드라마")) {
                naverCrollingData.setCateList("6");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("BL")) {
                naverCrollingData.setCateList("7");
              }
              else {
                naverCrollingData.setCateList("8");
              }

              // ebookCheck 가져오기 : 이거는 상세페이지로 이동할 때 이미 정해져있음
              naverCrollingData.setEbookCheck(ebookCheck);

              // novelAdult 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[5]")).getText().equals("청소년 이용불가")) {
                naverCrollingData.setNovelAdult("Y");
              } else {
                naverCrollingData.setNovelAdult("N");
              }
              break;
            }
          }
        }
        else if (ebookCheck.equals("웹소설") && ageGrade.equals("N")) {
          for (WebElement titleEl : titleEls) {
            String searchTitle = novelCommonEditService.editTitleForNovelEntity(titleEl.getText());
//            String testTitle = titleEl.getText();
            // 제목, ebookCheck, 성인여부가 일치하는 제목의 url 주소 얻기(같은 제목, 같은 ebookCheck인데 19세, 전체이용가 모두 있는 경우가 있기 때문)
            if (searchTitle.equals(title) && !titleEl.getText().contains("[단행본]") && titleEl.findElement(By.xpath("..")).findElements(By.cssSelector(".ico.n19")).isEmpty()){
              int platformIdIndex = titleEl.getAttribute("href").indexOf("=");

              // 검색결과 페이지에서 가져올 정보
              // platform 구분하기
              naverCrollingData.setPlatform(2);

              // platformId 가져오기
              naverCrollingData.setPlatformId(titleEl.getAttribute("href").substring(platformIdIndex + 1));

              // novelIntro 가져오기
              WebElement titleParentEl = titleEl.findElement(By.xpath("..")).findElement(By.xpath(".."));
              naverCrollingData.setNovelIntro(titleParentEl.findElement(By.className("dsc")).getText());
              // -------------------------------


              // 작품 디테일 페이지 접속하기
              driver.get(titleEl.getAttribute("href"));

              // novelTitle 설정하기
              naverCrollingData.setNovelTitle(title);

              // novelThumbnail 가져오기
              WebElement leftSide = driver.findElement(By.xpath("//*[@id=\"container\"]/div[1]"));
              WebElement thumbnailEl = leftSide.findElement(By.tagName("img"));

              naverCrollingData.setNovelThumbnail(thumbnailEl.getAttribute("src"));


              // novelAuthor 가져오기
              naverCrollingData.setNovelAuthor(driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[3]/a")).getText());

              // novelPubli 가져오기
              naverCrollingData.setNovelPubli(driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[4]/a")).getText());

              // novelCount 가져오기
              naverCrollingData.setNovelCount(Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"content\"]/h5/strong")).getText()));

              // novelCompleteYn 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[1]/span")).getText().equals("완결")) {
                naverCrollingData.setNovelCompleteYn("Y");
              } else {
                naverCrollingData.setNovelCompleteYn("N");
              }

              // novelPrice 가져오기
              naverCrollingData.setNovelPrice(Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"content\"]/div[4]/div/dl/dd/div/div[1]/span/span")).getText()));

              // novelStarRate 가져오기
              naverCrollingData.setNovelStarRate(Double.parseDouble(driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/div[1]/em")).getText()));

              // novelRelease 가져오기
              int novelReleaseStartIndex = driver.findElement(By.xpath("//*[@id=\"volumeList\"]/tr[1]/td[1]/div/em")).getText().indexOf("(");
              int novelReleaseEndIndex = driver.findElement(By.xpath("//*[@id=\"volumeList\"]/tr[1]/td[1]/div/em")).getText().indexOf(")");

              naverCrollingData.setNovelRelease(driver.findElement(By.xpath("//*[@id=\"volumeList\"]/tr[1]/td[1]/div/em")).getText().substring(novelReleaseStartIndex + 1, novelReleaseEndIndex - 1));

              // cateList 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("판타지")) {
                naverCrollingData.setCateList("1");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("현판")) {
                naverCrollingData.setCateList("2");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("로맨스")) {
                naverCrollingData.setCateList("3");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("로판")) {
                naverCrollingData.setCateList("4");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("무협")) {
                naverCrollingData.setCateList("5");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("드라마")) {
                naverCrollingData.setCateList("6");
              } else if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[2]/span/a")).getText().equals("BL")) {
                naverCrollingData.setCateList("7");
              }
              else {
                naverCrollingData.setCateList("8");
              }

              // ebookCheck 가져오기 : 이거는 상세페이지로 이동할 때 이미 정해져있음
              naverCrollingData.setEbookCheck(ebookCheck);

              // novelAdult 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[5]")).getText().equals("청소년 이용불가")) {
                naverCrollingData.setNovelAdult("Y");
              } else {
                naverCrollingData.setNovelAdult("N");
              }
              break;
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      driver.quit();
    }

    return naverCrollingData;
  }


  // 카카오 디테일 페이지 크롤링
  @Override
  public NovelPlatformEntity getKakaoCrolling(String title, String ne, String ageGrade) {
    NovelPlatformEntity kakaoCrollingData = new NovelPlatformEntity();

    WebDriver driver;

    System.setProperty("java.awt.headless", "false");
    System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

    // webDriver 옵션 설정
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--disable-popup-blocking"); // 팝업 안띄움
    options.addArguments("--headless"); // 브라우저 창 숨기고 실행
//    options.addArguments("--enable-automation");
//    options.addArguments("--window-position=-100000,-100000");
//    options.addArguments("--window-size=0,0");
//    options.addArguments("--lang=ko");
    options.addArguments("--disable-gpu");            //gpu 비활성화
    options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음

    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(300, TimeUnit.MILLISECONDS);

    Duration timeout = Duration.ofSeconds(2);
    WebDriverWait wait = new WebDriverWait(driver, timeout);

    String kakaoLoginUrl = "https://accounts.kakao.com/login/?continue=https%3A%2F%2Fkauth.kakao.com%2Foauth%2Fauthorize%3Fis_popup%3Dfalse%26ka%3Dsdk%252F2.1.0%2520os%252Fjavascript%2520sdk_type%252Fjavascript%2520lang%252Fko-KR%2520device%252FWin32%2520origin%252Fhttps%25253A%25252F%25252Fpage.kakao.com%26auth_tran_id%3DR4hmABVI31BOh4yeFDxmy3b383i4c3TmpReM3Yvue--1JWaLO3gXgJiV~2fu%26response_type%3Dcode%26state%3Dhttps%25253A%25252F%25252Fpage.kakao.com%25252F%26redirect_uri%3Dhttps%253A%252F%252Fpage.kakao.com%252Frelay%252Flogin%26through_account%3Dtrue%26client_id%3D49bbb48c5fdb0199e5da1b89de359484&talk_login=hidden#login";

    String kakaoSearchUrl = "https://page.kakao.com/search/result?keyword=" + title + "&categoryUid=11";

    String kakaoId = "jjeti11@kakao.com";
    String kakaoPw = "qntks505!";

    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    StringSelection userId = new StringSelection(kakaoId);
    StringSelection userPw = new StringSelection(kakaoPw);


    try {
//       카카오 로그인 페이지 접속하기
      driver.get(kakaoLoginUrl);
      Thread.sleep(200);


      // 아이디 입력
      WebElement idInput = driver.findElement(By.xpath("//*[@id=\"loginId--1\"]"));
      idInput.click();
      Thread.sleep(300);
      clipboard.setContents(userId, null);
      Thread.sleep(200);
//
//      idInput.sendKeys(Keys.CONTROL + "v");
      idInput.sendKeys(kakaoId);


      // 비밀번호 입력
      WebElement pwInput = driver.findElement(By.xpath("//*[@id=\"password--2\"]"));
      pwInput.click();
      Thread.sleep(200);
      clipboard.setContents(userPw, null);
      Thread.sleep(200);
//      pwInput.sendKeys(Keys.CONTROL + "v");

      pwInput.sendKeys(kakaoPw);

      Thread.sleep(200);

      // 로그인 버튼 클릭
      WebElement btnLogin = driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/div/form/div[4]/button[1]"));
      btnLogin.click();

      Set<Cookie> cookies = driver.manage().getCookies();

      wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__next\"]/div/div[1]/div/div[1]/a/img")));

      // 해당 작품과 동일한 이름의 작품을 찾는 카카오 페이지 접속하기
      driver.get(kakaoSearchUrl);

      if (!driver.findElements(By.cssSelector(".flex-1.cursor-pointer")).isEmpty()) {
        List<WebElement> aEls = driver.findElements(By.cssSelector(".flex-1.cursor-pointer"));
        if (ne.equals("단행본") && ageGrade.equals("Y")) {
          for (WebElement aEl : aEls) {
            WebElement searchTitleEl = aEl.findElement(By.cssSelector(".font-medium2.pb-2pxr"));
            WebElement ariaLabelEl = aEl.findElement(By.tagName("div"));
            String searchTitle = novelCommonEditService.editTitleForNovelEntity(searchTitleEl.getText());
            if (searchTitle.equals(title) && searchTitleEl.getText().contains("[단행본]") && ariaLabelEl.getAttribute("aria-label").contains("19세 연령 제한")) {
              kakaoCrollingData.setPlatform(1);

              // platformId 가져오기
              int platformIdIndex = aEl.getAttribute("href").lastIndexOf("/");
              kakaoCrollingData.setPlatformId(aEl.getAttribute("href").substring(platformIdIndex + 1));

              // 작품 디테일 페이지로 접속하기
              driver.get(aEl.getAttribute("href"));

              // novelTitle 저장하기(페이지 내에 있는 작품이기에 그냥 검색한 키워드가 곧 작품 제목이 됨)
              kakaoCrollingData.setNovelTitle(title);

              // novelThumbnail 가져오기
              kakaoCrollingData.setNovelThumbnail(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[1]/div/div/img")).getAttribute("src"));

              // novelAuthor 가져오기
              kakaoCrollingData.setNovelAuthor(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/div/span")).getText());

              // novelCount 가져오기
              kakaoCrollingData.setNovelCount(Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/span")).getText().substring(3).replace(",", "")));

              // starRate 가져오기
              WebElement starRateDivEl = driver.findElement(By.cssSelector(".justify-center.mt-16pxr"));
              if (starRateDivEl.findElements(By.tagName("img")).get(2).getAttribute("alt").equals("별점")) {
                kakaoCrollingData.setNovelStarRate(Double.parseDouble(starRateDivEl.findElements(By.tagName("span")).get(1).getText()));
              }
              else {
                kakaoCrollingData.setNovelStarRate(0);
              }

              // novelCompleteYn 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().equals("완결")) {
                kakaoCrollingData.setNovelCompleteYn("Y");
              }
              else {
                kakaoCrollingData.setNovelCompleteYn("N");
              }


              // cateList 가져오기
              String category = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[1]")).getText();
              if (category.contains("판타지")) {
                kakaoCrollingData.setCateList("1");
              }
              else if (category.contains("현판")) {
                kakaoCrollingData.setCateList("2");
              }
              else if (category.contains("로맨스")) {
                kakaoCrollingData.setCateList("3");
              }
              else if (category.contains("로판")) {
                kakaoCrollingData.setCateList("4");
              }
              else if (category.contains("무협")) {
                kakaoCrollingData.setCateList("5");
              }
              else if (category.contains("드라마")) {
                kakaoCrollingData.setCateList("6");
              }
              else if (category.contains("BL")) {
                kakaoCrollingData.setCateList("7");
              }
              else {
                kakaoCrollingData.setCateList("8");
              }


              // novelIntro 가져오기
              driver.get("https://page.kakao.com/content/" + kakaoCrollingData.getPlatformId() + "?tab_type=about");
              kakaoCrollingData.setNovelIntro(driver.findElement(By.cssSelector(".whitespace-pre-wrap.break-words")).getText());

              // novelPubli 가져오기
              kakaoCrollingData.setNovelPubli(driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(1).findElement(By.tagName("div")).getText());

              // novelAdult 가져오기
              if (driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(2).findElement(By.tagName("div")).getText().equals("19세이용가")) {
                kakaoCrollingData.setNovelAdult("Y");
              }
              else {
                kakaoCrollingData.setNovelAdult("N");
              }

              // novelPrice 가져오기
              if (driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().indexOf("원") == -1) {
                kakaoCrollingData.setNovelPrice(0);
              }
              else {
                int novelPriceIndex = driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().indexOf("원");
                kakaoCrollingData.setNovelPrice(Integer.parseInt(driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().substring(0, novelPriceIndex)));
              }

              // ebookCheck 가져오기
              kakaoCrollingData.setEbookCheck(ne);

              break;
            }
          }
        }
        else if (ne.equals("단행본") && ageGrade.equals("N")) {
          for (WebElement aEl : aEls) {
            WebElement searchTitleEl = aEl.findElement(By.cssSelector(".font-medium2.pb-2pxr"));
            WebElement ariaLabelEl = aEl.findElement(By.tagName("div"));
            String searchTitle = novelCommonEditService.editTitleForNovelEntity(searchTitleEl.getText());
            if (searchTitle.equals(title) && searchTitleEl.getText().contains("[단행본]") && !ariaLabelEl.getAttribute("aria-label").contains("19세 연령 제한")){
              kakaoCrollingData.setPlatform(1);

              // platformId 가져오기
              int platformIdIndex = aEl.getAttribute("href").lastIndexOf("/");
              kakaoCrollingData.setPlatformId(aEl.getAttribute("href").substring(platformIdIndex + 1));

              // 작품 디테일 페이지로 접속하기
              driver.get(aEl.getAttribute("href"));

              // novelTitle 저장하기(페이지 내에 있는 작품이기에 그냥 검색한 키워드가 곧 작품 제목이 됨)
              kakaoCrollingData.setNovelTitle(title);

              // novelThumbnail 가져오기
              kakaoCrollingData.setNovelThumbnail(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[1]/div/div/img")).getAttribute("src"));

              // novelAuthor 가져오기
              kakaoCrollingData.setNovelAuthor(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/div/span")).getText());

              // novelCount 가져오기
              kakaoCrollingData.setNovelCount(Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/span")).getText().substring(3).replace(",", "")));

              // starRate 가져오기
              WebElement starRateDivEl = driver.findElement(By.cssSelector(".justify-center.mt-16pxr"));
              if (starRateDivEl.findElements(By.tagName("img")).get(2).getAttribute("alt").equals("별점")) {
                kakaoCrollingData.setNovelStarRate(Double.parseDouble(starRateDivEl.findElements(By.tagName("span")).get(1).getText()));
              }
              else {
                kakaoCrollingData.setNovelStarRate(0);
              }

              // novelCompleteYn 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().equals("완결")) {
                kakaoCrollingData.setNovelCompleteYn("Y");
              }
              else {
                kakaoCrollingData.setNovelCompleteYn("N");
              }

              // cateList 가져오기
              String category = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[1]")).getText();
              if (category.contains("판타지")) {
                kakaoCrollingData.setCateList("1");
              }
              else if (category.contains("현판")) {
                kakaoCrollingData.setCateList("2");
              }
              else if (category.contains("로맨스")) {
                kakaoCrollingData.setCateList("3");
              }
              else if (category.contains("로판")) {
                kakaoCrollingData.setCateList("4");
              }
              else if (category.contains("무협")) {
                kakaoCrollingData.setCateList("5");
              }
              else if (category.contains("드라마")) {
                kakaoCrollingData.setCateList("6");
              }
              else if (category.contains("BL")) {
                kakaoCrollingData.setCateList("7");
              }
              else {
                kakaoCrollingData.setCateList("8");
              }


              // novelIntro 가져오기
              driver.get("https://page.kakao.com/content/" + kakaoCrollingData.getPlatformId() + "?tab_type=about");
              kakaoCrollingData.setNovelIntro(driver.findElement(By.cssSelector(".whitespace-pre-wrap.break-words")).getText());

              // novelPubli 가져오기
              kakaoCrollingData.setNovelPubli(driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(1).findElement(By.tagName("div")).getText());

              // novelAdult 가져오기
              if (driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(2).findElement(By.tagName("div")).getText().equals("19세이용가")) {
                kakaoCrollingData.setNovelAdult("Y");
              }
              else {
                kakaoCrollingData.setNovelAdult("N");
              }

              // novelPrice 가져오기
              if (driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().indexOf("원") == -1) {
                kakaoCrollingData.setNovelPrice(0);
              }
              else {
                int novelPriceIndex = driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().indexOf("원");
                kakaoCrollingData.setNovelPrice(Integer.parseInt(driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().substring(0, novelPriceIndex)));
              }

              // ebookCheck 가져오기
              kakaoCrollingData.setEbookCheck(ne);

              break;
            }
          }
        }
        else if (ne.equals("웹소설") && ageGrade.equals("Y")) {
          for (WebElement aEl : aEls) {
            WebElement searchTitleEl = aEl.findElement(By.cssSelector(".font-medium2.pb-2pxr"));
            WebElement ariaLabelEl = aEl.findElement(By.tagName("div"));
            String searchTitle = novelCommonEditService.editTitleForNovelEntity(searchTitleEl.getText());
            if (searchTitle.equals(title) && !searchTitleEl.getText().contains("[단행본]") && ariaLabelEl.getAttribute("aria-label").contains("19세 연령 제한")){
              kakaoCrollingData.setPlatform(1);

              // platformId 가져오기
              int platformIdIndex = aEl.getAttribute("href").lastIndexOf("/");
              kakaoCrollingData.setPlatformId(aEl.getAttribute("href").substring(platformIdIndex + 1));

              // 작품 디테일 페이지로 접속하기
              driver.get(aEl.getAttribute("href"));

              // novelTitle 저장하기(페이지 내에 있는 작품이기에 그냥 검색한 키워드가 곧 작품 제목이 됨)
              kakaoCrollingData.setNovelTitle(title);

              // novelThumbnail 가져오기
              kakaoCrollingData.setNovelThumbnail(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[1]/div/div/img")).getAttribute("src"));

              // novelAuthor 가져오기
              kakaoCrollingData.setNovelAuthor(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/div/span")).getText());

              // novelCount 가져오기
              kakaoCrollingData.setNovelCount(Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/span")).getText().substring(3).replace(",", "")));

              // starRate 가져오기
              WebElement starRateDivEl = driver.findElement(By.cssSelector(".justify-center.mt-16pxr"));
              if (starRateDivEl.findElements(By.tagName("img")).get(2).getAttribute("alt").equals("별점")) {
                kakaoCrollingData.setNovelStarRate(Double.parseDouble(starRateDivEl.findElements(By.tagName("span")).get(1).getText()));
              }
              else {
                kakaoCrollingData.setNovelStarRate(0);
              }

              // novelCompleteYn 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().equals("완결")) {
                kakaoCrollingData.setNovelCompleteYn("Y");
              }
              else {
                kakaoCrollingData.setNovelCompleteYn("N");
              }


              // cateList 가져오기
              String category = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[1]")).getText();
              if (category.contains("판타지")) {
                kakaoCrollingData.setCateList("1");
              }
              else if (category.contains("현판")) {
                kakaoCrollingData.setCateList("2");
              }
              else if (category.contains("로맨스")) {
                kakaoCrollingData.setCateList("3");
              }
              else if (category.contains("로판")) {
                kakaoCrollingData.setCateList("4");
              }
              else if (category.contains("무협")) {
                kakaoCrollingData.setCateList("5");
              }
              else if (category.contains("드라마")) {
                kakaoCrollingData.setCateList("6");
              }
              else if (category.contains("BL")) {
                kakaoCrollingData.setCateList("7");
              }
              else {
                kakaoCrollingData.setCateList("8");
              }


              // novelIntro 가져오기
              driver.get("https://page.kakao.com/content/" + kakaoCrollingData.getPlatformId() + "?tab_type=about");
              kakaoCrollingData.setNovelIntro(driver.findElement(By.cssSelector(".whitespace-pre-wrap.break-words")).getText());

              // novelPubli 가져오기
              kakaoCrollingData.setNovelPubli(driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(1).findElement(By.tagName("div")).getText());

              // novelAdult 가져오기
              if (driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(2).findElement(By.tagName("div")).getText().equals("19세이용가")) {
                kakaoCrollingData.setNovelAdult("Y");
              }
              else {
                kakaoCrollingData.setNovelAdult("N");
              }

              // novelPrice 가져오기
              if (driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().indexOf("원") == -1) {
                kakaoCrollingData.setNovelPrice(0);
              }
              else {
                int novelPriceIndex = driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().indexOf("원");
                kakaoCrollingData.setNovelPrice(Integer.parseInt(driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().substring(0, novelPriceIndex)));
              }

              // ebookCheck 가져오기
              kakaoCrollingData.setEbookCheck(ne);

              break;
            }
          }
        }
        else if (ne.equals("웹소설") && ageGrade.equals("N")) {
          for (WebElement aEl : aEls) {
            WebElement searchTitleEl = aEl.findElement(By.cssSelector(".font-medium2.pb-2pxr"));
            WebElement ariaLabelEl = aEl.findElement(By.tagName("div"));
            String searchTitle = novelCommonEditService.editTitleForNovelEntity(searchTitleEl.getText());
            if (searchTitle.equals(title) && !searchTitleEl.getText().contains("[단행본]") && !ariaLabelEl.getAttribute("aria-label").contains("19세 연령 제한")){
              kakaoCrollingData.setPlatform(1);

              // platformId 가져오기
              int platformIdIndex = aEl.getAttribute("href").lastIndexOf("/");
              kakaoCrollingData.setPlatformId(aEl.getAttribute("href").substring(platformIdIndex + 1));

              // 작품 디테일 페이지로 접속하기
              driver.get(aEl.getAttribute("href"));

              // novelTitle 저장하기(페이지 내에 있는 작품이기에 그냥 검색한 키워드가 곧 작품 제목이 됨)
              kakaoCrollingData.setNovelTitle(title);

              // novelThumbnail 가져오기
              kakaoCrollingData.setNovelThumbnail(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[1]/div/div/img")).getAttribute("src"));

              // novelAuthor 가져오기
              kakaoCrollingData.setNovelAuthor(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/div/span")).getText());

              // novelCount 가져오기
              kakaoCrollingData.setNovelCount(Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/span")).getText().substring(3).replace(",", "")));

              // starRate 가져오기
              WebElement starRateDivEl = driver.findElement(By.cssSelector(".justify-center.mt-16pxr"));
              if (starRateDivEl.findElements(By.tagName("img")).get(2).getAttribute("alt").equals("별점")) {
                kakaoCrollingData.setNovelStarRate(Double.parseDouble(starRateDivEl.findElements(By.tagName("span")).get(1).getText()));
              }
              else {
                kakaoCrollingData.setNovelStarRate(0);
              }

              // novelCompleteYn 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().equals("완결")) {
                kakaoCrollingData.setNovelCompleteYn("Y");
              }
              else {
                kakaoCrollingData.setNovelCompleteYn("N");
              }


              // cateList 가져오기
              String category = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[1]")).getText();
              if (category.contains("판타지")) {
                kakaoCrollingData.setCateList("1");
              }
              else if (category.contains("현판")) {
                kakaoCrollingData.setCateList("2");
              }
              else if (category.contains("로맨스")) {
                kakaoCrollingData.setCateList("3");
              }
              else if (category.contains("로판")) {
                kakaoCrollingData.setCateList("4");
              }
              else if (category.contains("무협")) {
                kakaoCrollingData.setCateList("5");
              }
              else if (category.contains("드라마")) {
                kakaoCrollingData.setCateList("6");
              }
              else if (category.contains("BL")) {
                kakaoCrollingData.setCateList("7");
              }
              else {
                kakaoCrollingData.setCateList("8");
              }


              // novelIntro 가져오기
              driver.get("https://page.kakao.com/content/" + kakaoCrollingData.getPlatformId() + "?tab_type=about");
              kakaoCrollingData.setNovelIntro(driver.findElement(By.cssSelector(".whitespace-pre-wrap.break-words")).getText());

              // novelPubli 가져오기
              kakaoCrollingData.setNovelPubli(driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(1).findElement(By.tagName("div")).getText());

              // novelAdult 가져오기
              if (driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(2).findElement(By.tagName("div")).getText().equals("19세이용가")) {
                kakaoCrollingData.setNovelAdult("Y");
              }
              else {
                kakaoCrollingData.setNovelAdult("N");
              }

              // novelPrice 가져오기
              if (driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().indexOf("원") == -1) {
                kakaoCrollingData.setNovelPrice(0);
              }
              else {
                int novelPriceIndex = driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().indexOf("원");
                kakaoCrollingData.setNovelPrice(Integer.parseInt(driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().substring(0, novelPriceIndex)));
              }

              // ebookCheck 가져오기
              kakaoCrollingData.setEbookCheck(ne);

              break;
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      driver.quit();
    }


    return kakaoCrollingData;
  }


  // 리디북스 디테일 페이지 정보 db에 저장하기
  @Override
  public void insertRidiToNovel(NovelEntity novelEntity) {
    novelRepository.save(novelEntity);
  }

  @Override
  public void insertRidiToPlatform(NovelPlatformEntity novelPlatformEntity) {
    novelPlatformRepository.save(novelPlatformEntity);
  }



  // 네이버 디테일 페이지 정보 db에 저장하기
  @Override
  public void insertNaverToNovel(NovelEntity novelEntity) {
    novelRepository.save(novelEntity);
  }

  @Override
  public void insertNaverToPlatform(NovelPlatformEntity novelPlatformEntity) {
    novelPlatformRepository.save(novelPlatformEntity);
  }

  // 카카오 디테일 페이지 정보 db에 저장하기
  @Override
  public void insertKakaoToNovel(NovelEntity novelEntity) {
    novelRepository.save(novelEntity);
  }

  @Override
  public void insertKakaoToPlatform(NovelPlatformEntity kakaoPlatformEntity) {
    novelPlatformRepository.save(kakaoPlatformEntity);
  }



  // ---------------- 좋아요 관련 -----------------
  // 좋아요 버튼 클릭 기능
  @Override
  public void updateNovelLike(int novelIdx, String id) {
    NovelLikeEntity novelLikeEntity = new NovelLikeEntity();

    // 매개변수 novelIdx, id값을 가진 NovelEntity, MemberEntity 가져오기
    Optional<MemberEntity> memberEntity = memberRepository.findById(id);
    MemberEntity likeId = memberEntity.get();

    Optional<NovelEntity> novelEntity = novelRepository.findById(novelIdx);
    NovelEntity likeNovelIdx = novelEntity.get();


    // 해당 유저와 작품으로 등록된 novel_like 데이터가 있는지?? 확인
    Optional<NovelLikeEntity> check = novelLikeRepository.findByIdAndNovelIdx(likeId, likeNovelIdx);
//    novelLikeEntity.setId(likeId);
//    novelLikeEntity.setNovelIdx(likeNovelIdx);

    // 데이터 자체가 존재하지 않는 경우
    if (check.isEmpty()) {
      // novel_like db에 데이터 저장(like_yn값을 "Y"로)
      novelLikeEntity.setId(likeId);
      novelLikeEntity.setNovelIdx(likeNovelIdx);
      novelLikeEntity.setLikeYn("Y");
      novelLikeRepository.save(novelLikeEntity);
    }
    // 데이터가 존재 하는 경우
    else {
      novelLikeEntity = check.get();
      // like_yn 값이 N이면 -> Y로 수정
      if (novelLikeEntity.getLikeYn().equals("N")) {

        novelLikeEntity.setLikeYn("Y");
        novelLikeRepository.save(novelLikeEntity);
      }
      // like_yn 값이 Y이면 -> N으로 수정
      else if (novelLikeEntity.getLikeYn().equals("Y")) {
        novelLikeEntity.setLikeYn("N");
        novelLikeRepository.save(novelLikeEntity);
      }
    }
  }

  // title, ebookCheck로 novelIdx 가져오기
  @Override
  public NovelEntity getNovelIdx(String title, String ebookCheck, String novelAdult) {
    NovelEntity novelIdx = novelRepository.findByNovelTitleAndEbookCheckAndNovelAdult(title, ebookCheck, novelAdult);
    return novelIdx;
  }
  // novelIdx로 좋아요 값이 "Y"인 count 가져오기
  @Override
  public int getNovelLikeCount(NovelEntity novelIdx) {

    int novelLikeCount = novelLikeRepository.countByNovelIdxAndLikeYn(novelIdx, "Y");
    return novelLikeCount;
  }

  // novelIdx로 좋아요 테이블 정보 가져오기
  @Override
  public List<NovelLikeEntity> getNovelLike(NovelEntity novelIdx) {
    List<NovelLikeEntity> novelLikeEntityList = new ArrayList<>();
    Optional<List<NovelLikeEntity>> opt = novelLikeRepository.findAllByNovelIdx(novelIdx);

    if (opt.isPresent()) {
      for (NovelLikeEntity novelLike : opt.get()) {
        novelLikeEntityList.add(novelLike);
      }
    }


    return novelLikeEntityList;
  }

  // ----------------------------------- 리뷰 관련 --------------------------------------------------
  // novelIdx로 리뷰(댓글) 테이블 정보 가져오기(댓글 좋아요 수도 함께 가져오기)
  @Override
  public List<NovelReplyEntity> getNovelReply(NovelEntity novelIdx) {
    List<NovelReplyEntity> novelReplyEntityList= new ArrayList<>();
    // novelIdx에 해당하는 리뷰 테이블 데이터 optional 타입으로 가져오기
    Optional<List<NovelReplyEntity>> opt = novelReplyRepository.findAllByNovelIdxOrderByCreateDtDesc(novelIdx);

    if (opt.isPresent()) {
      for (NovelReplyEntity novelReplyEntity : opt.get()) {
        novelReplyEntityList.add(novelReplyEntity);
      }
    }
    return novelReplyEntityList;
  }

  // 리뷰 등록하기
  @Override
  public void insertNovelReview(int novelIdx, String id, String replyContent, String spoilerYn) {
    NovelReplyEntity novelReplyEntity = new NovelReplyEntity();

    // 매개변수 novelIdx, id값을 가진 NovelEntity, MemberEntity 가져오기
    Optional<NovelEntity> novelEntity = novelRepository.findById(novelIdx);
    NovelEntity replyNovelIdx = novelEntity.get();

    Optional<MemberEntity> memberEntity = memberRepository.findById(id);
    MemberEntity replyId = memberEntity.get();

    try {
      // db등록을 위해 NovelReplyEntity 정보 설정하기
      novelReplyEntity.setId(replyId);
      novelReplyEntity.setNovelIdx(replyNovelIdx);
      novelReplyEntity.setReplyContent(replyContent);
      novelReplyEntity.setSpoilerYn(spoilerYn);

      // 기본값 'N'이 적용이 안되서 수동으로 설정해줌
      novelReplyEntity.setDeletedYn("N");

      // db등록을 위해 NovelReplyEntity 정보 설정하기
      novelReplyRepository.save(novelReplyEntity);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  // 리뷰(댓글)에 좋아요 클릭
  @Override
  public void updateReviewLike(String id, int replyIdx) {

    ReplyLikeEntity replyLikeEntity = new ReplyLikeEntity();

    // 매개변수인 id, replyIdx 값으로 memberEntity, novelReplyEntity 찾기
    Optional<MemberEntity> memberOpt = memberRepository.findById(id);
    MemberEntity memberEntity = memberOpt.get();

    Optional<NovelReplyEntity> novelReplyOpt = novelReplyRepository.findById(replyIdx);
    NovelReplyEntity novelReplyEntity = novelReplyOpt.get();

    // memberEntity, novelReplyEntity로 reply_like 테이블 조회하여 데이터 존재여부 확인
    Optional<ReplyLikeEntity> replyLikeOpt = replyLikeRepository.findByIdAndReplyIdx(memberEntity, novelReplyEntity);

    // 조회된 데이터가 없을 경우, 좋아요 Y 값 입력하기
    if (replyLikeOpt.isEmpty()) {
      replyLikeEntity.setId(memberEntity);
      replyLikeEntity.setReplyIdx(novelReplyEntity);
      replyLikeEntity.setLikeYn("Y");
      replyLikeRepository.save(replyLikeEntity);
    }
    // 조회된 데이터가 있을경우, 좋아요가 Y/N 인지 확인해서 반대로 수정
    else {
      replyLikeEntity = replyLikeOpt.get();
      if (replyLikeEntity.getLikeYn().equals("Y")) {
        replyLikeEntity.setLikeYn("N");
        replyLikeRepository.save(replyLikeEntity);
      }
      else if (replyLikeEntity.getLikeYn().equals("N")){
        replyLikeEntity.setLikeYn("Y");
        replyLikeRepository.save(replyLikeEntity);
      }
    }
  }

  // novelReplyEntity(replyIdx)를 통해 replyLikeEntity 리스트 가져오기
  @Override
  public List<ReplyLikeEntity> getReplyLikeList(NovelReplyEntity novelReplyEntity) {
    List<ReplyLikeEntity> replyLikeEntityList = new ArrayList<>();

    // novelReplyEntity(replyIdx)를 통해 모든 replyLike 테이블 정보 얻어오기
    Optional<List<ReplyLikeEntity>> replyLikeOptList = replyLikeRepository.findAllByReplyIdx(novelReplyEntity);

    if (replyLikeOptList.isPresent()) {
      for (ReplyLikeEntity replyLikeEntity : replyLikeOptList.get()) {
        replyLikeEntityList.add(replyLikeEntity);
      }
    }

    return replyLikeEntityList;
  }

  // novelReplyEntity(replyIdx)를 통해 좋아요가 'Y'인 개수가 출력되는 엔티티 가져오기
  @Override
  public List<ReplyLikeInterface> getReplyLikeCount() {
    List<ReplyLikeInterface> replyLikeInterfaceList = replyLikeRepository.findReplyLikeCount();

    return replyLikeInterfaceList;
  }

  // 리뷰(댓글)에 대한 신고 insert
  @Override
  public String insertReplyReport(int replyIdx, String reportContent, String reporter, String suspect) {

    // int 값인 replyIdx를 통해 replyIdx로 사용될 entity 가져오기
    Optional<NovelReplyEntity> novelReplyEntityOpt = novelReplyRepository.findById(replyIdx);
    NovelReplyEntity novelReplyEntity = novelReplyEntityOpt.get(); // replyIdx

    // 중복 신고인지 확인하는 절차
    Optional<ReportEntity> reportExist = reportRepository.findByReplyIdxAndReporter(novelReplyEntity, reporter);

    // 해당 유저가 해당 댓글에 신고한 기록이 없으면
    if (!reportExist.isPresent()) {
      // 매개변수로 넘어온 데이터들로 ReportEntity 객체 생성
      ReportEntity reportEntity = new ReportEntity();
      reportEntity.setReplyIdx(novelReplyEntity);
      reportEntity.setReportContent(reportContent);
      reportEntity.setReporter(reporter);
      reportEntity.setSuspect(suspect);

      // db에 저장
      reportRepository.save(reportEntity);

      // 신고 접수가 정상적으로 완료되었는지 확인 절차
      Optional<ReportEntity> reportEntityCheck = reportRepository.findByReplyIdx(novelReplyEntity);

      if (reportEntityCheck.isPresent()) {
        return "success";
      }
      else {
        return "failed";
      }
    }
    // 해당 유저가 해당 댓글에 신고한 기록 이있으면
    else {
      return "exist";
    }


  }
}





























