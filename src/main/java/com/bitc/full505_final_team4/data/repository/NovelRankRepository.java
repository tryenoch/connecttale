package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.NovelRankEntity;
import com.bitc.full505_final_team4.data.entity.PlatformRankIdx;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

// jpa 에서 데이터베이스에 명령어를 전달하는 인터페이스

/*
* save() : sql 의 insert, update 명령과 동일한 기능
* findById() : sql 의 select 명령과 동일한 기능, pk를 통해서 조회
* findAll() : sql 의 select 명령과 동일한 기능, pk를 통해서 삭제
* deleteAll() : sql 의 delete 명령과 동일, 전체 삭제
* 쿼리 메소드 : 특정 키워드를 사용하여 사용자 정의 메소드를 생성하는 방식의 메소드
* 쿼리 메소드를 사용하여 필요한 명령을 전달함
* List<NovelRankEntity> findAllByOrderByBoardIdxDesc();
*
* @Query : JpaRepository 에서 제공하는 기본 명령어 및 메소드 쿼리로 만들기 힘든 복잡한 쿼리문의 경우 JPQL 문법을 통해서 SQL 쿼리문을 직접 생성하여 실행할 수 있도록 하는 어노테이션
* */

public interface NovelRankRepository extends JpaRepository<NovelRankEntity, PlatformRankIdx> {

  // 리디 지정한 랭크 소설 들고오기
  NovelRankEntity findByPlatformAndRankNumAndUpdateDate(int platform, int rankNum, LocalDate date);

  // 플랫폼 번호에 해당하는 소설 목록 들고오기
  // 1 카카오 2 네이버 3 리디북스
  List<NovelRankEntity> findAllByPlatform(int platform);

}
