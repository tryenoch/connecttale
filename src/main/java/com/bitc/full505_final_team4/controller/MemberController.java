package com.bitc.full505_final_team4.controller;

import com.bitc.full505_final_team4.data.dto.BoardDTO;
import com.bitc.full505_final_team4.data.dto.MemberDto;
import com.bitc.full505_final_team4.data.dto.NovelDto;
import com.bitc.full505_final_team4.data.dto.NovelLikeDto;
import com.bitc.full505_final_team4.data.entity.BoardEntity;
import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelLikeEntity;
import com.bitc.full505_final_team4.service.BoardService;
import com.bitc.full505_final_team4.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

// StaffPage 구현
  @RequestMapping(value = "/staffPage/memberList", method = RequestMethod.GET)
  // JPA Pageable 사용(페이지네이션을 도와주는 인터페이스)
  public Object memberList(Pageable pageable) throws Exception {

    Map<String, Object> result = new HashMap<>();

    List<MemberDto> memberList = new ArrayList<>();
    Page<MemberEntity> memberPages = memberService.getMemberList(pageable);
    int totalPages = memberPages.getTotalPages();

    for (MemberEntity member : memberPages.getContent()) {
      MemberDto mem = MemberDto.toDto(member);
      memberList.add(mem);
    }

    result.put("result", "성공");
    result.put("totalPages", totalPages);
    result.put("nowPage", pageable.getPageNumber() + 1);
    result.put("memberList", memberList);
    return result;
  }

  @RequestMapping(value = "/myPage/likeList", method = RequestMethod.GET)
  // JPA Pageable 사용(페이지네이션을 도와주는 인터페이스)
  public Object likeList(Pageable pageable, @RequestParam String id) throws Exception {

    Map<String, Object> result = new HashMap<>();

    List<NovelDto> likeList = new ArrayList<>();
    Page<NovelEntity> likePages = memberService.getLikeList(pageable, id);
    int totalPages = likePages.getTotalPages();

    for (NovelEntity board : likePages.getContent()) {
      NovelDto req = NovelDto.toDto(board);
      likeList.add(req);
    }

    result.put("result", "성공");
    result.put("totalPages", totalPages);
    result.put("nowPage", pageable.getPageNumber() + 1);
    result.put("likeList", likeList);
    return result;
  }

  // 신고내역 => 신고 구현 완료 되면 구현예정
//  @RequestMapping(value = "/staffPage/reportList", method = RequestMethod.GET)
//  // JPA Pageable 사용(페이지네이션을 도와주는 인터페이스)
//  public Object reportList(Pageable pageable) throws Exception {
//
//    Map<String, Object> result = new HashMap<>();
//
//    List<MemberDto> memberList = new ArrayList<>();
//    Page<MemberEntity> memberPages = memberService.getMemberList(pageable);
//    int totalPages = memberPages.getTotalPages();
//
//    for (MemberEntity member : memberPages.getContent()) {
//      MemberDto mem = MemberDto.toDto(member);
//      memberList.add(mem);
//    }
//
//    result.put("result", "성공");
//    result.put("totalPages", totalPages);
//    result.put("nowPage", pageable.getPageNumber() + 1);
//    result.put("memberList", memberList);
//    return result;
//  }

  @RequestMapping(value = "/staffPage/levelUp", method = RequestMethod.POST)
  public Object levelUp(@RequestParam("id") String id) throws Exception {

    Map<String, String> result = new HashMap<>();

    memberService.levelUp(id);
    result.put("result", "해당 회원님의 등급이 관리자로 변경되었습니다.");

    return result;
  }

  @RequestMapping(value = "/staffPage/deleteMember", method = RequestMethod.POST)
  public Object deleteMember(@RequestParam("id") String id) throws Exception {

    Map<String, String> result = new HashMap<>();

    memberService.deleteMember(id);
    result.put("result", "해당 회원님의 계정이 정지되었습니다.");

    return result;
  }
}
