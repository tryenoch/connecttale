package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.BoardEntity;
import com.bitc.full505_final_team4.data.entity.BoardReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardReplyRepository extends JpaRepository<BoardReplyEntity, Integer> {

    List<BoardReplyEntity> findByBoardIdx(BoardEntity board);

}
