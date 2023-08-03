import React from 'react';
import joinLogo1 from "./joinLogo1non.svg";
import "../static/css/style.css"

function Join(props) {

    return (
        <div className={'container'}>
            <h1 className={'text-center'}>회원가입</h1>
            <div className={'row justify-content-center'}>
                <div className={'col-sm-10'}>
                    <div className={'row my-4'}>
                        <div className={'col-sm-4 d-grid justify-content-center'}>
                            <span className={'fs-6 text-secondary'}>STEP</span>
                            <span className={'text-center fs-6 text-secondary'}>01</span>
                            <img src={joinLogo1} alt="logo" className={'ImgSize text-secondary'} />
                            <span className={'text-secondary'}>회원가입</span>
                        </div>
                        <div className={'col-sm-4 d-flex justify-content-center'}>

                        </div>
                        <div className={'col-sm-4 d-flex justify-content-center'}>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Join;