import React, {useEffect, useState} from 'react';
import {Link, useNavigate} from "react-router-dom";
import MyPageButton from "./MyPageButton";

function LoginButton(props) {

    const navi = useNavigate();

    const handleLogout = () => {
        sessionStorage.clear();
        navi('/novel');
    }

    if (sessionStorage.getItem('member') != null) {
        return (<div className={"d-inline logout"}>
            <MyPageButton/>
            |
            <Link to={'/logout'} className={'ms-2 round'} onClick={handleLogout}>로그아웃</Link>
        </div>)
    } else {
        return (<div className={"d-inline"}>
            <Link to={'/login'} className={'btn btn-purple round'}>로그인</Link>
        </div>)
    }
}

export default LoginButton;