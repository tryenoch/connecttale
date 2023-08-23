import React, {useEffect, useState} from 'react';
import novel from "../Novel";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";
import {hr} from "date-fns/locale";


function NovelDetailInfo(props) {
  const [title, setTitle] = useState(encodeURIComponent(props.novelDetail.novelIdx.novelTitle));
  const [ebookCheck, setEbookCheck] = useState(props.novelDetail.novelIdx.ebookCheck);
  const [novelAdult, setNovelAdult] = useState(props.novelDetail.novelIdx.novelAdult);
  
  // 랜더링에 사용되는 state 들
  const [novelInfo, setNovelInfo] = useState(props.novelDetail);
  const [ridi, setRidi] = useState({});
  const [naver, setNaver] = useState({});
  const [kakao, setKakao] = useState({});
  const [baseItem, setBaseItem] = useState({});
  
  const [likeFlag, setLikeFlag] = useState(false);
  // const [novelLikeList, setNovelLikeList] = useState([]);
  // const [novelLikeCount, setNovelLikeCount] = useState(0);
  
  // 세션id값 받아오기, 세션 정보 없으면 noUser가 됨 -> 서버에서 memberEntity 못찾게끔
  const [loginId, setLoginId] = useState(sessionStorage.getItem("id") ? sessionStorage.getItem("id") : 'noUser');
  
  // console.log(kakao);
  // console.log(naver);
  // console.log(ridi);
  // console.log(baseItem);
  
  useEffect(() => {
    axios.get('/novelDetail', {
      params:
        {
          title: title,
          ebookCheck: ebookCheck,
          ageGrade: novelAdult
        }
    })
      .then(res => {
        setNovelInfo(res.data)
        console.log(novelInfo);
        
        // 소설 디테일 정보 setState 해주는 곳
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
        } else if (novelInfo.ridi != undefined && novelInfo.naver != undefined) {
          setRidi(novelInfo.ridi);
          setNaver(novelInfo.naver);
        } else if (novelInfo.ridi != undefined && novelInfo.kakao != undefined) {
          setRidi(novelInfo.ridi);
          setKakao(novelInfo.kakao);
        } else if (novelInfo.ridi != undefined) {
          setRidi(novelInfo.ridi);
        } else if (novelInfo.naver != undefined && novelInfo.kakao != undefined) {
          setNaver(novelInfo.naver);
          setKakao(novelInfo.kakao);
        } else if (novelInfo.naver != undefined) {
          setNaver(novelInfo.naver);
        } else if (novelInfo.kakao != undefined) {
          setKakao(novelInfo.kakao);
        }
        
        for (let i = 0; i < novelInfo.novelLikeList.length; i++) {
          if (novelInfo.novelLikeList[i].likeYn == 'Y' && novelInfo.novelLikeList[i].id.id == loginId) {
            setLikeFlag(true);
            break;
          }
          else {
            setLikeFlag(false);
          }
        }
        

      })
    
  }, []);
  
  
  // 좋아요 버튼 클릭이벤트
  const likeClickHandler = () => {
    // 세션 값이 존재하면 좋아요 버튼 동작
    if (sessionStorage.getItem("id")) {
      // 좋아요 버튼 눌렀을때 db에 like_yn값을 'Y'/'N'으로 변경하는 axios 통신
      axios.put('/novelDetailLike', null, {
        params: {
          novelIdx: baseItem.novelKeyDto.novelIdx,
          id: loginId
        }
      })
        .then(async (res) => {
          
          // console.log(res);
          // db의 작품 정보(제목, 저자, 별점 등)와 함께 좋아요 관련 정보를 받아오기 위한 axios통신
          
          const res2 = await axios.get('/novelDetail', {
            params: {
              title: title,
              ebookCheck: ebookCheck,
              ageGrade: novelAdult
            }
          })
          
          setNovelInfo(res2.data);
          console.log(novelInfo);
  
          for (let i = 0; i < novelInfo.novelLikeList.length; i++) {
            if (novelInfo.novelLikeList[i].likeYn == 'Y' && novelInfo.novelLikeList[i].id.id == loginId) {
              setLikeFlag(true);
              break;
            }
            else {
              setLikeFlag(false);
            }
          }
          
        })
        .catch(err => {
          console.log(err.message);
        })
    }
    // 세션 값이 없으면 로그인 화면으로 이동
    else {
      alert("회원만 이용 가능한 서비스입니다.");
    }
    
  }
  
  
  return (
    <div>
      <div className={'row'}>
        <div className={'col-sm-3'}>
          <img src={baseItem.novelThumbnail} alt="" className={'w-100 h-100'}/>
        </div>
        <div className={'col-sm-9'}>
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
                            ? <span>[BL]</span>
                            : baseItem.cateList == 8
                              ? <p>기타</p> : null
            }
            
            {/*제목, 좋아요 버튼*/}
            <div className={'row'}>
              <div className={'col-sm-9'}>
                <p className={'fw-bold fs-3'}>
                  {baseItem.novelTitle} [{baseItem.ebookCheck}] </p>
              </div>
              <div className={'col-sm-3'}>
                {/*<button type={'button'} className={'btn-outline-purple ms-2'} onClick={likeClickHandler}>*/}
                {/*  <i className="bi bi-heart detail-heart"></i>*/}
                {/*  <span>*/}
                {/*  {novelInfo.novelLikeCount}*/}
                {/*  </span>*/}
                {/*</button>*/}
                {
                  novelInfo.novelLikeList.map((item, index) => {
                    return (
                      <div key={index}>
                        <p>좋아요 아이디 : {item.id.id}</p>
                        <p>로그인 아이디 : {loginId}</p>
                        <p>{item.likeYn}</p>
                        <hr/>
                      </div>
                    )
                  })
                }
                
                <button type={'button'} className={likeFlag ? 'btn btn-outline-purple ms-2' : 'btn btn-purple ms-2'} onClick={likeClickHandler}>
                  <i className="bi bi-heart detail-heart"></i>
                  <span>
                  {novelInfo.novelLikeCount}
                  </span>
                </button>
                {/*{likeButton}*/}
              </div>
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
                    <span>/ 5점</span>
                  </p>
                  : null
              }
            
            </div>
            
            {/*출판사, 저자, 총화수, 완결유무, 연재요일, 출판일*/}
            <div className={'my-1'}>
              <p className={'fw-bold'}>{baseItem.novelAuthor}<span className={'ms-2 text-muted'}>저</span></p>
              <p className={'fw-bold'}>{baseItem.novelPubli}<span className={'ms-2 text-muted'}>출판</span></p>
              {
                baseItem.ebookCheck == '웹소설'
                  ? <p className={'fw-bold'}>총 {baseItem.novelCount} 화
                                             | {baseItem.novelCompleteYn == 'Y' ? '완결' : `연재중 | ${baseItem.novelUpdateDate}`}</p>
                  : <p className={'fw-bold'}>총 {baseItem.novelCount} 권
                                             | {baseItem.novelCompleteYn == 'Y' ? '완결' : '연재중'}</p>
              }
              {
                baseItem.novelRelease
                  ? <p className={'fw-bold'}>출판일 : {baseItem.novelRelease}</p>
                  : null
              }
              {/*가격 정보 및 사이트 바로가기 링크*/}
              <div className={'border p-2 rounded-3 my-2'}>
                {
                  Object.keys(kakao).length != 0
                    ? <p><img src="/kakao-page-icon.png" alt="" className={'platform-logo me-2'}/>카카오페이지 <span
                      className={'novel-price'}>{kakao.novelPrice}</span>원</p>
                    : null
                }
                {
                  Object.keys(naver).length != 0
                    ? <p><img src="/naver-series-icon.png" alt="" className={'platform-logo me-2'}/>네이버시리즈 <span
                      className={'novel-price'}>{naver.novelPrice}</span>쿠키</p>
                    : null
                }
                {
                  Object.keys(ridi).length != 0
                    ? <p><img src="/ridibooks-icon.jpg" alt="" className={'platform-logo me-2'}/>리디북스 <span
                      className={'novel-price'}>{ridi.novelPrice}</span>원</p>
                    : null
                }
              </div>
              {
                Object.keys(kakao).length != 0
                  ?
                  <Link to={`https://page.kakao.com/content/${kakao.platformId}`} className={'mx-2 btn-outline-purple'}>카카오페이지
                                                                                                                        바로가기</Link>
                  : null
              }
              {
                Object.keys(naver).length != 0
                  ? <Link to={`https://series.naver.com/novel/detail.series?productNo=${naver.platformId}`}
                          className={'mx-2 btn-outline-purple'}>네이버시리즈 바로가기</Link>
                  : null
              }
              {
                Object.keys(ridi).length != 0
                  ? <Link to={`https://ridibooks.com/books/${ridi.platformId}`} className={' btn-outline-purple'}>리디북스
                                                                                                                  바로가기</Link>
                  : null
              }
            </div>
          </div>
        </div>
      </div>
      {/*작품 소개 글*/}
      <div className={'my-4'}>
        <p className={'fs-4 ms-2'}>INTRO</p>
        <p className={'novel-intro'}>{baseItem.novelIntro}</p>
      </div>
      <hr/>
    </div>
  
  )
}

export default NovelDetailInfo;