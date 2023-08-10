import React, {useEffect, useState} from 'react';
import {Col, Row} from "react-bootstrap";
import * as MessageUtils from "../../../common/MessageUtils";
import axios from "axios";
import RankItem from "./RankItem";

function NovelMainRank() {
  /* 순위 리스트 초기화 */
  const [novelList, setNovelList] = useState([]);

  useEffect(() => {
    loadRankListRidi(1750);
  }, []);

  // 받아온 순위 리스트중 12개만 출력되도록
  let rankList = novelList.filter(novel => novel.novelIndexNum <13);
  rankList = rankList.map(novel =>
    /* 나중에 링크 추가 해야함 */
    <Col className={"rank-item"} key={novel.novelIndexNum}>
      <RankItem novel={novel} />
    </Col>
  );

  return (
    <div>
      <Row>
        <Col sm>
          <h3 className={"main-title"}>플랫폼별 일간 순위</h3>
          <div className={'my-3'}>
            <div className="btn-group btn-group-sm" role="group" aria-label="ridi cate button">
              <button className={'btn btn-purple px-3 btn-sm me-0'} onClick={(e) => {loadRankListRidi(1750)}}>리디북스</button>
              <div className="btn-group btn-group-sm" role="group">
                <button type="button" className="btn btn-outline-purple btn-sm dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                  카테고리
                </button>
                <ul className="dropdown-menu">
                  <li><button className="dropdown-item btn-sm" onClick={(e) => {loadRankListRidi(1750)}}>판타지</button></li>
                  <li><button className="dropdown-item btn-sm" onClick={(e) => {loadRankListRidi(1650)}}>로맨스</button></li>
                  <li><button className="dropdown-item btn-sm" onClick={(e) => {loadRankListRidi(6050)}}>로판</button></li>
                  <li><button className="dropdown-item btn-sm" onClick={(e) => {loadRankListRidi(4150)}}>BL</button></li>
                </ul>
              </div>
            </div>
            <button className={'btn btn-purple px-3 btn-sm rounded-5'}>네이버 시리즈</button>
            <button className={'btn btn-purple px-3 btn-sm rounded-5'}>카카오 페이지</button>
          </div>
        </Col>
      </Row>
      {/* 리스트 출력 */}
      <Row>
        {rankList}
      </Row>
    </div>

  )

  /* 기능 리스트 */

  // 리디 소설 불러오기
  function loadRankListRidi(category) {
    // MessageUtils.infoMessage("테스트 버튼을 클릭했습니다.");

    // const category = 1750;

    axios.get(`/novel/ridiRankList`, {
      params : { category : category }
    }).then(res => {
      // MessageUtils.infoMessage("ridiRankList 통신에 성공했습니다.");
      // console.log(res);
      console.log(res.data.ridiNovelList);

      const list = res.data.ridiNovelList;
      setNovelList(list);

    }).catch(err => {
      MessageUtils.errorMessage("에러 메세지", err);
    })
  }

}




export default NovelMainRank;