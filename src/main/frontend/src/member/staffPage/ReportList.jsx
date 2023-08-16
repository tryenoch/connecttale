import React, {useEffect, useState} from 'react';
import {Col, Form, Row} from "react-bootstrap";
import {Link} from "react-router-dom";
import axios from "axios";

function ReportList(props) {

    const [nowPage, setNowPage] = useState(0);
    const [endPage, setEndPage] = useState(0);
    const [pages, setPages] = useState([1]);
    const [reportList, setReportList] = useState([{}]);

    useEffect(() => {
        setNowPage(props.defaultPage);
    }, [])

    useEffect(() => {
        requestData();
    }, [props.data, nowPage]);

    const requestData = () => {
        axios.get(`/staffPage/reportList?page=${nowPage}&size=10`)
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
                setReportList(res.data.reportList);
            })
            .catch(err => {
                alert(`통신에 실패했습니다. err : ${err}`);
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
                            reportList.map((report, index) => {
                                return (
                                    <tr key={index}>
                                        <td>{report.id}</td>
                                        <td>{report.name}</td>
                                        <td>{report.nickname}</td>
                                        <td>{report.birthday}</td>
                                        <td>{report.gender}</td>
                                        <td>
                                            {report.grade}
                                            <button>등업</button>
                                        </td>
                                        <td><button>삭제</button></td>
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

export default ReportList;