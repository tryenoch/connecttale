package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.entity.NovelLikeEntity;
import com.bitc.full505_final_team4.data.entity.NovelLikeIdx;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NovelLikeRepository extends JpaRepository<NovelLikeEntity, NovelLikeIdx> {
  Page<NovelLikeEntity> findAllByIdAndLikeYn(MemberEntity member, String like);
}
