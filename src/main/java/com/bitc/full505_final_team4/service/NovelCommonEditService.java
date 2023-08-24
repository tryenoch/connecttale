package com.bitc.full505_final_team4.service;

import org.springframework.stereotype.Service;

@Service
public class NovelCommonEditService {
  // 추가 수정

  // Novel entity 에 넣기 위한 title 편집 함수
  public String editTitleForNovelEntity(String originalTitle) {

    /* 업데이트일 : 23.08.18
     * 공통으로 제목을 만들 수 있는 경우의 수 발견시 추가 예정
     * 예시 목록)
     * 이혼남남 1~2권 [BL][단행본][19세 완전판] => 이혼남남 1~2권
     * 이혼남남 [BL][단행본] => 이혼남남
     * [e북] 가시꽃과 원숭이 (19세 완전판) => 가시꽃과 원숭이
     * [e북] [특별 세트] 86 -에이티식스- (총 10권) => [특별 세트] 86 -에이티식스-
     * 이혼남남 [BL][단행본] (총 3권/완결) => 이혼남남
     * */

    String editTitle1 = originalTitle;
    String editTitle2 = "";


    int idx1 = 0;
    int idx2 = 0;

    // [e북] 이 포함되어있을 경우 삭제, 주로 ridi
    if (editTitle1.contains("[e북]")){
      idx1 = editTitle1.indexOf("[e북]");

      // [e북] 과 뒤의 한칸 공백까지 지움
      editTitle1 = editTitle1.substring(idx1 + 5 );
    }


    // (총 n권/완결여부) 또는 (총 n권) 관련 내용이 제일 마지막에 있을 때 지움
    // 주로 네이버, 가끔 리디 (카카오 X)
    if(editTitle1.contains("(총")){
      idx1 = editTitle1.lastIndexOf("(총");
      editTitle1 = editTitle1.substring(0, idx1);
    }


    // 익명으로 전해드립니다 [BL] [특전포함]
    // 불필요한 장르명을 나타내는 [BL] 이 포함되어 있을 경우 해당 부분 삭제
    if(editTitle1.contains("[BL]")){
      idx1 = editTitle1.indexOf("[BL]");

      // 뒤에 붙일 문자열을 먼저 정의
      editTitle2 = editTitle1.substring(idx1 + 4); // [특전포함]
      editTitle1 = editTitle1.substring(0, idx1); // 익명으로 전해드립니다
      editTitle1 = editTitle1 + editTitle2;
    }

    if(editTitle1.contains("[로맨스]")){
      idx1 = editTitle1.indexOf("[로맨스]");

      // 뒤에 붙일 문자열을 먼저 정의
      editTitle2 = editTitle1.substring(idx1 + 5); // [특전포함]
      editTitle1 = editTitle1.substring(0, idx1); // 익명으로 전해드립니다
      editTitle1 = editTitle1 + editTitle2;
    }

    if(editTitle1.contains("[판타지]")){
      idx1 = editTitle1.indexOf("[판타지]");

      // 뒤에 붙일 문자열을 먼저 정의
      editTitle2 = editTitle1.substring(idx1 + 5); // [특전포함]
      editTitle1 = editTitle1.substring(0, idx1); // 익명으로 전해드립니다
      editTitle1 = editTitle1 + editTitle2;
    }

    // 왕비가 된 기녀, 아라 홍련 [단행본/할인중] 과 같이(출처 카카오)
    // 할인 정보가 붙어 있을 경우 별도의 문서로 보고 자르지 않음(그대로 유지)
    if(editTitle1.contains("[단행본]")) {

      idx1 = editTitle1.indexOf("[단행본]");
      editTitle2 = editTitle1.substring(idx1 + 5);
      editTitle1 = editTitle1.substring(0, idx1);
      editTitle1 = editTitle1 + editTitle2;

    } else if (editTitle1.contains("(단행본)")) {

      idx1 = editTitle1.indexOf("(단행본)");
      editTitle2 = editTitle1.substring(idx1 + 5);
      editTitle1 = editTitle1.substring(0, idx1);
      editTitle1 = editTitle1 + editTitle2;

    }

    if(editTitle1.contains("[19세 완전판]")) {

      idx1 = editTitle1.indexOf("[19세 완전판]");
      editTitle2 = editTitle1.substring(idx1 + 9);
      editTitle1 = editTitle1.substring(0, idx1);
      editTitle1 = editTitle1 + editTitle2;

    } else if (editTitle1.contains("(19세 완전판)")) {

      idx1 = editTitle1.indexOf("(19세 완전판)");
      editTitle2 = editTitle1.substring(idx1 + 9);
      editTitle1 = editTitle1.substring(0, idx1);
      editTitle1 = editTitle1 + editTitle2;

    }

    if(editTitle1.contains("[15세 개정판]")) {

      idx1 = editTitle1.indexOf("[15세 개정판]");
      editTitle2 = editTitle1.substring(idx1 + 9);
      editTitle1 = editTitle1.substring(0, idx1);
      editTitle1 = editTitle1 + editTitle2;

    } else if (editTitle1.contains("(15세 개정판)")) {

      idx1 = editTitle1.indexOf("(15세 개정판)");
      editTitle2 = editTitle1.substring(idx1 + 9);
      editTitle1 = editTitle1.substring(0, idx1);
      editTitle1 = editTitle1 + editTitle2;

    }

    if(editTitle1.contains("[완결]")) {

      idx1 = editTitle1.indexOf("[완결]");
      editTitle2 = editTitle1.substring(idx1 + 4);
      editTitle1 = editTitle1.substring(0, idx1);
      editTitle1 = editTitle1 + editTitle2;

    } else if (editTitle1.contains("(완결)")) {

      idx1 = editTitle1.indexOf("(완결)");
      editTitle2 = editTitle1.substring(idx1 + 4);
      editTitle1 = editTitle1.substring(0, idx1);
      editTitle1 = editTitle1 + editTitle2;

    }

    if(editTitle1.contains("[독점]")) {

      idx1 = editTitle1.indexOf("[독점]");
      editTitle2 = editTitle1.substring(idx1 + 4);
      editTitle1 = editTitle1.substring(0, idx1);
      editTitle1 = editTitle1 + editTitle2;

    } else if (editTitle1.contains("(독점)")) {

      idx1 = editTitle1.indexOf("(독점)");
      editTitle2 = editTitle1.substring(idx1 + 4);
      editTitle1 = editTitle1.substring(0, idx1);
      editTitle1 = editTitle1 + editTitle2;

    }

    // 최종 문자열에서 맨앞, 맨뒤 공백이 남아있을 경우 제거(정규 표현식 사용)
    editTitle1 = editTitle1.stripLeading();
    editTitle1 = editTitle1.replaceAll("\\s+$", "");
    return editTitle1;
  }

}
