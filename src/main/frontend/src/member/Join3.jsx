import React from 'react';
import JoinLogoTop3 from "./JoinLogoTop3";
import {Link} from "react-router-dom";

function Join3(props) {

    return (
        <div className={'col-sm-10'}>
            <div className={'row justify-content-center'}>
                <div className={'col-sm-10'}>
                    <JoinLogoTop3/>
                </div>
            </div>
            <div className={'my-5'}> <br/><br/></div>
            <div className={'row mt-4 mb-5 justify-content-center'}>
                <div className={'col-sm-10 text-center'}>
                    <h2>축하드립니다</h2>
                    <h6>회원가입이 완료되었습니다.</h6>
                </div>
            </div>
            <div className={'row justify-content-center ms-5'}>
                <div className={'col-sm-3'}>
                    <Link to={'/'}>
                        <button type={'button'} className={'btn btn-pupple-inline'}>메인페이지로 돌아가기</button>
                    </Link>
                </div>
            </div>
        </div>

    )
}

export default Join3;