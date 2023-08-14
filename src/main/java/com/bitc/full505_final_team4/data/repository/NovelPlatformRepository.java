package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatFormIdx;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NovelPlatformRepository extends JpaRepository<NovelPlatformEntity, NovelPlatFormIdx> {

// 작품 platformId로 db내 저장된 platform 테이블 데이터 불러오기
  List <NovelPlatformEntity> findAllByNovelTitle(String title);

  List<NovelPlatformEntity> findAllByNovelIdx_NovelIdx(int novelIdx);
}
