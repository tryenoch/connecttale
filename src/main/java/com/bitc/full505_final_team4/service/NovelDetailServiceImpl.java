package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import com.bitc.full505_final_team4.data.repository.NovelPlatformRepository;
import com.bitc.full505_final_team4.data.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import javax.swing.text.Element;
import javax.swing.text.html.Option;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class NovelDetailServiceImpl implements NovelDetailService {

  private final NovelPlatformRepository novelPlatformRepository;
  private final NovelRepository novelRepository;

  public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
  public static String WEB_DRIVER_PATH = "C:\\chromedriver\\chromedriver.exe";


  // 찾은 novelIdx로 platform 테이블에서 데이터 가져오기
  @Override
  public List<NovelPlatformEntity> getNovelDetail(String platformId) {
    List<NovelPlatformEntity> novelDetail = new ArrayList<>();


    // platformId를 통해 novelIdx 찾기
    Optional<NovelPlatformEntity> novel = novelPlatformRepository.findByPlatformId(platformId);
    if (!novel.isEmpty()) {
      NovelEntity novelEntity = novel.get().getNovelEntity();
      List<NovelPlatformEntity> novelDetailAll = novelPlatformRepository.findAllByNovelEntity(novelEntity);
      for (NovelPlatformEntity p : novelDetailAll) {
        novelDetail.add(p);
      }
    }



    return novelDetail;
  }


  // 네이버 디테일 페이지 정보 크롤링
  @Override
  public NovelPlatformEntity getNaverCrolling(String platformId, String title, String novelOrEbook) {
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

      List<WebElement> titleEls = driver.findElements(By.className("N=a:nov.title"));
      if (!titleEls.isEmpty()) {
        if (novelOrEbook.equals("단행본")) {
          for (WebElement titleEl : titleEls) {
            if (titleEl.getText().contains(title) && titleEl.getText().contains("[단행본]")) {
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

              // novelTitle 가져오기
              naverCrollingData.setNovelTitle(driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/h2")).getText());

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
                naverCrollingData.setNovelCompleteYn('Y');
              } else {
                naverCrollingData.setNovelCompleteYn('N');
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

              // novelOrEbook 가져오기 : 이거는 상세페이지로 이동할 때 이미 정해져있음
              naverCrollingData.setNovelOrEbook(novelOrEbook);

              // novelAdult 가져오기
              if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[5]")).getText().equals("청소년 이용불가")) {
                naverCrollingData.setNovelAdult('Y');
              } else {
                naverCrollingData.setNovelAdult('N');
              }


              break;
            } else {
              break;
            }
          }
        } else if (novelOrEbook.equals("웹소설")) {
          for (WebElement titleEl : titleEls) {
            if (titleEl.getText().contains(title) && !titleEl.getText().contains("[단행본]")) {
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

              // novelTitle 가져오기
              naverCrollingData.setNovelTitle(driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/h2")).getText());

//              // novelThumbnail 가져오기
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
                naverCrollingData.setNovelCompleteYn('Y');
              } else {
                naverCrollingData.setNovelCompleteYn('N');
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

              // novelOrEbook 가져오기 : 이거는 상세페이지로 이동할 때 이미 정해져있음
              naverCrollingData.setNovelOrEbook(novelOrEbook);

              if (driver.findElement(By.xpath("//*[@id=\"content\"]/ul[1]/li/ul/li[5]")).getText().equals("청소년 이용불가")) {
                naverCrollingData.setNovelAdult('Y');
              } else {
                naverCrollingData.setNovelAdult('N');
              }

              break;
            } else {
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
  public NovelPlatformEntity getKakaoCrolling(String id, String title, String ne) {
    NovelPlatformEntity kakaoCrollingData = new NovelPlatformEntity();

    WebDriver driver;

//    System.setProperty("java.awt.headless", "false");
    System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

    ChromeOptions options = new ChromeOptions();
    options.addArguments("--disable-popup-blocking"); // 팝업 안띄움
//    options.addArguments("--headless"); // 브라우저 창 숨기고 실행
////    options.addArguments("--start-minimized");
//    options.addArguments("--enable-automation");
////    options.addArguments("--window-position=1980,1050");
////    Dimension windowSize = new Dimension(800, 600); // 원하는 크기로 설정
////    options.addArguments("--window-size=" + windowSize.width + "," + windowSize.height);
////    options.addArguments("--lang=ko");
////    options.addArguments("--disable-gpu");            //gpu 비활성화
////    options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음

    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(300, TimeUnit.MILLISECONDS);

//    String kakaoLoginUrl = "https://accounts.kakao.com/login/?continue=https%3A%2F%2Fkauth.kakao.com%2Foauth%2Fauthorize%3Fis_popup%3Dfalse%26ka%3Dsdk%252F2.1.0%2520os%252Fjavascript%2520sdk_type%252Fjavascript%2520lang%252Fko-KR%2520device%252FWin32%2520origin%252Fhttps%25253A%25252F%25252Fpage.kakao.com%26auth_tran_id%3DR4hmABVI31BOh4yeFDxmy3b383i4c3TmpReM3Yvue--1JWaLO3gXgJiV~2fu%26response_type%3Dcode%26state%3Dhttps%25253A%25252F%25252Fpage.kakao.com%25252F%26redirect_uri%3Dhttps%253A%252F%252Fpage.kakao.com%252Frelay%252Flogin%26through_account%3Dtrue%26client_id%3D49bbb48c5fdb0199e5da1b89de359484&talk_login=hidden#login";

    String kakaoSearchUrl = "https://page.kakao.com/search/result?keyword=" + title + "&categoryUid=11";

//    String kakaoId = "jeti11@naver.com";
//    String kakaoPw = "qntks505!";

//    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//    StringSelection userId = new StringSelection(kakaoId);
//    StringSelection userPw = new StringSelection(kakaoPw);

    try {
      // 카카오 로그인 페이지 접속하기
//      driver.get(kakaoLoginUrl);
//
//      // 아이디 입력
//      WebElement idInput = driver.findElement(By.xpath("//*[@id=\"loginId--1\"]"));
////      clipboard.setContents(userId, null);
////      idInput.click();
////      Thread.sleep(200);
////
////      idInput.sendKeys(Keys.CONTROL + "v");
//      idInput.sendKeys(kakaoId);
//      Thread.sleep(200);
//
//
//      // 비밀번호 입력
//      WebElement pwInput = driver.findElement(By.xpath("//*[@id=\"password--2\"]"));
////      clipboard.setContents(userPw, null);
////      pwInput.click();
////      Thread.sleep(200);
////      pwInput.sendKeys(Keys.CONTROL + "v");
//
//      pwInput.sendKeys(kakaoPw);
//      Thread.sleep(200);
//
//      // 로그인 버튼 클릭
//      WebElement btnLogin = driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/div/form/div[4]/button[1]"));
//      btnLogin.click();
//
//      Thread.sleep(100);

      // 해당 작품과 동일한 이름의 작품을 찾는 카카오 페이지 접속하기
      driver.get(kakaoSearchUrl);
      Thread.sleep(100);

      List<WebElement> aEls = driver.findElements(By.cssSelector(".flex-1.cursor-pointer"));

      // 검색 결과가 있는 경우
      if (!aEls.isEmpty()) {
        for (WebElement aEl : aEls) {
          WebElement searchTitleEl = aEl.findElement(By.cssSelector("div.flex.flex-col")).findElement(By.tagName("span"));
          // 찾는 작품이 단행본일 경우
          if (searchTitleEl.getText().contains("[완결]")) {
            String searchTitle = searchTitleEl.getText().substring(0, searchTitleEl.getText().indexOf("[") -1 );
            if (ne.equals("단행본")) {
              // 제목과 단행본 정보가 일치하는 지 확인
              if (title.equals(searchTitle) && searchTitle.contains("[단행본]")) {

                // platform 구분하기
                kakaoCrollingData.setPlatform(1);

                // platformId 가져오기
                int platformIdIndex = aEl.getAttribute("href").lastIndexOf("/");
                kakaoCrollingData.setPlatformId(aEl.getAttribute("href").substring(platformIdIndex + 1));

                // 작품 디테일 페이지 접속하기
                driver.get(aEl.getAttribute("href"));

                // novelTitle 가져오기
                kakaoCrollingData.setNovelTitle(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/span")).getText().substring(0, driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/span")).getText().indexOf("[") -1 ));

                // novelThumbnail 가져오기
                kakaoCrollingData.setNovelThumbnail(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[1]/div/div/img")).getAttribute("src"));

                // novelAuthor 가져오기
                kakaoCrollingData.setNovelAuthor(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/div/span")).getText());

                // novelCount 가져오기
                kakaoCrollingData.setNovelCount(Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/span")).getText().substring(3)));

                // starRate 가져오기
                kakaoCrollingData.setNovelStarRate(Double.parseDouble(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[1]/span[2]")).getText()));

                // novelCompleteYn 가져오기
                if (driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().equals("완결")) {
                  kakaoCrollingData.setNovelCompleteYn('Y');
                }
                else {
                  kakaoCrollingData.setNovelCompleteYn('N');
                }

                // novelUpdateDate 가져오기
                if (driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().contains("연재")) {
                  int novelUpdateDateIndex = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().indexOf("연재");
                  kakaoCrollingData.setNovelUpdateDate(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().substring(0, novelUpdateDateIndex - 1));
                }

                // cateList 가져오기
                kakaoCrollingData.setCateList(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[1]/span[4]")).getText());

                // novelRelease 가져오기
                kakaoCrollingData.setNovelRelease(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[2]/div[1]/div[2]/ul/li[1]/div/div/a/div/div[2]/div[2]/span[1]")).getText());

                // 작품소개 탭 클릭
                driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[1]/div/div/div[2]/a")).click();
                Thread.sleep(200);

                // novelIntro 가져오기
                kakaoCrollingData.setNovelIntro(driver.findElement(By.cssSelector(".whitespace-pre-wrap")).getText());

                // novelPubli 가져오기
                kakaoCrollingData.setNovelPubli(driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(1).findElement(By.tagName("div")).getText());

                // novelAdult 가져오기
                if (driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(2).findElement(By.tagName("div")).getText().equals("19세이용가")) {
                  kakaoCrollingData.setNovelAdult('Y');
                }
                else {
                  kakaoCrollingData.setNovelAdult('N');
                }

                // novelPrice 가져오기
                if (driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().indexOf("원") == -1) {
                  kakaoCrollingData.setNovelPrice(0);
                }
                else {
                  int novelPriceIndex = driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().indexOf("원");
                  kakaoCrollingData.setNovelPrice(Integer.parseInt(driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().substring(0, novelPriceIndex)));
                }

                // novelOrEbook 가져오기
                kakaoCrollingData.setNovelOrEbook(ne);

              }
            }
            else if (ne.equals("웹소설")) {
              if (title.equals(searchTitle) && !searchTitle.contains("[단행본]")) {

                // platform 구분하기
                kakaoCrollingData.setPlatform(1);

                // platformId 가져오기
                int platformIdIndex = aEl.getAttribute("href").lastIndexOf("/");
                kakaoCrollingData.setPlatformId(aEl.getAttribute("href").substring(platformIdIndex + 1));

                // 작품 디테일 페이지 접속하기
                driver.get(aEl.getAttribute("href"));

                // novelTitle 가져오기
                kakaoCrollingData.setNovelTitle(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/span")).getText().substring(0, driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/span")).getText().indexOf("[") -1 ));

                // novelThumbnail 가져오기
                kakaoCrollingData.setNovelThumbnail(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[1]/div/div/img")).getAttribute("src"));

                // novelAuthor 가져오기
                kakaoCrollingData.setNovelAuthor(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/div/span")).getText());

                // novelCount 가져오기
                kakaoCrollingData.setNovelCount(Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/span")).getText().substring(3)));

                // starRate 가져오기
                kakaoCrollingData.setNovelStarRate(Double.parseDouble(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[1]/span[2]")).getText()));

                // novelCompleteYn 가져오기
                if (driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().equals("완결")) {
                  kakaoCrollingData.setNovelCompleteYn('Y');
                }
                else {
                  kakaoCrollingData.setNovelCompleteYn('N');
                }

                // novelUpdateDate 가져오기
                if (driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().contains("연재")) {
                  int novelUpdateDateIndex = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().indexOf("연재");
                  kakaoCrollingData.setNovelUpdateDate(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().substring(0, novelUpdateDateIndex - 1));
                }

                // cateList 가져오기
                kakaoCrollingData.setCateList(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[1]/span[4]")).getText());

                // novelRelease 가져오기
                kakaoCrollingData.setNovelRelease(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[2]/div[1]/div[2]/ul/li[1]/div/div/a/div/div[2]/div[2]/span[1]")).getText());

                // 작품소개 탭 클릭
                driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[1]/div/div/div[2]/a")).click();
                Thread.sleep(200);

                // novelIntro 가져오기
                kakaoCrollingData.setNovelIntro(driver.findElement(By.cssSelector(".whitespace-pre-wrap")).getText());


                // novelPubli 가져오기
                kakaoCrollingData.setNovelPubli(driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(1).findElement(By.tagName("div")).getText());


                // novelAdult 가져오기
                if (driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(2).findElement(By.tagName("div")).getText().equals("19세이용가")) {
                  kakaoCrollingData.setNovelAdult('Y');
                }
                else {
                  kakaoCrollingData.setNovelAdult('N');
                }

                // novelPrice 가져오기
                if (driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().indexOf("원") == -1) {
                  kakaoCrollingData.setNovelPrice(0);
                }
                else {
                  int novelPriceIndex = driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().indexOf("원");
                  kakaoCrollingData.setNovelPrice(Integer.parseInt(driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().substring(0, novelPriceIndex)));
                }


                // novelOrEbook 가져오기
                kakaoCrollingData.setNovelOrEbook(ne);
              }
            }
          }
          else {
            String searchTitle = searchTitleEl.getText();
            if (ne.equals("단행본")) {
              // 제목과 단행본 정보가 일치하는 지 확인
              if (title.equals(searchTitle) && searchTitle.contains("[단행본]")) {

                // platform 구분하기
                kakaoCrollingData.setPlatform(1);

                // platformId 가져오기
                int platformIdIndex = aEl.getAttribute("href").lastIndexOf("/");
                kakaoCrollingData.setPlatformId(aEl.getAttribute("href").substring(platformIdIndex + 1));

                // 작품 디테일 페이지 접속하기
                driver.get(aEl.getAttribute("href"));

                // novelTitle 가져오기
                kakaoCrollingData.setNovelTitle(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/span")).getText());

                // novelThumbnail 가져오기
                kakaoCrollingData.setNovelThumbnail(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[1]/div/div/img")).getAttribute("src"));

                // novelAuthor 가져오기
                kakaoCrollingData.setNovelAuthor(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/div/span")).getText());

                // novelCount 가져오기
                kakaoCrollingData.setNovelCount(Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/span")).getText().substring(3)));

                // starRate 가져오기
                kakaoCrollingData.setNovelStarRate(Double.parseDouble(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[1]/span[2]")).getText()));

                // novelCompleteYn 가져오기
                if (driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().equals("완결")) {
                  kakaoCrollingData.setNovelCompleteYn('Y');
                }
                else {
                  kakaoCrollingData.setNovelCompleteYn('N');
                }

                // novelUpdateDate 가져오기
                if (driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().contains("연재")) {
                  int novelUpdateDateIndex = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().indexOf("연재");
                  kakaoCrollingData.setNovelUpdateDate(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().substring(0, novelUpdateDateIndex - 1));
                }

                // cateList 가져오기
                kakaoCrollingData.setCateList(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[1]/span[4]")).getText());

                // novelRelease 가져오기
                kakaoCrollingData.setNovelRelease(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[2]/div[1]/div[2]/ul/li[1]/div/div/a/div/div[2]/div[2]/span[1]")).getText());

                // 작품소개 탭 클릭
                driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[1]/div/div/div[2]/a")).click();
                Thread.sleep(200);

                // novelIntro 가져오기
                kakaoCrollingData.setNovelIntro(driver.findElement(By.cssSelector(".whitespace-pre-wrap")).getText());

                // novelPubli 가져오기
                kakaoCrollingData.setNovelPubli(driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(1).findElement(By.tagName("div")).getText());

                // novelAdult 가져오기
                if (driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(2).findElement(By.tagName("div")).getText().equals("19세이용가")) {
                  kakaoCrollingData.setNovelAdult('Y');
                }
                else {
                  kakaoCrollingData.setNovelAdult('N');
                }

                // novelPrice 가져오기
                if (driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().indexOf("원") == -1) {
                  kakaoCrollingData.setNovelPrice(0);
                }
                else {
                  int novelPriceIndex = driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().indexOf("원");
                  kakaoCrollingData.setNovelPrice(Integer.parseInt(driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().substring(0, novelPriceIndex)));
                }

                // novelOrEbook 가져오기
                kakaoCrollingData.setNovelOrEbook(ne);

              }
            }
            else if (ne.equals("웹소설")) {
              if (title.equals(searchTitle) && !searchTitle.contains("[단행본]")) {

                // platform 구분하기
                kakaoCrollingData.setPlatform(1);

                // platformId 가져오기
                int platformIdIndex = aEl.getAttribute("href").lastIndexOf("/");
                kakaoCrollingData.setPlatformId(aEl.getAttribute("href").substring(platformIdIndex + 1));

                // 작품 디테일 페이지 접속하기
                driver.get(aEl.getAttribute("href"));

                // novelTitle 가져오기
                kakaoCrollingData.setNovelTitle(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/span")).getText());

                // novelThumbnail 가져오기
                kakaoCrollingData.setNovelThumbnail(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[1]/div/div/img")).getAttribute("src"));

                // novelAuthor 가져오기
                kakaoCrollingData.setNovelAuthor(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/div/span")).getText());

                // novelCount 가져오기
                kakaoCrollingData.setNovelCount(Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/span")).getText().substring(3)));

                // starRate 가져오기
                kakaoCrollingData.setNovelStarRate(Double.parseDouble(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[1]/span[2]")).getText()));

                // novelCompleteYn 가져오기
                if (driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().equals("완결")) {
                  kakaoCrollingData.setNovelCompleteYn('Y');
                }
                else {
                  kakaoCrollingData.setNovelCompleteYn('N');
                }

                // novelUpdateDate 가져오기
                if (driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().contains("연재")) {
                  int novelUpdateDateIndex = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().indexOf("연재");
                  kakaoCrollingData.setNovelUpdateDate(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[2]/span")).getText().substring(0, novelUpdateDateIndex - 1));
                }

                // cateList 가져오기
                kakaoCrollingData.setCateList(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[3]/div[2]/div[1]/div[1]/span[4]")).getText());

                // novelRelease 가져오기
                kakaoCrollingData.setNovelRelease(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[2]/div[1]/div[2]/ul/li[1]/div/div/a/div/div[2]/div[2]/span[1]")).getText());

                // 작품소개 탭 클릭
                driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div[1]/div[2]/div[1]/div/div/div[2]/a")).click();
                Thread.sleep(200);

                // novelIntro 가져오기
                kakaoCrollingData.setNovelIntro(driver.findElement(By.cssSelector(".whitespace-pre-wrap")).getText());

                // novelPubli 가져오기
                kakaoCrollingData.setNovelPubli(driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(1).findElement(By.tagName("div")).getText());

                // novelAdult 가져오기
                if (driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(2).findElement(By.tagName("div")).getText().equals("19세이용가")) {
                  kakaoCrollingData.setNovelAdult('Y');
                }
                else {
                  kakaoCrollingData.setNovelAdult('N');
                }

                // novelPrice 가져오기
                if (driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().indexOf("원") == -1) {
                  kakaoCrollingData.setNovelPrice(0);
                }
                else {
                  int novelPriceIndex = driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().indexOf("원");
                  kakaoCrollingData.setNovelPrice(Integer.parseInt(driver.findElements(By.cssSelector(".font-small1.mb-8pxr")).get(3).findElement(By.tagName("div")).getText().substring(0, novelPriceIndex)));
                }



                // novelOrEbook 가져오기
                kakaoCrollingData.setNovelOrEbook(ne);
              }
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
}





























