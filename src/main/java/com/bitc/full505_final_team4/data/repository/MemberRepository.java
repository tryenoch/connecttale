package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    MemberEntity findByIdAndPw(String id, String pw);

    MemberEntity save(MemberEntity member);

    boolean existsById(String id);

    boolean existsByNickname(String nickname);

    MemberEntity findAllById(String id);

    boolean existsByIdAndPw(String id, String pw);
  Page<MemberEntity> findByDeletedYn(String deletedYn, Pageable pageable);
}
