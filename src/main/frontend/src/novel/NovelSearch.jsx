import React, {useEffect, useState} from 'react';
import {Link, useParams, useSearchParams} from "react-router-dom";

import RidiSearchResult2 from "./novelSearch/RidiSearchResult2";
import SearchPlatformTab from "./novelSearch/SearchPlatformTab";

function NovelSearch(props) {
  const [keyword, setKeyword] = useSearchParams();
  
  const [searchWord, setSearchWord] = useState(keyword.get('keyword'));
  
  useEffect(() => {
    setSearchWord(keyword.get('keyword'));
  }, [keyword.get("keyword")])
  
  return (
    <div className={'container my-4'}>
      <div className={'row'}>
        <div className={'col-sm-9 mx-auto'}>
          <div className={'row'}>
            <div className={'col-sm'}>
              <h4 className={'fw-bold'}>'{searchWord}' 검색 결과</h4>
            </div>
          </div>
          {/* 플랫폼 별 검색결과를 나타내기 위한 탭 3개*/}
          <div>
            <SearchPlatformTab keyword={searchWord} />
          </div>
        </div>
      </div>
    </div>
  )
}

export default NovelSearch;