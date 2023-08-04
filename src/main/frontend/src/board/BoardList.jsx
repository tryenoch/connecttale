import React, {useState} from 'react';
import {Col, Form, Row} from "react-bootstrap";

function BoardList(props) {

    const [keyword, setKeyword] = useState('');
    const [cate, setCate] = useState(0);

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
                                <Col>
                                    <Form.Select
                                        value={cate}
                                        onChange={(e) => setCate(e.target.value)}
                                    >
                                        <option value={0}>제목</option>
                                        <option value={1}>작성자</option>
                                        <option value={2}>등록일</option>
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
            </Col>
        </Row>
    )
}

export default BoardList;