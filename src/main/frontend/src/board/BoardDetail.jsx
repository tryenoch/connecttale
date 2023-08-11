import React, {useContext, useEffect, useState} from 'react';
import {Col, Container, Row} from "react-bootstrap";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import {boardList} from "./BoardMain";
import button from "bootstrap/js/src/button";

function BoardDetail(props) {
    // html 파싱을 위한 라이브러리
    const parse = require('html-react-parser');

    const navi = useNavigate();
    const profile = useParams();
    const [title, setTitle] = useState('');
    const [contents, setContents] = useState('');
    const [createId, setCreateId] = useState('');
    const [createDt, setCreateDt] = useState('');
    const [reqCate, setReqCate] = useState('');

    useEffect(() => {
        axios.get(`/board/${profile.idx}`)
            .then(res => {
                const board = res.data.board;
                console.log(board);
                setTitle(board.boardTitle);
                setContents(board.boardContents);
                setCreateId(board.createId);
                setCreateDt(board.createDt);
                setReqCate(board.reqCate);
            })
            .catch(err => {
            });
    }, []);
    const handleGotoMain = () => {
        navi("/board/main");
    }
    return (
        <Container className={'my-4'}>
            <h2>boardDetail</h2>
            <Row>
                <Col xs={10} className={'my-5 mx-auto'}>
                    <Row className={'border-3 border-black border-bottom py-2 mb-5'}>
                        <div className={'text-center mb-5'}>
                            <h1 className={'fw-bold'}>{boardList[profile.cate].title}</h1>
                        </div>
                    </Row>
                    <div className={'border-bottom border-1'}>
                        {/*분류 부분 reqCate를 이용하여 출력*/}
                        <h3 className={'my-2'}>분류 / {title}</h3>
                    </div>
                    {/*    조회수 작성일 작성자 / 조회수 칼럼 추가 */}
                    <div className={'d-flex justify-content-between p-2'}>
                        <span>작성자: {createId}</span>
                        <span>작성일: {createDt}</span>
                        <span>조회수</span>
                    </div>
                    <div className={'border-top border-bottom border-1 p-3'}>
                        {parse(contents)}
                    </div>
                    <div className={'d-flex justify-content-center my-3 delete-left'}>
                        <button type={'button'} className={'btn btn-primary px-4'} onClick={handleGotoMain}>목록</button>
                        {
                            true &&
                            (<button type={'button'} className={'btn btn-danger'}>삭제</button>)
                        }
                    </div>


                </Col>
            </Row>
        </Container>
    )
}

export default BoardDetail;