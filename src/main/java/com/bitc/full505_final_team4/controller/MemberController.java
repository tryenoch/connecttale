package com.bitc.full505_final_team4.controller;

import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class MemberController {
  private final MemberService memberService;

//  @RequestMapping(value = "/login", method = RequestMethod.POST)
//  public Object login(@RequestParam("id") String id, @RequestParam("pw") String pw) throws Exception {
//
////    MemberEntity member = memberService.login(id, pw);
////
////    Map<String, MemberEntity> result = new HashMap<>();
////    result.put("member", member);
////
////    return result;
//    return memberService.login(id, pw);
//  }

  @PostMapping(value = "/join/join2")
  public Object join(@RequestParam("id") String id, @RequestParam("pw") String pw, @RequestParam("name") String name, @RequestParam("nickname") String nickname, @RequestParam("gender") int gender, @RequestParam("birthday") String birthday) throws Exception {
    MemberEntity member = new MemberEntity();

    member.setId(id);
    member.setPw(pw);
    member.setName(name);
    member.setNickname(nickname);
    member.setGender(gender);
    member.setBirthday(birthday);

    memberService.join(member);

    Map<String, String> result = new HashMap<>();
    result.put("result", "success");

    return result;
  }

  @RequestMapping("/join/join3")
  public String joinSuccess() throws Exception {
    return "/join/join3";
  }
}
