import React, {useEffect, useState} from 'react';
import axios from "axios";
import {getElement} from "bootstrap/js/src/util";
import {useParams, useSearchParams} from "react-router-dom";
import NovelDetailInfo from "./novelDetail/NovelDetailInfo";
import NovelDetailReview from "./novelDetail/NovelDetailReview";

function NovelDetail(props) {
  const [seriesId, setSeriesId] = useSearchParams();
  const [name, setName] = useSearchParams();
  const [ebookChk, setEbookChk] = useSearchParams();
  
  
  const [platformId, setPlatformId] = useState(seriesId.get("platformId"));
  const [title, setTitle] = useState(name.get("title"));
  const [novelOrEbook, setNovelOrEbook] = useState(ebookChk.get("novelOrEbook"))
  return (
    <div className={'container my-4'}>
      <NovelDetailInfo platformId={platformId} title={title} novelOrEbook={novelOrEbook}/>
      <NovelDetailReview />

    </div>
  )
}

export default NovelDetail;