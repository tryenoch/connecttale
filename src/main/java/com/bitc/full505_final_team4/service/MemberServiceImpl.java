package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.NovelLikeDto;
import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelLikeEntity;
import com.bitc.full505_final_team4.data.repository.MemberRepository;
import com.bitc.full505_final_team4.data.repository.NovelLikeRepository;
import com.bitc.full505_final_team4.data.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;
  private final NovelLikeRepository novelLikeRepository;
  private final NovelRepository novelRepository;

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
  public List<NovelEntity> getLikeList(String id) throws Exception {
    List<NovelEntity> likeNovel = new ArrayList<>();
    MemberEntity member = memberRepository.findAllById(id);
    List<NovelLikeEntity> liked = novelLikeRepository.findAllByIdAndLikeYn(member, "Y");
    for (int i=0 ; i < liked.size() ; i++) {
      NovelLikeEntity novel = liked.get(i);
      NovelLikeDto req = NovelLikeDto.toDto(novel);
      likeNovel = novelRepository.findByNovelIdx(req.getNovelIdx().getNovelIdx());
    }
    return likeNovel;
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
