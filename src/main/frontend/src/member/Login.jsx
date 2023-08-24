import React, {useState} from 'react';
import '../static/css/style.css'
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";
import {Col} from "react-bootstrap";


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
                    sessionStorage.setItem('profile', member.sfile);
                    navi('/');
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
            <div className={'text-center logo'}>
                <h1>CONNECTTALE</h1>
            </div>
            <div className={'text-center'}>
                <p className={'login-info'}>로그인 정보를 입력해주세요</p>
            </div>
            <form method={'POST'} className={'login-form'}>
                <div className={'row justify-content-center'}>
                    <div className={'col-sm-5 d-flex'}>
                        <input type="text" name={'id'} id={'id'} className={' form-control rounded-1'} placeholder={'아이디를 입력하세요'} onChange={e => setId(e.target.value)}/>
                    </div>
                </div>
                <div className={'row justify-content-center'}>
                    <div className={'col-sm-5 d-flex mt-2'}>
                        <input type="password" name={'pw'} id={'pw'} className={'form-control rounded-1'} placeholder={'비밀번호를 입력하세요'} onChange={e => setPw(e.target.value)}/>
                    </div>
                </div>
                <Col className={'row mt-3 justify-content-center'}>
                    {/*<div className={'col-sm-3'}>
                        <button type={'button'} onClick={goBack} className={'btn btn-secondary input-s1'}>돌아가기
                        </button>
                    </div>*/}
                    <Col sm={5}>
                        <div className={'d-grid gap-2'}>
                            <button type={'button'} className={'btn btn-purple py-2'}
                                    onClick={login}>로그인
                            </button>
                        </div>
                        <div className={'join-link'}>
                            <Link to={'/join'}>
                                <span>회원 가입 하기</span>
                            </Link>
                        </div>
                    </Col>
                </Col>
            </form>
        </div>
    )
}

export default Login;