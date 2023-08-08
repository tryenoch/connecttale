package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.entity.BoardCateEntity;
import com.bitc.full505_final_team4.data.entity.BoardEntity;
import com.bitc.full505_final_team4.data.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public List<BoardEntity> getReqList() throws Exception {
        return boardRepository.findBoardEntityByBoardCate_Idx(1);
    }

    @Override
    public List<BoardEntity> getNotiList() throws Exception {
        return boardRepository.findBoardEntityByBoardCate_Idx(2);
    }

    @Override
    public List<BoardEntity> getEventList() throws Exception {
        return boardRepository.findBoardEntityByBoardCate_Idx(3);
    }
}
