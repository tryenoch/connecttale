import React, {useState} from 'react';
import axios from "axios";
import {useNavigate} from "react-router-dom";
import JoinLogoTop2 from "./JoinLogoTop2";

function ChangeInfo(props) {
    const [confirm, setConfirm] = useState({
        pwConfirm: "", confirmPwConfirm: "", nickConfirm: ""
    });

    const [pw, setPw] = useState("");
    const [confirmPw, setConfirmPw] = useState("");
    const [nick, setNick] = useState("");

    const navi = useNavigate();

    const changePw = (e) => {
        setPw(e.target.value);
    }
    const changeConfirmPw = (e) => {
        setConfirmPw(e.target.value);
    }
    const changeNick = (e) => {
        setNick(e.target.value);
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
        if (nick === null || nick === "" || confirm.nickConfirm === "이미 사용중인 닉네임입니다.") {
            setConfirm({confirm, nickConfirm: "닉네임을 확인하세요"});
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
        axios.post('/myPage/changeInfo', null, {
            params: {
                id: 'test1',
                pw: pw,
                nickname: nick
            }
        })
            .then(() => {
                alert(`회원정보 수정 성공`);
                navi(-1);
            })
            .catch(() => {
                alert('failed');
            });
    }

    return (
        <div>

            <div className={'row mt-4 ms-4 mb-5'}>
                <div className={'col-sm-10 ms-auto'}>
                    <h2>회원정보 수정</h2>
                    <h6>아래의 내용을 입력해 주세요</h6>
                </div>
            </div>
            <div className={'row'}>
                <div className={'col-sm-1'}></div>
                <div className={'col-sm-11'}>
                    <hr/>
                </div>
            </div>
            {/*<form onSubmit={eventClickOK} method={'POST'}>*/}
            <form method={'POST'}>
                <div className={'row mt-3'}>
                    <div className={'col-sm-10 ms-4 ms-auto'}>
                        <div className={'row mt-2'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>아이디</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="text" name={'nick'} id={'nick'}
                                       className={'input-s5 form-control rounded-1'} value={'test1'} disabled/>
                            </div>
                        </div>
                        <div className={'row mt-4'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>닉네임</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="text" name={'nick'} id={'nick'}
                                       className={'input-s4 form-control rounded-1'} placeholder={'닉네임을 입력하세요'}
                                       onChange={changeNick}/>
                                <button type={'button'} id={'confirmNick'} className={'btn btn-outline-purple ms-2'}
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
                        <div className={'row mt-5 justify-content-end'}>
                            <div className={'col-sm-8'}></div>
                            <div className={'col-sm-4'}>
                                <button type={'button'} onClick={() => {navi(-1);}}
                                        className={'btn btn-secondary ms-3'}>취소
                                </button>
                                <button type={'button'} onClick={eventClickOK}
                                        className={'btn btn-purple ms-5'}>수정
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>

    )
}

export default ChangeInfo;