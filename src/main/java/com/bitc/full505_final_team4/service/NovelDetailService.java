package com.bitc.full505_final_team4.service;

import com.bitc.full505_final_team4.data.dto.NovelReplyLikeInterface;
import com.bitc.full505_final_team4.data.entity.*;

import java.util.List;

public interface NovelDetailService {
  List<NovelPlatformEntity> getNovelDetail(String title, String ebookCheck, String novelAdult);

  void insertRidiToNovel(NovelEntity novelEntity);

  void insertRidiToPlatform(NovelPlatformEntity novelPlatformEntity);


  NovelPlatformEntity getNaverCrolling(String title, String novelOrEbook, String ageGrae);

  void insertNaverToNovel(NovelEntity novelEntity);

  void insertNaverToPlatform(NovelPlatformEntity novelPlatformEntity);

  NovelPlatformEntity getKakaoCrolling(String title, String ne, String ageGrae);

  void insertKakaoToNovel(NovelEntity novelEntity);

  void insertKakaoToPlatform(NovelPlatformEntity kakaoPlatformEntity);

  void updateNovelLike(int novelIdx, String id);

  NovelEntity getNovelIdx(String title, String ebookCheck, String novelAdult);

  int getNovelLikeCount(NovelEntity novelIdx);

  List<NovelLikeEntity> getNovelLike(NovelEntity novelIdx);

  List<NovelReplyLikeInterface> getNovelReply(NovelEntity novelIdx);

  void insertNovelReview(int novelIdx, String id, String replyContent, String spoilerYn);

  void updateReviewLike(String id, int replyIdx);

//  List<ReplyLikeEntity> getReplyLikeList(NovelReplyEntity novelReplyEntity);

}
