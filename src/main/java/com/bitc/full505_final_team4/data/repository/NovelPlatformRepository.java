package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.NovelPlatFormIdx;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NovelPlatformRepository extends JpaRepository<NovelPlatformEntity, NovelPlatFormIdx> {


  Optional<NovelPlatformEntity> findByPlatformId(String platformId);
}
