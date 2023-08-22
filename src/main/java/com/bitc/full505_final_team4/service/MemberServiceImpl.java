package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.NovelDto;
import com.bitc.full505_final_team4.data.dto.NovelLikeDto;
import com.bitc.full505_final_team4.data.dto.NovelReplyDto2;
import com.bitc.full505_final_team4.data.dto.ReportDto2;
import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.entity.NovelLikeEntity;
import com.bitc.full505_final_team4.data.entity.NovelReplyEntity;
import com.bitc.full505_final_team4.data.entity.ReportEntity;
import com.bitc.full505_final_team4.data.repository.MemberRepository;
import com.bitc.full505_final_team4.data.repository.NovelLikeRepository;
import com.bitc.full505_final_team4.data.repository.NovelReplyRepository;
import com.bitc.full505_final_team4.data.repository.ReportRepository;
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

  @Override
  public MemberEntity login(String id, String pw) throws Exception {
    MemberEntity login = memberRepository.findByIdAndPw(id, pw);
    return login;
  }

  @Override
  public void join(MemberEntity member) throws Exception {
    memberRepository.save(member);
  }

  @Override
  public void change(String id, String pw, String nickName) throws Exception {
    MemberEntity member = memberRepository.findAllById(id);
    member.setPw(pw);
    member.setNickname(nickName);
    memberRepository.save(member);
  }

  @Override
  public void changeNick(String id, String nickName) throws Exception {
    MemberEntity member = memberRepository.findAllById(id);
    member.setNickname(nickName);
    memberRepository.save(member);
  }

  @Override
  public void changePw(String id, String pw) throws Exception {
    MemberEntity member = memberRepository.findAllById(id);
    member.setPw(pw);
    memberRepository.save(member);
  }

  @Override
  public boolean confirmId(String id) throws Exception {
    return memberRepository.existsById(id);
  }

  @Override
  public boolean confirmNick(String nickname) throws Exception {
    return memberRepository.existsByNickname(nickname);
  }


  @Override
  public Page<MemberEntity> getMemberList(Pageable pageable) throws Exception {
    return memberRepository.findByDeletedYn("N", pageable);
  }

  @Override
  public Object getLikeList(String id, Pageable pageable) throws Exception {

    Map<String, Object> result = new HashMap<>();

    List<NovelDto> likeList = new ArrayList<>();
    MemberEntity member = memberRepository.findAllById(id);
    Page<NovelLikeEntity> likedPage = novelLikeRepository.findAllByIdAndLikeYn(member, "Y", pageable);
    int totalPages = likedPage.getTotalPages();

    for (NovelLikeEntity like : likedPage.getContent()) {
      NovelLikeDto req = NovelLikeDto.toDto(like);
      likeList.add(req.getNovelIdx());
    }

    result.put("likeList", likeList);
    result.put("totalPages", totalPages);
    result.put("nowPage", pageable.getPageNumber() + 1);
    return result;
  }

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

  @Override
  public void levelUp(String id) throws Exception {
    MemberEntity member = memberRepository.findAllById(id);
    member.setGrade(2);
    memberRepository.save(member);
  }

  @Override
  public void deleteMember(String id) throws Exception {
    MemberEntity member = memberRepository.findAllById(id);
    member.setDeletedYn("Y");
    memberRepository.save(member);
  }

}
