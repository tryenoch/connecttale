package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.entity.BoardEntity;

import java.util.List;

public interface BoardService {
    List<BoardEntity> getReqList() throws Exception;

    List<BoardEntity> getNotiList() throws Exception;

    List<BoardEntity> getEventList() throws Exception;
}
