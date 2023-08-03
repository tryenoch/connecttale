import React from 'react';
import '../static/css/style.css'
import JoinLogoTop1 from "./JoinLogoTop1";
import JoinLogoTop2 from "./JoinLogoTop2";
import JoinLogoTop3 from "./JoinLogoTop3";

function Join1(props) {

    return (
        <div className={'container'}>
            <h1 className={'text-center'}>회원가입</h1>
            <div className={'row justify-content-center'}>
                <div className={'col-sm-10'}>
                    <JoinLogoTop1/>
                    <JoinLogoTop2/>
                    <JoinLogoTop3/>
                </div>
            </div>
            <div>
                <input type="radio" />
            </div>
        </div>
    )
}

export default Join1;