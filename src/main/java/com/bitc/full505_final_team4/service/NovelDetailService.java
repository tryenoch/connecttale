package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;

import java.util.List;
import java.util.Map;

public interface NovelDetailService {
  List<NovelPlatformEntity> getNovelDetail(String platformId);

  void insertRidiToNovel(NovelEntity novelEntity);

  void insertRidiToPlatform(NovelPlatformEntity novelPlatformEntity);




}
