import React from 'react';
import joinLogo1 from "./joinLogo1.svg";
import "../static/css/style.css"

function Join(props) {

    return (
        <div className={'container'}>
            <h1 className={'text-center'}>회원가입</h1>
            <div className={'row justify-content-center'}>
                <div className={'col-sm-10'}>
                    <div className={'row my-4'}>
                        <div className={'col-sm-3 d-grid justify-content-center'}>
                            <span>STEP</span>
                            <img src={joinLogo1} alt="logo" className={'ImgSize'} />
                        </div>
                        <div className={'col-sm-3 d-flex justify-content-center'}>

                        </div>
                        <div className={'col-sm-3 d-flex justify-content-center'}>

                        </div>
                        <div className={'col-sm-3 d-flex justify-content-center'}>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Join;