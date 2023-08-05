import React from 'react';
import '../static/css/style.css'
import JoinLogoTop1 from "./JoinLogoTop1";
import JoinLogoTop2 from "./JoinLogoTop2";
import JoinLogoTop3 from "./JoinLogoTop3";
import {Link, Route, Routes} from "react-router-dom";

function Join2(props) {

    return (
        <div className={'col-sm-10'}>
            <div className={'row justify-content-center'}>
                <div className={'col-sm-10'}>
                    <JoinLogoTop2/>
                </div>
            </div>
            <div className={'row mt-4 ms-4 mb-5'}>
                <div className={'col-sm-10 ms-auto'}>
                    <h2>필수 입력 정보</h2>
                    <h6>아래의 내용을 입력해 주세요</h6>
                </div>
            </div>
            <div className={'row'}>
                <div className={'col-sm-1'}></div>
                <div className={'col-sm-10'}><hr/></div>
                <div className={'col-sm-1'}></div>
            </div>
            <form action="" method={'POST'}>
                <div className={'row mt-3'}>
                    <div className={'col-sm-10 ms-4 ms-auto'}>
                        <div className={'row'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>아이디</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="text" name={'id'} id={'id'} className={'input-s4 form-control rounded-1'} placeholder={'아이디를 입력하세요'}/>
                                <button type={'button'} id={'confirm'} className={'btn btn-pupple ms-3'}>중복확인</button>
                                <span id={'idCheckMessage'}></span>
                            </div>
                        </div>
                        <div className={'row mt-4'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>비밀번호</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="password" name={'pw'} id={'pw'} className={'input-s5 form-control rounded-1'} placeholder={'비밀번호를 입력하세요'}/>
                            </div>
                        </div>
                        <div className={'row mt-4'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>비밀번호 확인</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="password" name={'pw'} id={'cfPw'} className={'input-s5 form-control rounded-1'} placeholder={'한번 더 비밀번호를 입력하세요'}/>
                            </div>
                        </div>
                        <div className={'row mt-4'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>이름</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="text" name={'name'} id={'name'} className={'input-s5 form-control rounded-1'} placeholder={'이름을 입력하세요'}/>
                            </div>
                        </div>
                        <div className={'row mt-4'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>닉네임</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="text" name={'nick'} id={'nick'} className={'input-s5 form-control rounded-1'} placeholder={'닉네임을 입력하세요'}/>
                            </div>
                        </div>
                        <div className={'row mt-4'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>생년월일</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="text" name={'year'} id={'y'} className={'input-s2 form-control rounded-1'}/>
                                <label htmlFor="y" className={'ms-1 mt-2'}>년</label>
                                <input type="text" name={'month'} id={'m'} className={'input-s1 form-control rounded-1 ms-4'}/>
                                <label htmlFor="m" className={'ms-1 mt-2'}>월</label>
                                <input type="text" name={'day'} id={'d'} className={'input-s1 form-control rounded-1 ms-4'}/>
                                <label htmlFor="d" className={'ms-1 mt-2'}>일</label>
                            </div>
                        </div>
                        <div className={'row mt-4'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>성별</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="radio" name={'gender'} id={'male'} value={1}/>
                                <label htmlFor="male" className={'ms-1 mt-2 me-5'}>남성</label>
                                <input type="radio" name={'gender'} id={'female'} value={2}/>
                                <label htmlFor="female" className={'ms-1 mt-2'}>여성</label>
                            </div>
                        </div>
                        <div className={'row mt-5 justify-content-end'}>
                            <div className={'col-sm-8'}></div>
                            <div className={'col-sm-4'}>
                                <Link to={'/join/join3'}>
                                    <button type={'submit'} className={'btn btn-pupple-inline ms-5'}>회원가입</button>
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>

    )
}

export default Join2;