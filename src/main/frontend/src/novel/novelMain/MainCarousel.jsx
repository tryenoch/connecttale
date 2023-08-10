import React from 'react';
import {Col, Row} from "react-bootstrap";

function MainCarousel(props) {

  return (
    <Row className={"mx-auto"}>
      <Col sm className={"d-flex justify-content-center bg-secondary my-4 p-3"}>
        <h1>캐러셀 컴포넌트</h1>
      </Col>
    </Row>
  )
}

export default MainCarousel;