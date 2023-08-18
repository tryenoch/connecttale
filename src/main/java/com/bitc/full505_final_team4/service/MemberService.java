package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface MemberService {

  MemberEntity login(String id, String pw) throws Exception;

  void join(MemberEntity member) throws Exception;

  void change(String id, String pw, String nickName) throws Exception;

  boolean confirmId(String id) throws Exception;
  boolean confirmNick(String nickname) throws Exception;

  Page<MemberEntity> getMemberList(Pageable pageable) throws Exception;

  List<NovelEntity> getLikeList(String id) throws Exception;
  void levelUp(String id) throws Exception;
  void deleteMember(String id) throws Exception;
}
