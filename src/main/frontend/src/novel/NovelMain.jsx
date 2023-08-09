import React from 'react';
import {Link} from "react-router-dom";
import {Container} from "react-bootstrap";
import NovelMainTest from "./novelMain/NovelMainTest";
import NovelMainRank from "./novelMain/rank/NovelMainRank";

function NovelMain(props) {

    return (
      <div>
        <div>
          <h1>NovelMain</h1>
          <Link to={'/login'}>login</Link><br/>
          <Link to={'/join'}>join</Link><br/>
          <Link to={'/myPage'}>mypage</Link><br/>
          <Link to={'/board'}>board</Link><br/>
          <Link to={'/novelDetail'}>novel detail</Link><br />
          <Link to={'/novel/novelMainTest'}>novel Test</Link>
        </div>
        <NovelMainTest />
        <NovelMainRank />
      </div>
    )
}

export default NovelMain;