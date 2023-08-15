package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;

import java.util.List;

public interface NovelDetailService {
  List<NovelPlatformEntity> getNovelDetail(String platformId);

  void insertRidiToNovel(NovelEntity novelEntity);

  void insertRidiToPlatform(NovelPlatformEntity novelPlatformEntity);


  NovelPlatformEntity getNaverCrolling(String platformId, String title, String novelOrEbook);

  void insertNaverToNovel(NovelEntity novelEntity);

  void insertNaverToPlatform(NovelPlatformEntity novelPlatformEntity);

  NovelPlatformEntity getKakaoCrolling(String id, String title, String ne);

  void insertKakaoToNovel(NovelEntity novelEntity);

  void insertKakaoToPlatform(NovelPlatformEntity kakaoPlatformEntity);
}
