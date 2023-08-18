package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.NovelDto;
import com.bitc.full505_final_team4.data.dto.NovelPlatformDto;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NovelKakaoServiceImpl implements NovelKakaoService{

  @Override
  @Transactional
  public boolean storeKakaoRankList() throws Exception {
    return false;
  }

  @Override
  @Transactional
  public boolean storeKakaoRecentNovel() throws Exception {
    return false;
  }

  /**************** 단위로 자른 기능 모음 ****************/

  // jsoup 으로 최신 소설 list 들고 오기
  /*
   * 19세 또는 15세 작품 catch 문으로 이동 (추후 구현)
   * db 에 저장되지 않은 작품이면 NovelDto 생성 및 Entity 변환
   * */
  @Override
  @Transactional
  public List<NovelEntity> getKakaoRecentNovelList() throws Exception {

    List<NovelEntity> entityListNotAdult = new ArrayList<>();

    String url = "https://page.kakao.com/menu/10011/screen/84?sort_opt=latest";

    Document doc = Jsoup.connect(url).get();

    try {

      // 신작 리스트 Element 리스트
      Elements recentList = doc.select("div.mb-4pxr.flex-col > div > div.flex.grow.flex-col > div > div > div").select("a");

      // 반복문으로 연령이 걸리는 작품 먼저 거르기
      for (int i = 0; i < recentList.size();){

        Element listItem = recentList.get(i);

        Elements mainDiv = listItem.select("div");

        String findText = mainDiv.attr("aria-label");
        String ageInfo = getAgeInfo(findText); // 15세 또는 19세 작품인지
        String ebookCheck = getEbookCheck(findText); // 웹소설 여부
        String platformId = listItem.attr("href");
        platformId = getPlatformId(platformId); // 플랫폼 아이디




        try {

        } catch (NoSuchElementException e){
          // 작품이 15세 또는 19세로 로그인 해야 content 페이지에 접속이 가능하므로 우선 넘김

          i++;
        }



      }

    } catch (Exception e){
      System.out.println("[ERROR] Jsoup 크롤링 중 요소를 찾는 데 오류가 발생했습니다. ");
      e.printStackTrace();
    }

    return entityListNotAdult;
  }


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
  public  NovelPlatformDto getNovelPlatformDto(String platformId, NovelDto novelEntity) throws Exception {
    return null;
  }

  // json 에서 얻어오지 못하는 데이터 Selenium 으로 얻어오기
  // 함수 선언부 밖에서 driver.quit 해줘야함
  @Override
  public HashMap<String, Object> addRemainData(WebDriver driver, String platformId) throws Exception {
    return null;
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

  // 제목 양식 정리하기 (최대 3번 검사, "["괄호만 들어가있는 경우)
  @Override
  public String getTitle (String title) throws Exception {



    if(title.contains("[")) {
      int idx = 0;

      idx = title.indexOf("[");
      title = title.substring(0, idx);

    }

    return title;
  }

  /* 연령대 체크, 기본 리턴 값 N
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

  // 웹소설인지 단행본인지 체크
  @Override
  public String getEbookCheck(String title) throws Exception {

    String ebookCheck = "웹소설";

    if (title.contains("단행본")){
      ebookCheck = "단행본";
    }

    return ebookCheck;
  }

  // 연재중(연재요일) 또는 완결 여부
  @Override
  public String getCompleteNovel(String novelUpdateInfo) throws Exception {
    return null;
  }

  // 장르 이름 숫자 변환
  @Override
  public String cateListConverterIn(String cateInfo) throws Exception{
    return null;
  }

}
