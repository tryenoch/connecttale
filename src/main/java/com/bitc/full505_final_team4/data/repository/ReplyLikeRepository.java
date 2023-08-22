package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.dto.ReplyLikeInterface;
import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.entity.NovelReplyEntity;
import com.bitc.full505_final_team4.data.entity.ReplyLikeEntity;
import com.bitc.full505_final_team4.data.entity.ReplyLikeIdx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReplyLikeRepository extends JpaRepository<ReplyLikeEntity, ReplyLikeIdx> {

  Optional<ReplyLikeEntity> findByIdAndReplyIdx(MemberEntity memberEntity, NovelReplyEntity novelReplyEntity);

  Optional<List<ReplyLikeEntity>> findAllByReplyIdx(NovelReplyEntity novelReplyEntity);

  @Query("SELECT replyIdx AS replyIdx, COUNT(*) AS likeCnt FROM ReplyLikeEntity WHERE likeYn = 'Y' GROUP BY replyIdx")
  List<ReplyLikeInterface> findReplyLikeCount();
}
