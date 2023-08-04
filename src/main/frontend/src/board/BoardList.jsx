import React, {useState} from 'react';
import {Col, Form, Row} from "react-bootstrap";

function BoardList(props) {

    //페이지가 변할 때 cate 값 0으로 초기화
    const [cate, setCate] = useState(0);
    const [keyword, setKeyword] = useState('');

    const HandleSubmit = (event) => {
        alert(`검색어 : ${keyword}, 종류 : ${cate}`);
        event.preventDefault();

    }

    return (
        <Row>
            <Col xs={10} className={'my-5 mx-auto'}>
                <Row className={'border-3 border-black border-bottom py-2'}>
                    <Col className={'ps-0'}><h3 className={'fw-bold'}>{props.data.title}</h3></Col>
                    <Col className={'pe-0'}>
                        <form onSubmit={HandleSubmit}>
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
                        <tr>
                            <td>1</td>
                            <td className={'text-start cursor'}>비밀글 입니다.</td>
                            <td>tester1</td>
                            <td>2023.08.04</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td className={'text-start cursor'}>비밀글 입니다.</td>
                            <td>tester1</td>
                            <td>2023.08.04</td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td className={'text-start cursor'}>비밀글 입니다.</td>
                            <td>tester1</td>
                            <td>2023.08.04</td>
                        </tr>
                        </tbody>
                    </table>
                    <div className={'d-flex justify-content-center mx-auto my-3'}>
                        <button>처음 페이지</button>
                        <button>이전 페이지</button>
                        <button>1</button>
                        <button>다음 페이지</button>
                        <button>마지막 페이지</button>

                    </div>
                </Row>
            </Col>
        </Row>
    )
}

export default BoardList;