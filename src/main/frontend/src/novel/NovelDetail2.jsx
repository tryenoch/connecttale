import React from 'react';
// import NovelDetailInfo from "./novelDetail/NovelDetailInfo";
import NovelDetailReview from "./novelDetail/NovelDetailReview";
import {useLocation, useParams, useSearchParams} from "react-router-dom";
import NovelDetailInfo2 from "./novelDetail/NovelDetailInfo2";

function NovelDetail2(props) {
  const location = useLocation();
  
  const state = location.state;
  console.log(state.novelDetail);
  
  return (
    <div className={'container my-4'}>
      <NovelDetailInfo2 novelDetail={state.novelDetail}/>
      <NovelDetailReview />
  
    </div>
  )
}

export default NovelDetail2;