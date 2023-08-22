import React from 'react';
import {CategoryConverter} from "../../../common/NovelInfoConverter";
import {Link, useNavigate} from "react-router-dom";
import {fetchData} from "../../../common/NovelDetailFetch2";

function Item(props) {

  const navi = useNavigate();

  const novel = props.novel; // 개별 소설 정보 객체
  const sessionAdult = sessionStorage.getItem("adult");

  const title = novel.novelTitle;
  const thumbnail = novel.novelThumbnail;
  const platform = novel.platform;
  const author = novel.novelAuthor;
  const novelAdult = novel.novelAdult;
  const cateList = CategoryConverter(novel.cateList);

  const novelKey = novel.novelKeyDto.novelIdx;

  const handleLinkClick = async (novel) => {
    try {
      const novelDetail = await fetchData(novel.platformId, novel.novelTitle, novel.ebookCheck, novel.novelAdult);
      navi(`/novelDetail/${novel.novelTitle}`, {
        state: {
          novelDetail: novelDetail,
        }
      });
    } catch (error) {
      console.log(error.message);
    }
  };

  return (
    <Link
      onClick={(e) => {
        e.preventDefault();
        handleLinkClick(novel);
      }} to={`/novelDetail/${novel.title}`}
      >
      <div className={"text-center"}>
        {/* 세션 영역에 저장된 성인 여부가 Y다 그럼 다 보여줌 */}
        {/* 세션 영역에 저장된 성인 여부가 Y가 아니다, 그럼 안 보여줌*/}
        { novelAdult == "Y" ? // 성인작품일 경우
          sessionAdult =="Y" ? // 세션 정보가 있고 사용자가 성인일 경우
            <div className={"rank-item-img"}>
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
              <div className={"rank-item-img"}>
              <img src={thumbnail} alt=""/>
              </div>
            </div>
            // 세션 정보가 없거나 성인 유저가 아닐 경우
            :
            <div className={"rank-item-img"}>
              <img src={"https://page.kakaocdn.net/pageweb/2.12.2/public/images/img_age_19_Thumbnail_43.svg"} alt={"Adults content"}/>
            </div>
          // 세션 여부와 상관 없이 성인 작품이 아닐 경우
          : <div className={"rank-item-img"}>
            <img src={thumbnail} alt=""/>
          </div>
        }
      </div>
      <div className={"rank-info w-100"}>
        <p className={"item-title"}>{title}</p>
        <p className={"item-detail"}>
          <span className={"item-author"}>{author}</span>
          {
            cateList == null? null :
              <span>|
              <span className="item-cate ms-2">{cateList}</span>
              </span>
          }
        </p>
      </div>
    </Link>
  )
}

export default Item;