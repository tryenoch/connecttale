import React from 'react';
import {Col, Row} from "react-bootstrap";
import ItemList from "./ItemList";

function LikeNovelList(props) {

  return (
    <div className={'mb-5'}>
      <Row>
        <Col sm>
          <h3 className={"main-title"}>#유저 선정! 좋아요🔥 리스트</h3>
        </Col>
      </Row>
      <ItemList />
    </div>
  )
}

export default LikeNovelList;