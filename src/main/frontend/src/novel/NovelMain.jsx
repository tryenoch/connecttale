import React from 'react';
import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import NovelTest from "./NovelTest";

function NovelMain(props) {

    return (
        <div>
          <h1>NovelMain</h1>
          <Link to={'/login'}>login</Link><br/>
          <Link to={'/join'}>join</Link><br/>
          <Link to={'/myPage'}>mypage</Link><br/>
          <Link to={'/board'}>board</Link><br/>
          <Link to={'/novelDetail'}>novel detail</Link><br />
          <Link to={'/novel/novelTest'}>novel Test</Link>

        </div>
    )
}

export default NovelMain;