package com.bitc.full505_final_team4.data.repository;

import com.bitc.full505_final_team4.data.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository <MemberEntity, String> {

  MemberEntity findByIdAndPw(String id, String pw);
}
