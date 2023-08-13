package com.bitc.full505_final_team4.controller;

import com.bitc.full505_final_team4.data.dto.BoardDTO;
import com.bitc.full505_final_team4.data.dto.BoardReplyDTO;
import com.bitc.full505_final_team4.data.entity.BoardCateEntity;
import com.bitc.full505_final_team4.data.entity.BoardEntity;
import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.entity.ReqCateEntity;
import com.bitc.full505_final_team4.service.BoardService;
import com.bitc.full505_final_team4.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @RequestMapping(value = "/req", method = RequestMethod.GET)
    // JPA Pageable 사용(페이지네이션을 도와주는 인터페이스)
    public Object resList(Pageable pageable) throws Exception {

        Map<String, Object> result = new HashMap<>();

        List<BoardDTO> reqList = new ArrayList<>();
        Page<BoardEntity> boardPages = boardService.getReqList(pageable);
        int totalPages = boardPages.getTotalPages();

        for (BoardEntity board : boardPages.getContent()) {
            BoardDTO req = BoardDTO.toDTO(board);
            reqList.add(req);
        }

        result.put("result", "성공");
        result.put("totalPages", totalPages);
        result.put("nowPage", pageable.getPageNumber() + 1);
        result.put("boardType", "req");
        result.put("boardList", reqList);
        return result;
    }

    @RequestMapping(value = "/notice", method = RequestMethod.GET)
    public Object noticeList(Pageable pageable) throws Exception {

        Map<String, Object> result = new HashMap<>();

        List<BoardDTO> notiList = new ArrayList<>();
        Page<BoardEntity> boardPages = boardService.getNotiList(pageable);
        int totalPages = boardPages.getTotalPages();
        for (BoardEntity board : boardPages) {
            BoardDTO noti = BoardDTO.toDTO(board);
            notiList.add(noti);
        }

        result.put("result", "성공");
        result.put("totalPages", totalPages);
        result.put("nowPage", pageable.getPageNumber() + 1);
        result.put("boardType", "notice");
        result.put("boardList", notiList);
        return result;
    }

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public Object eventList(Pageable pageable) throws Exception {

        Map<String, Object> result = new HashMap<>();

        List<BoardDTO> eventList = new ArrayList<>();
        Page<BoardEntity> boardPages = boardService.getEventList(pageable);
        int totalPages = boardPages.getTotalPages();
        for (BoardEntity board : boardPages) {
            BoardDTO event = BoardDTO.toDTO(board);
            eventList.add(event);
        }

        result.put("result", "성공");
        result.put("totalPages", totalPages);
        result.put("nowPage", pageable.getPageNumber() + 1);
        result.put("boardType", "event");
        result.put("boardList", eventList);
        return result;
    }

    @RequestMapping(value = "/board/process", method = RequestMethod.POST)
    public Object writeBoard(BoardDTO board) throws Exception {
        Map<String, Object> result = new HashMap<>();

        int boardIdx = boardService.setBoard(board);

        result.put("result", "성공");
        result.put("boardIdx", boardIdx);

        return result;
    }

    @RequestMapping(value = "/board/{idx}", method = RequestMethod.GET)
    public Object getBoard(@PathVariable int idx) throws Exception {
        Map<String, Object> result = new HashMap<>();
        BoardDTO board = boardService.getBoard(idx);

        result.put("result", "성공");
        result.put("board", board);
        return result;
    }

    @RequestMapping(value = "/board/{idx}", method = RequestMethod.DELETE)
    public Object removeBoard(@PathVariable int idx) throws Exception {
        Map<String, Object> result = new HashMap<>();
        boardService.deleteBoard(idx);

        result.put("result", "성공");
        return result;
    }

    @RequestMapping(value = "/board/reply", method = RequestMethod.POST)
    public Object setBoardReply(BoardReplyDTO boardReply) throws Exception {
        Map<String, Object> result = new HashMap<>();
        boardService.setBoardReply(boardReply);

        result.put("result", "성공");
        return result;
    }
}
