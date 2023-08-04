import React from 'react';

function NovelSearchPrice(props) {
  
  return (
    <div className={'d-flex align-items-center my-1'}>
      <p className={'search-info fw-bold'}>가격 : </p>
      {/*리디북스 가격*/}
      <p className={'search-info'}><img src="/ridibooks-icon.jpg" alt="이미지가 없습니다." className={'search-logo'}/><span className={'ms-1 text-secondary'}>300원</span></p>
      {/*  네이버시리즈 가격*/}
      <p className={'ms-2 search-info'}><img src="/naver-series-icon.png" alt="이미지가 없습니다." className={'search-logo'}/><span className={'ms-1 text-secondary'}>200원</span></p>
      {/*  카카오페이지 가격*/}
      <p className={'ms-2 search-info'}><img src="/kakao-page-icon.png" alt="이미지가 없습니다." className={'search-logo'}/><span className={'ms-1 text-secondary'}>300원</span></p>
    </div>
  )
}

export default NovelSearchPrice;