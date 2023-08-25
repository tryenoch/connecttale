import React, {useEffect, useState} from 'react';
import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';

import RidiSearchResult2 from "./RidiSearchResult2";
import KakaoSearchResult2 from "./KakaoSearchResult2";
import NaverSearchResult2 from "./NaverSearchResult2";


function SearchPlatformTab(props) {
  const [searchWord, setSearchWord] = useState(props.keyword);
  
  useEffect(() => {
    setSearchWord(props.keyword);
  }, [props.keyword])
  
  
  return (
    <Tabs defaultActiveKey="ridi" id="fill-tab-example" className={'search-nav nav-pills2 nav-fill gap-2 p-2 small rounded-5 shadow-sm mb-4'}>
      <Tab eventKey="ridi" title="리디북스 검색결과" className={'nav-link cursor rounded-5'} >
        <RidiSearchResult2 keyword={searchWord} />
      </Tab>
      <Tab eventKey="kakao" title="카카오페이지 검색결과" className={'nav-link cursor rounded-5'} >
        <KakaoSearchResult2 keyword={searchWord} />
      </Tab>
      <Tab eventKey="naver" title="네이버시리즈 검색결과" className={'nav-link cursor rounded-5'} >
        <NaverSearchResult2 keyword={searchWord}/>
      </Tab>
    </Tabs>
  )
  


}

export default SearchPlatformTab;