import React from 'react';
import '../static/css/style.css'
import JoinLogoTop1 from "./JoinLogoTop1";
import JoinLogoTop2 from "./JoinLogoTop2";
import JoinLogoTop3 from "./JoinLogoTop3";

function Join2(props) {

    return (
        <div className={'container mt-5'}>
            <h1 className={'text-center'}>회원가입</h1>
            <div className={'row justify-content-center'}>
                <div className={'col-sm-10'}>
                    <div className={'row justify-content-center'}>
                        <div className={'col-sm-10'}>
                            <JoinLogoTop2/>
                        </div>
                    </div>
                    <div className={'row mt-4 ms-4 mb-2'}>
                        <h2>필수 입력 정보</h2>
                        <h6>아래의 내용을 입력해 주세요</h6>
                        <hr/>
                    </div>
                    <form action="" method={'POST'}>
                        <div className={'row'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>아이디</h4>
                            </div>
                            <div className={'col-sm-9 form-floating'}>
                                <input type="text" name={'id'} id={'id'} className={'form-control'}/>
                                <label htmlFor="{'id'}">아이디 입력</label>
                                <button type={'button'} id={'confirm'} className={'btn btn-pupple'}>중복확인</button>
                                <span id={'idCheckMessage'}></span>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default Join2;