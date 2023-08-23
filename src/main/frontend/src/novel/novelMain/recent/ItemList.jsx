import React, {useEffect, useState} from 'react';
import {Col, Row} from "react-bootstrap";
import {errorMessage} from "../../../common/MessageUtils";
import axios from "axios";
import RankItem from "../rank/RankItem";
import Item from "./item";

function ItemList(props) {

  // 최근 소설 리스트 state
  const [recentNovelList, setRecentNovelList] = useState([]);

  let list = 0;

  useEffect(() => {
    getRecentNovelList(6);
  }, []);

  list = recentNovelList.map(novel =>
    /* 나중에 링크 추가 해야함 */
    <Col className={"rank-item"} key={recentNovelList.indexOf(novel)}>
      <Item novel={novel} />
    </Col>
  );



    if(!list.length){
      // 리스트가 비었을 경우
      return(
        <div>
          <Row>
            {/*<button onClick={getRecentNovelList}>테스트</button>*/}
            <Col sm className={"d-flex justify-content-center empty-box"}>
              <div className={"p-5"}>
                <h3 className={'empty-list'}>좋아요 리스트가 없습니다.</h3>
              </div>
            </Col>
          </Row>
        </div>
        )
    }else {
      // 리스트가 안 비었을 경우 아이템 목록 반환
      return(
        <Row>
          {list}
        </Row>
        )
    }



  // 신규 소설 목록 데이터 들고오기
  function getRecentNovelList(itemCount){
    //alert('테스트 메세지입니다.');
    axios.get(`/novel/recentNovelList`, {
      params : { itemCount : itemCount }
    })
      .then(res => {
        // MessageUtils.infoMessage("비동기 통신에 성공했습니다.");
        console.log(res.data.result);
        console.log(res.data.list);
        setRecentNovelList(res.data.list);
      })
      .catch(err => {
        errorMessage("최신 소설 리스트", err);
      })
  }

}

export default ItemList;