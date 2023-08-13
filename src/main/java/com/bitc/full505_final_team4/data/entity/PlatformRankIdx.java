package com.bitc.full505_final_team4.data.entity;

/* NovelRankEntity 의 복합키 클래스 */

import lombok.Data;

import java.io.Serializable;

@Data
public class PlatformRankIdx implements Serializable {
  private int platform; // 1 카카오, 2 네이버, 3 리디북스
  /*
  * 리디북스는 불러오는 카테고리가 4개 이므로 카테고리 별 순위 값에
  * 0, 50, 100, 150을 더한다. (프론트에서 계산할 때 해당 숫자를 빼면 순위가 나온다)
  * */
  private int rankNum;
}
