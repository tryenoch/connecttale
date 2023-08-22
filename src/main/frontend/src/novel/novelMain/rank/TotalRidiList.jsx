import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";
import TotalList from "./TotalList";
import {Col, Row} from "react-bootstrap";
import TotalItem from "./TotalItem";
import axios from "axios";
import * as MessageUtils from "../../../common/MessageUtils";

function TotalRidiList(props) {

  // const [category, setCategory] = useState("1750");
  const [rankList, setRankList] = useState([]);

  useEffect(() => {
    getRidiList('1750');
  }, []);


  let list = rankList.filter(novel => novel.novelIndexNum < 21);
  list = list.map(novel =>
      <Row className={"total-list p-2"} key={novel.novelIndexNum}>
        <Col>
          <TotalItem novel={novel}/>
        </Col>
      </Row>
  )

  return (
      <div>
      <div className={'d-flex justify-content-between align-items-center'}>

        <div className={'clearfix'}>
          <h3 className={'float-start sub-rank-title'}>리디북스</h3>
          <div className="float-start btn-group btn-group-sm pt-1" role="group">
            <button type="button" className="btn btn-outline-purple btn-sm dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
              카테고리
            </button>
            <ul className="dropdown-menu">
              <li><button className="dropdown-item btn-sm" onClick={(e) => {getRidiList('1750')}}>판타지</button></li>
              <li><button className="dropdown-item btn-sm" onClick={(e) => {getRidiList('1650')}}>로맨스</button></li>
              <li><button className="dropdown-item btn-sm" onClick={(e) => {getRidiList('6050')}}>로판</button></li>
              <li><button className="dropdown-item btn-sm" onClick={(e) => {getRidiList('4150')}}>BL</button></li>
            </ul>
          </div>
        </div>

        <Link to={'https://ridibooks.com/category/1750'} target={'_blank'}>
              <span className={'me-2 more-link'}>더 보기
                <i className="bi bi-arrow-right-circle-fill ms-1 pt-1"></i>
              </span>
        </Link>
      </div>
        <div className={""}>
          {list}
        </div>
      </div>
  )

  // 카테고리에 따라 들고오는 리스트가 다름
  function getRidiList(category){
    axios.get(`/novel/ridiRankList`, {
      params : { category : category}
    }).then(res => {
      console.log(res.data.ridiNovelList);
      setRankList(res.data.ridiNovelList);
    }).catch(err => {
      MessageUtils.errorMessage("getRidiList", err);
    })
  }
}

export default TotalRidiList;