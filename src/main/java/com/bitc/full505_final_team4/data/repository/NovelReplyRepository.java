package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NovelReplyRepository extends JpaRepository<NovelReplyEntity, Integer> {

  Optional<List<NovelReplyEntity>> findAllByNovelIdxOrderByCreateDtDesc(NovelEntity novelIdx);

  Page<NovelReplyEntity> findById(MemberEntity replyId, Pageable pageable);

  Optional<NovelReplyEntity> findByReplyIdxAndDeletedYn(int replyIdx, String n);

//   NovelReplyLikeInterface의 컬럼명과 동일하게 별칭을 기입해줘야 함
//  @Query("SELECT nr.replyIdx AS replyIdx, nr.novelIdx AS novelIdx, nr.replyContent AS replyContent, nr.id AS id, nr.createDt AS createDt, nr.deletedYn AS deletedYn, nr.spoilerYn AS spoilerYn, COUNT(rl.likeYn) as likeCnt FROM NovelReplyEntity nr LEFT JOIN FETCH ReplyLikeEntity rl ON (nr.replyIdx = rl.replyIdx.replyIdx) WHERE rl.likeYn = 'Y' AND nr.novelIdx = :novelIdx GROUP BY nr.replyIdx ORDER BY nr.createDt DESC")
//  Optional<List<NovelReplyLikeInterface>> findNovelReplyFetchJoin(@Param("novelIdx") NovelEntity novelIdx);

}
