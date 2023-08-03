import React from 'react';
import joinLogo1non from "./joinLogo1non.svg";
import JoinLogo from "./JoinLogo1";
import joinLogo1 from "./joinLogo1.svg";

function Join(props) {

    return (
        <div className={'container'}>
            <h1 className={'text-center'}>회원가입</h1>
            <div className={'row justify-content-center'}>
                <div className={'col-sm-10'}>
                    <div className={'row my-4'}>
                        <div className={'col-sm-4 d-grid justify-content-center'}>
                            <span className={'text-center fs-6'}>STEP</span>
                            <span className={'text-center fs-6'}>01</span>
                            <img src={joinLogo1} alt="logo" className={'ImgSize'}/>
                            <span className={'text-center fs-7'}>회원가입</span>
                        </div>
                        <div className={'col-sm-4 d-grid justify-content-center'}>
                            <span className={'text-center fs-6 text-secondary'}>STEP</span>
                            <span className={'text-center fs-6 text-secondary'}>02</span>
                            <img src={joinLogo1non} alt="logo" className={'ImgSize'} />
                            <span className={'text-center text-secondary fs-7'}>정보입력</span>
                        </div>
                        <div className={'col-sm-4 d-grid justify-content-center'}>
                            <span className={'text-center fs-6 text-secondary'}>STEP</span>
                            <span className={'text-center fs-6 text-secondary'}>03</span>
                            <img src={joinLogo1non} alt="logo" className={'ImgSize'} />
                            <span className={'text-center text-secondary fs-7'}>가입완료</span>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    )
}

export default Join;