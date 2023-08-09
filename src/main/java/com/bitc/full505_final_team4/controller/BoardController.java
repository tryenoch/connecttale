package com.bitc.full505_final_team4.controller;

import com.bitc.full505_final_team4.data.dto.BoardDTO;
import com.bitc.full505_final_team4.data.entity.BoardEntity;
import com.bitc.full505_final_team4.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
            BoardDTO req = new BoardDTO();
            req.setBoardIdx(board.getBoardIdx());
            req.setBoardTitle(board.getBoardTitle());
            req.setBoardContents(board.getBoardContents());
            req.setCreateId(board.getCreateId().getNickname());
            req.setCreateDt(board.getCreateDt());
//            req.setReqCate(board.getReqCate().getReqName());

            reqList.add(req);
        }

        result.put("success", "성공");
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
            BoardDTO noti = new BoardDTO();
            noti.setBoardIdx(board.getBoardIdx());
            noti.setBoardTitle(board.getBoardTitle());
            noti.setBoardContents(board.getBoardContents());
            noti.setCreateId(board.getCreateId().getNickname());
            noti.setCreateDt(board.getCreateDt());

            notiList.add(noti);
        }

        result.put("success", "성공");
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
            BoardDTO event = new BoardDTO();
            event.setBoardIdx(board.getBoardIdx());
            event.setBoardTitle(board.getBoardTitle());
            event.setBoardContents(board.getBoardContents());
            event.setCreateId(board.getCreateId().getNickname());
            event.setCreateDt(board.getCreateDt());

            eventList.add(event);
        }

        result.put("success", "성공");
        result.put("totalPages", totalPages);
        result.put("nowPage", pageable.getPageNumber() + 1);
        result.put("boardType", "event");
        result.put("boardList", eventList);
        return result;
    }

}