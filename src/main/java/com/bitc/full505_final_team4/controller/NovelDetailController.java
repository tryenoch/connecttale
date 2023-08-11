package com.bitc.full505_final_team4.controller;

import com.bitc.full505_final_team4.service.NovelDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NovelDetailController {
  private NovelDetailService novelDetailService;

  @RequestMapping(value = "/novelDetail", method = RequestMethod.GET)
  public Object getRidiDetail(@RequestParam("platformId") String platformId) throws Exception {


    return null;
  }
}
