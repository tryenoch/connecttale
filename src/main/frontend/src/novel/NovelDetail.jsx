import React from 'react';

import NovelDetailReview from "./novelDetail/NovelDetailReview";
import {useLocation, useParams, useSearchParams} from "react-router-dom";
import NovelDetailInfo2 from "./novelDetail/NovelDetailInfo2";

function NovelDetail(props) {
  const location = useLocation();
  
  // const state = location.state;
  // console.log(state.novelDetail);
  
  console.log(location.state);
  
  if (!location.state || !location.state.novelDetail) {
    return <div>스테이트로 정보가 넘어오지 않았습니다.</div>; // 데이터가 없을 경우 로딩 처리
  }
  else {
    return (
      <div className={'container my-5'}>
        <div className={'row'}>
          <div className={'col-sm-9 mx-auto'}>
            <NovelDetailInfo2 novelDetail={location.state.novelDetail} />
            <NovelDetailReview novelIdx={location.state.novelDetail} />
          </div>
        </div>
      </div>
    )
  }
}

export default NovelDetail;