import React from 'react';
import {Link, Route, Routes} from "react-router-dom";

function MyMain(props) {

    return (
        <div>
            <div className={'row border rounded-1 p-1'}>
                <div className={'col-sm-3 bg-purple-light p-3 grid box-board'}>
                    <Link to={'/myPage/changeInfo'}>
                        <a href="" className={'fs-4 text-purple'}>닉네임<i className="bi bi-chevron-right text-secondary opacity-50 fw-bold"></i></a>
                        <p className={'text-secondary opacity-50 fw-bolder'}>아이디</p>
                    </Link>
                    <div className={'row justify-content-end'}>
                        <div className={'col-sm-7'}><button className={'btn btn-outline-purple'}>로그아웃</button></div>
                    </div>
                </div>
                <div className={'col-sm-9 p-3'}>
                    <p>내가남긴 글/문의사항/댓글내역</p>
                </div>
            </div>
            <div className={'row p-1'}><p>찜한작품 내역</p></div>
        </div>
    )
}

export default MyMain;