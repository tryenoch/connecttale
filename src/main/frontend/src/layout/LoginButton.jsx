import React from 'react';
import {Link} from "react-router-dom";

function LoginButton(props) {
  
  const loginInfo = props.status;
  
  return (
    <div className={"d-inline"}>
      {
        loginInfo?
        /* 로그인 정보가 있을 경우 적용 */

          <div className={"d-inline logout"}>
            <Link to={'/myPage'} className={'me-2'}>마이페이지</Link>
            |
            <Link to={'/logout'} className={'ms-2 round'}>로그아웃</Link>
          </div>
        :
          /* 로그인 정보가 없을 경우 적용 */
          <Link to={'/login'} className={'btn btn-purple round'}>로그인</Link>
      }
      
    </div>
  )
}

export default LoginButton;