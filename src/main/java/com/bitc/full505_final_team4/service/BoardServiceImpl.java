package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.entity.BoardCateEntity;
import com.bitc.full505_final_team4.data.entity.BoardEntity;
import com.bitc.full505_final_team4.data.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public Page<BoardEntity> getReqList(Pageable pageable) throws Exception {
        return boardRepository.findByBoardCate_IdxOrderByBoardIdxDesc(1, pageable);
    }

    @Override
    public Page<BoardEntity> getNotiList(Pageable pageable) throws Exception {
        return boardRepository.findByBoardCate_IdxOrderByBoardIdxDesc(2, pageable);
    }

    @Override
    public Page<BoardEntity> getEventList(Pageable pageable) throws Exception {
        return boardRepository.findByBoardCate_IdxOrderByBoardIdxDesc(3, pageable);
    }

    @Override
    public void setBoard(BoardEntity board) throws Exception {
        boardRepository.save(board);
    }
}
