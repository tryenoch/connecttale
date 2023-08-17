package com.bitc.full505_final_team4.controller;

import com.bitc.full505_final_team4.data.dto.LoginDTO;
import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;

    @RequestMapping(value = "/login/process", method = RequestMethod.POST)
    public Object loginProcess(LoginDTO login) throws Exception {
        Map<String, Object> result = new HashMap<>();

        boolean isUser = loginService.isUserInfo(login);

        result.put("result", "success");
        result.put("user", isUser);
        Optional<MemberEntity> en = loginService.getUserInfo(login.getId());
        if (isUser) result.put("userInfo", loginService.getUserInfo(login.getId()));

        return result;
    }

}
