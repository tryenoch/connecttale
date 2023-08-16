import React, {useState} from 'react';
import {Link, Route, Routes} from "react-router-dom";
import MemberList from "./MemberList";
import ReportList from "./ReportList";

function StaffMain(props) {
    const [page, setPage] = useState('member');

    const selectComponent = {
        member: <MemberList/>,
        report: <ReportList/>
    };
    return (
        <div>
            <div className={'row border rounded-1 p-1'}>
                <div className={'col-sm-3 bg-purple-light p-3 grid box-board'}>
                    <p href="" className={'fs-4 text-purple'}>닉네임
                        <i className="bi bi-chevron-right text-secondary opacity-50 fw-bold"></i>
                    </p>
                    <p className={'text-secondary opacity-50 fw-bolder'}>아이디</p>
                    <div className={'row justify-content-end mt-5'}>
                        <div className={'col-sm-7'}>
                            <button className={'btn btn-outline-purple'}>로그아웃</button>
                        </div>
                    </div>
                </div>
                <div className={'col-sm-9 p-3'}>
                    <div className={'row justify-content-between'}>
                        <div className={'col-sm d-grid justify-content-center'}>
                            <button className={'btn btn-light-purple mini-block'}
                                    onClick={() => {setPage('member');}}>
                                <i className="bi bi-heart fs-button"></i><br/>
                                <p className={'fs-5'}>회원 관리</p>
                            </button>
                        </div>
                        <div className={'col-sm d-grid justify-content-center'}>
                            <button className={'btn btn-light-purple mini-block'}
                                    onClick={() => {setPage('report');}}>
                                <i className="bi bi-file-earmark-text fs-button"></i><br/>
                                <p className={'fs-5'}>신고 목록</p>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div className={'row p-1 mt-3'}>
                {selectComponent[page]}
            </div>
        </div>
    )
}

export default StaffMain;