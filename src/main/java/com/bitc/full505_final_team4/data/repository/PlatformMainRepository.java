package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatFormIdx;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// service main 패키지와 연동되는 repository
public interface PlatformMainRepository extends JpaRepository<NovelPlatformEntity, NovelPlatFormIdx> {

  // 플랫폼 번호와 novel idx, 웹소설 또는 ebook 여부
  Optional<NovelPlatformEntity> findByPlatformAndNovelEntityAndNovelEAndNovelOrEbook(int platform, NovelEntity entity, String novelOrEbook);

}
