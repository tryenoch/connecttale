package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.NovelMainDto;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import com.bitc.full505_final_team4.data.repository.NovelMainRepository;
import com.bitc.full505_final_team4.data.repository.NovelRankRepository;
import com.bitc.full505_final_team4.data.repository.PlatformMainRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class NovelNaverServiceImpl implements NovelNaverService{

  private final NovelRankRepository novelRankRepository;
  private final NovelMainRepository novelMainRepository;
  private final PlatformMainRepository platformMainRepository;

  // 오늘 날짜의 네이버 순위 리스트 저장하기
  @Override
  @Transactional
  public boolean storeNaverRankList() throws Exception {
    return false;
  }

  // 네이버 최신작 리스트 불러온 후 테이블에 없는 작품 저장하기
  @Override
  @Transactional
  public boolean storeNaverRecentNovel(int pageNum) throws Exception {

    boolean result = false;

    // 성인 작품 제외
    // jsoup으로 크롤링한 최신 소설 dto(소설 제목, 플랫폼 전용 아이디) 리스트 들고오기,
    List<NovelMainDto> dtoListNotAdult = getNaverRecentNovelList(pageNum);

    // platform table 에 들어갈 엔티티 리스트가 저장되는 곳
    List<NovelPlatformEntity> novelPlatformEntityList = new ArrayList<>();

    for(int i = 0; i < dtoListNotAdult.size(); i++){

      NovelMainDto dto = dtoListNotAdult.get(i);

      String title = dto.getNovelTitle();
      String platformId = dto.getPlatformId();
      String ebookCheck = dto.getEbookCheck(); // 단행본 웹소설 여부

      try {
        // novel table 에 일치하는 제목이 있는지 확인, 없을 경우 exception 반환
        Optional<NovelEntity> entityNovel = novelMainRepository.findByNovelTitleAndEbookCheck(title, ebookCheck);

        // 일치하는 제목이 없다면 부여한 예외 지점으로 넘김
        if (entityNovel.isEmpty()){
          throw new NoSuchElementException();
        }else {
          // novel table 에서 있다면 novelIdx 를 얻어 platform 테이블에 2차 검색
          int novelIdx = entityNovel.get().getNovelIdx();

          Optional<NovelPlatformEntity> entityPlatform =
            platformMainRepository.findByPlatformAndNovelIdx_NovelIdx(2, novelIdx);

          if(entityPlatform.isEmpty()){
            throw new Exception();
          }

          // novel 과 platform 테이블 둘 다 데이터가 있으므로 다음 번호로 넘어간다.
          // 업데이트 된 경우에 대해서 나중에 추가 로직 필요
          i++;

        }

      } catch (NoSuchElementException e){
        /*
        * novel table 에도 데이터가 없으니 platform 에도 없음
        * 둘 다 데이터를 넣어야함
        * */

        // novel table 에 등록
        NovelEntity novelEntity = getNovelEntityFromJsoup(platformId);

        // platform entity 생성
        NovelPlatformEntity novelPlatformEntity = getNovelPlatformEntityFromJsoup(novelEntity, platformId);

        // list 에 더하기
        novelPlatformEntityList.add(novelPlatformEntity);


      } catch (Exception e){

        /*
        * novel 테이블에는 있지만 platform 에는 없는 것
        * platform table 에 데이터를 등록해야함
        * */

        // platform 데이터를 만들기 위한 매개변수 entity 들고오기
        NovelEntity novelEntity = novelMainRepository.findByNovelTitle(title).get();

        // platform entity 생성
        NovelPlatformEntity novelPlatformEntity = getNovelPlatformEntityFromJsoup(novelEntity, platformId);

        // list 에 더하기
        novelPlatformEntityList.add(novelPlatformEntity);

      }

    }

    // add 한 목록들 저장
    try {

      platformMainRepository.saveAll(novelPlatformEntityList);
      result = true;
      System.out.println("SUCCESS MESSAGE : 네이버 신작 목록이 업데이트 되었습니다.");
    } catch (Exception e){

      // 오류 발생시 false 반환
      e.printStackTrace();
      result = false;
    }


    return result;
  }

  /**************** 단위로 자른 기능 모음 ****************/

  // jsoup 으로 최신 소설 list 들고오기 페이지당 25개 (소설 제목, 플랫폼 전용 아이디)
  // 각 소설에 대한 정보는 dto에 담는다, 19세는 우선 제외 추후 구현 예정
  @Override
  public List<NovelMainDto> getNaverRecentNovelList(int pageNum) throws Exception {

    List<NovelMainDto> dtoListNotAdult = new ArrayList<>();

    String url = "https://series.naver.com/novel/recentList.series?page=" + pageNum;

    Document doc = Jsoup.connect(url).get();

    try {

      // 최신 소설 element 리스트 (총 25개)
      Elements recentList = doc.select(".lst_list").select("li");

      for (int i = 0; i < recentList.size();){
        NovelMainDto novel = new NovelMainDto();

        Element recentNovel = recentList.get(i);

        try{

          // 19금 아이콘이 있는지 찾기 비지 않았다면, 성인 컨텐츠
          if(!ObjectUtils.isEmpty(recentNovel.select("em.ico.n19"))){
            // 19금 작품이면 관련 내용 추후 구현, 우선 다음 내용으로 넘어감
            throw new NoSuchElementException();
          }

          Elements infoA = recentNovel.select("div > h3 > a");

          String title = infoA.attr("title");

          // 단행본 웹소설 여부 들고오기
          String ebookCheck = getEbookCheck(title);
          novel.setEbookCheck(ebookCheck);

          title = editNaverNovelTitle(title); // 소설 제목만 들고오기 [, (제외
          novel.setNovelTitle(title);


          // 플랫폼 전용 아이디 들고오기

          String platformId = infoA.attr("href");
          platformId = editNaverPlatformId(platformId);
          novel.setPlatformId(platformId);

          if(dtoListNotAdult.add(novel)){
            i++;
          }

        } catch (NoSuchElementException e) {
          // 19금 작품이면 관련 내용 추후 구현, 우선 다음 내용으로 넘어감
          i++;
        }

      }

    }
    catch (Exception e){
      e.printStackTrace();
    }


    return dtoListNotAdult;
  }

  // jsoup 으로 novel 에 맞는 정보 들고온 후 entity save 및 리턴
  @Override
  @Transactional
  public NovelEntity getNovelEntityFromJsoup(String platformId) throws Exception {

//    NovelEntity novelEntity = new NovelEntity();


    // novel_title, novel_thumbnail, novel_adult 를 얻어야함

    String url = "https://series.naver.com/novel/detail.series?productNo=";
    url += platformId;

    Document doc = Jsoup.connect(url).get();

    try {

      Elements page = doc.select("#container");

      Elements img = page.select("div.aside .pic_area img");

      String title = img.attr("alt");
      String editTitle = editNaverNovelTitle(title); // novel_title
      String ebookCheck = getEbookCheck(title); // ebook_check

      // novel_thumbnail
      String imgUrl = img.attr("src");
      imgUrl = editNaverThumbnail(imgUrl);

      Elements infoList = page.select("li.info_lst > ul > li");
      // novel_adult
      String ageInfo = infoList.get(4).text();
      ageInfo = getAdultYn(ageInfo);


      NovelEntity novelEntity = new NovelEntity(editTitle, imgUrl, ageInfo, ebookCheck);
      novelMainRepository.save(novelEntity);

      return novelEntity;

    }catch (Exception e){
      e.printStackTrace();

      return null;
    }

  }

  // jsoup으로 아이디에 해당하는 novel platform 정보 들고온 후 entity 리턴
  @Override
  @Transactional
  public NovelPlatformEntity getNovelPlatformEntityFromJsoup(NovelEntity novelEntity, String platformId) throws Exception {

    NovelPlatformEntity entity = new NovelPlatformEntity();

    String url = "https://series.naver.com/novel/detail.series?productNo=";
      url += platformId;

    Document doc = Jsoup.connect(url).get();
    entity.setPlatform(2); // platform, naver
    entity.setPlatformId(platformId); // platform_id
    entity.setNovelIdx(novelEntity); // novel_idx

    try {

      Elements page = doc.select("#container");

      Elements img = page.select("div.aside .pic_area img");

      // novel_title
      String title = img.attr("alt");
      String editTitle = editNaverNovelTitle(title);
      entity.setNovelTitle(editTitle); // novel_title

      String ebookCheck = getEbookCheck(title);
      entity.setEbookCheck(ebookCheck); // ebook_check


      // novel_thumbnail
      String imgUrl = img.attr("src");
      imgUrl = editNaverThumbnail(imgUrl);
      entity.setNovelThumbnail(imgUrl);

      Elements infoList = page.select("li.info_lst > ul > li");

      String introScript = page.select("div._synopsis").text();
      entity.setNovelIntro(introScript); // novel_intro

      String writer = infoList.get(2).select("a").text();
      entity.setNovelAuthor(writer); // novel_author

      String publi = infoList.get(3).select("a").text();
      entity.setNovelPubli(publi); // novel_publi

      int totalCount = Integer.parseInt(page.select(".end_total_episode").select("strong").text());
      entity.setNovelCount(totalCount); // novel_count

      String completeYn = infoList.get(0).select("a").text();
      completeYn = getCompleteYn(completeYn);
      entity.setNovelCompleteYn(completeYn); // novel_complete_yn

      String priceInfo = page.select("div.area_price").select(".point_color").text();
      int price = getNovelPrice(priceInfo);
      entity.setNovelPrice(price); // novel_price

      String starInfo = page.select(".score_area").select("em").text();
      double starRate = Double.parseDouble(starInfo);
      entity.setNovelStarRate(starRate); // novel_star_rate

      /*
      Elements test1 = page.select(".tbl_buy");
      Elements test2 = page.select(".tbl_buy").select("#volumeList");

      String firstDate = page.select(".subj").get(0).select("em").text();
      firstDate = getReleaseDate(firstDate);
      entity.setNovelRelease(firstDate); // novel_release, 오류남, 해당페이지에서 동적으로 데이터를 입력하는 부분이라 받아올 수 없음
      */

      String cate = infoList.get(1).select("a").text();
      cate = cateListConverterIn(cate);
      entity.setCateList(cate); // cate_list

      String adultYn = infoList.get(4).select("a").text();
      adultYn = getAdultYn(adultYn);
      entity.setNovelAdult(adultYn); // novel_adult

      // novel_recent_update 얻을 수는 있는데 페이지 주소를 바꿔야 해서 보류

      return entity;

    } catch (Exception e){
      e.printStackTrace();

      return null;
    }
  }

  // naver 테이블에 저장하기 위한 용
  // 네이버 소설 제목에 [, 또는 (로 시작하는 부분이 있을 경우 자르기
  @Override
  public String editNaverNovelTitle(String title) throws Exception{

    // 예시 : 나의 행복한 결혼 [단행본] (총 4권/미완결)
    // 결과 : 나의 행복한 결혼
    // 다른 경우 추가시 수정 예정

    if(title.contains("(")){
      int idx = title.lastIndexOf("(");
      title = title.substring(0, idx -1);
    }

    if(title.contains("[")){
      int idx = title.lastIndexOf("[");
      title = title.substring(0, idx -1);
    }

    return title;
  }

  // a 태그에 있는 소설 아이디를 얻기 위한 함수
  @Override
  public String editNaverPlatformId(String platformUrl) throws Exception{

    /*
    * 예시 : https://series.naver.com/novel/detail.series?productNo=4130558
    * 결과 : 4130558
    * */

    String platformId = "";

    if(platformUrl.contains("=")){
      int idx = platformUrl.lastIndexOf("=");
      platformId = platformUrl.substring(idx +1);
    }

    return platformId;
  }

  // 썸네일 주소 변환
  @Override
  public String editNaverThumbnail(String imgUrl) throws Exception {

    /*
    * 예시 : https://comicthumb-phinf.pstatic.net/20230426_280/pocket_16824743041949x0OK_JPEG/%B3%AA%B4%C2_%C7%E3%BC%F6%BE%C6%BA%F1_%C5%B0%B8%B6%B4%D4_1%B1%C7.jpg?type=m260
    * 결과 : https://comicthumb-phinf.pstatic.net/20230426_280/pocket_16824743041949x0OK_JPEG/%B3%AA%B4%C2_%C7%E3%BC%F6%BE%C6%BA%F1_%C5%B0%B8%B6%B4%D4_1%B1%C7.jpg
    * */

    String editThumbnailUrl = "";

    if(imgUrl.contains("?")) {
      int idx = imgUrl.lastIndexOf("?");
      editThumbnailUrl = imgUrl.substring(0, idx);
    }

    return editThumbnailUrl;
  }

  // 성인 컨텐츠 여부 (페이지 내에서 찾는 것)
  @Override
  public String getAdultYn(String ageInfo) throws Exception{
    String adultYn = "N";

    if(ageInfo.contains("19")){
      adultYn = "Y";
    }

    return adultYn;
  }

  // 웹소설 또는 ebook 여부
  @Override
  public String getEbookCheck(String title) throws Exception{
    String ebookCheck = "웹소설";

    if(title.contains("단행본")){
      ebookCheck = "단행본";
    }

    return ebookCheck;
  }

  // 연재중 완결 여부
  @Override
  public String getCompleteYn(String serialInfo) throws Exception {

    String completeYn = "N";

    if(serialInfo.contains("완결")){
      completeYn = "Y";
    }

    return completeYn;
  }

  // 가격 변환 함수
  @Override
  public int getNovelPrice(String priceNum) throws Exception{
    int price = 0;

    if(priceNum.contains("무료")){
      price = 0;
    } else {
      price = Integer.parseInt(priceNum);
    }

    if(price != 0){
      price = (price * 100);
    }

    return price;
  }

  @Override
  public String getReleaseDate(String dateInfo) throws Exception{
    /*
    * 네이버 출시일이 (2023.06.27.) 이렇게 되어있기 때문에
    * 양쪽 괄호를 없애는 함수
    * 결과 : 2023.06.27
    * */

    dateInfo = dateInfo.substring(1, dateInfo.length()-2);

    return dateInfo;
  }

  // entity 에 넣기 위한 숫자 리스트 변환
  @Override
  public String cateListConverterIn(String cateItem) throws Exception {
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
