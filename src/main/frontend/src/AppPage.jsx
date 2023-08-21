import React from 'react';
import Header from "./layout/Header";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Novel from "./novel/Novel";
import NovelCate from "./novel/NovelCate";
import NovelDetail from "./novel/NovelDetail";
import NovelSearch from "./novel/NovelSearch";
import Board from "./board/Board";
import MyPage from "./member/myPage/MyPage";
import Join from "./member/join/Join";
import Login from "./member/Login";
import Footer from "./layout/Footer";
import Main from "./Main";
import {Container} from "react-bootstrap";
import StaffPage from "./member/staffPage/StaffPage";
import NovelDetail2 from "./novel/NovelDetail2";

function AppPage(props) {
  return (
    <div>
      <Header />
      <Container fluid={'md'} className={"mx-auto"}>
        <Routes>
          <Route index path={"/*"} element={<Main />}/>
          <Route path={'/novel/*'} element={<Novel/>}/>
          <Route path={'/novelCate'} element={<NovelCate/>}/>
          <Route path={'/novelDetail/*'} element={<NovelDetail2/>}/>
          <Route path={'/novelSearch'} element={<NovelSearch/>}/>
          <Route path={'/board/*'} element={<Board/>}/>
          <Route path={'/myPage/*'} element={<MyPage/>}/>
          <Route path={'/staffPage/*'} element={<StaffPage/>}/>
          <Route path={'/join/*'} element={<Join/>}/>
          <Route path={'/login'} element={<Login/>}/>
        </Routes>
      </Container>
      <Footer />
    </div>
  )
}

export default AppPage;