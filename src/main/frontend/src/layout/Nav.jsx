import React, {useState} from 'react';
import {Container, Navbar} from "react-bootstrap";
import {Link} from "react-router-dom";
import '../static/css/default.css';
import NovelSearchForm from "../novel/NovelSearchForm";
import LoginButton from "./LoginButton";

function Nav(props) {

  /* 로그인 관련 state 추후 설정*/
  // const [loginInfo, setLoginInfo] = setLoginInfo(false);
  const loginInfo = false;

  return (
    <Navbar expand={"md"} className={''}>
      <Container className={"py-1 nav-style board d-flex justify-content-between"}>
        <div className={'d-flex justify-content-start'}>
          {/* */}
          <Link to={'/novel'} className={'logo me-4 py-1'}>
            <span>CONNECTTALE</span>
            {/*<span>TALE</span>*/}
          </Link>
          <Link to={'/novelCate'} className={'menu me-3'}>순위</Link>
          <Link to={'/novelCate'} className={'menu me-1'}>카테고리</Link>
          {/* novel search form */}
          <NovelSearchForm />
        </div>
        <div className={''}>
          <Link to={'/board'} className={'me-3 menu'}>커뮤니티</Link>
          {/* 로그인 정보가 있을 때와 없을 때 다르게 나오도록 함 */}
          <LoginButton status={loginInfo}/>
        </div>
      </Container>
    </Navbar>
  )
}

export default Nav;