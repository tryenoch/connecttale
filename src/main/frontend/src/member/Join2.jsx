import React, {useState} from 'react';
import '../static/css/style.css'
import JoinLogoTop1 from "./JoinLogoTop1";
import JoinLogoTop2 from "./JoinLogoTop2";
import JoinLogoTop3 from "./JoinLogoTop3";
import {Link, Route, Routes, useNavigate} from "react-router-dom";
import axios from "axios";

function Join2(props) {
    const [userId, setUserId] = useState("");
    const [pw, setPw] = useState("");
    const [name, setName] = useState("");
    const [nick, setNick] = useState("");
    const [gender, setGender] = useState(0);
    const [year, setYear] = useState("");
    const [month, setMonth] = useState("");
    const [day, setDay] = useState("");

    const navi = useNavigate();

    const changeId = (e) => {
        setUserId(e.target.value);
    }
    const changePw = (e) => {
        setPw(e.target.value);
    }
    const changeName = (e) => {
        setName(e.target.value);
    }
    const changeNick = (e) => {
        setNick(e.target.value);
    }
    const changeGender = (e) => {
        setGender(e.target.value);
    }
    const changeYear = (e) => {
        setYear(e.target.value);
    }
    const changeMonth = (e) => {
        setMonth(e.target.value);
    }
    const changeDay = (e) => {
        setDay(e.target.value);
    }

    const eventClickOK = () => {
        axios.post('/join/join2', null, {
            params: {
                id: userId,
                pw: pw,
                name: name,
                nickname: nick,
                gender: gender,
                birthday: year + month + day
            }
        })
            .then(res => {
                alert(`통신\n${res.data}`);
                navi('/join/join3');
            })
            .catch(() => {
                alert('failed');
                navi(-1);
            });
    }

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
                <div className={'col-sm-10'}>
                    <hr/>
                </div>
                <div className={'col-sm-1'}></div>
            </div>
            <form onSubmit={eventClickOK} method={'POST'}>
                <div className={'row mt-3'}>
                    <div className={'col-sm-10 ms-4 ms-auto'}>
                        <div className={'row'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>아이디</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="text" name={'id'} id={'id'} className={'input-s4 form-control rounded-1'}
                                       placeholder={'아이디를 입력하세요'} onChange={changeId}/>
                                <button type={'button'} id={'confirm'} className={'btn btn-pupple ms-3'}>중복확인</button>
                                <span id={'idCheckMessage'}></span>
                            </div>
                        </div>
                        <div className={'row mt-4'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>비밀번호</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="password" name={'pw'} id={'pw'}
                                       className={'input-s5 form-control rounded-1'} placeholder={'비밀번호를 입력하세요'}
                                       onChange={changePw}/>
                            </div>
                        </div>
                        <div className={'row mt-4'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>비밀번호 확인</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="password" name={'pw'} id={'cfPw'}
                                       className={'input-s5 form-control rounded-1'} placeholder={'한번 더 비밀번호를 입력하세요'}/>
                            </div>
                        </div>
                        <div className={'row mt-4'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>이름</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="text" name={'name'} id={'name'}
                                       className={'input-s5 form-control rounded-1'} placeholder={'이름을 입력하세요'}
                                       onChange={changeName}/>
                            </div>
                        </div>
                        <div className={'row mt-4'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>닉네임</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="text" name={'nick'} id={'nick'}
                                       className={'input-s5 form-control rounded-1'} placeholder={'닉네임을 입력하세요'}
                                       onChange={changeNick}/>
                            </div>
                        </div>
                        <div className={'row mt-4'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>생년월일</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="text" name={'year'} id={'y'} className={'input-s2 form-control rounded-1'}
                                       onChange={changeYear}/>
                                <label htmlFor="y" className={'ms-1 mt-2'}>년</label>
                                <input type="text" name={'month'} id={'m'}
                                       className={'input-s1 form-control rounded-1 ms-4'}
                                       onChange={changeMonth}/>
                                <label htmlFor="m" className={'ms-1 mt-2'}>월</label>
                                <input type="text" name={'day'} id={'d'}
                                       className={'input-s1 form-control rounded-1 ms-4'}
                                       onChange={changeDay}/>
                                <label htmlFor="d" className={'ms-1 mt-2'}>일</label>
                            </div>
                        </div>
                        <div className={'row mt-4'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>성별</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="radio" name={'gender'} id={'male'} value={1} onClick={changeGender}/>
                                <label htmlFor="male" className={'ms-1 mt-2 me-5'}>남성</label>
                                <input type="radio" name={'gender'} id={'female'} value={2} onClick={changeGender}/>
                                <label htmlFor="female" className={'ms-1 mt-2'}>여성</label>
                            </div>
                        </div>
                        <div className={'row mt-5 justify-content-end'}>
                            <div className={'col-sm-8'}></div>
                            <div className={'col-sm-4'}>
                                <button type={'submit'} className={'btn btn-pupple-inline ms-5'}>회원가입</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>

    )
}

export default Join2;