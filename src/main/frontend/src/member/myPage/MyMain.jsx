import React, {useState} from 'react';
import {Link, Route, Routes} from "react-router-dom";

import LikeList from "./LikeList";
import MyContent from "./MyContent";
import MyQNA from "./MyQNA";
import MyComment from "./MyComment";
import Logo from "../Logo/joinLogo1.svg";


function MyMain(props) {
    const [page, setPage] = useState('like');
    const [member, setMember] = useState(JSON.parse(sessionStorage.getItem('member')))

    const selectComponent = {
        like: <LikeList/>,
        content: <MyContent/>,
        qna: <MyQNA/>,
        comment: <MyComment/>
    };
    return (
        <div>
            <div className={'row border rounded-1 p-1'}>
                <div className={'col-sm-3 bg-purple-light p-3 d-block box-board'}>
                    <div className={'row'}>
                        <div className={'col-sm'}>
                            <div className={'box-sm'}>
                                {
                                    sessionStorage.getItem("profile") !== null ?
                                        <img src={`/profile/${sessionStorage.getItem("profile")}`} alt="" id={'preview'}
                                             className={'img-fluid profile'}/>
                                        :
                                        <img src={require("../Logo/user.png").default} alt="" id={'preview'} className={'img-fluid profile'}/>
                                }
                            </div>
                        </div>
                        <div className={'col-sm'}>
                            <Link to={'/myPage/changeInfo'}>
                                <a href="" className={'fs-4 text-purple'}>{member.nickname}
                                    <i className="bi bi-chevron-right text-secondary opacity-50 fw-bold"></i>
                                </a>
                                <p className={'text-secondary opacity-50 fw-bolder'}>{sessionStorage.getItem('id')}</p>
                            </Link>
                        </div>
                    </div>
                </div>
                <div className={'col-sm-9 p-3 justify-content-around'}>
                    <button className={'btn btn-light-purple mini-block ms-1'} onClick={() => {
                        setPage('like');
                    }}>
                        <i className="bi bi-heart fs-button"></i><br/>
                        <p className={'fs-5'}>찜한 작품</p>
                    </button>
                    <button className={'btn btn-light-purple mini-block ms-2'} onClick={() => {
                        setPage('content');
                    }}>
                        <i className="bi bi-file-earmark-text fs-button"></i><br/>
                        <p className={'fs-5'}>나의 글</p>
                    </button>
                    <button className={'btn btn-light-purple mini-block ms-2'} onClick={() => {
                        setPage('qna');
                    }}>
                        <i className="bi bi-quora fs-button"></i><br/>
                        <p className={'fs-5'}>문의사항</p>
                    </button>
                    <button className={'btn btn-light-purple mini-block ms-2'} onClick={() => {
                        setPage('comment');
                    }}>
                        <i className="bi bi-chat-square-dots fs-button"></i><br/>
                        <p className={'fs-5'}>댓글내역</p>
                    </button>
                </div>
            </div>
            <div className={'row p-1 mt-3'}>
                {selectComponent[page]}
            </div>
        </div>
    )
}

export default MyMain;