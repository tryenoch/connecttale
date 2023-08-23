import React, {useEffect, useState} from 'react';
import {Col, Form, Row} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";
import {fetchData} from "../../common/NovelDetailFetch2";
function MyContent(props) {
    const [keyword, setKeyword] = useState('');
    // 카테고리 변경 시 페이지 번호가 0으로 초기화 x
    const [nowPage, setNowPage] = useState(0);
    const [endPage, setEndPage] = useState(0);
    const [pages, setPages] = useState([1]);
    const [replyList, setReplyList] = useState([{}]);
    const navi = useNavigate();

    useEffect(() => {
        setNowPage(props.defaultPage);
    }, [])

    useEffect(() => {
        requestData();
    }, [props.data, nowPage]);

    const requestData = () => {
        axios.get(`/myPage/myComment?id=${sessionStorage.getItem('id')}&page=${nowPage}&size=10`)
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
                setReplyList(res.data.replyList);
                console.log(res.data.replyList);
            })
            .catch(err => {
                alert(`통신에 실패했습니다. err : ${err}`);
            })
    }

    const handleLinkClick = async (novel) => {
        console.log(novel);
        try {
            const novelDetail = await fetchData(novel.novelThumbnail, novel.novelTitle, novel.ebookCheck, novel.novelIdx, novel.novelAdult);
            navi(`/novelDetail/${novel.novelTitle}`, {
                state: {
                    novelDetail: novelDetail,
                }
            });
        } catch (error) {
            console.log(error.message);
        }
    };


    return (
        <Row>
            <Col xs={10} className={'my-5 mx-auto'}>
                <Row className={'border-3 border-black border-bottom py-2'}>
                    <Col className={'ps-0'}><h3 className={'fw-bold'}>MyQnA</h3></Col>
                </Row>
                <Row>
                    <table className={'text-center table'}>
                        <colgroup>
                            <col width={'8%'}/>
                            <col width={'30%'}/>
                            <col width={'40%'}/>
                            <col width={'10%'}/>
                            <col width={'12%'}/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>번호</th>
                            <th>소설 제목</th>
                            <th>댓글 내용</th>
                            <th>작성자</th>
                            <th>날짜</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            replyList.map((reply, index) => {
                                console.log(reply);
                                return (
                                    <tr key={index}>
                                        <td>{reply.replyIdx}</td>
                                        <td className={'text-start cursor'}>
                                            <Link onClick={e => {
                                                e.preventDefault();
                                                handleLinkClick(reply);
                                            }} to={`/novelDetail/${reply.novelTitle}`}>{reply.novelTitle}</Link>
                                        </td>
                                        <td>{reply.replyContent}</td>
                                        <td>
                                            {reply.id}
                                        </td>
                                        <td>{reply.createDt}</td>
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

export default MyContent;