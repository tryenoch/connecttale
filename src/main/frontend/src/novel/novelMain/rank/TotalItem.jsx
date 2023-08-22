import React from 'react';
import {Col, Row} from "react-bootstrap";

function TotalItem(props) {

  const novel = props.novel;
  const sessionAdult = sessionStorage.getItem("adult"); // 세션 성인여부 정보

  const rank = novel.novelIndexNum;
  const title = novel.novelTitle;
  const author = novel.novelAuthor;
  const cate = novel.cateList;
  const thumbnail = novel.novelThumbnail;
  let adultsOnly = novel.adultsOnly;

  return (
    <Row className={'total-item align-items-center p-2 mt-1'}>
      <Col sm={1} className={''}>
        <h3 className={'total-num align-middle'}>
          {rank}
        </h3>
      </Col>
      <Col sm={3}>
        {/*<div className={'total-img'}>
          <img src={thumbnail} alt={title} className={'img-item rounded-1'}/>
        </div>*/}
        { adultsOnly ? // 성인작품일 경우
          sessionAdult =="Y" ? // 세션 정보가 있고 사용자가 성인일 경우
            <div className={"total-img"}>
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
              <div className={"total-img"}>
                <img src={thumbnail} alt={title} className={'img-item rounded-1'}/>
              </div>
            </div>
            // 세션 정보가 없거나 성인 유저가 아닐 경우
            :
            <div className={"total-img"}>
              <img src={"https://page.kakaocdn.net/pageweb/2.12.2/public/images/img_age_19_Thumbnail_43.svg"} alt={"Adults content"} className={'img-item rounded-1'}/>
            </div>
          // 세션 여부와 상관 없이 성인 작품이 아닐 경우
          : <div className={"total-img"}>
            <img src={thumbnail} alt={title} className={'img-item rounded-1'}/>
          </div>
        }
      </Col>
      <Col>
        <div className={'w-100 info'}>
          <h5>{title}</h5>
          <div>
            {author != null ? <span>{author}</span> : null}
            {cate != null ? <span className={'ms-2'}><span className={'me-2'}>|</span>{cate}</span> : null}
          </div>
        </div>
      </Col>
    </Row>
  )
}

export default TotalItem;