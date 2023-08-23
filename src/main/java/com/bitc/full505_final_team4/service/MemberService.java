package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.entity.BoardEntity;
import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface MemberService {
// 회원가입
  void join(MemberEntity member) throws Exception;
// 회원정보 수정
  void change(String id, String pw, String nickName) throws Exception;
//  닉네임만 변경
  void changeNick(String id, String nickName) throws Exception;
// Pw만 변경
  void changePw(String id, String pw) throws Exception;
// id 유효성 검사
  boolean confirmId(String id) throws Exception;
  // nickname 유효성 검사
  boolean confirmNick(String nickname) throws Exception;

// 회원목록을 Pageable을 이용하여 페이지네이션하여 가져오기
  Page<MemberEntity> getMemberList(Pageable pageable) throws Exception;
//  등업(grade 변경)
  void levelUp(String id) throws Exception;
//  회원정보 삭제(deleteYn 변경)
  void deleteMember(String id) throws Exception;
//
  Object getReplyList(Pageable pageable, String id);
  // myQna 구현
  Page<BoardEntity> getQnaList(Pageable pageable, String id) throws Exception;

  Object getLikeList(String id, Pageable pageable) throws Exception;

  Object getReportList(Pageable pageable);
}
