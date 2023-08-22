import React, {useEffect, useState} from 'react';
import TotalItem from "./TotalItem";
import {Col, Row} from "react-bootstrap";
import axios from "axios";
import * as MessageUtils from "../../../common/MessageUtils";

function TotalList(props) {
 // 수정
  const platform = props.platform;
  const [rankList, setRankList] = useState([]);

  useEffect(() => {
    setTotalList(platform);
  }, []);


  let list = rankList.filter(novel => novel.novelIndexNum < 21);
  list = list.map(novel =>
    <Row className={"total-list p-2"} key={novel.novelIndexNum}>
      <Col>
        <TotalItem novel={novel}/>
      </Col>
    </Row>
  )

  return (
    <div className={""}>
      {list}
    </div>
  )

  // 플랫폼에 따라 들고오는 함수가 다름
  function setTotalList(platform) {
    if(platform == "naver"){ getNaverList(0, 20); }
    else if(platform == "kakao") { getKakaoList(); }
  }

  function getNaverList(startNum, endNum){
    axios.get(`/novel/naverRankList`, {
      params : {
        startNum : startNum,
        endNum : endNum
      }
    }).then(res => {
      // MessageUtils.infoMessage("ridiRankList 통신에 성공했습니다.");
      // console.log(res);
      console.log(res.data.naverNovelList);

      setRankList(res.data.naverNovelList);

    }).catch(err => {
      MessageUtils.errorMessage("getNaverList", err);
    })
  }

  function getKakaoList(){
    axios.get(`/novel/kakaoRankList`, {
      params : { urlId : "94" }
    }).then(res => {
      // MessageUtils.infoMessage("ridiRankList 통신에 성공했습니다.");
      // console.log(res);
      console.log(res.data.kakaoNovelList);

      setRankList(res.data.kakaoNovelList);

    }).catch(err => {
      MessageUtils.errorMessage("getKakaoList", err);
    })
  }

}

export default TotalList;