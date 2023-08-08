package com.bitc.full505_final_team4.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BoardController {

    @RequestMapping(value = "/res/{page}", method = RequestMethod.GET)
    public Object resList(@PathVariable int page) throws Exception {

        Map<String, Object> result = new HashMap<>();

        List<String> boardList = new ArrayList<>();
        int totalCount = 127; // boardList.size()

        result.put("success", "성공");
        result.put("totalCount", totalCount);
        result.put("nowPage", page);
        result.put("boardType", "res");
        return result;
    }

    @RequestMapping(value = "/notice/{page}", method = RequestMethod.GET)
    public Object noticeList(@PathVariable int page) throws Exception {

        Map<String, Object> result = new HashMap<>();


        List<String> boardList = new ArrayList<>();
        int totalCount = 127; // boardList.size()

        result.put("success", "성공");
        result.put("totalCount", totalCount);
        result.put("nowPage", page);
        result.put("boardType", "notice");
        return result;
    }

    @RequestMapping(value = "/event/{page}", method = RequestMethod.GET)
    public Object eventList(@PathVariable int page) throws Exception {

        Map<String, Object> result = new HashMap<>();

        List<String> boardList = new ArrayList<>();
        int totalCount = 127; // boardList.size()

        result.put("success", "성공");
        result.put("totalCount", totalCount);
        result.put("nowPage", page);
        result.put("boardType", "event");
        return result;
    }

}
