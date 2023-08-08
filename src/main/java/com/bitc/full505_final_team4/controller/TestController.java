package com.bitc.full505_final_team4.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class TestController {


  @RequestMapping("/hello")
  public List<String> hello() {
    return Arrays.asList("안녕하세요", "Hello");
  }
  
  //여기부터
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
// 여기까지
}
