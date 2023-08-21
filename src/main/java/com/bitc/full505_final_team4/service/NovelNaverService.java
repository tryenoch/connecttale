package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.NovelMainDto;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.Map;

public interface NovelNaverService {
  // 오늘 날짜의 네이버 순위 리스트 저장하기
  boolean storeNaverRankList () throws Exception;

  // 네이버 최신작 리스트 불러온 후 테이블에 없는 작품 저장하기
  boolean storeNaverRecentNovel(int pageNum) throws Exception;

  /**************** 단위로 자른 기능 모음 ****************/

  // jsoup 으로 최신 소설 list 들고오기 페이지당 25개 (소설 제목, 플랫폼 전용 아이디)
  List<NovelMainDto> getNaverRecentNovelList(int pageNum) throws Exception;

  // 리스트 에서 각 li 의 날짜 정보 들고오기
  String getUpdateDateInList(String element) throws Exception;

  // jsoup 으로 novel 에 맞는 정보 들고온 후 entity save 및 리턴
  NovelEntity getNovelEntityFromJsoup(String platformId) throws Exception;

  // jsoup으로 아이디에 해당하는 novel platform 정보 들고온 후 entity 리턴
  NovelPlatformEntity getNovelPlatformEntityFromJsoup(NovelEntity novelEntity, String platformId) throws Exception;

  String editNaverNovelTitle(String title) throws Exception;

  String editNaverPlatformId(String platformUrl) throws Exception;

  String editNaverThumbnail(String imgUrl) throws Exception;

  String getAdultYn(String ageInfo) throws Exception;

  String getEbookCheck(String title) throws Exception;

  String getCompleteYn(String serialInfo) throws Exception;

  int getNovelPrice(String priceNum) throws Exception;

  String getReleaseDate(String dateInfo) throws Exception;

  String cateListConverterIn(String cateItem) throws Exception;
}
