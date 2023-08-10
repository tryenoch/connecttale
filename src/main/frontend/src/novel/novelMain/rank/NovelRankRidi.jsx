import React, {useEffect, useState} from 'react';
import {Col, Row} from "react-bootstrap";
import * as MessageUtils from "../../../common/MessageUtils";
import axios from "axios";
import RankItem from "./RankItem";

function NovelRankRidi() {
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
          <h3 className={"main-title"}>플랫폼별 실시간 랭킹</h3>
          {/*<div>*/}
          {/*  <button className={'btn btn-purple'} onClick={ loadRankListRidi }>테스트 버튼</button>*/}
          {/*  <button className={'btn btn-purple ms-2'} onClick={ addNovelTest }>작품 추가 테스트 버튼</button>*/}
          {/*</div>*/}
        </Col>
      </Row>
      <Row>
        {rankList}
      </Row>
    </div>

  )

  /* 기능 리스트 */
  function addNovelTest(){
    const updateNovelRankList = [
      ...novelList,
      {
        rank : novelList.length + 1,
        title : "작품 제목 " + (novelList.length + 1)
      }
    ];

    setNovelList(updateNovelRankList);
  }

  /* 소설 불러오기 */
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




export default NovelRankRidi;