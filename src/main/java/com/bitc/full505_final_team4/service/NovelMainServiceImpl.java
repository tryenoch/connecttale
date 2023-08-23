package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.common.JsonUtils;
import com.bitc.full505_final_team4.common.WebDriverUtil;
import com.bitc.full505_final_team4.data.dto.NovelMainDto;
import com.bitc.full505_final_team4.data.dto.NovelPlatformDto;
import com.bitc.full505_final_team4.data.dto.NovelRankDto;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import com.bitc.full505_final_team4.data.entity.NovelRankEntity;
import com.bitc.full505_final_team4.data.repository.NovelLikeRepository;
import com.bitc.full505_final_team4.data.repository.NovelMainRepository;
import com.bitc.full505_final_team4.data.repository.NovelRankRepository;
import com.bitc.full505_final_team4.data.repository.PlatformMainRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.propertyeditors.CurrencyEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NovelMainServiceImpl implements NovelMainService{

  private final NovelCommonEditService commonEditService;
  private final NovelRankRepository novelRankRepository;
  private final NovelLikeRepository novelLikeRepository;
  private final NovelNaverService novelNaverService;
  private final PlatformMainRepository platformMainRepository;
  private final NovelMainRepository novelMainRepository;

  // 확인용 데이터 불러오기
  @Override
  public boolean checkTodayRankData(int platform, int rankNum, LocalDate date) throws Exception {
    // 오늘 불러온 데이터가 있다면 true 이므로 데이터를 저장하는 메소드를 동작 시킬 필요가 없다.
    NovelRankEntity check = novelRankRepository.findByPlatformAndRankNumAndUpdateDate(platform, rankNum, date);

    if(!ObjectUtils.isEmpty(check)){
      return true;
    } else { return false; }
  }

  // 리디북스 특정 카테고리 데이터를 저장한다.
  @Override
  public boolean storeRidiCategoryRankList(int category, int startNum) throws Exception {

    boolean result = false;
    int cateRankNum = ridiCategoryRankNum(category);

    List<NovelRankEntity> novelRankList = new ArrayList<>();

    String url = "https://api.ridibooks.com/v2/bestsellers?category_includes=";
    url += category + "&offset=";
    // startNum : 첫 순위 번호
    url += (startNum - 1) + "&limit=50&period=DAILY";


    try {
      // 페이지 json 객체로 변환
      JSONObject novelResult = (JSONObject) JsonUtils.jsonUrlParser(url).get("data");
      ArrayList<JSONObject> novelList = (ArrayList<JSONObject>) novelResult.get("items"); // 배열 변환

      if (!novelList.isEmpty()) {
        for (JSONObject novelItem : novelList) {
          // 각각 item에 들어있는 book Object
          HashMap<String, Object> book = (HashMap<String, Object>) novelItem.get("book");

          // 값을 저장할 entity
//          NovelRankEntity novel = new NovelRankEntity();

          // platform : 리디북스는 3
          int platform = 3;

          // 소설 순위
          int rankNum = novelList.indexOf(novelItem) + 1 + cateRankNum; // novelItem의 인덱스 번호

          // 플랫폼 제공 아이디
          String platformId = book.get("book_id").toString();

          // 소설 제목 얻어오기
          JSONObject serial = (JSONObject) book.get("serial");
          String title = serial.get("title").toString();

          // 작가 이름 얻어오기
          ArrayList authorsList = (ArrayList) book.get("authors");
          HashMap<String, Object> authors = (HashMap<String, Object>) authorsList.get(0);
          String author = authors.get("name").toString();

          // 소설 썸네일
          JSONObject cover = (JSONObject) book.get("cover");
          String thumbnail = cover.get("large").toString();

          // 소설 카테고리
          /*JSONArray categories = (JSONArray) book.get("categories");
          JSONObject categoryFirst = (JSONObject) categories.get(0);
          novel.setCategory(categoryFirst.get("name").toString());*/


          // 소설 별점
          /*JSONArray ratings = (JSONArray) book.get("ratings");
          novel.setNovelStarRate(getStarRate(ratings)); // 하위에 구현한 함수 사용*/

          int adultsOnly = 0;

          // 성인 여부
          if ((Boolean) book.get("adults_only")) {
            adultsOnly = 1; // 성인 작품 맞음
          } else {
            adultsOnly = 0; // 성인 작품 아님
          }

          novelRankList.add(new NovelRankEntity(platform, rankNum, title, author, thumbnail, platformId, LocalDate.now(), category, adultsOnly)); // entity 리스트에 저장
        }

        novelRankRepository.saveAll(novelRankList);
        result = true;
      }
    } catch (Exception e){
      e.printStackTrace();
    }


    return result;
  }

  /* 리디북스 카테고리 별 순위 리스트 불러오기
  * 시작번호로부터 20개 출력
  * 총 100위까지 출력되도록 함 */
  @Override
  public List<NovelMainDto> getRidiRankList(String category, int startNum) throws Exception {

    List<NovelMainDto> novelDtoList = new ArrayList<>();

    String url = "https://api.ridibooks.com/v2/bestsellers?category_includes=";
      url += category + "&offset=";
      url += (startNum - 1) + "&limit=20&period=DAILY";

    // 페이지 json 객체로 변환
    JSONObject novelResult = (JSONObject) JsonUtils.jsonUrlParser(url).get("data");
    ArrayList<JSONObject> novelList = (ArrayList<JSONObject>) novelResult.get("items"); // 배열 변환

    if (!novelList.isEmpty()){
      for(JSONObject novelItem : novelList){
        // 각각 item에 들어있는 book Object
        HashMap<String, Object > book = (HashMap<String, Object>) novelItem.get("book");

        // 값을 저장할 dto
        NovelMainDto novel = new NovelMainDto();

        // 소설 순위
        novel.setNovelIndexNum(novelList.indexOf(novelItem) + 1); // novelItem의 인덱스 번호

        // 플랫폼 제공 아이디
        novel.setPlatformId(book.get("book_id").toString());

        // 소설 제목 얻어오기
        JSONObject serial = (JSONObject) book.get("serial");
        novel.setNovelTitle(commonEditService.editTitleForNovelEntity(serial.get("title").toString()));

        // 작가 이름 얻어오기
        ArrayList authorsList = (ArrayList) book.get("authors");
        HashMap<String, Object> authors = (HashMap<String, Object>) authorsList.get(0);
        novel.setNovelAuthor(authors.get("name").toString());

        // 소설 썸네일
        JSONObject cover = (JSONObject) book.get("cover");
        novel.setNovelThumbnail(cover.get("large").toString());

        // 소설 카테고리
        JSONArray categories = (JSONArray) book.get("categories");
        JSONObject categoryFirst = (JSONObject) categories.get(0);
        novel.setCateList(categoryFirst.get("name").toString());

        // 소설 별점
        JSONArray ratings = (JSONArray) book.get("ratings");
        novel.setNovelStarRate(getStarRate(ratings)); // 하위에 구현한 함수 사용

        // 성인 여부
        novel.setAdultsOnly((Boolean) book.get("adults_only"));

        // "웹소설" 또는 "단행본" 여부
        novel.setEbookCheck("웹소설");


        novelDtoList.add(novel);
      }

      return novelDtoList;
    }

    return null;
  }

  /* 네이버 리스트 불러오기 */
  @Override
  public List<NovelMainDto> getNaverRankList(String startNum, String endNum, int totalPageNum) throws Exception {
    List<NovelMainDto> novelList = new ArrayList<>();

    String url = "https://series.naver.com/novel/top100List.series?rankingTypeCode=DAILY&categoryCode=ALL&page=";

    int start = Integer.parseInt(startNum);
    int end = Integer.parseInt(endNum);

    for (int i = 1; i <= totalPageNum ; i++) {
      // 문자열 url을 주소화하여 페이지를 가져옴
      Document doc = Jsoup.connect(url + i).get();

      try{

        Elements listDoc = doc.getElementsByClass("comic_top_lst").select("li");
        List<Element> list = listDoc.subList(start, end);

        for (Element rankItem : list){
          NovelMainDto novel = new NovelMainDto();

          // 순위
          int rankNum = list.indexOf(rankItem);
          novel.setNovelIndexNum(rankNum + 1);

          // 플랫폼 아이디
          String link = rankItem.select(".pic").attr("href");
          String platformId = link.substring(link.lastIndexOf("=") + 1);

          novel.setPlatformId(platformId);

          // 썸네일
          String thumbnailEl = rankItem.select("img").attr("src");
          String thumbnail = thumbnailEl.substring(0, thumbnailEl.lastIndexOf("?"));
          novel.setNovelThumbnail(thumbnail);

          // 제목
          String title = rankItem.select("h3").select("a").text();
          /*if (title.contains("[")){
            int idx = title.lastIndexOf("[");
            title = title.substring(0, idx -1);
          }*/

          String ebookCheck = novelNaverService.getEbookCheck(title); // 웹소설 여부
          novel.setEbookCheck(ebookCheck);

          title = commonEditService.editTitleForNovelEntity(title);
          novel.setNovelTitle(title);

          // 작가
          String author = rankItem.select(".author").text();
          novel.setNovelAuthor(author);

          // 별점
          double starRate = Double.parseDouble(rankItem.select(".score_num").text());
          novel.setNovelStarRate(starRate);

          boolean adultsOnly = false;

          if(!ObjectUtils.isEmpty(rankItem.select("em.ico.n19"))){
            adultsOnly = true;
          }

          novel.setAdultsOnly(adultsOnly); // 성인여부



          novelList.add(novel);

        }

      }catch (Exception e){
        e.printStackTrace();
      }
    }
    return novelList;
  }

  @Override
  public List<NovelMainDto> getKakaoList(String urlId) throws Exception {

    // 크롤링 할 WebDriverUtil 객체 생성
    WebDriver driver = WebDriverUtil.getChromeDriver();

    List<NovelMainDto> novelDtoList = new ArrayList<>();

    // 카카오 웹소설 실시간 랭킹 주소
    String url = "https://page.kakao.com/menu/10011/screen/" + urlId;

    if (!ObjectUtils.isEmpty(driver)){
      driver.get(url);

      try {
        // 브라우저 이동 시 생기는 로드시간을 기다린다.
        // HTTP 응답속도보다 자바의 컴파일 속도가 더 빠르기 때문에 임의적으로 1초를 대기한다
//        Thread.sleep(1000);
//        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        // 대기시간 duration 객체 생성
        Duration duration = Duration.ofSeconds(60);
        // WebDriverWait 객체 생성 : 페이지가 열릴때까지 duration 만큼 기다림
        WebDriverWait wait = new WebDriverWait(driver, duration);

        try {
          //카카오 웹소설 랭킹 리스트 들고오기
          wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@style='border-color:transparent;border-width:4px']")));


//      WebElement element = driver.findElement(By.cssSelector(".foldable:grid-inner-border-cols6"));
          List<WebElement> list = driver.findElements(By.xpath("//div[@style='border-color:transparent;border-width:4px']")).subList(0, 12);
          // list = list.subList(0, 20);// 20개 리스트 들고오기

          if(!ObjectUtils.isEmpty(list)){ // list 가 null 값이 아니라면
            for(WebElement element : list){
              /* dto 에 필수적으로 넣어야 하는 요소
               * 플랫폼 아이디, 제목, 썸네일 주소, 플랫폼 이름 */
              NovelMainDto novel = new NovelMainDto();

              novel.setPlatform("kakao"); // 플랫폼 이름 입력

              //순위 불러오기
              novel.setNovelIndexNum(list.indexOf(element) + 1); // novelItem의 인덱스 번호

              // a 태그에서 id 값 잘라오기
              String id = element.findElement(By.tagName("a")).getAttribute("href");
              int idIdx = id.lastIndexOf("/")+1;
              id = id.substring(idIdx);

              novel.setPlatformId(id);

              // 제목
              String title = element.findElement(By.cssSelector(".line-clamp-2")).getText();
              novel.setNovelTitle(title);

//          String locator = "//img[@alt=\"썸네일\"][" + list.indexOf(element) + "]";
              //썸네일
              String thumbnail = "";

              try {
            /*if (ObjectUtils.isEmpty(element.findElement(By.cssSelector(".object-cover.visible")).getAttribute("src"))){
              throw new org.openqa.selenium.NoSuchElementException();
            } else {*/
                thumbnail = element.findElement(By.cssSelector(".object-cover.visible")).getAttribute("src");
                novel.setNovelThumbnail(thumbnail);


              }catch (NoSuchElementException e){
                // 썸네일 객체가 없다면 성인 컨텐츠라는 뜻이므로 adultsOnly를 true로 한다
                novel.setAdultsOnly(true);
                novel.setNovelThumbnail(thumbnail);

                // ※ Socket Exception 이 발생하므로 추가 조정할 것
              }

              novelDtoList.add(novel);
            }
          }

      /*
      // element 리스트 내 아이디를 담을 배열
      List<String> idList = new ArrayList<>();

      for(WebElement link : list){
        String id = link.getAttribute("href");

        for (int i = 0; i < 2; i++) {
          // 아이디 값만 잘라오기, "/"가 두개라 두번 돌림
          id = id.substring(id.lastIndexOf("/") + 1);
        }

        idList.add(id); // 리스트에 추가
      }

      // 아이디에 해당하는 작품 정보 리스트 들고오기
      for(String novelId : idList){
        NovelMainDto novel = getKakaoNovel(novelId);
        novelDtoList.add(novel);
      }*/
        }
        catch (Exception e){
          e.printStackTrace();

          // 이미 켜져있던 크롬드라이버를 강제종료하는 트라이캐치문
          try {
            Process process = Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe /t");
            int exitCode = process.waitFor();
          } catch (IOException | InterruptedException e2) {
          }


        }
        finally {
          driver.quit();
        }

      } catch (Exception e){
        e.printStackTrace();
      }

    }



    return novelDtoList;
  }

  /* 카카오 특정 작품 정보 가져오기 , selenium 방식 */
  @Override
  public NovelMainDto getKakaoNovel(String novelId) throws Exception {

    // 크롤링 할 WebDriverUtil 객체 생성
    WebDriver driver = WebDriverUtil.getChromeDriver();

    NovelMainDto novel = new NovelMainDto();


    String url = "https://page.kakao.com/content/" + novelId;

    driver.get(url);

    if (!ObjectUtils.isEmpty(driver)){
      driver.get(url);

      try {
        // 브라우저 이동 시 생기는 로드시간을 기다린다.
        // HTTP 응답속도보다 자바의 컴파일 속도가 더 빠르기 때문에 임의적으로 1초를 대기한다.
        Thread.sleep(500);
      } catch (InterruptedException e){
        e.printStackTrace();
      }

    }

    try {

      // 작품 제목
      String title = driver.findElement(By.xpath("//meta[@property='og:title']")).getAttribute("content");
      novel.setNovelTitle(title);

      // 작가 이름
      String author = driver.findElement(By.xpath("//meta[@name='author']")).getAttribute("content");
      novel.setNovelAuthor(author);

      // 썸네일 주소
      String thumbnail = driver.findElement(By.xpath("//meta[@property='og:image']")).getAttribute("content");
      novel.setNovelThumbnail(thumbnail);

      WebElement infoTag = driver.findElement(By.cssSelector(".mt-16pxr.text-el-60"));

      // 장르
      String cate = infoTag.findElements(By.tagName("span")).get(3).getText();
      novel.setCateList(cate);

      // 별점
      String starRate = infoTag.findElements(By.tagName("span")).get(1).getText();
      double rate = Double.parseDouble(starRate);
      novel.setNovelStarRate(rate);


      // 작품설명 메타태그로 들고올 수 있음, 필요시 추가
    } catch (Exception e){
      e.printStackTrace();
    }
    finally {
      driver.quit();
    }

    return novel;
  }

  // 최신 리스트 itemCount 수만큼 들고오기
  @Override
  public List<NovelPlatformDto> getRecentNovelList(int itemCount) throws Exception{

    Pageable pageable = PageRequest.of(0, itemCount);

    List<NovelPlatformEntity> entityList = platformMainRepository.findNovelPlatformEntitiesByOrderByNovelReleaseDesc(pageable);

    // entity 리스트를 dto 리스트로 변환
    List<NovelPlatformDto> list = entityList.stream().map(m -> new NovelPlatformDto().toDto(m)).collect(Collectors.toList());


    return list;
  }

  // 좋아요 높은 순대로 itemCount 수만큼 들고오기
  @Override
  public List<NovelPlatformDto> getMaxLikeNovelList(int itemCount) throws Exception {
    Pageable pageable = PageRequest.of(0, itemCount);

    List<Integer> likeNovelList = novelLikeRepository.findNovelLikeMaxCount(pageable);

    List<NovelPlatformEntity> entityList = new ArrayList<>();

    /* platform num 3 > 2 > 1 순으로 platform 정보 얻어오기 */
    for (int novelIdx : likeNovelList){
      for (int i = 3; i > 0; i--) {
        // 3번 플랫폼부터 entity 가 존재하는 지 찾아보기 있으면 list 에 더하고 for 문에서 탈출
        Optional<NovelPlatformEntity> entity = platformMainRepository.findByNovelIdx_NovelIdxAndPlatform(novelIdx, i);
        if(!ObjectUtils.isEmpty(entity)){
          entityList.add(entity.get());
          break;
        }
      }
    }

    // entity 리스트를 dto 리스트로 변환
    List<NovelPlatformDto> list = entityList.stream().map(m -> new NovelPlatformDto().toDto(m)).collect(Collectors.toList());

    return list;
  }

  @Override
  public List<NovelPlatformDto> getCateNovelList (String cateItem, String page) throws Exception{
    Pageable pageable = PageRequest.of(Integer.parseInt(page), 10);

    List<NovelPlatformEntity> entityList = new ArrayList<>();

    if (cateItem.equals("0")){
      entityList = platformMainRepository.findAllBy(pageable);
    } else {
      entityList = platformMainRepository.findNovelPlatformEntitiesByCateListLike(cateItem, pageable);
    }

    // entity 리스트를 dto 리스트로 변환
    List<NovelPlatformDto> list = entityList.stream().map(m -> new NovelPlatformDto().toDto(m)).collect(Collectors.toList());

    return list;
  }

  /* Ridi Json 에서 들고온 ratings 별점으로 변환하기 (10점 만점 기준) */
  @Override
  public double getStarRate(JSONArray ratings) throws Exception {

    /* 계산식
    * ((1점 * 1점 count) + ... + (5점 * 5점 count)) / totalCount
    * */

    String starRate = "";

    double multiRating = 0;
    double totalCount = 0;

    /* count * rating 총합 구하기 */

    for (int i = 0; i < ratings.size(); i++) {

      JSONObject rateObject = (JSONObject) ratings.get(i);
      int rating = Integer.parseInt(rateObject.get("rating").toString()) ;
      int count = Integer.parseInt(rateObject.get("count").toString()) ;

      int multiRate = rating * count;

      multiRating += multiRate;
      totalCount += count;

    }

    DecimalFormat form = new DecimalFormat("#.#");

    double total = (multiRating / totalCount) * 2;
    total = Double.parseDouble(form.format(total));


    // 왜 한자리수 올림이 안되는 건지...
//    total = (double) Math.ceil((total * 100) / 100.0);

    // 소수점 한자리까지 보여주는 별점 반환
//    starRate = String.format("%.1f", total);

    return total;
  }

  // 리디 카테고리별 pk 중복 방지를 위한 카테고리별 pk 생성 메소드
  // 리디북스 카테고리 1750 : 판타지(0) / 1650 : 로맨스(50) / 6050 : 로판(100) / 4150: BL(150)
  @Override
  public int ridiCategoryRankNum(int category) throws Exception {
    int cateRankNum = 0;
    switch (category){
      case 1750 : // 판타지
        cateRankNum = 0;
        break;

      case 1650 : // 로맨스
        cateRankNum = 50;
        break;

      case 6050 : // 로판
        cateRankNum = 100;
        break;

      case 4150 : // BL
        cateRankNum = 150;
        break;
    }

    return cateRankNum;
  }


}

