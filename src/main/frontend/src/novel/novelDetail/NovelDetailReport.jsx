import React, {useState} from 'react';
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";

function NovelDetailReport(props) {
  // 신고하고자 하는 reply_idx 및 id(글쓴이)
  const [replyIdx, setReplyIdx] = useState(props.replyIdx);
  const [suspect, setSuspect] = useState(props.suspect);
  const [replyContent, setReplyConetn] = useState(props.replyContent);
  const [reportContent, setReportContent] = useState('');
  
  // 세션id값 받아오기, 세션 정보 없으면 noUser가 됨 -> 서버에서 memberEntity 못찾게끔
  const [loginId, setLoginId] = useState(sessionStorage.getItem("id")? sessionStorage.getItem("id") : 'noUser');
  
  // 모달창 숨김 여부 state
  const [show, setShow] = useState(false);
  
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const reportContentChange = (e) => setReportContent(e.target.value);
  
  
  const reportSubmit = () => {
  
  }

  return (

    <div>
      <Button variant={'btn btn-outline-danger fs-6'} onClick={handleShow}>신고</Button>
      
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>리뷰 신고</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
              <Form.Label>신고 댓글</Form.Label>
              <Form.Control
                type="text"
                placeholder={replyContent}
                autoFocus
              />
            </Form.Group>
            <Form.Group
              className="mb-3"
              controlId="exampleForm.ControlTextarea1"
            >
              <Form.Label>신고 사유</Form.Label>
              <Form.Control as={'textarea'} rows={5} value={reportContent} onChange={reportContentChange} placeholder={'신고 사유를 입력해주세요'} />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            취소
          </Button>
          <Button variant="danger" onClick={reportSubmit}>
            신고 제출
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  )
}

export default NovelDetailReport;