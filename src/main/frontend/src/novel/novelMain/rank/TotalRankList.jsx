import React, {useState} from 'react';
import {Col, Row} from "react-bootstrap";
import TotalList from "./TotalList";
import {Link} from "react-router-dom";
import TotalRidiList from "./TotalRidiList";

function TotalRankList(props) {

  return (
    <div>
      <h1 className={'main-title'}>TOP 30 순위 리스트</h1>
      <Row className={'align-item-center mt-4 mb-5'}>
        <Col className={'me-3'}>
          <div className={'d-flex justify-content-between align-items-center'}>
            <h3 className={'sub-rank-title'}>네이버</h3>
            <Link to={'https://series.naver.com/novel/top100List.series'} target={'_blank'}>
              <span className={'me-2 more-link'}>더 보기
                <i className="bi bi-arrow-right-circle-fill ms-1 pt-1"></i>
              </span>
            </Link>
          </div>

          <TotalList platform={"naver"}/>
        </Col>
        <Col className={'mx-3'}>
          <div className={'d-flex justify-content-between align-items-center'}>
            <h3 className={'sub-rank-title'}>카카오</h3>
            <Link to={'https://page.kakao.com/menu/10011/screen/94'} target={'_blank'}>
              <span className={'me-2 more-link'}>더 보기
                <i className="bi bi-arrow-right-circle-fill ms-1 pt-1"></i>
              </span>
            </Link>
          </div>
          <TotalList platform={"kakao"}/>
        </Col>
        <Col className={'ms-3'}>
          <TotalRidiList />
        </Col>
      </Row>
    </div>
  )
}

export default TotalRankList;