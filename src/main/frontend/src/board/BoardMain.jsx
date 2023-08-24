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
            <ul className="nav nav-pills nav-fill gap-2 p-1 small rounded-5 shadow-sm">
              {
                boardList.map((value, index) => {
                  return (<li
                      key={value.id}
                      className="nav-item"
                      role="presentation">
                    <button
                        className={index === selectBoard ? 'nav-link active selected cursor rounded-5' : 'nav-link unselected cursor rounded-5'}
                        data-bs-toggle="tab"
                        type="button"
                        role="tab"
                        onClick={() => selectTabHandler(index)}
                    >{value.title}
                    </button>
                  </li>)
                })
              }

            </ul>
          </Col>

        </Row>
        <div>
          <BoardList data={boardList[selectBoard]} defaultPage={0}/>
        </div>
      </Container>
  )
}

export default BoardMain;