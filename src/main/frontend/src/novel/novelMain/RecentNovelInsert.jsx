import React from 'react';
import axios from "axios";
import * as MessageUtils from "../../common/MessageUtils";

function RecentNovelInsert(props) {

  return (
    <div>
      <button className={"btn btn-purple me-2"} onClick={getRidiRecentNovel}>리디북스 신작 추가</button>
      <button className={"btn btn-purple me-2"} onClick={getNaverRecentNovel}>네이버 신작 추가</button>
      <button className={"btn btn-purple"} onClick={getKakaoRecentNovel}>카카오 신작 추가</button>
    </div>
  )

  // 리디 최신작 추가 서버 연동 함수
  function getRidiRecentNovel(){
    //alert('테스트 메세지입니다.');
    axios.get(`/novel/ridiRecentNovelUpdate`)
      .then(res => {
        // MessageUtils.infoMessage("비동기 통신에 성공했습니다.");
        console.log(res.data.result);
      })
      .catch(err => {
        MessageUtils.errorMessage("리디 신작 업데이트", err);
      })
  }

  // 네이버 최신작 추가 서버 연동 함수
  function getNaverRecentNovel(pageNum){
    // alert('테스트 메세지입니다.');
    axios.get(`/novel/naverRecentNovelUpdate`, {
      params : {pageNum : 1}
    })
      .then(res => {
        MessageUtils.infoMessage("비동기 통신에 성공했습니다.");
        console.log(res.data.result);
      })
      .catch(err => {
        MessageUtils.errorMessage("네이버 신작 업데이트", err);
      })
  }

  // 카카오 최신작 추가 서버 연동 함수
  function getKakaoRecentNovel(pageNum){
    // alert('테스트 메세지입니다.');
    axios.get(`/novel/kakaoRecentNovelUpdate`)
        .then(res => {
          MessageUtils.infoMessage("비동기 통신에 성공했습니다.");
          console.log(res.data.result);
        })
        .catch(err => {
          MessageUtils.errorMessage("카카오 신작 업데이트", err);
        })
  }

}

export default RecentNovelInsert;