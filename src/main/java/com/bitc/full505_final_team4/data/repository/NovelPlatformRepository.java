package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformGroupKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NovelPlatformRepository extends JpaRepository<NovelPlatformEntity, NovelPlatformGroupKey> {

}
