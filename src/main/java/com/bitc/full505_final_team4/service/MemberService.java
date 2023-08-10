package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.MemberDto;
import com.bitc.full505_final_team4.data.entity.MemberEntity;

public interface MemberService {

  MemberEntity login(String id, String pw) throws Exception;

  void join(MemberEntity member) throws Exception;

  boolean confirmId(String id) throws Exception;
  boolean confirmNick(String nickname) throws Exception;
}
