import React from 'react';
import {Col, Container, Form, Row} from "react-bootstrap";
import {useLocation} from "react-router-dom";

function BoardDetail() {
    // 컨텍스트 사용 참고 : App10 파일
    const location = useLocation();
    const data = location.state?.data;

    return (
        <Container className={'my-4'}>
            <h2>boardDetail</h2>
            <Row>
                <Col xs={10} className={'my-5 mx-auto'}>
                    <Row className={'border-3 border-black border-bottom py-2 mb-5'}>
                        <div className={'text-center mb-5'}>
                            <h1 className={'fw-bold'}>{data}</h1>
                        </div>
                    </Row>
                    <div className={'border-top border-bottom border-1'}>
                        <h3 className={'my-2'}>{location.idx}</h3>
                    </div>
                    {/*    조회수 작성일 작성자 / 조회수 칼럼 추가 */}
                    <div className={'d-flex justify-content-between p-2'}>
                        <span>작성자</span>
                        <span>작성일</span>
                        <span>조회수</span>
                    </div>
                    <div className={'border-top border-bottom border-1 p-3'}>
                    {/*    html 파싱 데이터 출력*/}
                        contents
                    </div>
                    <div className={'d-flex justify-content-center my-3'}>
                        <button type={'button'} className={'btn btn-primary px-4'}>목록</button>
                    </div>


                </Col>
            </Row>
        </Container>
    )
}

export default BoardDetail;