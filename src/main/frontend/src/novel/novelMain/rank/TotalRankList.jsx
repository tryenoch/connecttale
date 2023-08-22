import React, {useState} from 'react';
import {Col, Row} from "react-bootstrap";
import TotalList from "./TotalList";
import {Link} from "react-router-dom";

function TotalRankList(props) {

  const [category, setCategory] = useState("1750");

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
          <div className={'d-flex justify-content-between align-items-center'}>

            <div className={'clearfix'}>
              <h3 className={'float-start sub-rank-title'}>리디북스</h3>
              <div className="float-start btn-group btn-group-sm pt-1" role="group">
                <button type="button" className="btn btn-outline-purple btn-sm dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                  카테고리
                </button>
                <ul className="dropdown-menu">
                  <li><button className="dropdown-item btn-sm" onClick={(e) => {setCategory('1750')}}>판타지</button></li>
                  <li><button className="dropdown-item btn-sm" onClick={(e) => {setCategory('1650')}}>로맨스</button></li>
                  <li><button className="dropdown-item btn-sm" onClick={(e) => {setCategory('6050')}}>로판</button></li>
                  <li><button className="dropdown-item btn-sm" onClick={(e) => {setCategory('4150')}}>BL</button></li>
                </ul>
              </div>
            </div>

            <Link to={'https://ridibooks.com/category/1750'} target={'_blank'}>
              <span className={'me-2 more-link'}>더 보기
                <i className="bi bi-arrow-right-circle-fill ms-1 pt-1"></i>
              </span>
            </Link>
          </div>
          <TotalList platform={"ridi"} category={category}/>
        </Col>
      </Row>
    </div>
  )
}

export default TotalRankList;