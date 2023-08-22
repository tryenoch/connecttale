package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.NovelDto;
import com.bitc.full505_final_team4.data.dto.NovelMainDto;
import com.bitc.full505_final_team4.data.dto.NovelPlatformDto;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface NovelKakaoService {

  // 카카오 순위 리스트 불러오기 (jsoup) 사용
  List<NovelMainDto> getKakaoList(String urlId) throws Exception;
  boolean storeKakaoRankList() throws Exception;

  boolean storeKakaoRecentNovel() throws Exception;

  /**************** 단위로 자른 기능 모음 ****************/

  // jsoup 으로 최신 소설 list 들고 오기
  /*
  * 19세 또는 15세 작품 catch 문으로 이동 (추후 구현)
  * db 에 저장되지 않은 작품이면 NovelDto 생성 및 Entity 변환
  * */
  ArrayList<HashMap> getKakaoRecentNovelList() throws Exception;

  // jsoup 에서 전달해준 Elements 로 NovelDto 만들기 및 Entity 저장
  NovelEntity getNovelEntity(Element novelInfo) throws Exception;

  // NovelEntity 와 아이디에 해당하는 json 파일에서 Dto 정보 얻어오기
  // 이후 selenium 함수로 얻어온 데이터를 더하여 dto 완성, driver 선언부 확인하기
  NovelPlatformEntity getNovelPlatformEntity(String platformId, NovelEntity novelEntity) throws Exception;

  // json 에서 얻어오지 못하는 데이터 Selenium 으로 얻어오기
  // 함수 선언부 밖에서 driver.quit 해줘야함
  HashMap<String, Object> addRemainData(WebDriver driver, String platformId) throws Exception;

  // [jsoup] a 태그에서 플랫폼 아이디 자르기
  String getPlatformId(String hrefText) throws Exception;

  // 제목 양식 정리하기
  String getTitle (String title) throws Exception;

  String getAgeInfo(String info) throws Exception;

  String getAgeInfoFromJson(String info) throws Exception;

  // 웹소설인지 단행본인지 체크
  String getEbookCheck(String title) throws Exception;

  // 가격 정보 얻어오기
  int getPriceInfo (String retailPrice) throws Exception;

  // 총 화수 구하기
  int getNovelCount (String info) throws Exception;

  // 연재중(연재요일) 또는 완결 여부
  String getCompleteNovel(String novelUpdateInfo) throws Exception;

  // 장르 이름 숫자 변환
  String cateListConverterIn(String cateInfo) throws Exception;
}
