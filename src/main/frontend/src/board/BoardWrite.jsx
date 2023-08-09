import React from 'react';
import {Col, Container, Row} from "react-bootstrap";

function BoardWrite() {

    return (
        <Container className={'my-4'}>
            <h2>boardWrite</h2>
            <Row>
                <Col xs={10} className={'my-5 mx-auto'}>
                    <Row className={'border-3 border-black border-bottom py-2'}>
                        <Col className={'ps-0'}>
                            <h3 className={'fw-bold'}>글 쓰기</h3>
                        </Col>
                        <form>

                        </form>
                    </Row>
                </Col>
            </Row>
        </Container>
    )
}

export default BoardWrite;