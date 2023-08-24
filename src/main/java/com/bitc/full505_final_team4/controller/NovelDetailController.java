package com.bitc.full505_final_team4.controller;

import com.bitc.full505_final_team4.data.dto.*;
import com.bitc.full505_final_team4.data.entity.*;
import com.bitc.full505_final_team4.service.NovelDetailService;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.devtools.Reply;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class NovelDetailController {
  private final NovelDetailService novelDetailService;

  // ---------------------db에 있는 디테일페이지 데이터 불러오기-------------------------
  @RequestMapping(value = "/novelDetail", method = RequestMethod.GET)
  public Object getNovelDetail(@RequestParam("title") String encodeTitle, @RequestParam("ebookCheck") String ebookCheck, @RequestParam("ageGrade") String novelAdult) throws Exception {
    Map<String, Object> novelDetail = new HashMap<>();

    System.out.println(encodeTitle);
    String title = URLDecoder.decode(encodeTitle, "UTF-8");
    System.out.println(title);

    // 소설 디테일 정보(제목, 저자, 인트로, 등) 관련 내용
    // 매개변수 title, ebookCheck를 통해 db에 해당 이름으로 저장된 소설 정보 다가져오기
    List<NovelPlatformEntity> allNovelDetail = novelDetailService.getNovelDetail(title, ebookCheck, novelAdult);



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

      // 여기부터는 좋아요, 리뷰, 신고 관련 프론트로 전달할 내용

      // -------------------좋아요------------------
      // 1. title, ebookCheck, ageGrade를 통해 novelIdx 얻기
      NovelEntity novelIdx = novelDetailService.getNovelIdx(title, ebookCheck, novelAdult);

      // 2. novel_idx에 대한 좋아요 수 데이터 가져오기
      int novelLikeCount = novelDetailService.getNovelLikeCount(novelIdx);

      // 3. novel_idx에 대한 좋아요 테이블 데이터 가져오기
      List<NovelLikeEntity> novelLikeEntityList = novelDetailService.getNovelLike(novelIdx);

      // NovelLikeEntity 객체를 프론트로 전달하기 위해 DTO 타입으로 변경
      List<NovelLikeDto> novelLikeList = new ArrayList<>();

      if (!novelLikeEntityList.isEmpty()) {
        for (NovelLikeEntity novelLikeEntity : novelLikeEntityList) {
          NovelLikeDto novelLikeDto = NovelLikeDto.toDto(novelLikeEntity);
          novelLikeList.add(novelLikeDto);

        }
      }

      novelDetail.put("novelIdx", novelIdx);
      novelDetail.put("novelLikeCount", novelLikeCount);
      novelDetail.put("novelLikeList", novelLikeList);


      // --------------------------------- 리뷰 관련 정보 가져오기 ----------------------------------
      List<NovelReplyDto> novelReplyList = new ArrayList<>();
      List<ReplyLikeDto> replyLikeList = new ArrayList<>();
      // COUNT 함수가 사용된 ENTITY를 가져오기 위해 필요한 인터페이스 객체 생성


      // novelIdx를 통해 novel_reply 테이블 데이터 가져오기
      List<NovelReplyEntity> novelReplyEntityList = novelDetailService.getNovelReply(novelIdx);
      for (NovelReplyEntity novelReplyEntity : novelReplyEntityList) {
        NovelReplyDto novelReplyDto = NovelReplyDto.toDto(novelReplyEntity);
        novelReplyList.add(novelReplyDto);

        // novelIdx를 통해 찾은 reply_like 테이블의 reply_idx를 이용해서 like_yn='Y'인 개수 구하기
        // 1. replyIdx에 해당하는 replyLike 엔티티 가져오기
        List<ReplyLikeEntity> replyLikeEntityList = novelDetailService.getReplyLikeList(novelReplyEntity);
        for (ReplyLikeEntity replyLikeEntity : replyLikeEntityList) {
          ReplyLikeDto replyLikeDto = ReplyLikeDto.toDto(replyLikeEntity);
          replyLikeList.add(replyLikeDto);
        }

      }
//
      // 2. reply 해당하는 좋아요 'Y'인 개수가 나오는 entity 가져오기
      List<ReplyLikeInterface> replyLikeCountList = new ArrayList<>();
      List<ReplyLikeInterface> replyLikeInterfaceList = novelDetailService.getReplyLikeCount();

      for (ReplyLikeInterface replyLikeInterface : replyLikeInterfaceList) {
        replyLikeCountList.add(replyLikeInterface);
      }
      novelDetail.put("novelReplyList", novelReplyList);
//    novelDetail.put("replyLikeList", replyLikeList);
      novelDetail.put("replyLikeCountList", replyLikeCountList);


      return novelDetail;
    }
    else {
      return null;
    }
  }


  // -------------------------------- db에 데이터 저장 ------------------------------------
  // 리디북스 디테일 페이지 정보 db 저장

  @RequestMapping(value = "/novelDetail", method = RequestMethod.POST)
  public void insertNovelDetail(@RequestParam("title") String encodeTitle, @RequestParam("ne") String ne, @RequestParam("ageGrade") String novelAdult) throws Exception {
    // 인코딩된 문자열을 다시 원래의 문자열로 디코딩해주는 절차 필요함
    System.out.println(encodeTitle);
    String title = URLDecoder.decode(encodeTitle, "UTF-8");
    System.out.println(title);

//    System.out.println(title);
//    System.out.println(id);
//    System.out.println(ne);

    NovelEntity novelEntity = new NovelEntity();
    // 리디북스 디테일 정보 Json 타입으로 받아와서 platform entity에 저장
    NovelPlatformEntity ridiPlatformEntity = novelDetailService.getRidiCrolling(title, ne, novelAdult); // 크롤링은 아니지만 이름 통일을 위해
    // 네이버 디테일페이지 정보 가져와서 platform entity에 저장
    NovelPlatformEntity naverPlatformEntity = novelDetailService.getNaverCrolling(title, ne, novelAdult);
    // 카카오 디테일페이지 정보 가져와서 platform entity에 저장
    NovelPlatformEntity kakaoPlatformEntity = novelDetailService.getKakaoCrolling(title, ne, novelAdult);


//      System.out.println(kakaoPlatformEntity); // 1
//      System.out.println("----------------------\n");
//      System.out.println(naverPlatformEntity); // 2
//      System.out.println("----------------------\n");
//      System.out.println(ridiPlatformEntity); // 3

    // 리디북스에 해당 작품이 없을 때
    if (ridiPlatformEntity.getPlatformId() == null) {

      // 네이버, 카카오만 있을 경우,
      if (naverPlatformEntity.getPlatformId() != null && kakaoPlatformEntity.getPlatformId() != null) {
        novelEntity.setNovelTitle(naverPlatformEntity.getNovelTitle());
        novelEntity.setNovelThumbnail(naverPlatformEntity.getNovelThumbnail());
        novelEntity.setNovelAdult(naverPlatformEntity.getNovelAdult());
        novelEntity.setEbookCheck(naverPlatformEntity.getEbookCheck());

        // 네이버 디테일 페이지 정보를 novel entity에 저장
        novelDetailService.insertNaverToNovel(novelEntity);

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


// -------------------------------- 좋아요 버튼 클릭 ------------------------------------
  @RequestMapping(value = "/novelDetailLike", method = RequestMethod.PUT)
  public String updateDetailLike(@RequestParam("novelIdx") int novelIdx, @RequestParam("id") String id) throws Exception {
//
//    System.out.println(novelIdx);
//    System.out.println(novelTitle);
//    System.out.println(ebookCheck);
//    System.out.println(id);

    try {
      // ※ 기능 : id와, novelIdx 가 일치하는 테이블의 데이터의 값을 Y/N으로 변경
      // 여기까지는 db저장 및 수정(like_yn)
      novelDetailService.updateNovelLike(novelIdx, id);
      return "좋아요 버튼 누르기 success";
    }
    catch (Exception e) {
      e.printStackTrace();
      return "좋아요 버튼 누르기 fail";
    }
  }

// -------------------------------- 리뷰 작성 클릭 ------------------------------------
  @RequestMapping(value = "/novelDetailReview", method = RequestMethod.POST)
  public void updateDetailReview(@RequestParam("novelIdx") int novelIdx, @RequestParam("id") String id, @RequestParam("replyContent") String replyContent, @RequestParam("spoCheck") boolean spoCheck) throws Exception {
    String spoilerYn = "";

    // true/false로 넘어온 값을 Y/N 형식으로 바꿔주기
    if (spoCheck == true) {
      spoilerYn = "Y";
    }
    else {
      spoilerYn = "N";
    }

    try {
      novelDetailService.insertNovelReview(novelIdx, id, replyContent, spoilerYn);

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  // ------------------------------- 리뷰에 좋아요 클릭 --------------------------------
  @RequestMapping(value = "/novelDetailReplyLike", method = RequestMethod.PUT)
  public String updateReviewLike(@RequestParam("id") String id, @RequestParam("replyIdx") int replyIdx) throws Exception {

    try {
      novelDetailService.updateReviewLike(id, replyIdx);

      return "댓글 좋아요 성공";
    }
    catch (Exception e) {
      e.printStackTrace();
      return "댓글 좋아요 실패";
    }
  }

  // ----------------------------- 리뷰(댓글) 신고 ----------------------------------
  @RequestMapping(value = "/novelDetailReport", method = RequestMethod.POST)
  public String insertReplyReport(@RequestParam("replyIdx") int replyIdx, @RequestParam("reportContent") String reportContent, @RequestParam("reporter") String reporter, @RequestParam("suspect") String suspect) throws Exception {
    String reportCheck = novelDetailService.insertReplyReport(replyIdx, reportContent, reporter, suspect);

    if (reportCheck == "success") {
      return "댓글 신고 완료";
    }
    else if (reportCheck == "exist") {
      return "이미 신고한 댓글입니다.";
    }
    else {
      return "댓글 신고 중 오류가 발생했습니다.";
    }
  }

  // -------------------------------- 리뷰(댓글) 삭제 ----------------------------------
  @RequestMapping(value = "/novelDetailReplyDelete", method = RequestMethod.DELETE)
  public String deleteNovelReply(@RequestParam("replyIdx") int replyIdx) throws Exception {
    String deleteCheck = novelDetailService.deleteNovelReply(replyIdx);

    return deleteCheck;
  }
}



