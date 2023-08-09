import React from 'react';
import {Col, Container, Row} from "react-bootstrap";
import {CKEditor} from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";

function BoardWrite() {

    return (
        <Container className={'my-4'}>
            <h2>boardWrite</h2>
            <Row>
                <Col xs={10} className={'my-5 mx-auto'}>
                    <Row className={'border-3 border-black border-bottom py-2 mb-3'}>
                        <Col className={'ps-0'}>
                            <h3 className={'fw-bold'}>글 쓰기</h3>
                        </Col>
                    </Row>
                    <form>
                        <CKEditor
                            editor={ClassicEditor}
                            config={{
                                placeholder: "내용을 입력하세요.",
                            }}
                            onReady={editor => {
                                // You can store the "editor" and use when it is needed.
                                console.log('Editor is ready to use!', editor);
                            }}
                            onChange={(event, editor) => {
                                const data = editor.getData();
                                console.log({event, editor, data});
                            }}
                            onBlur={(event, editor) => {
                                console.log('Blur.', editor);
                            }}
                            onFocus={(event, editor) => {
                                console.log('Focus.', editor);
                            }}
                        />
                    </form>
                </Col>
            </Row>
        </Container>
    )
}

export default BoardWrite;