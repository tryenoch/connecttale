package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.dto.NovelLikeCountDto;
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
  Page<NovelLikeEntity> findAllByIdAndLikeYn(MemberEntity id, String like, Pageable pageable);


  Optional<NovelLikeEntity> findByIdAndNovelIdx(MemberEntity likeId, NovelEntity likeNovelIdx);


  int countByNovelIdxAndLikeYn(NovelEntity novelIdx, String y);

  Optional<List<NovelLikeEntity>> findAllByNovelIdx(NovelEntity novelIdx);

  // 좋아요 높은 순 구하기
  @Query(
    value = "SELECT novel_idx " +
      "FROM novel_like " +
      "WHERE like_yn = 'Y' GROUP BY novel_idx ORDER BY count(novel_idx) desc ",
    nativeQuery = true
  )
  List<Integer> findNovelLikeMaxCount(Pageable pageable);


}
