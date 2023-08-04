import React from 'react';
import {Link} from "react-router-dom";

function login(props) {

  const goBack = () => {
    alert('확인용알림창');
  };

  return (<div className={'container'}>
    <h1>login</h1>
    <div className={'my-5'}><br/><br/></div>
    <div className={'row my-5 justify-content-center'}>
      <div className={'col-sm-6'}>
        <form action="" method={'POST'}>
          <div className={'row'}>
            <div className={'col-sm-3'}>
              <h4 className={'fw-bold'}>아이디</h4>
            </div>
            <div className={'col-sm-9 d-flex'}>
              <input type="text" name={'id'} id={'id'} className={'input-s3 form-control rounded-1'} placeholder={'아이디를 입력하세요'}/>
            </div>
          </div>
          <div className={'row my-4'}>
            <div className={'col-sm-3'}>
              <h4 className={'fw-bold'}>비밀번호</h4>
            </div>
            <div className={'col-sm-9 d-flex'}>
              <input type="password" name={'pw'} id={'pw'} className={'input-s3 form-control rounded-1'} placeholder={'비밀번호를 입력하세요'}/>
            </div>
          </div>
          <div className={'row justify-content-center ms-5 mt-5'}>
            <div className={'col-sm-3'}>
              <button onClick={goBack} className={'btn btn-secondary input-s1'}>돌아가기</button>
            </div>
            <div className={'col-sm-3 ms-2'}>
              <Link to={'/'}>
                <button type={'button'} className={'btn btn-pupple-inline input-s1'}>로그인</button>
              </Link>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>)
}

export default login;