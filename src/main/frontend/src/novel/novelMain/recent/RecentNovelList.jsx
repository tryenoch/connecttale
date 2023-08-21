import React from 'react';
import {Col, Row} from "react-bootstrap";
import ItemList from "./ItemList";

function RecentNovelList(props) {

  return (
    <div className={'mb-5'}>
      <Row>
        <Col sm>
          <h3 className={"main-title"}>NEW! 최근 출시작 👀</h3>
        </Col>
      </Row>
      <ItemList />
    </div>
  )
}

export default RecentNovelList;