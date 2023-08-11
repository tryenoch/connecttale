import React from 'react';
import {Col, Row} from "react-bootstrap";
import '../../../static/css/novel.css';

function KakaoRankItem(props) {

  const novel = props.novel; // 개별 소설 정보 객체

  /* 받아온 값 목록, dto 일부만 쓸 거기 때문에 구조 분해 할당이 안됨 */
  const rankNum = novel.novelIndexNum;
  const title = novel.novelTitle;
  // const author = novel.novelAuthor;
  // const cate = novel.cateList;
  const thumbnail = novel.novelThumbnail;
  // const starRate = novel.novelStarRate;
  let adultsOnly = novel.adultsOnly;

  return (
    <div>
      <div>
        <div className={"rank-item-img text-center"}>
          <h3 className={"rank-num"}>{rankNum}</h3>
          {/*세션 영역에 저장된 성인 여부에 따라 이미지 보이는 거 다르게 해야함 */}
          {
            adultsOnly ? <img src={"https://page.kakaocdn.net/pageweb/2.12.2/public/images/img_age_19_Thumbnail_43.svg"} alt={"Adults content"}/>
              : <img src={thumbnail} alt=""/>
          }
        </div>
      </div>
      <div className={"rank-info w-100"}>
        <p className={"item-title"}>{title}</p>
        <div>
          {/*<p className={"item-detail"}>*/}
          {/*  <span className={"item-author me-2"}>{author}</span>*/}
          {/*  |*/}
          {/*  <span className={"item-cate"}>{cate}</span>*/}
          {/*</p>*/}
          {/*<p className={"item-star"}>*/}
          {/*  <svg width="1em" height="1em" viewBox="0 0 11 11" fill="none" xmlns="http://www.w3.org/2000/svg" aria-label="구매자 평균 별점" className="fig-httsil">*/}
          {/*    <path fillRule="evenodd" clipRule="evenodd"*/}
          {/*          d="M5.5 0l1.812 3.303L11 4.025 8.446 6.759l.459 3.741L5.5 8.903 2.095 10.5l.459-3.74L0 4.002l3.688-.7L5.5 0z"*/}
          {/*          fill="currentColor"></path>*/}
          {/*  </svg>*/}
          {/*  <span className={"ms-1"}>{starRate}</span></p>*/}
        </div>
      </div>
    </div>

  )
}

export default KakaoRankItem;