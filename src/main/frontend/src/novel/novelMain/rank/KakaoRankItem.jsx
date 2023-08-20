import React from 'react';
import {Col, Row} from "react-bootstrap";
import '../../../static/css/novel.css';

function KakaoRankItem(props) {

  const novel = props.novel; // 개별 소설 정보 객체
  const sessionAdult = sessionStorage.getItem("adult"); // 세션 성인여부 정보

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
          { adultsOnly ? // 성인 유저이며 해당 작품이 성인작품일 경우
            sessionAdult =="Y" ?
              <div>
                <svg width="1em" height="1em" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" aria-label="19세 미만 구독불가" className="adult-img">
                  <circle cx="12" cy="12" r="12" fill="#fff"></circle>
                  <path opacity="0.8" d="M.5 12a11.5 11.5 0 1023 0 11.5 11.5 0 10-23 0" stroke="#DC3232"></path>
                  <g clipPath="url(#BadgeAdult_svg__clip0_7220_94960)">
                    <path fillRule="evenodd" clipRule="evenodd"
                          d="M11.567 10.677c0-1.547 1.19-2.677 2.86-2.677C16.07 8 17.5 9.156 17.5 11.902v.01C17.5 14.48 16.331 16 14.395 16c-1.414 0-2.47-.829-2.716-1.996l-.011-.058h1.355l.016.053c.203.533.678.882 1.356.882 1.222 0 1.739-1.183 1.798-2.64l.002-.088.003-.087h-.107c-.282.602-.955 1.135-2.011 1.135-1.478 0-2.513-1.066-2.513-2.513v-.01zm1.344-.037c0 .882.635 1.516 1.51 1.516.87 0 1.537-.623 1.537-1.484v-.01c0-.872-.667-1.553-1.52-1.553-.85 0-1.527.67-1.527 1.52v.011zm-3.028 5.17H8.506V9.531h-.09L6.5 10.873V9.589L8.512 8.19h1.37v7.62z"
                          fill="#000"></path>
                  </g>
                  <defs>
                    <clipPath id="BadgeAdult_svg__clip0_7220_94960">
                      <path fill="#fff" transform="translate(6 8)" d="M0 0h12v8H0z"></path>
                    </clipPath>
                  </defs>
                </svg>
                <img src={thumbnail} alt=""/>
              </div>
              // 세션 정보가 없거나 성인 유저가 아닐 경우
              : <img src={"https://page.kakaocdn.net/pageweb/2.12.2/public/images/img_age_19_Thumbnail_43.svg"} alt={"Adults content"}/>
            // 세션 여부와 상관 없이 성인 작품이 아닐 경우
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