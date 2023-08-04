import React, {useEffect} from 'react';
import axios from "axios";

function NovelSearchScore(props) {
  
  useEffect(() => {
  
  }, [])
  
  
  return (
    <div className={'d-flex align-items-center my-1'}>
      <span className={'fw-bolder'}>평점 : </span>
      {
      
      }
      {/*리디북스 별점*/}
      <p className={'search-info'}><img src="/ridibooks-icon.jpg" alt="이미지가 없습니다." className={'search-logo'}/><span className={'ms-1 search-score'}>4.9점</span></p>
      {/*  네이버시리즈 별점*/}
      <p className={'ms-2 search-info'}><img src="/naver-series-icon.png" alt="이미지가 없습니다." className={'search-logo'}/><span className={'ms-1 search-score'}>4.4점</span></p>
      {/*  카카오페이지 별점*/}
      <p className={'ms-2 search-info'}><img src="/kakao-page-icon.png" alt="이미지가 없습니다." className={'search-logo'}/><span className={'ms-1 search-score'}>4.7점</span></p>
      
    </div>
  )
}

export default NovelSearchScore;