package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.BoardDTO;
import com.bitc.full505_final_team4.data.dto.BoardReplyDTO;
import com.bitc.full505_final_team4.data.entity.*;
import com.bitc.full505_final_team4.data.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardCateRepository boardCateRepository;
    private final ReqCateRepository reqCateRepository;
    private final BoardReplyRepository boardReplyRepository;

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
    public int setBoard(BoardDTO board) throws Exception {
        //boardDTO를 Entity로 파싱 성능적인 면에서 getOne방식이 임시 entity를 생성하는 것보다 좋음
        // getOne 방식의 지원 중단으로 공식문서에서 getReferenceById를 사용권장

        // member entity 호출 코드
        MemberEntity member = memberRepository.getReferenceById(board.getCreateId());

        // boardCate entity 호출 코드
        BoardCateEntity boardCate = boardCateRepository.getReferenceById(board.getBoardCate());

        // reqCate entity 호출 코드
        // reqCate가 0이면 null로 저장 (공지사항, 이벤트 경우 0으로 받도록 구현)
        ReqCateEntity reqCate;
        if (board.getReqCate().equals("0")) {
            reqCate = null;
        } else {
            reqCate = reqCateRepository.getReferenceById(Integer.valueOf(board.getReqCate()));
        }

        BoardEntity boardEntity = BoardEntity.builder()
                .boardTitle(board.getBoardTitle())
                .boardContents(board.getBoardContents())
                .createId(member)
                .boardCate(boardCate)
                .reqCate(reqCate)
                .build();

        // save의 결과로 저장된 boardentity가 반환된다 (null이 아닐경우)
        // 반환된 entity의 id값을 리턴 / 첨부파일이 있을 경우 id값을 참조하기 위해
        BoardEntity resultBorad = boardRepository.save(boardEntity);
        return resultBorad.getBoardIdx();
    }

    @Override
    public BoardDTO getBoard(int idx) throws Exception {
        BoardEntity boardEntity = boardRepository.findByBoardIdx(idx);
        return BoardDTO.toDTO(boardEntity);
    }

    // myQna 구현
    @Override
    public Page<BoardEntity> getQnaList(Pageable pageable, String id) throws Exception {
        return boardRepository.findByCreateId_IdAndBoardCate_IdxOrderByBoardIdxDesc(id, 1, pageable);
    }

    @Override
    public void deleteBoard(int idx) throws Exception {
        boardRepository.deleteById(idx);
    }

    @Override
    public void setBoardReply(BoardReplyDTO boardReply) throws Exception {

        // member entity 호출 코드
        MemberEntity member = memberRepository.getReferenceById(boardReply.getCreateId());

        // board entity 호출 코드
        BoardEntity board = boardRepository.getReferenceById(boardReply.getBoardIdx());

        BoardReplyEntity replyEntity = BoardReplyEntity.builder()
                .boardEntity(board)
                .reply(boardReply.getReply())
                .createId(member)
                .build();

        boardReplyRepository.save(replyEntity);
    }

    @Override
    public List<BoardReplyDTO> getBoardReplyList(int idx) throws Exception {
        List<BoardReplyDTO> replyList = new ArrayList<>();

        BoardEntity board = boardRepository.getReferenceById(idx);

        List<BoardReplyEntity> replyEntityList = boardReplyRepository.findByBoardIdx(board);
        for (BoardReplyEntity reply : replyEntityList) {
            replyList.add(BoardReplyDTO.toDTO(reply));
        }
        return replyList;
    }

    @Override
    public void deleteBoardReply(int idx) throws Exception {
        boardReplyRepository.deleteById(idx);
    }

    @Override
    public List<BoardEntity> searchTitleBoard(String keyword) throws Exception {
        return boardRepository.findByBoardCate_IdxAndBoardTitleContainsOrderByBoardIdxDesc(1, keyword);
    }

    @Override
    public List<BoardEntity> searchWriterBoard(String keyword) throws Exception {
        return boardRepository.findByBoardCate_IdxAndCreateId_IdContainsOrderByBoardIdxDesc(1, keyword);
    }
}
