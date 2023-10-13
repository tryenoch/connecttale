# ConnectTale

## 서비스 개요
- 웹소설 순위 정보 및 가격, 작품 소개 등의 상세 정보를 한 눈에 볼 수 있는 플랫폼
- 정보를 가져오는 웹소설 사이트는 [리디북스](https://ridibooks.com/fantasy/webnovel), [카카오 페이지](https://page.kakao.com/menu/10011), [네이버 시리즈](https://series.naver.com/novel/home.series) 로 특정한다. 

## 개발배경
- 인기 플랫폼에서 독점하고 있는 작품들이 늘어나면서 소설 정보를 따로 찾아보아야 하는 불편함 대두
- 각 플랫폼에서 볼 수 있는 작품들을 한 곳에서 볼 수 있는 니즈 충족
- 여러 사이트에서 함께 서비스 되고 있는 작품들 간의 가격 비교
- 특정한 사이트들의 정보를 들고오는 과정을 자동화 하는 것이 목표이다.

### 사이트 주소
- [바로가기](http://ec2-15-164-162-2.ap-northeast-2.compute.amazonaws.com:8080/)

### 진행기간 및 개발인원
> 2023.07.31 - 2023.07.24 (4명)
- [chanmi](https://github.com/enoch012) / 메인, 순위, 카테고리 페이지 
- [sangwon](https://github.com/jeti11) / 소설 검색, 소설 상세 페이지
- [seungwook](https://github.com/Wookicooki) / 회원 정보, 마이페이지
- [joontae](https://github.com/joontae-98) / 게시판, 로그인, 배포

## 주요 개발환경
### Backend
- Spring Boot 3.1.2 / SpringDataJpa / Selenium / Jsoup / Json-Simple / MySQL

### Frontend
- React / Bootstrap / Axios

## 개선사항 및 문제 해결 내역
### 여러 Novel Entity INSERT 시 에러 
- DB에 저장되지 않은 최신 웹소설 Novel Entity와 Platform Entity List를 각각 모아 한꺼번에 저장하고자 했으나 Platform Entity가 외래키로 참조하는 Novel Entity `@ID`의 전략이 `@GeneratedValue(strategy = GenerationType.IDENTITY)` 와 같을 경우 DB에 INSERT SQL이 실행 되어야 그 id 값을 알 수 있었다. 
- IDENTITY 전략에서만 예외적으로 Entity 생성과 함께 INSERT 쿼리를 날리기로 한다.(하나의 Transaction 안에서 여러 INSERT Query가 네트워크를 탄다고 해서 비약적인 차이가 나는 것은 아니었다. [참고](https://gmlwjd9405.github.io/2019/08/12/primary-key-mapping.html)) 

### 공통 제목 양식을 위한 `NovelCommonEditService` 클래스 생성
- 각각의 플랫폼에서 데이터를 들고 올 때 타 사이트와 같은 작품을 가리킴에도 제목 양식이 달라 오류가 발생하고 있었다. 
- DB에 저장할 때 같은 작품일 경우 Title 데이터를 통일 시킬 수 있도록 필터 역할을 하는 Title Edit Service 클래스 구현.
 
<details>
<summary>접기/펼치기</summary> 

``` java 
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

    if(editTitle1.contains("[연재]")) {

      idx1 = editTitle1.indexOf("[연재]");
      editTitle2 = editTitle1.substring(idx1 + 4);
      editTitle1 = editTitle1.substring(0, idx1);
      editTitle1 = editTitle1 + editTitle2;

    } else if (editTitle1.contains("(연재)")) {

      idx1 = editTitle1.indexOf("(연재)");
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

```
</details>
 

## 구현 기능 목록
> 아래 목록에서 `사이트`는 개발한 서비스를 가리키며, `플랫폼`은 `리디북스, 카카오 페이지, 네이버 시리즈` 각각의 서비스를 가리킨다. 
1. 웹소설 메인
    - 플랫폼별 일간 웹소설 순위를 볼 수 있다. (메인, 서브페이지)
    - 성인 작품일 경우 로그인 상태의 성인 사용자만 썸네일을 볼 수 있다.
    - 최신 웹소설 목록을 볼 수 있다.
      - 단, 관리자가 업데이트 버튼을 눌러 데이터베이스에 저장해주어야 한다. 
      - **수정중** / 카카오 페이지(크롤링 대상 페이지 변동)
    - 웹소설 검색어를 입력하면 사이트별 검색 결과를 볼 수 있다.
        - **수정중** / 크롤링 드라이버 창이 뜨는 현상
    - 사이트 내에서 가장 좋아요가 높은 소설을 볼 수 있다.     
2. 웹소설 상세페이지
    - 웹소설 상세 정보를 볼 수 있다. (화수, 작품 소개, 작가명, 출판사 등)
    - 플랫폼별 가격 정보를 볼 수 있다.
    - 사이트 내 좋아요 수를 볼 수 있다.
    - 사이트 내 작품 리뷰 댓글을 볼 수 있다.
    - 리뷰 댓글을 신고할 수 있다.   
3. 회원
    - 일반적인 로그인, 회원가입, 회원 정보 수정
    - 성인일 경우, 성인 작품 썸네일과 상세페이지를 볼 수 있다.
    - 작품 상세페이지 내 좋아요를 남길 수 있다.
    - 작품 상세페이지 내 리뷰 댓글을 남길 수 있다.
    - 모든 게시판의 글을 볼 수 있다.
    - 문의 게시판에 글을 남길 수 있다.
    - 마이 페이지에서 좋아요 리스트, 내 문의글 리스트를 볼 수 있다.    
4. 관리자
    - 가입한 회원 목록과 등급을 볼 수 있다.
    - 신고한 리뷰 댓글 리스트를 볼 수 있다.
    - 최신 소설 데이터를 업데이트 할 수 있다.
    - 모든 게시판의 글쓰기, 삭제가 가능하다.   
5. 게시판
    - 기본 CRUD
    - 문의 게시판의 카테고리 기능
    - 게시글 검색 기능
