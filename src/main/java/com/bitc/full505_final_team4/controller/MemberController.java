package com.bitc.full505_final_team4.controller;

import com.bitc.full505_final_team4.data.dto.*;
import com.bitc.full505_final_team4.data.entity.*;
import com.bitc.full505_final_team4.service.BoardService;
import com.bitc.full505_final_team4.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class MemberController {
  private final MemberService memberService;

  // 회원가입
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

//  회원정보 수정
  @RequestMapping(value = "/myPage/changeInfo", method = RequestMethod.POST)
  public Object changeInfo(@RequestParam("id") String id, @RequestParam("pw") String pw, @RequestParam("nickname") String nickname) throws Exception {

    memberService.change(id, pw, nickname);

    Map<String, String> result = new HashMap<>();
    result.put("result", "success");

    return result;
  }

//  닉네임만 변경
  @RequestMapping(value = "/myPage/changeNick", method = RequestMethod.POST)
  public Object changeNick(@RequestParam("id") String id, @RequestParam("nickname") String nickname) throws Exception {

    memberService.changeNick(id, nickname);

    Map<String, String> result = new HashMap<>();
    result.put("result", "success");

    return result;
  }

  // PW만 변경
  @RequestMapping(value = "/myPage/changePw", method = RequestMethod.POST)
  public Object changePw(@RequestParam("id") String id, @RequestParam("pw") String pw) throws Exception {

    memberService.changePw(id, pw);

    Map<String, String> result = new HashMap<>();
    result.put("result", "success");

    return result;
  }

  // id 유효성 검사(이미 있는 id인지)
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

  // nickname 유효성 검사
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

// 관리자 페이지(회원목록)
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

//  관리자페이지(신고내역)
  @RequestMapping(value = "/staffPage/reportList", method = RequestMethod.GET)
  public Object reportList(Pageable pageable) throws Exception {
    return memberService.getReportList(pageable); // MemberServiceImpl에서 Map타입으로 정의하여 넘겨줌.
  }

//  관리자 페이지(회원목록 중 등급 upgrade)
  @RequestMapping(value = "/staffPage/levelUp", method = RequestMethod.POST)
  public Object levelUp(@RequestParam("id") String id) throws Exception {

    Map<String, String> result = new HashMap<>();

    memberService.levelUp(id);
    result.put("result", "해당 회원님의 등급이 관리자로 변경되었습니다.");

    return result;
  }

//  관리자페이지(회원 목록 중 회원정보 삭제(deleteYn = Y)
  @RequestMapping(value = "/staffPage/deleteMember", method = RequestMethod.POST)
  public Object deleteMember(@RequestParam("id") String id) throws Exception {

    Map<String, String> result = new HashMap<>();

    memberService.deleteMember(id);
    result.put("result", "해당 회원님의 계정이 정지되었습니다.");

    return result;
  }

  //  마이페이지(찜한목록)
  @RequestMapping(value = "/myPage/likeList", method = RequestMethod.GET)
  public Object likeList(Pageable pageable, @RequestParam String id) throws Exception {

    Object result = memberService.getLikeList(id, pageable);

    return result;
  }

  //  마이페이지(QnA)
  @RequestMapping(value = "/myPage/myQna", method = RequestMethod.GET)
  public Object myQna(Pageable pageable, @RequestParam String id) throws Exception {

    Map<String, Object> result = new HashMap<>();

    List<BoardDTO> qnaList = new ArrayList<>();
    Page<BoardEntity> boardPages = memberService.getQnaList(pageable, id);
    int totalPages = boardPages.getTotalPages();

    for (BoardEntity board : boardPages.getContent()) {
      BoardDTO req = BoardDTO.toDTO(board);
      qnaList.add(req);
    }

    result.put("result", "성공");
    result.put("totalPages", totalPages);
    result.put("nowPage", pageable.getPageNumber() + 1);
    result.put("boardList", qnaList);
    return result;
  }

  //  마이페이지(내가 쓴 댓글목록)
  @RequestMapping(value = "/myPage/myComment", method = RequestMethod.GET)
  // JPA Pageable 사용(페이지네이션을 도와주는 인터페이스)
  public Object myComment(Pageable pageable, @RequestParam String id) throws Exception {

    Object result = memberService.getReplyList(pageable, id);

    return result;
  }

  @RequestMapping(value = "/profile/upload", method = RequestMethod.POST)
  public Object profileUpload(@RequestParam("id")String  id, MultipartHttpServletRequest multipart) throws Exception {
    Map<String, String> result = new HashMap<>();
    String sFile = memberService.profileUpload(id, multipart);
    result.put("sFile", sFile);
    return result;
  }
}
