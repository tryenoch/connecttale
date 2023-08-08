package com.bitc.full505_final_team4.controller;

import com.bitc.full505_final_team4.data.dto.BoardDTO;
import com.bitc.full505_final_team4.data.entity.BoardEntity;
import com.bitc.full505_final_team4.service.BoardService;
import lombok.RequiredArgsConstructor;
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

    @RequestMapping(value = "/req/{page}", method = RequestMethod.GET)
    public Object resList(@PathVariable int page) throws Exception {

        Map<String, Object> result = new HashMap<>();

        List<BoardDTO> reqList = new ArrayList<>();
        List<BoardEntity> boardList = boardService.getReqList();
        int totalCount = boardList.size();
        for (BoardEntity board : boardList) {
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
        result.put("totalCount", totalCount);
        result.put("nowPage", page);
        result.put("boardType", "req");
        result.put("boardList", reqList);
        return result;
    }

    @RequestMapping(value = "/notice/{page}", method = RequestMethod.GET)
    public Object noticeList(@PathVariable int page) throws Exception {

        Map<String, Object> result = new HashMap<>();

        List<BoardDTO> notiList = new ArrayList<>();
        List<BoardEntity> boardList = boardService.getNotiList();
        int totalCount = boardList.size();
        for (BoardEntity board : boardList) {
            BoardDTO noti = new BoardDTO();
            noti.setBoardIdx(board.getBoardIdx());
            noti.setBoardTitle(board.getBoardTitle());
            noti.setBoardContents(board.getBoardContents());
            noti.setCreateId(board.getCreateId().getNickname());
            noti.setCreateDt(board.getCreateDt());

            notiList.add(noti);
        }

        result.put("success", "성공");
        result.put("totalCount", totalCount);
        result.put("nowPage", page);
        result.put("boardType", "notice");
        result.put("boardList", notiList);
        return result;
    }

    @RequestMapping(value = "/event/{page}", method = RequestMethod.GET)
    public Object eventList(@PathVariable int page) throws Exception {

        Map<String, Object> result = new HashMap<>();

        List<BoardDTO> eventList = new ArrayList<>();
        List<BoardEntity> boardList = boardService.getEventList();
        int totalCount = boardList.size();
        for (BoardEntity board : boardList) {
            BoardDTO event = new BoardDTO();
            event.setBoardIdx(board.getBoardIdx());
            event.setBoardTitle(board.getBoardTitle());
            event.setBoardContents(board.getBoardContents());
            event.setCreateId(board.getCreateId().getNickname());
            event.setCreateDt(board.getCreateDt());

            eventList.add(event);
        }

        result.put("success", "성공");
        result.put("totalCount", totalCount);
        result.put("nowPage", page);
        result.put("boardType", "event");
        result.put("boardList", eventList);
        return result;
    }

}
