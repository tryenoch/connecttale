import React from 'react';
import '../static/css/style.css'
import JoinLogoTop2 from "./JoinLogoTop2";
import JoinLogoTop3 from "./JoinLogoTop3";
import JoinLogoTop1 from "./JoinLogoTop1";

function Join1(props) {

    return (
        <div className={'container mt-5'}>
            <h1 className={'text-center'}>회원가입</h1>
            <div className={'row justify-content-center'}>
                <div className={'col-sm-10'}>
                    <div className={'row justify-content-center'}>
                        <div className={'col-sm-10'}>
                            <JoinLogoTop1/>
                        </div>
                    </div>
                    <div className={'row mt-4 ms-4 mb-2'}>
                        <div className={'col-sm-10 ms-auto'}>
                            <h2>약관 동의</h2>
                        </div>
                    </div>
                    <div className={'row justify-content-center mt-5'}>
                        <button className={'btn btn-outline-primary block m-3'} type="button" id="upper14"
                                name="14YO">14세 이상<br/>
                            <i className="bi bi-person fs-button"></i>
                        </button>
                        <button className={'btn btn-outline-primary-low block m-3'} type="button" id="lower14"
                                name="14YO">14세 미만 <br/>
                            <i className="bi bi-people fs-button"></i>
                        </button>
                    </div>
                    <div className={'row mt-3 justify-content-center mt-5'}>
                        <div className={'col-sm-10'}>
                            <div className={'row justify-content-center'}>
                                <div className={'col-sm-8 ms-auto text-center'}>
                                    <input type="checkbox" id={'useOk'}/>
                                    <label htmlFor="useOk" className={'ms-2'}>
                                        <span className={'text-color-red'}>[필수]</span>
                                        개인정보 수집 및 이용동의
                                    </label>
                                </div>
                                <div className={'col-sm-4'}>
                                    <a href="#" className={'text-decoration-none ms-5'}>[전문보기]</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Join1;