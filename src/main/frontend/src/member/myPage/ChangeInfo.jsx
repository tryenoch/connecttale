import React, {useCallback, useRef, useState} from 'react';
import axios from "axios";
import {useNavigate} from "react-router-dom";
import User from "../Logo/user.png";


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
                } else {
                    setConfirm({confirm, nickConfirm: data.result});
                }
            })
            .catch();
    }

    const eventClickOK = (e) => {
        if ((nick === "" || confirm.nickConfirm === "이미 사용중인 닉네임입니다.") && pw === "") {
            setConfirm({confirm, nickConfirm: "닉네임을 다시 입력해주세요"});
            setConfirm({confirm, pwConfirm: "비밀번호를 입력하세요"});
            return;
        }
        if (nick !== "" && pw === "") {
            axios.post('/myPage/changeNick', null, {
                params: {
                    id: sessionStorage.getItem('id'),
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
        if (pw !== "" && nick === "") {
            if (pw !== confirmPw) {
                setConfirm({confirm, confirmPwConfirm: "비밀번호와 다릅니다."});
                return;
            }
            axios.post('/myPage/changePw', null, {
                params: {
                    id: sessionStorage.getItem('id'),
                    pw: pw,
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
        if (pw !== "" && pw === confirmPw && nick !== "" && confirm.nickConfirm !== "이미 사용중인 닉네임입니다.") {
            axios.post('/myPage/changeInfo', null, {
                params: {
                    id: sessionStorage.getItem('id'),
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
    }
    const reload = () => {
        axios.post(`/login/reload`, null, {
            params: {
                id: sessionStorage.getItem('id'),
            }
        })
            .then(res => {
                console.log(res.data);
                // 세션 저장 구현
                const member = res.data.userInfo;
                sessionStorage.setItem("member", JSON.stringify(member));
                sessionStorage.setItem("id", member.id);
                const year = new Date().getFullYear();
                const birth = Number(member.birthday.substring(0, 4));
                const adult = (year - birth) >= 19 ? 'Y' : 'N';
                sessionStorage.setItem("adult", adult);
                sessionStorage.setItem('grade', member.grade);
                navi('/');
            })
            .catch(err => {
                alert(err);
            });

    }

    const [selectedFile, setSelectedFile] = useState(null);
    const [imgFile, setImgFile] = useState("");
    const imgRef = useRef();

    const handleFileChange = (event) => {
        setSelectedFile(event.target.files[0]);
        const file = imgRef.current.files[0];
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = () => {
            setImgFile(reader.result);
        };
    };

    const handleFileUpload = async () => {
        if (selectedFile) {
            const formData = new FormData();
            formData.append('file', selectedFile);

            try {
                const response = await axios.post('/profile/upload', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                    params: {
                        id: sessionStorage.getItem('id')
                    }
                });

                sessionStorage.setItem("profile", response.data.sFile);
                console.log('File uploaded:', response.data.sFile);
                // 업로드 성공 시 처리
            } catch (error) {
                console.error('Error uploading file:', error);
                // 업로드 실패 시 처리
            }
        }
    };

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
                            <form encType={'multipart/form-data'} onSubmit={'upload'}>
                                <div className={'col-sm-3 mx-auto '}>
                                    <div className={'box'}>
                                        <label htmlFor="image-file" className={'w-100 h-100'}>
                                            {
                                                imgFile === null || imgFile === "" ?
                                                    sessionStorage.getItem("profile") === null ?
                                                        <img src={User} alt="" id={'preview'} className={'img-fluid profile bg-white'}/>
                                                        :
                                                        <img src={`/profile/${sessionStorage.getItem("profile")}`} alt="" id={'preview'} className={'img-fluid profile'}/>
                                                    :
                                                    <img src={imgFile} alt="" id={'preview'} className={'img-fluid profile'}/>
                                            }
                                        </label>
                                        <input type="file" name={'imageFile'} id={'image-file'} accept={'image/*'}
                                               className={'invisible'} onChange={handleFileChange} ref={imgRef}/>
                                    </div>
                                    <button type={'submit'} className={'btn btn-outline-purple mt-1 ms-4'}
                                            onClick={handleFileUpload}>사진 변경
                                    </button>
                                </div>
                            </form>
                        </div>
                        <div className={'row mt-5'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>아이디</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="text" name={'id'} id={'id'}
                                       className={'input-s5 form-control rounded-1'}
                                       value={sessionStorage.getItem('id')} disabled/>
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
                            <div className={'col-sm-9'}><span
                                className={'ms-2 mt-2 text-purple bold'}>{confirm.nickConfirm}</span></div>
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
                            <div className={'col-sm-9'}><span
                                className={'ms-2 mt-2 text-purple bold'}>{confirm.pwConfirm}</span></div>
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
                            <div className={'col-sm-9'}><span
                                className={'ms-2 mt-2 text-outline-purple bold'}>{confirm.confirmPwConfirm}</span></div>
                        </div>
                        <div className={'row mt-5 justify-content-end'}>
                            <div className={'col-sm-8'}></div>
                            <div className={'col-sm-4'}>
                                <button type={'button'} onClick={() => {
                                    navi(-1);
                                }}
                                        className={'btn btn-secondary ms-3'}>취소
                                </button>
                                <button type={'button'} onClick={() => {
                                    eventClickOK();
                                    reload();
                                }}
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