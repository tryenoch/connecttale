import React, {useState} from 'react';
import {Col, Row} from "react-bootstrap";
import * as MessageUtils from "../../../common/MessageUtils";
import axios from "axios";

function NovelRankRidi() {
  /* 순위 리스트 초기화 */
  const [novelRankList, setNovelRankList] = useState([
    /*{
      rank : 1,
      title : "작품 제목",
      thumbnail : "썸네일 이미지",
      author : "작가 이름",
      starRate : "별점"
    }*/
  ]);

  /*setNovelRankList({

  });*/



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
        {
          novelRankList.map(
            (novel) => (
              <Col sm={3}>
                <div key={novel.rank}>
                  <p>작품 순위 : {novel.rank}</p>
                  <p>작품 제목 : {novel.title}</p>
                </div>
              </Col>
            )
          )
        }
      </Row>
    </div>

  )

  /* 기능 리스트 */
  function addNovelTest(){
    const updateNovelRankList = [
      ...novelRankList,
      {
        rank : novelRankList.length + 1,
        title : "작품 제목 " + (novelRankList.length + 1)
      }
    ];

    setNovelRankList(updateNovelRankList);
  }

  function rankTest() {
    // MessageUtils.infoMessage("테스트 버튼을 클릭했습니다.");

    const category = "1750";

    axios.get(`/novel/ridiRankList`, {
      params : { category : category }
    }).then(res => {
      MessageUtils.infoMessage("ridiRankList 통신에 성공했습니다.");
      console.log(res);
      console.log(res.data.result);
    }).catch(err => {
      MessageUtils.errorMessage("에러 메세지", err);
    })
  }

}




export default NovelRankRidi;