import React from 'react';
import {Col, Row} from "react-bootstrap";

function BoardList(props) {

    return (
        <Row>
            <Col xs={10} className={'my-5 mx-auto'}>
                <Row className={'border-3 border-black border-bottom py-2'}>
                    <Col className={'ps-0'}><h3 className={'fw-bold'}>{props.data.title}</h3></Col>
                    <Col className={'pe-0'}>
                        <Row>
                            <form>
                                <Col>
                                </Col>
                                <Col xs={8} className={'search-bar'}>
                                    <input type={'text'} placeholder={'검색어를 입력하세요'}/>
                                    <button type={'submit'}><i className="bi bi-search"></i></button>
                                </Col>
                            </form>
                        </Row>
                    </Col>
                </Row>
            </Col>
        </Row>
    )
}

export default BoardList;