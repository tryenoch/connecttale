package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelLikeEntity;
import com.bitc.full505_final_team4.data.entity.NovelLikeIdx;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NovelLikeRepository extends JpaRepository<NovelLikeEntity, NovelLikeIdx> {


  Optional<NovelLikeEntity> findByIdAndNovelIdx(MemberEntity likeId, NovelEntity likeNovelIdx);


  int countByNovelIdxAndLikeYn(NovelEntity novelIdx, String y);

  Optional<List<NovelLikeEntity>> findAllByNovelIdx(NovelEntity novelIdx);

  NovelLikeEntity findAllByIdAndLikeYn(String id, String like);

}
