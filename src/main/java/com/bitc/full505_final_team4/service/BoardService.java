package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    Page<BoardEntity> getReqList(Pageable pageable) throws Exception;

    Page<BoardEntity> getNotiList(Pageable pageable) throws Exception;

    Page<BoardEntity> getEventList(Pageable pageable) throws Exception;

    void setBoard(BoardEntity board) throws Exception;
}
