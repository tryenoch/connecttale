import React, {useEffect, useState} from 'react';
import {Col, Form, Row} from "react-bootstrap";
import axios from "axios";

const boardList = [
    {
        idx: 1,
        title: '제목 1',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 2,
        title: '제목 2',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 3,
        title: '제목 3',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 4,
        title: '제목 4',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 5,
        title: '제목 5',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 6,
        title: '제목 6',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 7,
        title: '제목 7',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 8,
        title: '제목 8',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 9,
        title: '제목 9',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 10,
        title: '제목 10',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 11,
        title: '제목 11',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 12,
        title: '제목 12',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 13,
        title: '제목 13',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 14,
        title: '제목 14',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 15,
        title: '제목 15',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 16,
        title: '제목 16',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 17,
        title: '제목 17',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 18,
        title: '제목 18',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 19,
        title: '제목 19',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 20,
        title: '제목 20',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 21,
        title: '제목 21',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 22,
        title: '제목 22',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
    {
        idx: 23,
        title: '제목 23',
        createId: 'tester1',
        createDt: '2023.08.04',
    },
]

function BoardList(props) {

    //페이지가 변할 때 cate 값 0으로 초기화
    const [cate, setCate] = useState(0);
    const [keyword, setKeyword] = useState('');
    const [totalCount, setTotalCount] = useState(0);
    const [nowPage, setNowPage] = useState(1);
    const [endPage, setEndPage] = useState(0);

    useEffect(() => {
        requestData();
    }, [nowPage]);

    useEffect(() => {
        setNowPage(1)
    }, [props]);

    const requestData = () => {
        axios.get(`/${props.data.type}/${nowPage}`)
            .then(res => {
                console.log(res);
                setTotalCount(res.data.totalCount);
                setEndPage(Math.ceil(res.data.totalCount / 5));
            })
            .catch(err => {
                alert(`통신에 실패했습니다. err : ${err}`);
            })
    }

    const handleSubmit = (event) => {
        alert(`검색어 : ${keyword}, 종류 : ${cate}`);
        event.preventDefault();
    }

    const handlePage = (event) => {
        setNowPage(event.target.value);
    }

    return (
        <Row>
            <Col xs={10} className={'my-5 mx-auto'}>
                <Row className={'border-3 border-black border-bottom py-2'}>
                    <Col className={'ps-0'}><h3 className={'fw-bold'}>{props.data.title}</h3></Col>
                    <Col className={'pe-0'}>
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
                    </Col>
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
                                        <td>{index}</td>
                                        <td className={'text-start cursor'}>{board.title}</td>
                                        <td>{board.createId}</td>
                                        <td>{board.createDt}</td>
                                    </tr>
                                )
                            })
                        }
                        </tbody>
                    </table>
                    <div className={'d-flex justify-content-center mx-auto my-3'}>

                        <button
                            className={nowPage <= 1 ? 'btn btn-primary disabled' : 'btn btn-primary'}
                            onClick={() => setNowPage(1)}
                        >처음 페이지
                        </button>
                        <button
                            className={nowPage <= 1 ? 'btn btn-primary disabled' : 'btn btn-primary'}
                            onClick={() => setNowPage(nowPage - 1)}>이전 페이지
                        </button>
                        <span>{totalCount}, {endPage}</span>
                        <button
                            className={nowPage >= endPage ? 'btn btn-primary disabled' : 'btn btn-primary'}
                            onClick={() => setNowPage(nowPage + 1)}
                        >다음 페이지
                        </button>
                        <button
                            className={nowPage >= endPage ? 'btn btn-primary disabled' : 'btn btn-primary'}
                            onClick={() => setNowPage(endPage)}
                        >마지막 페이지
                        </button>

                    </div>
                </Row>
            </Col>
        </Row>
    )
}

export default BoardList;