import React, {useEffect, useState} from 'react';

function NovelSearch(props) {
  const [searchWord, setSearchWord] = useState();
  
  useEffect(() => {
    // 검색란에 입력한 단어로 searchWord를 수정해야함
    setSearchWord();
  }, [])
  
  
  
  return (
    <div className={'container my-4'}>
      <h5 className={'fw-bold'}>'{searchWord}' 검색 결과</h5>
      
    </div>
  )
}

export default NovelSearch;