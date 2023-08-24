import React, {useEffect, useState} from 'react';
import {Col, Row} from "react-bootstrap";
import axios from "axios";
import {errorMessage} from "../common/MessageUtils";
import Item from "./novelMain/recent/item";
import CateITem from "./novelMain/cate/CateITem";

function NovelCate(props) {

  const defaultPage = 0;

  const [cate, setCate] = useState("0");
  const [novelList, setNovelList] = useState([]);
  const [nowPage, setNowPage] = useState(0);
  const [endPage, setEndPage] = useState(0);
  const [pages, setPages] = useState([1]);


  const handleChangeCateValue = (e) => {
    setCate(e.target.value);
  }

  useEffect(() => {
    // 처음 페이지 로딩 시 실행
    getCateNovelList(0, 0);
    setNowPage(defaultPage);
  }, [])

  useEffect(() => {
    // 카테고리 명 클릭시 실행
    setNowPage(0);
    getCateNovelList();
  }, [cate])

  useEffect(() => {
    // 카테고리 명 클릭시 실행
    getCateNovelList();
  }, [nowPage])



  const getCateNovelList = () => {
    //alert('테스트 메세지입니다.');
    axios.get(`/novel/cateNovelList?page=${nowPage}&size=10`, {
      params : { cate : cate }
    })
      .then(res => {
        // MessageUtils.infoMessage("비동기 통신에 성공했습니다.");
        console.log(res.data.result);
        console.log(res.data.list);

        let arr = [];
        let now = res.data.nowPage;
        let end = res.data.totalPages;

        let firstPage = now - 2 > 0 ? now - 2 : 1;
        let lastPage = now + 2 > end ? end : now + 2;

        if (lastPage === end){
          firstPage = end - 4 > 1 ? end - 4 : 1;
        }
        if(firstPage === 1){
          lastPage = end > 5 ? 5 : end;
        }

        for(let i = firstPage; i <= lastPage; i++){
          arr.push(i);
        }

        console.log(`arr : ${arr}, first : ${firstPage}, last : ${lastPage}, nowPage : ${now}, total: ${end}`);

        setPages(arr);
        setEndPage(res.data.totalPages);
        setNovelList(res.data.list);

      })
      .catch(err => {
        errorMessage("카테고리별 소설 리스트", err);
      })
  }


  let list = novelList.map(novel =>
    /* 나중에 링크 추가 해야함 */
    <Row className={'cate-low mb-2'}>
      <Col key={novelList.indexOf(novel)}>
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
      <Row className={'mb-5'}>
        {list}
      </Row>
      <Row>
        <Col>
          <div className={'d-flex justify-content-between ps-0 align-items-center'}>
            <div className={'mx-auto my-3 pages cursor'}>
              <a
                className={nowPage <= 0 ? 'text-black-50' : ''}
                onClick={() => {
                  if (nowPage <= 0) {
                    return null
                  }
                  return setNowPage(0)
                }}
              ><i className="bi bi-chevron-double-left"></i>
              </a>
              <a
                className={nowPage <= 0 ? 'text-black-50' : ''}
                onClick={() => {
                  if (nowPage <= 0) {
                    return null
                  }
                  return setNowPage(nowPage - 1)
                }}>
                <i className="bi bi-chevron-left"></i>
              </a>
              {
                pages.map((value) => {
                  return (
                    <a
                      key={value}
                      className={nowPage === value - 1 ? 'selected-page mx-2' : 'text-black-50 mx-2'}
                      onClick={() => setNowPage(value - 1)}
                    >{value}</a>);
                })
              }
              <a
                className={nowPage >= endPage - 1 ? 'text-black-50' : ''}
                onClick={() => {
                  if (nowPage >= endPage - 1) {
                    return null
                  }
                  return setNowPage(nowPage + 1)
                }}
              ><i className="bi bi-chevron-right"></i>
              </a>
              <a
                className={nowPage >= endPage - 1 ? 'text-black-50' : ''}
                onClick={() => {
                  if (nowPage >= endPage - 1) {
                    return null
                  }
                  return setNowPage(endPage - 1)
                }}
              ><i className="bi bi-chevron-double-right"></i>
              </a>
            </div>
          </div>
        </Col>
      </Row>
    </div>

  )

}

export default NovelCate;