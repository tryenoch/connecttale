import React, {useState} from 'react';
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import axios from "axios";

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
  const handleShow = () => {
    if (sessionStorage.getItem("id")) {
      setShow(true);
    }
    else {
      alert("회원만 이용 가능한 서비스입니다.");
    }
  }
  const reportContentChange = (e) => setReportContent(e.target.value);
  
  
  const reportSubmit = () => {
    axios.post("/novelDetailReport", null, {
      params: {
        replyIdx: replyIdx,
        reportContent: reportContent,
        reporter: loginId,
        suspect: suspect
      }
    })
      .then(res => {
        console.log(res);
        if (res.data == '댓글 신고 완료') {
          handleClose();
          alert("신고가 접수되었습니다.");
        }
        else if (res.data == '이미 신고한 댓글입니다.') {
          handleClose();
          alert("이미 신고한 댓글입니다.");
        }
        else {
          alert("신고 접수 중 오류가 발생했습니다.");
        }
      })
  }

  return (

    <div>
      <span className={'badge-sm text-bg-dark px-3 py-1 rounded-pill me-1'} onClick={handleShow}>신고</span>
      <span className={'badge-sm text-bg-dark px-3 py-1 rounded-pill ms-1'} onClick={handleShow}>삭제</span>
      
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
              />
            </Form.Group>
            <Form.Group
              className="mb-3"
              controlId="exampleForm.ControlTextarea1"
            >
              <Form.Label>신고 사유</Form.Label>
              <Form.Control as={'textarea'} rows={5} value={reportContent} onChange={reportContentChange} placeholder={'신고 사유를 입력해주세요'} autoFocus/>
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