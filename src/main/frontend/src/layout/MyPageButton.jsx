import React from 'react';
import {Link} from "react-router-dom";

function MyPageButton() {

    if (sessionStorage.getItem('grade') == 2) {
        return (<Link to={'/staffPage'} className={'me-2'}>관리자페이지</Link>);
    } else {
        return (<Link to={'/myPage'} className={'me-2'}>마이페이지</Link>);
    }
}

export default MyPageButton;