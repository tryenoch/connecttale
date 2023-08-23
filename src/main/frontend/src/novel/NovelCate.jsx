import React, {useEffect, useState} from 'react';
import {Col, Row} from "react-bootstrap";
import axios from "axios";
import {errorMessage} from "../common/MessageUtils";
import Item from "./novelMain/recent/item";
import CateITem from "./novelMain/cate/CateITem";

function NovelCate(props) {

  const [cate, setCate] = useState("0");
  const [novelList, setNovelList] = useState([]);

  const handleChangeCateValue = (e) => {
    setCate(e.target.value);
  }

  useEffect(() => {
    // 처음 페이지 로딩 시 실행
    getCateNovelList(0, 0);
  }, [])

  useEffect(() => {
    // 카테고리 명 클릭시 실행
    getCateNovelList(cate, 0);
  }, [cate])

  let list = novelList.map(novel =>
    /* 나중에 링크 추가 해야함 */
    <Row>
      <Col className={""} key={novelList.indexOf(novel)}>
        <CateITem novel={novel} />
      </Col>
    </Row>
  );
  
  return (
    <div>

      <Row>
        <Col>
        <h1 className={'main-title my-3'}>카테고리별로 보기</h1>
        <div className={'cate-tabs'}>
          <ul className={'clearfix cate-list'}>
            <li>
              <input type={"radio"} value={"0"} checked={ cate === "0"} onChange={handleChangeCateValue} id={'cate_0'}/>
              <label htmlFor={'cate_0'}>
                전체
              </label>
            </li>
            <li>
              <input type={"radio"} value={"1"} checked={ cate === "1"} onChange={handleChangeCateValue} id={'cate_1'}/>
              <label htmlFor={'cate_1'}>
                판타지
              </label>
            </li>
            <li>
              <input type={"radio"} value={"2"} checked={ cate === "2"} onChange={handleChangeCateValue} id={'cate_2'}/>
              <label htmlFor={'cate_2'}>
                현판
              </label>
            </li>
            <li>
              <input type={"radio"} value={"3"} checked={ cate === "3"} onChange={handleChangeCateValue} id={'cate_3'}/>
              <label htmlFor={'cate_3'}>
                로맨스
              </label>
            </li>
            <li>
              <input type={"radio"} value={"4"} checked={ cate === "4"} onChange={handleChangeCateValue} id={'cate_4'}/>
              <label htmlFor={'cate_4'}>
                로판
              </label>
            </li>
            <li>
              <input type={"radio"} value={"5"} checked={ cate === "5"} onChange={handleChangeCateValue} id={'cate_5'}/>
              <label htmlFor={'cate_5'}>
                무협
              </label>
            </li>
            <li>
              <input type={"radio"} value={"6"} checked={ cate === "6"} onChange={handleChangeCateValue} id={'cate_6'}/>
              <label htmlFor={'cate_6'}>
                드라마
              </label>
            </li>
            <li>
              <input type={"radio"} value={"7"} checked={ cate === "7"} onChange={handleChangeCateValue} id={'cate_7'}/>
              <label htmlFor={'cate_7'}>
                BL
              </label>
            </li>
            <li>
              <input type={"radio"} value={"8"} checked={ cate === "8"} onChange={handleChangeCateValue} id={'cate_8'}/>
              <label htmlFor={'cate_8'}>
                기타
              </label>
            </li>
          </ul>
        </div>
        </Col>
      </Row>
      <Row>
        {list}
      </Row>
    </div>
  )

  function getCateNovelList(cate, page){
    //alert('테스트 메세지입니다.');
    axios.get(`/novel/cateNovelList`, {
      params : { cate : cate, page : page }
    })
      .then(res => {
        // MessageUtils.infoMessage("비동기 통신에 성공했습니다.");
        console.log(res.data.result);
        console.log(res.data.list);
        setNovelList(res.data.list);
      })
      .catch(err => {
        errorMessage("카테고리별 소설 리스트", err);
      })
  }

}

export default NovelCate;