package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.common.JsonUtils;
import com.bitc.full505_final_team4.common.WebDriverUtil;
import com.bitc.full505_final_team4.data.dto.NovelDto;
import com.bitc.full505_final_team4.data.dto.NovelMainDto;
import com.bitc.full505_final_team4.data.dto.NovelPlatformDto;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import com.bitc.full505_final_team4.data.repository.NovelMainRepository;
import com.bitc.full505_final_team4.data.repository.NovelPlatformRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class NovelKakaoServiceImpl implements NovelKakaoService{

  private final NovelCommonEditService novelCommonEditService;
  private final NovelMainRepository novelMainRepository;
  private final NovelPlatformRepository novelPlatformRepository;

  // 카카오 순위 리스트 불러오기 (jsoup) 사용
  @Override
  public List<NovelMainDto> getKakaoList(String urlId) throws Exception{
    List<NovelMainDto> list = new ArrayList<>();

    String url = "https://page.kakao.com/menu/10011/screen/" + urlId;

    Document doc = Jsoup.connect(url).get();

    try {
      List<Element> rankList = doc.select("div.mb-4pxr.flex-col > div > div.flex.grow.flex-col > div > div > div").select("a");

      if(!ObjectUtils.isEmpty(rankList)){
        for (Element element : rankList){

          // 따온 정보를 dto에 저장
          NovelMainDto novel = new NovelMainDto();

          novel.setPlatform("kakao");
          novel.setNovelIndexNum(rankList.indexOf(element) + 1);

          // a 태그에서 id 값 잘라오기
          String id = element.attr("href");
          id = getPlatformId(id);
          novel.setPlatformId(id);

          // 제목
          String title = element.select(".text-el-60.line-clamp-2").text();
          title = novelCommonEditService.editTitleForNovelEntity(title);
          novel.setNovelTitle(title);

          // 썸네일
          String thumbnail = "";

          thumbnail = element.select(".select-none.object-cover").attr("src");
          thumbnail = thumbnail.substring(0, thumbnail.length() -1);

          boolean adultsOnly = false;

          if (ObjectUtils.isEmpty(element.select(".select-none.object-cover"))){
            adultsOnly = true;
          }

          novel.setNovelThumbnail(thumbnail);
          novel.setAdultsOnly(adultsOnly);

          list.add(novel);
        }
      }

    } catch (Exception e){
      System.out.println("[ERROR] 카카오 랭크 리스트 크롤링 중 오류가 발생하였습니다.");
      e.printStackTrace();
    }


    return list;
  }

  @Override
  @Transactional
  public boolean storeKakaoRankList() throws Exception {
    return false;
  }

  @Override
  @Transactional
  public boolean storeKakaoRecentNovel() throws Exception {
    boolean success = false;

    // 생성된 platform Entity 를 저장할 목록
    List<NovelPlatformEntity> platformNovelList = new ArrayList<>();

    // novel table 에 막 저장된, 전체 연령가 카카오 소설 목록
    // 넘어온 값은 platformId 와 함께 쓸 Novel entity
    ArrayList<HashMap> novelEntityList = getKakaoRecentNovelList();

    // 크롤링용 드라이버 생성
    WebDriver driver = WebDriverUtil.getChromeDriver();

    try {

      for (HashMap novelInfo : novelEntityList){

        String platformId = (String) novelInfo.get("platformId");
        NovelEntity novelEntity = (NovelEntity) novelInfo.get("novel");
        NovelDto novelDto = NovelDto.toDto(novelEntity);

        // Json 으로 정보 얻어옴
        NovelPlatformDto platformDto = getNovelPlatformDto(platformId, novelDto);
        // Selenium 으로 추가 정보 얻어옴
        HashMap<String, Object> remainData = addRemainData(driver, platformId);


        platformDto.setNovelCount((int) remainData.get("novelCount"));
        platformDto.setNovelCompleteYn((String) remainData.get("novelCompleteYn"));
        platformDto.setNovelStarRate((double) remainData.get("novelStarRate"));
        platformDto.setNovelUpdateDate((String) remainData.get("novelUpdateDate"));
        platformDto.setNovelRelease((String) remainData.get("novelReleaseDate"));


        NovelPlatformEntity novelPlatformEntity = platformDto.toEntity(platformDto);

        novelPlatformRepository.save(novelPlatformEntity);
        platformNovelList.add(novelPlatformEntity);

      }

//      novelPlatformRepository.saveAll(platformNovelList);
      System.out.println("[SUCCESS] 카카오 최신 웹소설 리스트가 총 "+ platformNovelList.size() + "개 업데이트 되었습니다. (전체 연령가) ");

      success = true;

    } catch (Exception e){

      System.out.println("[ERROR] storeKakaoRecentNovel 시도 중 오류가 발생했습니다.");
      e.printStackTrace();

    } finally {
      driver.close();
    }




    return success;
  }

  /**************** 단위로 자른 기능 모음 ****************/

  // jsoup 으로 최신 소설 list 들고 오기
  /*
   * 19세 또는 15세 작품 catch 문으로 이동 (추후 구현)
   * db 에 저장되지 않은 작품이면 NovelDto 생성 및 Entity 변환
   * platform Id 와 entity 를 전달해줌
   * */
  @Override
  @Transactional
  public ArrayList<HashMap> getKakaoRecentNovelList() throws Exception {

    ArrayList<HashMap> novelList = new ArrayList<>();

    String url = "https://page.kakao.com/menu/10011/screen/84?sort_opt=latest";

    Document doc = Jsoup.connect(url).get();

    try {

      // 신작 리스트 Element 리스트
      List<Element> recentList = doc.select("div.mb-4pxr.flex-col > div > div.flex.grow.flex-col > div > div > div").select("a").subList(0, 15);

      // 반복문으로 연령이 걸리는 작품 먼저 거르기
      for (int i = 0; i < recentList.size();){

        Element listItem = recentList.get(i);

        try {

          Elements mainDiv = listItem.select("div");

          String findText = mainDiv.attr("aria-label");
          String ageInfo = getAgeInfo(findText); // 15세 또는 19세 작품인지

          if (ageInfo.equals("Y") || ageInfo.equals("M")){
            throw new NoSuchElementException();
          }

          String ebookCheck = getEbookCheck(findText); // 웹소설 여부
          String platformId = listItem.attr("href");
          platformId = getPlatformId(platformId); // 플랫폼 아이디

          // Entity 에 넣을 제목 양식 다듬기
          String novelTitle = mainDiv.select(".text-el-60.line-clamp-2").text();
          novelTitle = novelCommonEditService.editTitleForNovelEntity(novelTitle);

          // novel table 에 있는지 확인하기(제목, 연령, 웹소설 여부)
          Optional<NovelEntity> novelCheck = novelMainRepository.findByNovelTitleAndEbookCheckAndNovelAdult(novelTitle, ebookCheck, ageInfo);

          if(!novelCheck.isEmpty()){
            // novel table 에 있는지 추가 검사
            throw new Exception();
          } else {

            // 없으면 entity 생성
            String thumbnail = mainDiv.select(".select-none.object-cover").attr("src"); // 썸네일 정보

            /*NovelEntity novel = new NovelEntity();

            novel.setNovelTitle(novelTitle);
            novel.setNovelAdult(ageInfo);
            novel.setNovelThumbnail(thumbnail);
            novel.setEbookCheck(ebookCheck);*/

            NovelEntity novel = NovelEntity.builder()
                    .novelThumbnail(thumbnail)
                    .novelAdult(ageInfo)
                    .novelTitle(novelTitle)
                    .ebookCheck(ebookCheck)
                    .build();

            HashMap<String, Object> novelInfo = new HashMap<>();

            novelMainRepository.save(novel); // db 저장, 그래야 idx 값이 생김

            novelInfo.put("platformId", platformId);
            novelInfo.put("novel", novel);

            novelList.add(novelInfo);
          }

        } catch (NoSuchElementException e){

          // 작품이 15세 또는 19세로 로그인 해야 content 페이지에 접속이 가능하므로 우선 넘김
          i++;
          continue;
        } catch (Exception e){
          // Novel table 에 이미 등록되어 있으므로 추가 등록 할 필요가 없다.
          i++;
          continue;
        }

      }

    } catch (Exception e){
      System.out.println("[ERROR] Jsoup 크롤링 중 오류가 발생했습니다. ");
      e.printStackTrace();
    }

    return novelList;
  }


  // 사용 안함
  // jsoup 에서 전달해준 Elements 로 NovelDto 만들기 및 Entity 저장
  @Override
  @Transactional
  public  NovelEntity getNovelEntity(Element novelInfo) throws Exception {
    return null;
  }

  // NovelEntity 와 아이디에 해당하는 json 파일에서 Dto 정보 얻어오기
  // 이후 selenium 함수로 얻어온 데이터를 더하여 dto 완성, driver 선언부 확인하기
  @Override
  @Transactional
  public  NovelPlatformDto getNovelPlatformDto(String platformId, NovelDto novelDto) throws Exception {

    String url = "https://page.kakao.com/_next/data/2.12.2/ko/content/" + platformId +".json";
    NovelPlatformDto dto = new NovelPlatformDto(); // 데이터 넣을 entity 생성

    try {
      JSONObject novelResult = (JSONObject) JsonUtils.jsonUrlParser(url).get("pageProps");

      dto.setPlatform(1); // platform, 카카오로 1번 고정
      dto.setPlatformId(platformId); // platform_id
      dto.setNovelKeyDto(novelDto); // novel_idx

      // novel 주요 정보
      JSONObject metaInfo = (JSONObject) novelResult.get("metaInfo");
      String ogTitle = metaInfo.get("ogTitle").toString();
      dto.setEbookCheck(getEbookCheck(ogTitle)); // ebook_check
      dto.setNovelTitle(novelDto.getNovelTitle()); // novel_title

      String img = metaInfo.get("image").toString();
      img = img.substring(0, img.length() -1);
      dto.setNovelThumbnail(img); // novel_thumbnail

      String introDesc = metaInfo.get("description").toString();
      dto.setNovelIntro(introDesc); // novel_intro

      String author = metaInfo.get("author").toString(); // novel_author
      dto.setNovelAuthor(author);

      JSONObject dehydratedState = (JSONObject) novelResult.get("dehydratedState");
      ArrayList<JSONObject> queries = (ArrayList<JSONObject>) dehydratedState.get("queries");
      JSONObject state = (JSONObject) queries.get(0).get("state");
      JSONObject data = (JSONObject) state.get("data");
      JSONObject contentHomeAbout = (JSONObject) data.get("contentHomeAbout");
      JSONObject detail = (JSONObject) contentHomeAbout.get("detail");

      String publi = detail.get("publisherName").toString();
      dto.setNovelPubli(publi); // novel_publi

      String priceText = detail.get("retailPrice").toString();
      int price = getPriceInfo(priceText);

      dto.setNovelPrice(price); // novel_price

      String cate = detail.get("category").toString();
      cate = cateListConverterIn(cate);
      dto.setCateList(cate); // cate_list

      String ageGrade = detail.get("ageGrade").toString();
      dto.setNovelAdult(getAgeInfoFromJson(ageGrade)); // novel_adult


    } catch (Exception e){
      System.out.println("[ERROR] " + platformId + "JSON으로 데이터를 불러오는 중 오류가 발생했습니다.");
    }

    return dto;
  }

  // json 에서 얻어오지 못하는 데이터 Selenium 으로 얻어오기
  // 함수 선언부 밖에서 driver.quit 해줘야함
  @Override
  public HashMap<String, Object> addRemainData(WebDriver driver, String platformId) throws Exception {

    HashMap<String, Object> remainData = new HashMap<>();

    String url = "https://page.kakao.com/content/" + platformId;

    try {
      driver.get(url);
      Thread.sleep(500);
      driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

      int novelCount = 0;
      String novelCompleteYn = "N";
      double novelStarRate = 0.0;
      String novelUpdateDate = "";
      String novelReleaseDate = "";

      // .mt-16pxr.text-el-60
      WebElement infoDiv = driver.findElement(By.cssSelector(".mt-16pxr.text-el-60"));

      String novelCountInfo = driver.findElement(By.cssSelector(".text-el-50.line-clamp-1")).getText();
      novelCount = getNovelCount(novelCountInfo);


      novelUpdateDate = driver.findElement(By.cssSelector(".flex-col.text-el-50")).findElements(By.tagName("span")).get(0).getText();

      String infoDivText = infoDiv.getText();
      novelCompleteYn = getCompleteNovel(infoDivText);

      novelReleaseDate = driver.findElements(By.cssSelector(".break-all.align-middle")).get(0).getText();


      try {
        WebElement starImg = infoDiv.findElement(By.xpath("//img[@alt='별점']"));

        String starRate = infoDiv.findElements(By.tagName("span")).get(1).getText();
        novelStarRate = Double.parseDouble(starRate);


      } catch (Exception e) {
        // 이미지가 없으면 별점이 아직 없는 것이므로 0.0 점
        novelStarRate = 0.0;
      }

      // 정의한 데이터 넣기
      remainData.put("novelCount", novelCount);
      remainData.put("novelCompleteYn", novelCompleteYn);
      remainData.put("novelStarRate", novelStarRate);
      remainData.put("novelUpdateDate", novelUpdateDate);
      remainData.put("novelReleaseDate", novelReleaseDate);


    }catch (Exception e){
      System.out.println("[ERROR] "+ platformId +"의 셀레니움 크롤링 중 오류가 발생했습니다.");
      e.printStackTrace();
    }

    return remainData;
  }

  // [jsoup] a 태그에서 플랫폼 아이디 자르기
  @Override
  public String getPlatformId(String hrefText) throws Exception {

    String platformId = "";

    if (hrefText.contains("/")){
      int idx = hrefText.lastIndexOf("/");
      platformId = hrefText.substring(idx + 1);
    }

    return platformId;

  }

  // NovelCommonEditService 내의 editTitleForNovelEntity 함수로 대체
  @Override
  public String getTitle (String title) throws Exception {

    return title;
  }

  /* 목록에서 들고오는 용
  연령대 체크, 기본 리턴 값 N
  * 19세일경우 Y
  * 15세일경우 마찬가지로 카카오에서 접속이 막히므로, M으로 우선 표시*/
  @Override
  public String getAgeInfo(String info) throws Exception {
    String editInfo ="N";

    if (info.contains("19세")){
      editInfo = "Y";
    } else if (info.contains("15세")) {
      editInfo = "M";
    }

    return editInfo;
  }

  @Override
  public String getAgeInfoFromJson(String info) throws Exception{
    String editInfo = "N";

    if(info.contains("Nineteen")){
      editInfo = "Y";
    }

    return editInfo;
  }

  // 웹소설인지 단행본인지 체크
  @Override
  public String getEbookCheck(String title) throws Exception {

    String ebookCheck = "웹소설";

    if (title.contains("단행본")){
      ebookCheck = "단행본";
    }

    return ebookCheck;
  }

  @Override
  public int getPriceInfo (String retailPrice) throws Exception{
    int price = 0;

    if (retailPrice.contains("원")){
      int idx = retailPrice.indexOf("원");
      retailPrice = retailPrice.substring(0, idx);

      price = Integer.parseInt(retailPrice);
    }

    return price;
  }

  @Override
  public int getNovelCount (String info) throws Exception{
    int count = 0;

    if(info.contains("전체")){
      info = info.substring(3);
      count = Integer.parseInt(info);
    }

    return count;
  }

  // 연재중(연재요일) 또는 완결 여부
  @Override
  public String getCompleteNovel(String novelUpdateInfo) throws Exception {

    String str = "N";

    if(novelUpdateInfo.contains("완결")){
      str = "Y";
    }

    return str;
  }

  // 장르 이름 숫자 변환
  @Override
  public String cateListConverterIn(String cateItem) throws Exception{

    String convertNum = "";

    if (cateItem.contains("판타지")){
      convertNum = "1";
    } else if (cateItem.contains("현판")) {
      convertNum = "2";
    } else if (cateItem.contains("로맨스")) {
      convertNum = "3";
    } else if (cateItem.contains("로판")) {
      convertNum = "4";
    } else if (cateItem.contains("무협")) {
      convertNum = "5";
    } else if (cateItem.contains("미스터리") || cateItem.contains("라이트노벨")){
      convertNum = "6";
    } else if (cateItem.contains("BL")) {
      convertNum = "7";
    } else {
      System.out.println("cateListConverterIn : 일치하는 카테고리 명이 없습니다.");
    }


    return convertNum;
  }

}
