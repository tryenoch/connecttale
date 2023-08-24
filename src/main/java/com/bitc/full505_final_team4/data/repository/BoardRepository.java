package com.bitc.full505_final_team4.data.repository;


import com.bitc.full505_final_team4.data.entity.BoardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    List<BoardEntity> findBoardEntityByBoardCate_Idx(int idx);

    Page<BoardEntity> findByBoardCate_IdxOrderByBoardIdxDesc(int idx, Pageable pageable);

    // myQna 구현
    Page<BoardEntity> findByCreateId_IdAndBoardCate_IdxOrderByBoardIdxDesc(String id, int idx, Pageable pageable);

    BoardEntity findByBoardIdx(int idx);

    List<BoardEntity> findByBoardCate_IdxAndBoardTitleContainsOrderByBoardIdxDesc(int idx, String title);

    List<BoardEntity> findByBoardCate_IdxAndCreateId_IdContainsOrderByBoardIdxDesc(int idx, String id);

    @Transactional
    @Modifying
    @Query("update BoardEntity p set p.hitCnt = p.hitCnt + 1 where p.boardIdx = :id")
    int updateCnt(@Param("id") int id);
}
