package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.NovelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// service main 패키지와 연동되는 repository
public interface NovelMainRepository extends JpaRepository<NovelEntity, Integer> {
  // 소설 제목으로 select 하기
  Optional<NovelEntity> findByNovelTitle(String title);

//  NovelEntity findByNovelTitle(String title);

}
