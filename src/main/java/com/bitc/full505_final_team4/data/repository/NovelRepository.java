package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.NovelEntity;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NovelRepository extends JpaRepository<NovelEntity, Integer> {

  NovelEntity findByNovelTitleAndEbookCheck(String title, String ebookCheck);

  Slice<NovelEntity> findByNovelIdx(int likeNum);

}
