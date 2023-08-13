import React, {useEffect, useState} from 'react';
import {Col, Form, Row} from "react-bootstrap";
import axios from "axios";
import {Link} from "react-router-dom";

function BoardList(props) {

    //페이지가 변할 때 cate 값 0으로 초기화 - 검색 종류 cate
    const [cate, setCate] = useState(0);
    const [keyword, setKeyword] = useState('');
    // 카테고리 변경 시 페이지 번호가 0으로 초기화 x
    const [nowPage, setNowPage] = useState(0);
    const [endPage, setEndPage] = useState(0);
    const [pages, setPages] = useState([1]);
    const [boardList, setBoardList] = useState([{}]);

    useEffect(() => {
        setNowPage(props.defaultPage);
    }, [])

    useEffect(() => {
        requestData();
    }, [props.data, nowPage]);

    const requestData = () => {
        axios.get(`/${props.data.type}?page=${nowPage}&size=10`)
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
                setBoardList(res.data.boardList);
            })
            .catch(err => {
                alert(`통신에 실패했습니다. err : ${err}`);
            })
    }

    const handleSubmit = (event) => {
        alert(`검색어 : ${keyword}, 종류 : ${cate}`);
        // 검색 기능 API 구현
        event.preventDefault();
    }

    return (
        <Row>
            <Col xs={10} className={'my-5 mx-auto'}>
                <Row className={'border-3 border-black border-bottom py-2'}>
                    <Col className={'ps-0'}><h3 className={'fw-bold'}>{props.data.title}</h3></Col>
                    {
                        (props.data.id === 0) &&
                        (<Col className={'pe-0'}>
                            <form onSubmit={handleSubmit}>
                                <Row>
                                    <Col className={'px-0'}>
                                        <Form.Select
                                            className={'border-2 border-black select-box cursor fw-bold'}
                                            value={cate}
                                            onChange={(e) => setCate(e.target.value)}
                                        >
                                            <option value={0}>제목</option>
                                            <option value={1}>작성자</option>
                                        </Form.Select>
                                    </Col>
                                    <Col xs={8} className={'search-bar'}>
                                        <input
                                            type={'text'}
                                            value={keyword}
                                            placeholder={'검색어를 입력하세요'}
                                            onChange={(e) => setKeyword(e.target.value)}
                                        />
                                        <button type={'submit'}><i className="bi bi-search"></i></button>
                                    </Col>
                                </Row>
                            </form>
                        </Col>)}
                </Row>
                <Row>
                    <table className={'text-center table'}>
                        <colgroup>
                            <col width={'10%'}/>
                            <col width={'60%'}/>
                            <col width={'15%'}/>
                            <col width={'15%'}/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>날짜</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            boardList.map((board, index) => {
                                return (
                                    <tr key={index}>
                                        <td>{board.boardIdx}</td>
                                        <td className={'text-start cursor'}>
                                            <Link to={`/board/detail/${props.data.id}/${board.boardIdx}`}>
                                                {board.boardTitle}
                                            </Link>
                                        </td>
                                        <td>{board.createId}</td>
                                        <td>{board.createDt}</td>
                                    </tr>
                                )
                            })
                        }
                        </tbody>
                    </table>
                    <div className={'d-flex justify-content-end'}>
                        {
                            // 특정 조건에서만 랜더링 코드
                            props.data.type === 'req' &&
                            (<Link to={'/board/write'} className={'btn btn-primary'}>글 쓰기</Link>)
                        }

                    </div>
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

export default BoardList;