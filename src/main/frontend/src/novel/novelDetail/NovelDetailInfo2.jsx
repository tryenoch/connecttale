import React, {useEffect, useState} from 'react';
import novel from "../Novel";
import {Link} from "react-router-dom";
import {fetchData} from "../../common/NovelDetailFetch2";


function NovelDetailInfo(props) {
  const [novelInfo, setNovelInfo] = useState({});
  const [ridi, setRidi] = useState({});
  const [naver, setNaver] = useState({});
  const [kakao, setKakao] = useState({});
  const [baseItem, setBaseItem] = useState({});
  
  // console.log(kakao);
  // console.log(naver);
  // console.log(ridi);
  // console.log(baseItem);
  
  
  useEffect(() => {
    // setNovelInfo(props.novelDetail);
    
    if (novelInfo.ridi) {
      setBaseItem(novelInfo.ridi);
    } else if (novelInfo.naver) {
      setBaseItem(novelInfo.naver);
    } else if (novelInfo.kakao) {
      setBaseItem(novelInfo.kakao);
    }
    
    if (novelInfo.ridi != undefined && novelInfo.naver != undefined && novelInfo.kakao != undefined) {
      setRidi(novelInfo.ridi);
      setNaver(novelInfo.naver);
      setKakao(novelInfo.kakao);
    }
    else if (novelInfo.ridi != undefined && novelInfo.naver != undefined) {
      setRidi(novelInfo.ridi);
      setNaver(novelInfo.naver);
    }
    else if (novelInfo.ridi != undefined && novelInfo.kakao != undefined) {
      setRidi(novelInfo.ridi);
      setKakao(novelInfo.kakao);
    }
    else if (novelInfo.ridi != undefined) {
      setRidi(novelInfo.ridi);
    }
    else if (novelInfo.naver != undefined && novelInfo.kakao != undefined) {
      setNaver(novelInfo.naver);
      setKakao(novelInfo.kakao);
    }
    else if (novelInfo.naver != undefined) {
      setNaver(novelInfo.naver);
    }
    else if (novelInfo.kakao != undefined) {
      setKakao(novelInfo.kakao);
    }
  }, [props.novelDetail]);
  
  
  
  return (
    <div>
      <div className={'row'}>
        <div className={'col-sm-4'}>
          <img src={baseItem.novelThumbnail} alt="" className={'w-100 h-100'}/>
        </div>
        <div className={'col-sm-8'}>
          <div className={'ms-4'}>
            {
              baseItem.cateList == 1
                ? <span>[판타지]</span>
                : baseItem.cateList == 2
                  ? <span>[현판]</span>
                  : baseItem.cateList == 3
                    ? <span>[로맨스]</span>
                    : baseItem.cateList == 4
                      ? <span>[로판]</span>
                      : baseItem.cateList == 5
                        ? <span>[무협]</span>
                        : baseItem.cateList == 6
                          ? <span>[드라마]</span>
                          : baseItem.cateList == 7
                            ? <span>[BL]</span> : <p>카테고리</p>
            }
        
            {/*제목, 좋아요 버튼*/}
            <div className={'d-flex align-items-center'}>
              <p className={'fw-bold fs-3'}>{baseItem.novelTitle} [{baseItem.ebookCheck}]</p>
              <button type={'button'} className={'btn-outline-purple ms-2'}><i className="bi bi-heart"></i><span>좋아요</span></button>
            </div>
        
            {/*  플랫폼 별 별점 정보*/}
            <div className={'d-flex align-items-center'}>
              {
                Object.keys(kakao).length != 0
                  ? <p>
                    <img src="/kakao-page-icon.png" alt="" className={'platform-logo'}/>
                    <span className={'mx-2 novel-score'}>{kakao.novelStarRate}</span>
                    <span>/ 10점</span>
                  </p>
                  : null
              }
            </div>
            <div className={'d-flex align-items-center'}>
              {
                Object.keys(naver).length != 0
                  ? <p>
                    <img src="/naver-series-icon.png" alt="" className={'platform-logo'}/>
                    <span className={'mx-2 novel-score'}>{naver.novelStarRate}</span>
                    <span>/ 10점</span>
                  </p>
                  : null
              }
            </div>
            <div className={'d-flex align-items-center'}>
              {
                Object.keys(ridi).length != 0
                  ? <p>
                    <img src="/ridibooks-icon.jpg" alt="" className={'platform-logo'}/>
                    <span className={'mx-2 novel-score'}>{ridi.novelStarRate}</span>
                    <span>/ 10점</span>
                  </p>
                  : null
              }
        
            </div>
        
            {/*출판사, 저자, 총화수, 완결유무, 연재요일, 출판일*/}
            <div className={'my-1'}>
              <p>{baseItem.novelAuthor}<span className={'ms-2 text-muted'}>저</span></p>
              <p>{baseItem.novelPubli}<span className={'ms-2 text-muted'}>출판</span></p>
              {
                baseItem.ebookCheck == '웹소설'
                  ? <p>총 {baseItem.novelCount} 화 | {baseItem.novelCompleteYn == 'Y' ? '완결' : `연재중 | ${baseItem.novelUpdateDate}`}</p>
                  : <p>총 {baseItem.novelCount} 권 | {baseItem.novelCompleteYn == 'Y' ? '완결' : '연재중'}</p>
              }

              <p>출판일 : {baseItem.novelRelease}</p>
          
              {/*가격 정보 및 사이트 바로가기 링크*/}
              <div className={'border p-2 rounded-3 my-2'}>
                {
                  Object.keys(kakao).length != 0
                    ? <p><img src="/kakao-page-icon.png" alt="" className={'platform-logo me-2'}/>카카오페이지 <span className={'novel-price'}>{kakao.novelPrice}</span>원</p>
                    : null
                }
                {
                  Object.keys(naver).length != 0
                    ? <p><img src="/naver-series-icon.png" alt="" className={'platform-logo me-2'}/>네이버시리즈 <span className={'novel-price'}>{naver.novelPrice}</span>쿠키</p>
                    : null
                }
                {
                  Object.keys(ridi).length != 0
                    ? <p><img src="/ridibooks-icon.jpg" alt="" className={'platform-logo me-2'}/>리디북스 <span className={'novel-price'}>{ridi.novelPrice}</span>원</p>
                    : null
                }
              </div>
              {
                Object.keys(kakao).length != 0
                  ? <Link to={`https://page.kakao.com/content/${kakao.platformId}`} className={'mx-2 btn-outline-purple'}>카카오페이지 바로가기</Link>
                  : null
              }
              {
                Object.keys(naver).length != 0
                  ? <Link to={`https://series.naver.com/novel/detail.series?productNo=${naver.platformId}`} className={'mx-2 btn-outline-purple'}>네이버시리즈 바로가기</Link>
                  : null
              }
              {
                Object.keys(ridi).length != 0
                  ? <Link to={`https://ridibooks.com/books/${ridi.platformId}`} className={' btn-outline-purple'}>리디북스 바로가기</Link>
                  : null
              }
            </div>
          </div>
        </div>
      </div>
      {/*작품 소개 글*/}
      <div className={'my-4'}>
        <p className={'novel-intro'}>{baseItem.novelIntro}</p>
      </div>
      <hr/>
    </div>
    
  )
}

export default NovelDetailInfo;