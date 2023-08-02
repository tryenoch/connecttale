package com.bitc.full505_final_team4.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {

    @RequestMapping("/hello")
    public List<String> hello() {
        return Arrays.asList("안녕하세요", "Hello");
    }
}
