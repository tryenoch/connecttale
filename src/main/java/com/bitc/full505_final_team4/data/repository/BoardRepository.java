package com.bitc.full505_final_team4.data.repository;


import com.bitc.full505_final_team4.data.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    List<BoardEntity> findBoardEntityByBoardCate_Idx(int idx);

    Page<BoardEntity> findByBoardCate_IdxOrderByBoardIdxDesc(int idx, Pageable pageable);

    BoardEntity findByBoardIdx(int idx);

    List<BoardEntity> findByBoardCate_IdxAndBoardTitleContainsOrderByBoardIdxDesc(int idx, String title);

    List<BoardEntity> findByBoardCate_IdxAndCreateId_IdContainsOrderByBoardIdxDesc(int idx, String id);
}
