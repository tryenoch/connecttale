import React, {useEffect, useState} from 'react';
import {Col, Form, Row} from "react-bootstrap";
import {Link} from "react-router-dom";
import axios from "axios";

function MemberList(props) {

    const [id, setId] = useState("");
    const [nowPage, setNowPage] = useState(0);
    const [endPage, setEndPage] = useState(0);
    const [pages, setPages] = useState([1]);
    const [memberList, setMemberList] = useState([{}]);

    useEffect(() => {
        setNowPage(props.defaultPage);
    }, [])

    useEffect(() => {
        requestData();
    }, [props.data, nowPage]);

    const requestData = () => {
        axios.get(`/staffPage/memberList?page=${nowPage}&size=10`)
            .then(res => {

                console.log(res.data);

                let offset = (Math.ceil(res.data.nowPage / 5) - 1) * 5 + 1;
                let arr = [];
                let lastNum = offset + 5;
                if (lastNum > Math.ceil(res.data.totalPages / 5)) {
                    lastNum = Math.ceil(res.data.totalPages / 5) + 1;
                }
                for (let i = offset; i <= lastNum; i++) {
                    arr.push(i);
                }
                setPages(arr);
                setEndPage(res.data.totalPages);
                setMemberList(res.data.memberList);
            })
            .catch(err => {
                alert(`통신에 실패했습니다. err : ${err}`);
            })

    }

    const levelUp = () => {
        axios.post('/staffPage/levelUp', null, {
            params: {
                id: id
            }
        })
            .then(res => {
                alert(res.data.result);
            })
            .catch(err => {
                alert("해당 회원의 등급변경이 실패하였습니다.");
            })
    }

    const deleteMember = () => {
        axios.post('/staffPage/deleteMember', null, {
            params: {
                id: id
            }
        })
            .then(res => {
                alert(res.data.result);
            })
            .catch(err => {
                alert("해당 회원의 계정이 정지되지 않았습니다.");
            })
    }

    const handleSubmit = (event) => {
        // alert(`검색어 : ${keyword}`);
        event.preventDefault();
    }


    return (
        <Row>
            <Col xs={10} className={'my-5 mx-auto'}>
                <Row className={'border-3 border-black border-bottom py-2'}>
                    <Col className={'ps-0'}><h3 className={'fw-bold'}>MemberList</h3></Col>
                </Row>
                <Row>
                    <table className={'text-center table'}>
                        <colgroup>
                            <col width={'14.5%'}/>
                            <col width={'14.5%'}/>
                            <col width={'14%'}/>
                            <col width={'14%'}/>
                            <col width={'14%'}/>
                            <col width={'14%'}/>
                            <col width={'14%'}/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>아이디</th>
                            <th>이름</th>
                            <th>닉네임</th>
                            <th>생일</th>
                            <th>성별</th>
                            <th>등급</th>
                            <th>삭제</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            memberList.map((member, index) => {
                                return (
                                    <tr key={index}>
                                        <td>{member.id}</td>
                                        <td>{member.name}</td>
                                        <td>{member.nickname}</td>
                                        <td>{member.birthday}</td>
                                        <td>{member.gender}</td>
                                        <td>
                                            {member.grade}
                                            <button className={'btn btn-mini btn-outline-purple px-2 ms-2'}
                                                    onClick={() => {
                                                        setId(member.id);
                                                        levelUp();
                                                    }}>등업
                                            </button>
                                        </td>
                                        <td>
                                            <button className={'btn btn-mini btn-outline-danger px-2'}
                                                    onClick={() => {
                                                        setId(member.id);
                                                        deleteMember();
                                                    }}>삭제
                                            </button>
                                        </td>
                                    </tr>
                                )
                            })
                        }
                        </tbody>
                    </table>
                    <div className={'d-flex justify-content-center mx-auto my-3 pages cursor'}>
                        <a
                            className={nowPage <= 0 ? 'text-black-50' : ''}
                            onClick={() => {
                                if (nowPage <= 0) {
                                    return null
                                }
                                return setNowPage(0)
                            }}
                        ><i className="bi bi-chevron-double-left"></i>
                        </a>
                        <a
                            className={nowPage <= 0 ? 'text-black-50' : ''}
                            onClick={() => {
                                if (nowPage <= 0) {
                                    return null
                                }
                                return setNowPage(nowPage - 1)
                            }}>
                            <i className="bi bi-chevron-left"></i>
                        </a>
                        {
                            pages.map((value) => {
                                return (
                                    <a
                                        key={value}
                                        className={nowPage === value - 1 ? 'text-black' : 'text-black-50'}
                                        onClick={() => setNowPage(value - 1)}
                                    >{value}</a>);
                            })
                        }
                        <a
                            className={nowPage >= endPage - 1 ? 'text-black-50' : ''}
                            onClick={() => {
                                if (nowPage >= endPage - 1) {
                                    return null
                                }
                                return setNowPage(nowPage + 1)
                            }}
                        ><i className="bi bi-chevron-right"></i>
                        </a>
                        <a
                            className={nowPage >= endPage - 1 ? 'text-black-50' : ''}
                            onClick={() => {
                                if (nowPage >= endPage - 1) {
                                    return null
                                }
                                return setNowPage(endPage - 1)
                            }}
                        ><i className="bi bi-chevron-double-right"></i>
                        </a>

                    </div>
                </Row>
            </Col>
        </Row>
    )
}

export default MemberList;