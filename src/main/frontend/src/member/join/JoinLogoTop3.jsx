import React from 'react';
import joinLogo1non from '../Logo/joinLogo1non.svg'
import joinLogo2non from '../Logo/joinLogo2non.svg'
import joinLogo3 from '../Logo/joinLogo3.svg'
import joinLogo1 from "../Logo/joinLogo1.svg";
import joinLogo3non from "../Logo/joinLogo3non.svg";

function JoinLogoTop3(props) {

    return (
      <div className={'row mb-3 justify-content-center'}>
        <div className={'col-sm-2 ps-0 d-grid justify-content-center text-center join-step'}>
          <span>STEP</span>
          <span>01</span>
          <div className={'d-grid justify-content-center'}>
            <img src={joinLogo1} alt="logo"/>
          </div>
          <span>회원가입</span>
        </div>
        <div className={'col-sm-2 mt-4 ps-0'}>
          <hr/>
        </div>
        <div className={'col-sm-2 d-grid ps-0 justify-content-center text-center text-secondary join-step'}>
          <span>STEP</span>
          <span>02</span>
          <div className={'d-grid justify-content-center'}>
            <img src={joinLogo2non} alt="logo" />
          </div>
          <span>정보입력</span>
        </div>
        <div className={'col-sm-2 mt-4 ps-0'}>
          <hr/>
        </div>
        <div className={'col-sm-2 ps-0 d-grid justify-content-center text-center text-secondary join-step active-step'}>
          <span>STEP</span>
          <span>03</span>
          <div className={'d-grid justify-content-center'}>
            <img src={joinLogo3non} alt="logo" />
          </div>
          <span>가입완료</span>
        </div>
      </div>
    )
}

export default JoinLogoTop3;