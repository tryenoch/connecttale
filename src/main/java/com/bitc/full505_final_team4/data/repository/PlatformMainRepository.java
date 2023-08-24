package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatFormIdx;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

// service main 패키지와 연동되는 repository
public interface PlatformMainRepository extends JpaRepository<NovelPlatformEntity, NovelPlatFormIdx> {

  // 플랫폼 번호와 novel idx, 웹소설 또는 ebook 여부
  /*@Query("SELECT p FROM NovelPlatformEntity AS p" +
    "WHERE p.platform = :platform" +
    "AND p.novelIdx = :novelIdx" +
    "AND p.novelOrEbook = :novelOrEbook")
  NovelPlatformEntity queryFindByNovel(@Param("platform") int platform, @Param("novelIdx") int novelIdx, @Param("novelOrEbook") String novelOrEbook);*/

  Optional<NovelPlatformEntity> findByPlatformAndNovelIdx_NovelIdx(int platform, int novelIdx);

  /*@Query(
    "SELECT p FROM NovelPlatformEntity AS p ORDER BY p.novelRelease"
  )*/
  List<NovelPlatformEntity> findNovelPlatformEntitiesByOrderByNovelReleaseDesc(Pageable pageable);

  Optional<NovelPlatformEntity> findByNovelIdx_NovelIdxAndPlatform(int novelIdx, int platform);

  // cateList에 cateItem이 포함되어 있는 리스트 들고오기
  Page<NovelPlatformEntity> findNovelPlatformEntitiesByCateListLike(String cateItem, Pageable pageable);

  Page<NovelPlatformEntity> findAllBy(Pageable pageable);
}
