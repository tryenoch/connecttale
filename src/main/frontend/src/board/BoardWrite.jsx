import React, {useState} from 'react';
import {Col, Container, Form, Row} from "react-bootstrap";
import {CKEditor} from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import {useNavigate} from "react-router-dom";
import axios from "axios";

function BoardWrite() {

    const navi = useNavigate();

    const [title, setTitle] = useState('');
    const [contents, setContents] = useState('');
    const [boardCate, setBoardCate] = useState(1);
    const [createId, setCreateId] = useState('test1');
    const [reqCate, setReqCate] = useState(0);

    const handleSubmit = (event) => {
        // submit으로 인한 화면이동을 막기 위해 preventDefault 추가
        event.preventDefault();
        //빈 데이터 안 보내도록 조건 추가
        if (title.trim().length <= 0) {
            alert('제목을 입력하세요.');
            return;
        }

        if (contents.trim().length <= 0) {
            alert('내용을 입력하세요.');
            return;
        }

        axios.post(`/board/process`, null, {
            params: {
                boardTitle: title,
                boardContents: contents,
                boardCate: boardCate,
                createId: createId,
                reqCate: reqCate
            }
        })
            .then(res => {
                navi("/board/main");
            })
            .catch(err => {
                alert(`통신에 실패했습니다. board/write : ${err}`);
            })
    }

    return (
        <Container className={'my-4'}>
            <h2>boardWrite</h2>
            <Row>
                <Col xs={10} className={'my-5 mx-auto'}>
                    <Row className={'border-3 border-black border-bottom py-2 mb-3'}>
                        <div className={'text-center mb-5'}>
                            <h1 className={'fw-bold'}>문의사항</h1>
                        </div>
                    </Row>
                    <form onSubmit={handleSubmit}>
                        <Row className={'mb-3'}>
                            <Col xs={2}>
                                <Form.Select
                                    className={'border-1 border-black select-box cursor fw-bold'}
                                    value={reqCate}
                                    onChange={(e) => setReqCate(e.target.value)}
                                >
                                    <option value={0}>=== 선택 ===</option>
                                    <option value={1}>작성자</option>
                                    <option value={2}>버그</option>
                                    <option value={3}>저장</option>
                                </Form.Select>
                            </Col>
                            <Col>
                                <input
                                    type="text"
                                    className={'form-control border-1 border-black'}
                                    placeholder={'제목을 입력하세요.'}
                                    value={title}
                                    onChange={(e) =>
                                        setTitle(e.target.value)}
                                />
                            </Col>
                        </Row>
                        {/*스마트 에디터의 글을 볼 때는 html-react-parser 라이브러리 이용!*/}
                        <CKEditor
                            editor={ClassicEditor}
                            config={{
                                placeholder: "내용을 입력하세요.",
                            }}
                            onChange={(event, editor) => {
                                const data = editor.getData();
                                setContents(data);
                            }}
                        />
                        <div className={'d-flex justify-content-end'}>
                            <button type={'submit'} className={'btn btn-primary'}>작성</button>
                        </div>
                    </form>
                </Col>
            </Row>
        </Container>
    )
}

export default BoardWrite;