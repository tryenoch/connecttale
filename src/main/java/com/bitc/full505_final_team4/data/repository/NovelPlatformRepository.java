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


  Optional<NovelPlatformEntity> findByPlatformId(String platformId);

  List<NovelPlatformEntity> findAllByNovelEntity(NovelEntity novelEntity);
}
