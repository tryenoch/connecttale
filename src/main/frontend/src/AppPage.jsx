import React from 'react';
import Header from "./layout/Header";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Novel from "./novel/Novel";
import NovelCate from "./novel/NovelCate";
import NovelDetail from "./novel/NovelDetail";
import NovelSearch from "./novel/NovelSearch";
import Board from "./board/Board";
import MyPage from "./member/MyPage";
import Join from "./member/Join";
import Login from "./member/Login";
import Footer from "./layout/Footer";
import Main from "./Main";
import {Container} from "react-bootstrap";

function AppPage(props) {
  return (
    <BrowserRouter>
      <Header />
      <Container fluid={'md'}>
        <Routes>
          <Route index path={"/"} element={<Main />}/>
          <Route path={'/novel'} element={<Novel/>}/>
          <Route path={'/novelCate'} element={<NovelCate/>}/>
          <Route path={'/novelDetail'} element={<NovelDetail/>}/>
          <Route path={'/novelSearch'} element={<NovelSearch/>}/>
          <Route path={'/board'} element={<Board/>}/>
          <Route path={'/myPage'} element={<MyPage/>}/>
          <Route path={'/join'} element={<Join/>}/>
          <Route path={'/login'} element={<Login/>}/>
        </Routes>
      </Container>
      <Footer />
    </BrowserRouter>
  )
}

export default AppPage;