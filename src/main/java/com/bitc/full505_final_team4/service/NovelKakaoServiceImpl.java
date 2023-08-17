package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.NovelDto;
import com.bitc.full505_final_team4.data.dto.NovelPlatformDto;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class NovelKakaoServiceImpl implements NovelKakaoService{

  public boolean storeKakaoRankList() throws Exception {
    return false;
  }

  public boolean storeKakaoRecentNovel() throws Exception {
    return false;
  }

  /**************** 단위로 자른 기능 모음 ****************/

  // jsoup 으로 최신 소설 list 들고 오기
  /*
   * 19세 또는 15세 작품 catch 문으로 이동 (추후 구현)
   * db 에 저장되지 않은 작품이면 NovelDto 생성 및 Entity 변환
   * */
  public List<NovelEntity> getKakaoRecentNovelList() throws Exception {

    List<NovelEntity> entityListNotAdult = new ArrayList<>();

    String url = "https://page.kakao.com/menu/10011/screen/84?sort_opt=latest";

    Document doc = Jsoup.connect(url).get();

    try {

      Elements recentList = doc.select("div.mb-4pxr.flex-col > div > div.flex.grow.flex-col > div > div > div").select("a");

    } catch (NoSuchElementException e){

    }

    return entityListNotAdult;
  }

  // jsoup 에서 전달해준 Elements 로 NovelDto 만들기 및 Entity 저장
  public  NovelEntity getNovelEntity(Elements novelInfo) throws Exception {
    return null;
  }

  // NovelEntity 와 아이디에 해당하는 json 파일에서 Dto 정보 얻어오기
  // 이후 selenium 함수로 얻어온 데이터를 더하여 dto 완성, driver 선언부 확인하기
  public  NovelPlatformDto getNovelPlatformDto(String platformId, NovelDto novelEntity) throws Exception {
    return null;
  }

  // json 에서 얻어오지 못하는 데이터 Selenium 으로 얻어오기
  // 함수 선언부 밖에서 driver.quit 해줘야함
  public HashMap<String, Object> addRemainData(WebDriver driver, String platformId) {
    return null;
  }

  // 웹소설인지 단행본인지 체크
  public String getEbookCheck(String title) throws Exception {
    return null;
  }

  // 연재중(연재요일) 또는 완결 여부
  public String getCompleteNovel(String novelUpdateInfo) throws Exception {
    return null;
  }

  // 장르 이름 숫자 변환
  public String cateListConverterIn(String cateInfo) throws Exception{
    return null;
  }

}
