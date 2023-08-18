import React, {useEffect, useState} from 'react';
import {Link, useNavigate} from "react-router-dom";

function LoginButton(props) {

    const navi = useNavigate();
    const [button, setButton] = useState();

    useEffect(() => {
        if (sessionStorage.getItem('grade') == 2) {
            setButton(<Link to={'/staffPage'} className={'me-2'}>관리자페이지</Link>);
        } else {
            setButton(<Link to={'/myPage'} className={'me-2'}>마이페이지</Link>);
        }
    });
    const handleLogout = () => {
        sessionStorage.clear();
        navi('/novel');
    }

    if (sessionStorage.getItem('member') != null) {
        return (<div className={"d-inline logout"}>
            {button}
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