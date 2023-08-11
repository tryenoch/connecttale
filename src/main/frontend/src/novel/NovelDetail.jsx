import React, {useEffect, useState} from 'react';
import axios from "axios";
import {getElement} from "bootstrap/js/src/util";
import {useParams, useSearchParams} from "react-router-dom";
import NovelDetailInfo from "./novelDetail/NovelDetailInfo";
import NovelDetailReview from "./novelDetail/NovelDetailReview";

function NovelDetail(props) {
  const [platformId, setPlatformId] = useSearchParams();
  const [name, setName] = useSearchParams();
  
  const [seriesId, setSeriesId] = useState(platformId.get("platformId"));
  const [title, setTitle] = useState(name.get("title"));
  
  return (
    <div className={'container my-4'}>
      <h1>Novel Detail</h1>
      <NovelDetailInfo seriesId={seriesId} title={title}/>
      <NovelDetailReview />

    </div>
  )
}

export default NovelDetail;