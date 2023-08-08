package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.MemberDto;
import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
  private final MemberRepository memberRepository;

  @Override
  public MemberEntity login(String id, String pw) throws Exception {
    MemberEntity login = memberRepository.findByIdAndPw(id, pw);
    return login;
  }

  @Override
  public void join(MemberEntity member) throws Exception {
    memberRepository.save(member);
  }
}
