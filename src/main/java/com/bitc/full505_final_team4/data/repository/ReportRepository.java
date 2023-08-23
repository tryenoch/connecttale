package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.NovelReplyEntity;
import com.bitc.full505_final_team4.data.entity.ReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {
  Optional<ReportEntity> findByReplyIdx(NovelReplyEntity novelReplyEntity);

  Optional<ReportEntity> findByReplyIdxAndReporter(NovelReplyEntity novelReplyEntity, String reporter);

}
