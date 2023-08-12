package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import com.bitc.full505_final_team4.data.repository.NovelPlatformRepository;
import com.bitc.full505_final_team4.data.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NovelDetailServiceImpl implements NovelDetailService{

  private final NovelPlatformRepository novelPlatformRepository;
  private final NovelRepository novelRepository;

  // 클릭한 작품 제목의 novelIdx 찾기
  @Override
  public int getNovelIdx(String platformId) {
    int novelidx = 0;
    Optional<NovelPlatformEntity> novel = novelPlatformRepository.findByPlatformId(platformId);


    return novelidx;
  }


  @Override
  public void insertRidiToNovel(NovelEntity novelEntity) {
    novelRepository.save(novelEntity);
  }

  @Override
  public void insertRidiToPlatform(NovelPlatformEntity novelPlatformEntity) {
    novelPlatformRepository.save(novelPlatformEntity);
  }




  // 리디북스 디테일 정보 db 저장하기
//  @Override
//  public void insertRidi(NovelPlatformEntity novelPlatformEntity) {
//    novelPlatformRepository.save(novelPlatformEntity);
//  }
}
