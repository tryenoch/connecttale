package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.NovelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NovelRepository extends JpaRepository<NovelEntity, Integer> {

  NovelEntity findByNovelTitleAndEbookCheck(String title, String ebookCheck);

  List<NovelEntity> findByNovelIdx(int likeNum);

}
