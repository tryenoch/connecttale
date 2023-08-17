package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.LoginDTO;
import com.bitc.full505_final_team4.data.entity.MemberEntity;

import java.util.Optional;

public interface LoginService {
    boolean isUserInfo(LoginDTO login) throws Exception;

    Optional<MemberEntity> getUserInfo(String id) throws Exception;
}
