import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Novel from "./novel/Novel";
import Board from "./board/Board";
import MyPage from "./member/MyPage";
import Join from "./member/Join";
import Login from "./member/Login";

function Main(props) {

    return (
        <BrowserRouter>
            <Routes>
                <Route index path={"/"} element={<Novel/>}/>
                <Route path={'/novel'} element={<Novel/>}/>
                <Route path={'/board'} element={<Board/>}/>
                <Route path={'/myPage'} element={<MyPage/>}/>
                <Route path={'/join'} element={<Join/>}/>
                <Route path={'/login'} element={<Login/>}/>
            </Routes>

        </BrowserRouter>
    )
}

export default Main;