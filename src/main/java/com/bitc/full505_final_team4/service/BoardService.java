package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.BoardDTO;
import com.bitc.full505_final_team4.data.dto.BoardReplyDTO;
import com.bitc.full505_final_team4.data.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    Page<BoardEntity> getReqList(Pageable pageable) throws Exception;

    Page<BoardEntity> getNotiList(Pageable pageable) throws Exception;

    Page<BoardEntity> getEventList(Pageable pageable) throws Exception;

    int setBoard(BoardDTO board) throws Exception;

    BoardDTO getBoard(int idx) throws Exception;


    void deleteBoard(int idx) throws Exception;

    void setBoardReply(BoardReplyDTO boardReply) throws Exception;

    List<BoardReplyDTO> getBoardReplyList(int idx) throws Exception;

    void deleteBoardReply(int idx) throws Exception;

    List<BoardEntity> searchTitleBoard(String keyword) throws Exception;

    List<BoardEntity> searchWriterBoard(String keyword) throws Exception;
}
