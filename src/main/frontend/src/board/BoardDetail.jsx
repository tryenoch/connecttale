import React, {useContext, useEffect, useState} from 'react';
import {Col, Container, Row} from "react-bootstrap";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import {boardList} from "./BoardMain";
import button from "bootstrap/js/src/button";
import parse from "html-react-parser";

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
  const [isRender, setIsRender] = useState(false);

  useEffect(() => {
    getDate();
  }, []);

  useEffect(() => {
    getDate();
  }, [isRender]);

  const getDate = () => {
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
  }
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
    if (reply.trim().length > 0) {
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
    } else {
      alert('댓글을 입력해 주세요');
    }
  }

  const handelReplyDelete = (index, idx) => {
    axios.delete(`/board/reply/${idx}`, null)
        .then(res => {
          console.log(res.data.result);
          setIsRender(!isRender);
        })
        .catch(err => {
          alert(`통신에 실패했습니다. board/reply delete : ${err}`);
        })
  }
  return (
      <Container className={'my-4'}>
        <div>
        </div>
        <Row>
          <Col xs={10} className={'my-5 mx-auto'}>
            <Row className={'py-2 mb-3'}>
              <div className={'text-center mb-3'}>
                <h1 className={'fw-bold board-title lg'}>{boardList[profile.cate].title}</h1>
              </div>
            </Row>
            <Row className={'my-2 board-content-header align-items-center'}>
              {
                  (reqCate.trim().length > 0) &&
                  (<Col xs={2} className={''}>
                    <h3 className={'text-center cate'}>{reqCate}</h3>
                  </Col>)
              }
              <Col>
                <h3>{title}</h3>
              </Col>
            </Row>
            <Row>
              <Col className={'ps-0'}>
                <div className={'d-flex justify-content-between content-info'}>
                  <span>{nickName}</span>
                  <div>
                    <span>{createDt}</span>
                    <span className={'mx-3'}>|</span>
                    <span> {hitCnt}</span>
                  </div>
                </div>
                <div className={'p-3 content-desc'}>
                  {parse(contents)}
                </div>
                <div className={'d-flex justify-content-between my-3 delete-left'}>
                  <button type={'button'} className={'btn btn-dark px-4'} onClick={handleGotoMain}>목록</button>
                  {
                    // 본인 or 관리자로 로그인했을 경우 렌더링하게 구현
                      true &&
                      (<button type={'button'} className={'btn btn-outline-dark px-4'}
                               onClick={handleDelete}>삭제</button>)
                  }
                </div>
              </Col>
            </Row>
            <Row>
              <Col className={'ps-0'}>
                {/*    댓글 구현부*/}
                {
                    (profile.cate == 0) &&
                    (<div>
                      <form onSubmit={handleReply}>
                        {/*<p>{JSON.parse(sessionStorage.getItem('member')).nickname}</p>*/}
                        <div className={'py-2'}>
                          <input
                              type="text"
                              value={reply}
                              className={'reply-input board'}
                              onChange={(event) =>
                                  setReply(event.target.value)}/>
                          <button type={'submit'} className={'reply-btn text-center'}><i className="bi bi-send"></i>
                          </button>
                        </div>
                      </form>
                      {/* 댓글 배열을 이용하여 댓글 구현*/}
                      {
                        replyList.map((value, index) => {
                          return (
                              <div key={index} className={'mt-3 reply-content'}>
                                <div className={'d-flex justify-content-between'}>
                                  <div>
                                    <span className={'fw-bold'}>{value.nickName}</span>
                                    <span className={'ms-3 text-black-50'}>{value.createDt}</span>
                                  </div>
                                  <div>
                                    {
                                      //댓글 닉네임 가져오기 id랑
                                        ((sessionStorage.getItem('id') == value.createId) || (sessionStorage.getItem('grade') == 2)) &&
                                        <button type={"button"} className={'btn btn-dark'}
                                                onClick={() => handelReplyDelete(index, value.idx)}>삭제
                                        </button>
                                    }
                                  </div>
                                </div>
                                <p>{value.reply}</p>
                              </div>)
                        })
                      }

                    </div>)
                }
              </Col>
            </Row>


          </Col>
        </Row>
      </Container>
  )
}

export default BoardDetail;