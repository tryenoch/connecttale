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
  const [boardIdx, setBoardIdx] = useState(0);
  const [title, setTitle] = useState('');
  const [contents, setContents] = useState('');
  const [createId, setCreateId] = useState('');
  const [createDt, setCreateDt] = useState('');
  const [reqCate, setReqCate] = useState('');
  const [hitCnt, setHitCnt] = useState(0);
  const [reply, setReply] = useState('');
  const [replyList, setReplyList] = useState([]);
  const [nickName, setNickName] = useState('');
  const [loginId, setLoginId] = useState('');

  useEffect(() => {
    axios.get(`/board/${profile.idx}`)
        .then(res => {
          const board = res.data.board;
          console.log(board);
          console.log(res.data.replyList);
          setBoardIdx(board.boardIdx);
          setTitle(board.boardTitle);
          setContents(board.boardContents);
          setNickName(board.nickName);
          setCreateId(board.createId);
          setCreateDt(board.createDt);
          setReqCate(board.reqCate);
          setHitCnt(board.hitCnt);
          setReplyList(res.data.replyList);

          setLoginId(sessionStorage.getItem('id'));
        })
        .catch(err => {
        });
  }, []);
  const handleGotoMain = () => {
    navi("/board/main");
  }

  const handleDelete = () => {
    axios.delete(`/board/${profile.idx}`, null)
        .then(res => {
          navi("/board/main");
        })
        .catch(err => {
          alert(`통신에 실패했습니다. board/delete : ${err}`);
        })
  }

  const handleReply = () => {
    axios.post(`/board/reply`, null, {
      params: {
        // DTO와 params의 멤버가 동일 해야함
        idx: 0,
        reply: reply,
        boardIdx: boardIdx,
        createId: loginId,
      }
    })
        .then(res => {
          console.log(res.data.result)
        })
        .catch(err => {
          alert(`통신에 실패했습니다. board/reply post : ${err}`);
        });
  }

  const handelReplyDelete = (event, idx) => {
    axios.delete(`/board/reply/${idx}`, null)
        .then(res => {
          console.log(res.data.result);
          event.reload();
        })
        .catch(err => {
          alert(`통신에 실패했습니다. board/reply delete : ${err}`);
        })
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
            <Row className={'border-bottom border-1 my-2'}>
              {
                  (reqCate.trim().length > 0) &&
                  (<Col xs={2}>
                    <h3 className={'text-center'}>{reqCate}</h3>
                  </Col>)
              }
              <Col>
                <h3>{title}</h3>
              </Col>
            </Row>
            <div className={'d-flex justify-content-between p-2'}>
              <span>작성자: {nickName}</span>
              <span>작성일: {createDt}</span>
              <span>조회수: {hitCnt}</span>
            </div>
            <div className={'border-top border-bottom border-1 p-3'}>
              {parse(contents)}
            </div>
            <div className={'d-flex justify-content-center my-3 delete-left'}>
              <button type={'button'} className={'btn btn-primary px-4'} onClick={handleGotoMain}>목록</button>
              {
                // 본인 or 관리자로 로그인했을 경우 렌더링하게 구현
                  true &&
                  (<button type={'button'} className={'btn btn-danger'} onClick={handleDelete}>삭제</button>)
              }
            </div>
            {/*    댓글 구현부*/}
            {
                (profile.cate == 0) &&
                (<div>
                  <form onSubmit={handleReply}>
                    <p>{JSON.parse(sessionStorage.getItem('member')).nickname}</p>
                    <div className={'reply-input py-1'}>
                      <input
                          type="text"
                          value={reply}
                          className={'input-s6'}
                          onChange={(event) =>
                              setReply(event.target.value)}/>
                      <button type={'submit'} className={'btn'}><i className="bi bi-send text-purple"></i>
                      </button>
                    </div>
                  </form>
                  {/* 댓글 배열을 이용하여 댓글 구현*/}
                  {
                    replyList.map((value, index) => {
                      return (<div key={index} className={'mt-3'}>
                        <Row>
                          <Col>
                            <h4>{value.nickName}</h4>
                          </Col>
                          <Col className={'d-flex justify-content-end'}>
                            {
                              //댓글 닉네임 가져오기 id랑
                                ((sessionStorage.getItem('id') == value.createId) || (sessionStorage.getItem('grade') == 2)) &&
                                <button type={"button"} className={'btn btn-danger'}
                                        onClick={(event) => handelReplyDelete(event, value.idx)}>삭제
                                </button>
                            }
                          </Col>
                        </Row>
                        <p>{value.createDt}</p>
                        <p>{value.reply}</p>
                      </div>)
                    })
                  }

                </div>)
            }
          </Col>
        </Row>
      </Container>
  )
}

export default BoardDetail;