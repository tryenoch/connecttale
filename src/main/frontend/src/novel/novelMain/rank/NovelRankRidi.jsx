import React, {useState} from 'react';
import {Col, Row} from "react-bootstrap";
import * as MessageUtils from "../../../common/MessageUtils";

function NovelRankRidi() {
  /* 순위 리스트 초기화 */
  const [novelRankList, setNovelRankList] = useState([
    {
      rank : 1,
      title : "작품 제목 1"
    }
  ]);

  /*setNovelRankList({

  });*/

  const rankTest = () => {
    MessageUtils.infoMessage("테스트 버튼을 클릭했습니다.");

  }

  return (
    <div>
      <Row>
        <Col sm>
          <h1>리디 랭크 컴포넌트 테스트</h1>
          <div>
            <button className={'btn btn-purple'} onClick={ rankTest }>테스트 버튼</button>
            <button className={'btn btn-purple ms-2'} onClick={ addNovelTest }>작품 추가 테스트 버튼</button>
          </div>
        </Col>
      </Row>
      <Row>
        <Col sm>
          {
            novelRankList.map(
              (novel) => (
                <div key={novel.rank}>
                  <p>작품 순위 : {novel.rank}</p>
                  <p>작품 제목 : {novel.title}</p>
                </div>
              )
            )
          }
        </Col>
      </Row>
    </div>

  )
}

function addNovelTest(){

}



export default NovelRankRidi;