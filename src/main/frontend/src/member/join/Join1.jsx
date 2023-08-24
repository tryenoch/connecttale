import React from 'react';
import '../../static/css/style.css'
import JoinLogoTop1 from "./JoinLogoTop1";
import {Link, Route, Routes} from "react-router-dom";
import Join2 from "./Join2";

function Join1(props) {

    return (
        <div className={'col-sm-10'}>
            <div className={'row justify-content-center'}>
                <div className={'col-sm-10 ps-0'}>
                    <JoinLogoTop1/>
                </div>
            </div>
            <div className={'row mt-4 justify-content-center'}>
                <div className={'col-sm-8 join-sub-tit ps-0'}>
                    <h2>약관 동의</h2>
                </div>
            </div>
            <Link to={'/join/join2'}>
                <div className={'row justify-content-center mt-2'}>
                    <button className={'btn btn-outline-purple block m-3'}>
                        14세 이상<br/>
                        <i className="bi bi-person fs-button"></i>
                    </button>
                    <button className={'btn btn-light-purple block m-3'}>
                        14세 미만 <br/>
                        <i className="bi bi-people fs-button"></i>
                    </button>
                </div>
            </Link>
            <div className={'row mt-3 justify-content-center mt-3'}>
                <div className={'col-sm-6'}>
                    <div className={'d-flex justify-content-between'}>
                        <div className={''}>
                            <input type="checkbox" id={'useOk'}/>
                            <label htmlFor="useOk" className={'ms-2'}>
                                <span className={'text-color-red'}>[필수]</span>
                                개인정보 수집 및 이용동의
                            </label>
                        </div>
                        <div className={'me-3'}>
                            <Link href="#" className={'text-decoration-none'}>[전문보기]</Link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Join1;