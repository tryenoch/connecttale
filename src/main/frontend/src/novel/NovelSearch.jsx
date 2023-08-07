import React, {useEffect, useState} from 'react';
import {Link, useParams, useSearchParams} from "react-router-dom";

import axios from "axios";
import KakaoSearchResult from "./novelSearch/KakaoSearchResult";

function NovelSearch(props) {
  const [keyword, setKeyword] = useSearchParams();
  
  const [platform, setPlatform] = useState('카카오');
  const [searchWord, setSearchWord] = useState(keyword.get('keyword'));
  
  useEffect(() => {
    setSearchWord(keyword.get('keyword'));
  }, [keyword])
  
  const platformChange = e => setPlatform(e.target.value);

  return (
    <div className={'container my-4'}>
      <div className={'row'}>
        <div className={'col-sm-9 mx-auto'}>
          <div className={'row'}>
            <div className={'col-sm'}>
              <h4 className={'fw-bold'}>'{searchWord}' 검색 결과</h4>
            </div>
            <div className={'col-sm d-flex justify-content-end'}>
              <select name="" id="search-platform" value={platform} onChange={platformChange}>
                <option value="카카오">카카오 검색결과</option>
                <option value="네이버">네이버 검색결과</option>
                <option value="리디북스">리디북스 검색결과</option>
              </select>
            </div>
          </div>
          {
            platform == '카카오' && <KakaoSearchResult keyword={searchWord}/>
          }
        </div>
      </div>
    </div>
  )
}

export default NovelSearch;