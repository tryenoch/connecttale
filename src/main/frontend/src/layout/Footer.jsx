import React from 'react';
import {Col, Container, Row} from "react-bootstrap";

function Footer(props) {
  return (
      <div>
        <hr/>
        <Container fluid={"sm"} className={'p-5'}>

          <Row>
            <Col>
              <p className={'logo'}>CONNECTTALE</p>
              <p><strong>조장</strong> 주찬미: 메인, 순위, 카테고리 페이지 구현</p>
              <p>조원 배상원: 소설 검색기능, 소설상세 페이지 구현</p>
              <p>조원 서승욱: 로그인/로그아웃 페이지, 마이/관리자 페이지 구현</p>
              <p>조원 박준태: 게시판 페이지 구현 및 배포</p>
            </Col>
            <Col>
            {/*  더 넣을 내용 있으면 여기다가!*/}
            </Col>
          </Row>
        </Container>

      </div>
  )
}

export default Footer;