import React from 'react';
import joinLogo1non from './Logo/joinLogo1non.svg'
import joinLogo2 from './Logo/joinLogo2.svg'
import joinLogo3non from './Logo/joinLogo3non.svg'

function JoinLogoTop2(props) {

    return (
        <div className={'row my-4 justify-content-center'}>
            <div className={'col-sm-2 d-grid justify-content-center text-center text-secondary'}>
                <span className={'fs-7'}>STEP</span>
                <span className={'fs-6'}>01</span>
                <div className={'d-grid justify-content-center'}>
                    <img src={joinLogo1non} alt="logo"/>
                </div>
                <span className={'fs-7'}>회원가입</span>
            </div>
            <div className={'col-sm-2 mt-4'}>
                <hr/>
            </div>
            <div className={'col-sm-2 d-grid justify-content-center text-center fw-semibold'}>
                <span className={'fs-7'}>STEP</span>
                <span className={'fs-6'}>02</span>
                <div className={'d-grid justify-content-center'}>
                    <img src={joinLogo2} alt="logo" />
                </div>
                <span className={'fs-7'}>정보입력</span>
            </div>
            <div className={'col-sm-2 mt-4'}>
                <hr/>
            </div>
            <div className={'col-sm-2 d-grid justify-content-center text-center text-secondary'}>
                <span className={'fs-7'}>STEP</span>
                <span className={'fs-6'}>03</span>
                <div className={'d-grid justify-content-center'}>
                    <img src={joinLogo3non} alt="logo" />
                </div>
                <span className={'fs-7'}>가입완료</span>
            </div>
        </div>
    )
}

export default JoinLogoTop2;