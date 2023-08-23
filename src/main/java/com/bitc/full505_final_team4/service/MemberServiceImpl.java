package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.NovelDto;
import com.bitc.full505_final_team4.data.dto.NovelLikeDto;
import com.bitc.full505_final_team4.data.dto.NovelReplyDto2;
import com.bitc.full505_final_team4.data.dto.ReportDto2;
import com.bitc.full505_final_team4.data.entity.*;
import com.bitc.full505_final_team4.data.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;
  private final NovelLikeRepository novelLikeRepository;
  private final NovelReplyRepository novelReplyRepository;
  private final ReportRepository reportRepository;
  private final BoardRepository boardRepository;

// 회원가입
  @Override
  public void join(MemberEntity member) throws Exception {
    memberRepository.save(member);
  }
// 회원정보 변경
  @Override
  public void change(String id, String pw, String nickName) throws Exception {
    MemberEntity member = memberRepository.findAllById(id);
    member.setPw(pw);
    member.setNickname(nickName);
    memberRepository.save(member);
  }
  // 회원정보 변경(nickname만)
  @Override
  public void changeNick(String id, String nickName) throws Exception {
    MemberEntity member = memberRepository.findAllById(id);
    member.setNickname(nickName);
    memberRepository.save(member);
  }
  // 회원정보 변경(pw만)
  @Override
  public void changePw(String id, String pw) throws Exception {
    MemberEntity member = memberRepository.findAllById(id);
    member.setPw(pw);
    memberRepository.save(member);
  }
// id 중복확인
  @Override
  public boolean confirmId(String id) throws Exception {
    return memberRepository.existsById(id);
  }
  // nickname 중복확인
  @Override
  public boolean confirmNick(String nickname) throws Exception {
    return memberRepository.existsByNickname(nickname);
  }

// 회원목록 Pagenation으로 가져오기(Pageable)활용
  @Override
  public Page<MemberEntity> getMemberList(Pageable pageable) throws Exception {
    return memberRepository.findByDeletedYn("N", pageable);
  }

  // myQna 구현
  @Override
  public Page<BoardEntity> getQnaList(Pageable pageable, String id) throws Exception {
    return boardRepository.findByCreateId_IdAndBoardCate_IdxOrderByBoardIdxDesc(id, 1, pageable);
  }

//  찜한목록 가져오기(Pageable활용)
  @Override
  public Object getLikeList(String id, Pageable pageable) throws Exception {

    Map<String, Object> result = new HashMap<>();

    List<NovelDto> likeList = new ArrayList<>();
    MemberEntity member = memberRepository.findAllById(id); // id를 통해 MemberEntity 타입의 회원정보 객체 가져오기
    Page<NovelLikeEntity> likedPage = novelLikeRepository.findAllByIdAndLikeYn(member, "Y", pageable); // id가 외래키로 MemberEntity타입임.
    int totalPages = likedPage.getTotalPages();

    for (NovelLikeEntity like : likedPage.getContent()) { // Page타입의 객체의 내용을 NovelLikeEntity타입의 객체에 대입
      NovelLikeDto req = NovelLikeDto.toDto(like); // 대입한 NovelLikeEntity타입의 객체를 NovelLikeDto타입의 객체로 변환
      likeList.add(req.getNovelIdx()); // novelIdx또한 외래키로 NovelEntity타입이므로, List<NovelDto>타입의 객체에 저장
    }

    result.put("likeList", likeList);
    result.put("totalPages", totalPages);
    result.put("nowPage", pageable.getPageNumber() + 1);
    return result;
  }

//  내가 쓴 댓글 목록 가져오기
  @Override
  public Object getReplyList(Pageable pageable, String id) {
    Map<String, Object> result = new HashMap<>();

    List<NovelReplyDto2> replyList = new ArrayList<>();
    MemberEntity replyId = memberRepository.findAllById(id);
    Page<NovelReplyEntity> replyPage = novelReplyRepository.findById(replyId, pageable);

    int totalPages = replyPage.getTotalPages();

    for (NovelReplyEntity reply : replyPage.getContent()) {
      NovelReplyDto2 req = NovelReplyDto2.toDto(reply);
      replyList.add(req);
    }

    result.put("replyList", replyList);
    result.put("totalPages", totalPages);
    result.put("nowPage", pageable.getPageNumber() + 1);
    return result;
  }

//  신고목록 가져오기
  @Override
  public Object getReportList(Pageable pageable) {
    Map<String, Object> result = new HashMap<>();

    List<ReportDto2> reportList = new ArrayList<>();
    Page<ReportEntity> reportPage = reportRepository.findAll(pageable);
    int totalPages = reportPage.getTotalPages();

    for (ReportEntity report : reportPage.getContent()) {
      ReportDto2 req = ReportDto2.toDto(report);
      reportList.add(req);
    }

    result.put("reportList", reportList);
    result.put("totalPages", totalPages);
    result.put("nowPage", pageable.getPageNumber() + 1);
    return result;
  }

//  등업(update)
  @Override
  public void levelUp(String id) throws Exception {
    MemberEntity member = memberRepository.findAllById(id);
    member.setGrade(2);
    memberRepository.save(member);
  }

//  회원정보 제거(deleteYn => Y 업데이트)
  @Override
  public void deleteMember(String id) throws Exception {
    MemberEntity member = memberRepository.findAllById(id);
    member.setDeletedYn("Y");
    memberRepository.save(member);
  }

}
