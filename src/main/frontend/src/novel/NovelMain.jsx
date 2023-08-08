import React from 'react';
import {Link} from "react-router-dom";
import {Container} from "react-bootstrap";
import NovelMainTest from "./novelMain/NovelMainTest";

function NovelMain(props) {

    return (
      <Container fluid={"md"}>
        <div>
          <h1>NovelMain</h1>
          <Link to={'/login'}>login</Link><br/>
          <Link to={'/join'}>join</Link><br/>
          <Link to={'/myPage'}>mypage</Link><br/>
          <Link to={'/board'}>board</Link><br/>
          <Link to={'/novelDetail'}>novel detail</Link><br />
          <Link to={'/novel/novelTest'}>novel Test</Link>
        </div>
        <NovelMainTest />

      </Container>
    )
}

export default NovelMain;