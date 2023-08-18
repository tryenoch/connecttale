package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelLikeEntity;
import com.bitc.full505_final_team4.data.entity.NovelLikeIdx;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import java.util.List;

public interface NovelLikeRepository extends JpaRepository<NovelLikeEntity, NovelLikeIdx> {
  List<NovelLikeEntity> findAllByIdAndLikeYn(MemberEntity id, String like);


  Optional<NovelLikeEntity> findByIdAndNovelIdx(MemberEntity likeId, NovelEntity likeNovelIdx);


  int countByNovelIdxAndLikeYn(NovelEntity novelIdx, String y);

  Optional<List<NovelLikeEntity>> findAllByNovelIdx(NovelEntity novelIdx);


}
