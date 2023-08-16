package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.LoginDTO;
import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final MemberRepository memberRepository;

    @Override
    public boolean isUserInfo(LoginDTO login) throws Exception {
        return memberRepository.existsByIdAndPw(login.getId(), login.getPw());
    }

    @Override
    public Optional<MemberEntity> getUserInfo(String id) throws Exception {
        return memberRepository.findById(id);
    }
}
