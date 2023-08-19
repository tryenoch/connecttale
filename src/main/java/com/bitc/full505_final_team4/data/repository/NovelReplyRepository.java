package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NovelReplyRepository extends JpaRepository<NovelReplyEntity, Integer> {

  Optional<List<NovelReplyEntity>> findAllByNovelIdxOrderByCreateDtDesc(NovelEntity novelIdx);
}
