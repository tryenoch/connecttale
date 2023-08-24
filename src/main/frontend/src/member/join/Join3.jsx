import React from 'react';
import JoinLogoTop3 from "./JoinLogoTop3";
import {Link} from "react-router-dom";

function Join3(props) {

    return (
        <div className={'col-sm-10'}>
            <div className={'row justify-content-center'}>
                <div className={'col-sm-10 ps-0'}>
                    <JoinLogoTop3/>
                </div>
            </div>
            <div className={'my-5 pb-2'}></div>
            <div className={'row mt-5 mb-5 justify-content-center'}>
                <div className={'col-sm-10 text-center ps-0 join-sub-tit'}>
                    <h1>축하드립니다</h1>
                    <h5>회원가입이 완료되었습니다.</h5>
                </div>
            </div>
            <div className={'row justify-content-center'}>
                <div className={'col-sm-3 ps-0 text-center'}>
                    <Link to={'/'}>
                        <button type={'button'} className={'btn btn-outline-purple purple-round'}>메인페이지로 돌아가기</button>
                    </Link>
                </div>
            </div>
        </div>

    )
}

export default Join3;