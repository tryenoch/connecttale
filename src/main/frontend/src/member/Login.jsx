import React, {useState} from 'react';
import '../static/css/style.css'
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";


function Login(props) {
    const [id, setId] = useState('');
    const [pw, setPw] = useState("");

    const navi = useNavigate();

    const login = (e) => {
        e.preventDefault();

        axios.post(`/login/process`, null, {
            params: {
                id: id,
                pw: pw
            }
        })
            .then(res => {
                console.log(res.data);
                if (res.data.user) {
                    // 세션 저장 구현
                    const member = res.data.userInfo;
                    sessionStorage.setItem("member", JSON.stringify(member));
                    sessionStorage.setItem("id", member.id);
                    const year = new Date().getFullYear();
                    const birth = Number(member.birthday.substring(0, 4));
                    const adult = (year - birth) >= 19 ? 'Y' : 'N';
                    sessionStorage.setItem("adult",adult);
                    sessionStorage.setItem('grade', member.grade);
                    navi('/novel');
                } else {
                    alert('존재하지 않는 사용자 또는 잘못된 비밀번호입니다.');
                }

            })
            .catch(err => {
                alert(err);
            });
    }

    const goBack = () => {
        navi(-1);
    };

    return (
        <div className={'container'}>
            <h1>login</h1>
            <div className={'my-5'}><br/><br/></div>
            <form method={'POST'}>
                <div className={'row my-5 justify-content-center'}>
                    <div className={'col-sm-6'}>
                        <div className={'row justify-content-center'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>아이디</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="text" name={'id'} id={'id'} className={'input-s3 form-control rounded-1'}
                                       placeholder={'아이디를 입력하세요'} onChange={e => setId(e.target.value)}/>

                            </div>
                        </div>
                        <div className={'row my-4'}>
                            <div className={'col-sm-3'}>
                                <h4 className={'fw-bold'}>비밀번호</h4>
                            </div>
                            <div className={'col-sm-9 d-flex'}>
                                <input type="password" name={'pw'} id={'pw'}
                                       className={'input-s3 form-control rounded-1'}
                                       placeholder={'비밀번호를 입력하세요'} onChange={e => setPw(e.target.value)}/>
                            </div>
                        </div>
                        <div className={'row ms-5 mt-5'}>
                            <div className={'col-sm-3'}>
                                <button type={'button'} onClick={goBack} className={'btn btn-secondary input-s1'}>돌아가기
                                </button>
                            </div>
                            <div className={'col-sm-3 ms-1'}>
                                <Link to={'/join'}>
                                    <button type={'button'} className={'btn btn-outline-purple input-s1'}>회원가입</button>
                                </Link>
                            </div>
                            <div className={'col-sm-3 ms-1'}>
                                <button type={'button'} className={'btn btn-purple input-s1'}
                                        onClick={login}>로그인
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default Login;