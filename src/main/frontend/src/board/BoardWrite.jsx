import React, {useEffect, useState} from 'react';
import {Col, Container, Form, Row} from "react-bootstrap";
import {CKEditor} from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import {boardList} from "./BoardMain";
import UploadAdapter from "../common/UploadAdapter";

function BoardWrite() {

    const navi = useNavigate();
    const query = useParams();
    const [title, setTitle] = useState('');
    const [contents, setContents] = useState('');
    const [boardCate, setBoardCate] = useState(1);
    const [createId, setCreateId] = useState('');
    const [reqCate, setReqCate] = useState(0);

    useEffect(() => {
        setBoardCate(boardList[query.id].code);
        setCreateId(sessionStorage.getItem('id'));
    }, []);

    const handleSubmit = (event) => {
        // submit으로 인한 화면이동을 막기 위해 preventDefault 추가
        event.preventDefault();
        //빈 데이터 안 보내도록 조건 추가
        if (reqCate === 0) {
            alert('문의 종류를 입력하세요.');
            return;
        }

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
                boardIdx: 0,
                hitCnt: 0,
                // int 타입이 null을 받지 못해서
                // DTO와 params의 멤버가 동일 해야함
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

    const handleMoveToMenu = () => {
        navi("/board/main");
    }

    // file upload 코드
    function MyCustomUploadAdapterPlugin(editor) {
        editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
            return new UploadAdapter(loader)
        }
    }


    return (
        <Container className={'my-4'}>
            <h2>boardWrite</h2>
            <Row>
                <Col xs={10} className={'my-5 mx-auto'}>
                    <Row className={'border-3 border-black border-bottom py-2 mb-3'}>
                        <div className={'text-center mb-5'}>
                            <h1 className={'fw-bold'}>{boardList[query.id].title}</h1>
                        </div>
                    </Row>
                    <form onSubmit={handleSubmit}>
                        <Row className={'mb-3'}>
                            {
                                (boardCate == 1) &&
                                (<Col xs={2}>
                                    <Form.Select
                                        className={'border-1 border-black select-box cursor fw-bold'}
                                        value={reqCate}
                                        onChange={(e) => setReqCate(e.target.value)}
                                    >
                                        <option value={0}>=== 선택 ===</option>
                                        <option value={1}>서비스</option>
                                        <option value={2}>이용 장애</option>
                                        <option value={3}>콘텐츠</option>
                                        <option value={4}>이벤트</option>
                                        <option value={5}>개선 제안</option>
                                    </Form.Select>
                                </Col>)
                            }
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
                                extraPlugins: [MyCustomUploadAdapterPlugin],
                                placeholder: "내용을 입력하세요.",
                            }}
                            onChange={(event, editor) => {
                                const data = editor.getData();
                                setContents(data);
                            }}
                        />
                        <div className={'d-flex justify-content-end'}>
                            <button type={'button'} className={'btn btn-secondary'} onClick={handleMoveToMenu}>목록
                            </button>
                            <button type={'submit'} className={'btn btn-primary'}>작성</button>
                        </div>
                    </form>
                </Col>
            </Row>
        </Container>
    )
}

export default BoardWrite;