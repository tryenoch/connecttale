import React from 'react';
import {Col, Container, Row} from "react-bootstrap";

function Footer(props) {
  return (
    <div>
      <Container fluid className={'pt-4 pb-5 footer'}>
        <Container>
          <Row className={'d-flex justify-content-center'}>
            <Col sm={9}>
              <div className={'foot-content'}>
                <div className={'foot-tit mb-3'}>
                  <p className={'logo'}>CONNECTTALE</p>
                  <p>Busan IT Education Center Fullstack 505</p>
                </div>
                <p>팀장 <span className={'m-name'}>주찬미</span> 메인, 순위, 카테고리 페이지 구현</p>
                <p>팀원 <span className={'m-name'}>배상원</span> 소설 검색기능, 소설상세 페이지 구현</p>
                <p>팀원 <span className={'m-name'}>서승욱</span> 로그인/로그아웃 페이지, 마이/관리자 페이지 구현</p>
                <p>팀원 <span className={'m-name'}>박준태</span> 게시판 페이지 구현 및 배포</p>
              </div>
            </Col>
            <Col>
            {/*  더 넣을 내용 있으면 여기다가!*/}
            </Col>
          </Row>
        </Container>
      </Container>

    </div>
  )
}

export default Footer;