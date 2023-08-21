package com.bitc.full505_final_team4.data.dto;

import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelReplyEntity;
import com.bitc.full505_final_team4.data.entity.ReplyLikeEntity;

import java.time.LocalDateTime;

// 리뷰(댓글)에 대한 좋아요 카운트를 조인하여 데이터를 받을 Interface 설정
// NovelReplyEntity와 ReplyLikeEntity를 숫자 연산 함수(COUNT, SUM 등)를 사용한 조인 시 데이터를 받을 인터페이스를 별도로 생성해줘야 함
public interface ReplyLikeInterface {
  NovelReplyEntity getReplyIdx();
  int getLikeCnt();
}
