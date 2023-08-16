import React, {useState} from 'react';
import '../../static/css/style.css'
import JoinLogoTop1 from "./JoinLogoTop1";
import JoinLogoTop2 from "./JoinLogoTop2";
import JoinLogoTop3 from "./JoinLogoTop3";
import {Link, Route, Routes, useNavigate} from "react-router-dom";
import axios from "axios";

function Join2(props) {

    const [confirm, setConfirm] = useState({
        idConfirm: "", pwConfirm: "", confirmPwConfirm: "", nameConfirm: "", nickConfirm: "", genderConfirm: "", birthConfirm: ""
    });

    const [userId, setUserId] = useState("");
    const [pw, setPw] = useState("");
    const [confirmPw, setConfirmPw] = useState("");
    const [name, setName] = useState("");
    const [nick, setNick] = useState("");
    const [gender, setGender] = useState(0);
    const [year, setYear] = useState("");
    const [mon, setMon] = useState("");
    const [day, setDay] = useState("");

    const navi = useNavigate();

    const changeId = (e) => {
        setUserId(e.target.value);
    }
    const changePw = (e) => {
        setPw(e.target.value);
    }
    const changeConfirmPw = (e) => {
        setConfirmPw(e.target.value);
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
    const changeMon = (e) => {
        setMon(e.target.value);
    }
    const changeDay = (e) => {
        setDay(e.target.value);
    }
    const confirmId = () => {
        axios.get('/join/join2', {
            params: {
                id: userId
            }
        })
            .then(res => {
                const data = res.data;
                if (userId === null || userId === "") {
                    setConfirm({confirm, idConfirm: "아이디를 입력해 주세요"});
                }
                else {
                    setConfirm({confirm, idConfirm: data.result});
                }
            })
            .catch();
    }

    const confirmNick = () => {
        axios.patch('/join/join2', null, {
            params: {
                nickname: nick
            }
        })
            .then(res => {
                const data = res.data;
                if (nick === null || nick === "") {
                    setConfirm({confirm, nickConfirm: "닉네임을 입력해 주세요"});
                }
                else {
                    setConfirm({confirm, nickConfirm: data.result});
                }
            })
            .catch();
    }

    const eventClickOK = () => {
        if (userId === null || userId === "" || confirm.idConfirm === "이미 사용중인 ID 입니다." ) {
            setConfirm({confirm, idConfirm: "아이디를 다시 입력하세요"});
            return;
        }
        if (nick === null || nick === "" || confirm.nickConfirm === "이미 사용중인 닉네임입니다.") {
            setConfirm({confirm, nickConfirm: "닉네임을 입력하세요"});
            return;
        }
        if (pw === null || pw === "") {
            setConfirm({confirm, pwConfirm: "비밀번호를 입력하세요"});
            return;
        }
        if (pw !== confirmPw) {
            setConfirm({confirm, confirmPwConfirm: "비밀번호와 다릅니다."});
            return;
        }
        if (name === null || name === "") {
            setConfirm({confirm, nameConfirm: "이름을 입력하세요"});
            return;
        }
        if (name === null || name === "") {
            setConfirm({confirm, nameConfirm: "이름을 입력하세요"});
            return;
        }
        if (year === null || year === "" || mon === null || mon === "" || day === null || day === "") {
            setConfirm({confirm, birthConfirm: "생년월일을 입력하세요"});
            return;
        }
        if (gender === null || gender === "" || gender === 0) {
            setConfirm({confirm, genderConfirm: "성별을 선택하세요"});
            return;
        }
        axios.post('/join/join2', null, {
            params: {
                id: userId,
                pw: pw,
                name: name,
                nickname: nick,
                gender: gender,
                year: year,
                mon: mon,
                day: day
            }
        })
            .then(() => {
                alert(`통신성공`);
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
            {/*<form onSubmit={eventClickOK} method={'POST'}>*/}
            <form method={'POST'}>
                <div className={'row mt-3'}>
                    <div className={'col-sm-10 ms-4 ms-auto'}>
                        <div className={'row'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>아이디</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="text" name={'id'} id={'id'} className={'input-s4 form-control rounded-1'}
                                       placeholder={'아이디를 입력하세요'} onChange={changeId}/>
                                <button type={'button'} id={'confirmId'} className={'btn btn-outline-purple ms-3'}
                                        onClick={confirmId}>중복확인
                                </button>
                            </div>
                            <div className={'col-sm-3'}></div>
                            <div className={'col-sm-9'}><span className={'ms-2 mt-2 text-purple bold'}>{confirm.idConfirm}</span></div>
                        </div>
                        <div className={'row mt-2'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>닉네임</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="text" name={'nick'} id={'nick'}
                                       className={'input-s4 form-control rounded-1'} placeholder={'닉네임을 입력하세요'}
                                       onChange={changeNick}/>
                                <button type={'button'} id={'confirmNick'} className={'btn btn-outline-purple ms-3'}
                                        onClick={confirmNick}>중복확인
                                </button>
                            </div>
                            <div className={'col-sm-3'}></div>
                            <div className={'col-sm-9'}><span className={'ms-2 mt-2 text-purple bold'}>{confirm.nickConfirm}</span></div>
                        </div>
                        <div className={'row mt-2'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>비밀번호</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="password" name={'pw'} id={'pw'}
                                       className={'input-s5 form-control rounded-1'} placeholder={'비밀번호를 입력하세요'}
                                       onChange={changePw}/>
                            </div>
                            <div className={'col-sm-3'}></div>
                            <div className={'col-sm-9'}><span className={'ms-2 mt-2 text-purple bold'}>{confirm.pwConfirm}</span></div>
                        </div>
                        <div className={'row mt-2'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>비밀번호 확인</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="password" name={'pw'} id={'cfPw'}
                                       className={'input-s5 form-control rounded-1'} placeholder={'한번 더 비밀번호를 입력하세요'}
                                       onChange={changeConfirmPw}/>
                            </div>
                            <div className={'col-sm-3'}></div>
                            <div className={'col-sm-9'}><span className={'ms-2 mt-2 text-outline-purple bold'}>{confirm.confirmPwConfirm}</span></div>
                        </div>
                        <div className={'row mt-2'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>이름</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="text" name={'name'} id={'name'}
                                       className={'input-s5 form-control rounded-1'} placeholder={'이름을 입력하세요'}
                                       onChange={changeName}/>
                            </div>
                            <div className={'col-sm-3'}></div>
                            <div className={'col-sm-9'}><span className={'ms-2 mt-2 text-outline-purple bold'}>{confirm.nameConfirm}</span></div>
                        </div>
                        <div className={'row mt-4'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>생년월일</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="number" name={'birth'} id={'yy'} min={1950} max={9999}
                                       className={'input-s2 form-control rounded-1'}
                                       onChange={changeYear}/>
                                <span className={'mt-2 ms-2'}>년</span>
                                <input type="number" name={'birth'} id={'mm'} min={1} max={12} minLength={2}
                                       className={'input-s1 form-control rounded-1 ms-3'}
                                       onChange={changeMon}/>
                                <span className={'mt-2 ms-2'}>월</span>
                                <input type="number" name={'birth'} id={'dd'} min={1} max={31} minLength={2}
                                       className={'input-s1 form-control rounded-1 ms-3'}
                                       onChange={changeDay}/>
                                <span className={'mt-2 ms-2'}>일</span>
                            </div>
                            <div className={'col-sm-3'}></div>
                            <div className={'col-sm-9'}><span className={'ms-2 mt-2 text-purple bold'}>{confirm.birthConfirm}</span></div>
                        </div>
                        <div className={'row mt-2'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>성별</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="radio" name={'gender'} id={'male'} value={1} onChange={changeGender}/>
                                <label htmlFor="male" className={'ms-1 mt-2 me-5'}>남성</label>
                                <input type="radio" name={'gender'} id={'female'} value={2} onChange={changeGender}/>
                                <label htmlFor="female" className={'ms-1 mt-2'}>여성</label>
                            </div>
                            <div className={'col-sm-3'}></div>
                            <div className={'col-sm-9'}><span className={'ms-2 mt-2 text-purple bold'}>{confirm.genderConfirm}</span></div>
                        </div>
                        <div className={'row mt-5 justify-content-end'}>
                            <div className={'col-sm-8'}></div>
                            <div className={'col-sm-4'}>
                                <button type={'button'} onClick={eventClickOK}
                                        className={'btn btn-purple ms-5'}>회원가입
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>

    )
}

export default Join2;