import React, {useState} from 'react';
import {Col, Container, Row} from "react-bootstrap";
import BoardList from "./BoardList";
export const boardList = [
  {
    id: 0,
    title: '문의사항',
    type: 'req',
    code: 1,
  },

  {
    id: 1,
    title: '공지사항',
    type: 'notice',
    code: 2,
  },

  {
    id: 2,
    title: '이벤트',
    type: 'event',
    code: 3,
  }
];

function BoardMain() {

  const [selectBoard, setSelectBoard] = useState(0);

  const selectTabHandler = (index) => {
    setSelectBoard(index);
  }

  return (
      <Container className={'my-4'}>
        <Row>
          <Col xs={6} className={'mx-auto'}>
            <div className={'nav justify-content-between px-5 py-3 tabs-bg'}>
              {
                boardList.map((value, index) => {
                  return <li
                      key={value.id}
                      className={'nav-item'}>
                    <a
                        className={index === selectBoard ? 'nav-link active selected cursor' : 'nav-link unselected cursor'}
                        onClick={() => selectTabHandler(index)}>{value.title}</a>
                  </li>
                })
              }
            </div>
          </Col>
        </Row>
        <div>
          <BoardList data={boardList[selectBoard]} defaultPage={0}/>
        </div>
      </Container>
  )
}

export default BoardMain;