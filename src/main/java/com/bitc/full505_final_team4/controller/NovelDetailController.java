package com.bitc.full505_final_team4.controller;

import com.bitc.full505_final_team4.data.dto.NovelDTO;
import com.bitc.full505_final_team4.data.dto.NovelPlatformDto;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import com.bitc.full505_final_team4.service.NovelDetailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.system.SystemProperties;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class NovelDetailController {
  private final NovelDetailService novelDetailService;

  // ---------------------db에 있는 디테일페이지 데이터 불러오기-------------------------
  @RequestMapping(value = "/novelDetail", method = RequestMethod.GET)
  public Object getNovelDetail(@RequestParam("title") String title, @RequestParam("ebookCheck") String ebookCheck) throws Exception {
    Map<String, NovelPlatformDto> novelDetail = new HashMap<>();

    // title을 통해 db에 해당 이름으로 저장된 소서 정보 다가져오기
    List<NovelPlatformEntity> allNovelDetail = novelDetailService.getNovelDetail(title, ebookCheck);


    if (!allNovelDetail.isEmpty()) {
      for (NovelPlatformEntity p : allNovelDetail) {
        if (p.getPlatform() == 1) {
          // builder 사용하여 entity를 dto로 바꿔줌(프론트에 전달하기 위함)
          NovelPlatformDto kakaoPlatformDto = NovelPlatformDto.toDto(p);
          novelDetail.put("kakao", kakaoPlatformDto);
        }
        else if (p.getPlatform() == 2) {
          // builder 사용하여 entity를 dto로 바꿔줌(프론트에 전달하기 위함)
          NovelPlatformDto naverPlatformDto = NovelPlatformDto.toDto(p);
          novelDetail.put("naver", naverPlatformDto);
        }
        else if (p.getPlatform() == 3) {
          // builder 사용하여 entity를 dto로 바꿔줌(프론트에 전달하기 위함)
          NovelPlatformDto ridiPlatformDto = NovelPlatformDto.toDto(p);
          novelDetail.put("ridi", ridiPlatformDto);
        }
      }

    }

    return novelDetail;

  }
  // -------------------------------- db에 데이터 저장 ------------------------------------

  // 리디북스 디테일 페이지 정보 db 저장

  @RequestMapping(value = "/novelDetail", method = RequestMethod.POST)
  public void insertRidiDetail(@RequestParam("id") String id, @RequestParam("title") String title, @RequestParam("ne") String ne, NovelPlatformEntity ridiPlatformEntity) throws Exception {
//
//    System.out.println(title);
//    System.out.println(id);
//    System.out.println(ne);
//    System.out.println(ridiPlatformEntity);

    NovelEntity novelEntity = new NovelEntity();

    try {
      // 네이버 디테일페이지 정보 가져와서 platform entity에 저장
      NovelPlatformEntity naverPlatformEntity = novelDetailService.getNaverCrolling(id, title, ne);
      // 카카오 디테일페이지 정보 가져와서 platform entity에 저장
      NovelPlatformEntity kakaoPlatformEntity = novelDetailService.getKakaoCrolling(id, title, ne);


      System.out.println(kakaoPlatformEntity); // 1
      System.out.println("----------------------\n");
      System.out.println(naverPlatformEntity); // 2
      System.out.println("----------------------\n");
      System.out.println(ridiPlatformEntity); // 3

      // 리디북스에 해당 작품이 없을 때
      if (ridiPlatformEntity.getPlatformId() == null) {

        // 네이버, 카카오만 있을 경우,
        if (naverPlatformEntity.getPlatformId() != null && kakaoPlatformEntity.getPlatformId() != null) {
          novelEntity.setNovelTitle(naverPlatformEntity.getNovelTitle());
          novelEntity.setNovelThumbnail(naverPlatformEntity.getNovelThumbnail());
          novelEntity.setNovelAdult(naverPlatformEntity.getNovelAdult());
          novelEntity.setEbookCheck(naverPlatformEntity.getEbookCheck());

          novelDetailService.insertNaverToNovel(novelEntity);

          // 네이버 디테일 페이지 정보를 novel entity에 저장
          naverPlatformEntity.setNovelIdx(novelEntity);

          // 네이버 디테일 페이지 정보를 NovelPlatformEntity에 저장
          novelDetailService.insertNaverToPlatform(naverPlatformEntity);

          kakaoPlatformEntity.setNovelIdx(novelEntity);
          // 카카오 디테일 페이지 정보를 NovelPlatformEntity에 저장
          novelDetailService.insertKakaoToPlatform(kakaoPlatformEntity);

        }
        // 네이버만 있을 경우,
        else if (naverPlatformEntity.getPlatformId() != null) {
          novelEntity.setNovelTitle(naverPlatformEntity.getNovelTitle());
          novelEntity.setNovelThumbnail(naverPlatformEntity.getNovelThumbnail());
          novelEntity.setNovelAdult(naverPlatformEntity.getNovelAdult());
          novelEntity.setEbookCheck(naverPlatformEntity.getEbookCheck());

          novelDetailService.insertNaverToNovel(novelEntity);

          // 네이버 디테일 페이지 정보를 novel entity에 저장
          naverPlatformEntity.setNovelIdx(novelEntity);

          // 네이버 디테일 페이지 정보를 NovelPlatformEntity에 저장
          novelDetailService.insertNaverToPlatform(naverPlatformEntity);
        }
        // 카카오만 있을 경우,
        else if (kakaoPlatformEntity.getPlatformId() != null) {
          novelEntity.setNovelTitle(kakaoPlatformEntity.getNovelTitle());
          novelEntity.setNovelThumbnail(kakaoPlatformEntity.getNovelThumbnail());
          novelEntity.setNovelAdult(kakaoPlatformEntity.getNovelAdult());
          novelEntity.setEbookCheck(kakaoPlatformEntity.getEbookCheck());

          novelDetailService.insertKakaoToNovel(novelEntity);

          // 카카오 디테일 페이지 정보를 novel entity에 저장
          kakaoPlatformEntity.setNovelIdx(novelEntity);

          // 카카오 디테일 페이지 정보를 NovelPlatformEntity에 저장
          novelDetailService.insertKakaoToPlatform(kakaoPlatformEntity);
        }
      }

      // 리디에 해당 작품이 있을 때,
      else {
        // 리디, 카카오, 네이버 모두 있을 경우,
        if (naverPlatformEntity.getPlatformId() != null && kakaoPlatformEntity.getPlatformId() != null) {
          novelEntity.setNovelTitle(ridiPlatformEntity.getNovelTitle());
          novelEntity.setNovelThumbnail(ridiPlatformEntity.getNovelThumbnail());
          novelEntity.setNovelAdult(ridiPlatformEntity.getNovelAdult());
          novelEntity.setEbookCheck(ridiPlatformEntity.getEbookCheck());

          novelDetailService.insertRidiToNovel(novelEntity);

          ridiPlatformEntity.setNovelIdx(novelEntity); // 복합키인 novel 엔티티 추가
          novelDetailService.insertRidiToPlatform(ridiPlatformEntity);

          naverPlatformEntity.setNovelIdx(novelEntity);
          novelDetailService.insertNaverToPlatform(naverPlatformEntity);

          kakaoPlatformEntity.setNovelIdx(novelEntity);
          novelDetailService.insertKakaoToPlatform(kakaoPlatformEntity);
        }

        // 리디, 네이버만 있을 경우
        else if (naverPlatformEntity.getPlatformId() != null) {
          novelEntity.setNovelTitle(ridiPlatformEntity.getNovelTitle());
          novelEntity.setNovelThumbnail(ridiPlatformEntity.getNovelThumbnail());
          novelEntity.setNovelAdult(ridiPlatformEntity.getNovelAdult());
          novelEntity.setEbookCheck(ridiPlatformEntity.getEbookCheck());

          novelDetailService.insertRidiToNovel(novelEntity);

          // 리디북스 디테일 페이지 정보를 NovelPlatformEntity에 저장
          ridiPlatformEntity.setNovelIdx(novelEntity); // 복합키인 novel 엔티티 추가
          novelDetailService.insertRidiToPlatform(ridiPlatformEntity);

          naverPlatformEntity.setNovelIdx(novelEntity);
          novelDetailService.insertNaverToPlatform(naverPlatformEntity);
        }

        // 리디, 카카오만 있는 경우
        else if (kakaoPlatformEntity.getPlatformId() != null) {
          novelEntity.setNovelTitle(ridiPlatformEntity.getNovelTitle());
          novelEntity.setNovelThumbnail(ridiPlatformEntity.getNovelThumbnail());
          novelEntity.setNovelAdult(ridiPlatformEntity.getNovelAdult());
          novelEntity.setEbookCheck(ridiPlatformEntity.getEbookCheck());

          novelDetailService.insertRidiToNovel(novelEntity);

          // 리디북스 디테일 페이지 정보를 NovelPlatformEntity에 저장
          ridiPlatformEntity.setNovelIdx(novelEntity); // 복합키인 novel 엔티티 추가
          novelDetailService.insertRidiToPlatform(ridiPlatformEntity);

          kakaoPlatformEntity.setNovelIdx(novelEntity);
          novelDetailService.insertKakaoToPlatform(kakaoPlatformEntity);
        }

        // 리디만 있는 경우
        else if (naverPlatformEntity.getPlatformId() == null && kakaoPlatformEntity.getPlatformId() == null) {
          novelEntity.setNovelTitle(ridiPlatformEntity.getNovelTitle());
          novelEntity.setNovelThumbnail(ridiPlatformEntity.getNovelThumbnail());
          novelEntity.setNovelAdult(ridiPlatformEntity.getNovelAdult());
          novelEntity.setEbookCheck(ridiPlatformEntity.getEbookCheck());

          novelDetailService.insertRidiToNovel(novelEntity);

          // 리디북스 디테일 페이지 정보를 NovelPlatformEntity에 저장
          ridiPlatformEntity.setNovelIdx(novelEntity); // 복합키인 novel 엔티티 추가
          novelDetailService.insertRidiToPlatform(ridiPlatformEntity);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
