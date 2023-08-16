package com.bitc.full505_final_team4.controller;

import com.bitc.full505_final_team4.data.dto.BoardDTO;
import com.bitc.full505_final_team4.data.dto.MemberDto;
import com.bitc.full505_final_team4.data.entity.BoardEntity;
import com.bitc.full505_final_team4.data.entity.MemberEntity;
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

@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class MemberController {
  private final MemberService memberService;
  private final BoardService boardService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public Object login(@RequestParam("id") String id, @RequestParam("pw") String pw) throws Exception {

    MemberEntity member = memberService.login(id, pw);
    Map<String, MemberEntity> result = new HashMap<>();
    if (member.getId() == "" || member.getId() == null) {
      result.put("result", member);
    }

    return result;
  }

  @RequestMapping(value = "/join/join2", method = RequestMethod.POST)
  public Object join(@RequestParam("id") String id, @RequestParam("pw") String pw, @RequestParam("name") String name, @RequestParam("nickname") String nickname, @RequestParam("gender") int gender, @RequestParam("year") String year, @RequestParam("mon") String mon, @RequestParam("day") String day) throws Exception {

    String mm = mon.length() < 2 ? '0' + mon : mon;
    String dd = day.length() < 2 ? '0' + day : day;

    MemberEntity mem = MemberEntity.builder()
        .id(id)
        .pw(pw)
        .name(name)
        .nickname(nickname)
        .gender(gender)
        .birthday(year+mm+dd)
        .grade(1)
        .deletedYn("N")
        .build();

    memberService.join(mem);

    Map<String, String> result = new HashMap<>();
    result.put("result", "success");

    return result;
  }

  @RequestMapping(value = "/myPage/changeInfo", method = RequestMethod.POST)
  public Object changeInfo(@RequestParam("id") String id, @RequestParam("pw") String pw, @RequestParam("nickname") String nickname) throws Exception {

    memberService.change(id, pw, nickname);

    Map<String, String> result = new HashMap<>();
    result.put("result", "success");

    return result;
  }

  @RequestMapping(value = "/join/join2", method = RequestMethod.GET)
  public Object confirmId(@RequestParam("id") String id) throws Exception {

    boolean confirm = memberService.confirmId(id);
    Map<String, String> result = new HashMap<>();

    if (confirm == true) {
      result.put("result", "이미 사용중인 ID 입니다.");
    }
    else {
      result.put("result", "사용가능한 ID 입니다.");
    }

    return result;
  }

  @RequestMapping(value = "/join/join2", method = RequestMethod.PATCH)
  public Object confirmNick(@RequestParam("nickname") String nickname) throws Exception {

    boolean confirm = memberService.confirmNick(nickname);
    Map<String, String> result = new HashMap<>();

    if (confirm == true) {
      result.put("result", "이미 사용중인 닉네임입니다.");
    }
    else {
      result.put("result", "사용가능한 닉네임입니다.");
    }

    return result;
  }

  @RequestMapping(value = "/myPage/myQna", method = RequestMethod.GET)
  // JPA Pageable 사용(페이지네이션을 도와주는 인터페이스)
  public Object myQna(Pageable pageable, @RequestParam("id") String id) throws Exception {

    Map<String, Object> result = new HashMap<>();

    List<BoardDTO> reqList = new ArrayList<>();
    Page<BoardEntity> boardPages = boardService.getQnaList(pageable, id);
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

}
