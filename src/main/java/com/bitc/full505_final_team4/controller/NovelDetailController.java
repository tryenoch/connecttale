package com.bitc.full505_final_team4.controller;

import com.bitc.full505_final_team4.data.dto.NovelDTO;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import com.bitc.full505_final_team4.service.NovelDetailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class NovelDetailController {
  private final NovelDetailService novelDetailService;

  // db 불러오기
  @RequestMapping(value = "/novelDetail", method = RequestMethod.GET)
  public Object getNovelDetail(@RequestParam("platformId") String platformId) throws Exception {
    // platformId(매개변수) 를 통해 novelIdx 찾기
    int novelIdx = novelDetailService.getNovelIdx(platformId);

    return null;
  }

  // db 저장
  @RequestMapping(value = "/novelDetail", method = RequestMethod.POST)
  public void insertNovelDetail(NovelPlatformEntity novelPlatformEntity) throws Exception {
    // 리디북스 디테일 페이지 정보를 NovelEntity에 저장
    NovelEntity novelEntity = new NovelEntity();
    novelEntity.setNovelTitle(novelPlatformEntity.getNovelTitle());
    novelEntity.setNovelThumbnail(novelPlatformEntity.getNovelThumbnail());
    novelEntity.setNovelAdult(novelPlatformEntity.getNovelAdult());

    novelDetailService.insertRidiToNovel(novelEntity);


    // 리디북스 디테일 페이지 정보를 NovelPlatformEntity에 저장
    novelPlatformEntity.setNovelIdx(novelEntity); // 복합키인 novel 엔티티 추가
    novelDetailService.insertRidiToPlatform(novelPlatformEntity);



//    novelDetailService.insertRidi(novelPlatformEntity);

//    System.out.println(novelPlatformEntity);
//    System.out.println(ridiNovelDTO.getNovelTitle());


  }
}
