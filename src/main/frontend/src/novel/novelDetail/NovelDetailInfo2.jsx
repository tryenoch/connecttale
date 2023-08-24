import React, {useEffect, useState} from 'react';
import novel from "../Novel";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";
import {hr} from "date-fns/locale";
import {CategoryConverter} from "../../common/NovelInfoConverter";
import {Col, Row} from "react-bootstrap";


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

  // 세션id값 받아오기, 세션 정보 없으면 noUser가 됨 -> 서버에서 memberEntity 못찾게끔
  const [loginId, setLoginId] = useState(sessionStorage.getItem("id") ? sessionStorage.getItem("id") : 'noUser');

  console.log(baseItem.cateList);
  let cateList = "";

  if (baseItem.cateList == null || baseItem.cateList == undefined) {
    cateList = "";
  } else {
    cateList = CategoryConverter(baseItem.cateList);
  }

  useEffect(() => {
    console.log(novelInfo);
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


      })
      .catch(err => {
        console.log(err.message);
      })

  }, []);

  let heartSelect;
  const heart = novelInfo.novelLikeList.map((item) => (
    item.id.id == loginId && item.likeYn == 'Y' ? '찜' : null
  ));

  if (heart.indexOf('찜') > -1) {
    heartSelect = true;
  }


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

        })
        .catch(err => {
          console.log(err.message);
        })
    }
    // 세션 값이 없기 때문에 로그인하라는 알림창
    else {
      alert("회원만 이용 가능한 서비스입니다.");
    }
  }

  return (
    <div>
      <div className={'row'}>
        <div className={'col-sm-4 align-items-start img-area'}>
          <div className={'detail-image'}>
            <img src={baseItem.novelThumbnail} alt="" className={'w-100'}/>
          </div>
        </div>
        <div className={'col'}>
          <div className={'ms-4'}>
            {/*제목, 좋아요 버튼*/}
            <div className={'row'}>
              <div className={'col-sm-9 '}>
                <div>
                  <div className={'mb-2'}>
                    <span className={'detail-cate px-3 py-1 '}>{cateList ? cateList : null}</span>
                  </div>
                  <div className={'d-flex justify-content-start align-items-center'}>
                    <h3 className={'detail-title mb-0'}>{baseItem.novelTitle}</h3>
                    <p>/ {baseItem.ebookCheck}</p>
                  </div>
                </div>

              </div>
              <div className={'col-sm-3 d-flex justify-content-end'}>
                <div className={'detail-like-btn text-center'}>
                  <div>
                    <button type={'button'} onClick={likeClickHandler}>
                      <span className={'heart-span'}><svg version="1.0" xmlns="http://www.w3.org/2000/svg" className={'heart-ico'} viewBox="0 0 512.000000 512.000000" preserveAspectRatio="xMidYMid meet">
                        <g transform="translate(0.000000,512.000000) scale(0.100000,-0.100000)" stroke="none">
                          <path className={`${heartSelect ? 'heart-click' : 'heart-style'}`} d="M1435 4349 c-515 -47 -925 -432 -1032 -967 -24 -122 -24 -368 0 -480 108 -499 511 -1026 1216 -1587 287 -228 672 -490 767 -521 83 -27 265 -27 349 0 70 23 363 216 605 397 800 602 1261 1175 1377 1712 24 111 24 358 0 479
-108 540 -519 922 -1042 967 -359 31 -713 -111 -985 -395 -42 -44 -88 -95
-103 -114 l-27 -35 -27 35 c-54 69 -190 203 -263 259 -251 192 -538 278 -835
250z"/>
                        </g>
                      </svg></span>
                    </button>
                  </div>
                  <div>
                    <span className={'like-count'}>
                      {novelInfo.novelLikeCount}
                    </span>
                  </div>
                </div>
              </div>
            </div>
            <div className={'mb-1'}>
              {/*  플랫폼 별 별점 정보*/}
              <span className={'detail-sub-info'}>별점 정보</span>
            </div>
            <div className={'d-flex justify-content-start mb-3'}>
              <div className={'d-flex align-items-center me-3'}>
                {
                  Object.keys(kakao).length != 0
                    ? <span>
                    <img src="/kakao-page-icon.png" alt="" className={'platform-logo'}/>
                    <span className={'mx-2 novel-score'}>{kakao.novelStarRate}</span>
                    <span className={'rating-base'}>/ 10점</span>
                  </span>
                    : null
                }
              </div>
              <div className={'d-flex align-items-center me-3'}>
                {
                  Object.keys(naver).length != 0
                    ? <span>
                      <img src="/naver-series-icon.png" alt="" className={'platform-logo'}/>
                      <span className={'mx-2 novel-score'}>{naver.novelStarRate}</span>
                      <span className={'rating-base'}>/ 10점</span>
                    </span>
                    : null
                }
              </div>
              <div className={'d-flex align-items-center me-3'}>
                {
                  Object.keys(ridi).length != 0
                    ? <span>
                      <img src="/ridibooks-icon.jpg" alt="" className={'platform-logo'}/>
                      <span className={'mx-2 novel-score'}>{ridi.novelStarRate}</span>
                      <span className={'rating-base'}>/ 5점</span>
                    </span>
                    : null
                }
              </div>
            </div>


            {/*출판사, 저자, 총화수, 완결유무, 연재요일, 출판일*/}
            <div className={'my-1'}>
              <p className={'fw-bold'}>{baseItem.novelAuthor}<span className={'ms-2 text-muted'}>저</span></p>
              <p className={'fw-bold'}>{baseItem.novelPubli}<span className={'ms-2 text-muted'}>출판</span></p>
              {
                baseItem.ebookCheck == '웹소설'
                  ? <p className={'fw-bold'}>총 {baseItem.novelCount} 화
                    / {baseItem.novelCompleteYn == 'Y' ? '완결' : baseItem.novelUpdateDate ? `연재중 | ${baseItem.novelUpdateDate}` : '연재중'}</p>
                  : <p className={'fw-bold'}>총 {baseItem.novelCount} 권
                    / {baseItem.novelCompleteYn == 'Y' ? '완결' : baseItem.novelUpdateDate ? `연재중 | ${baseItem.novelUpdateDate}` : '연재중'}</p>
              }
              {
                baseItem.novelRelease
                  ? <p className={'fw-bold'}>출시일 / {baseItem.novelRelease}</p>
                  : null
              }
              {/*가격 정보 및 사이트 바로가기 링크*/}
              <div className={''}>
                <div className={'my-2 mt-4'}>
                  <span className={'detail-sub-info'}>가격 정보</span>
                  <span className={'ms-2'}> ※ 클릭하시면 해당 플랫폼으로 이동합니다.</span>
                </div>
                <table className={'table table-hover detail-price-table'}>
                  {
                    Object.keys(kakao).length != 0
                      ?
                      <tr>
                        <Link to={`https://page.kakao.com/content/${kakao.platformId}`} target={'_blank'}>
                          <td>
                            <p><img src="/kakao-page-icon.png" alt="" className={'platform-logo me-2'}/>카카오페이지 <span
                              className={'novel-price'}>{kakao.novelPrice}</span>원</p>
                          </td>
                        </Link>
                      </tr>
                      : null
                  }
                  {
                    Object.keys(naver).length != 0
                      ? <tr>
                        <Link to={`https://series.naver.com/novel/detail.series?productNo=${naver.platformId}`} target={'_blank'}>
                          <td><p><img src="/naver-series-icon.png" alt="" className={'platform-logo me-2'} target={'_blank'}/>네이버시리즈 <span
                            className={'novel-price'}>{naver.novelPrice}</span>쿠키</p>
                          </td>
                        </Link>
                      </tr>
                      : null
                  }
                  {
                    Object.keys(ridi).length != 0
                      ? <tr>
                        <Link to={`https://ridibooks.com/books/${ridi.platformId}`} target={'_blank'}>
                          <td><p><img src="/ridibooks-icon.jpg" alt="" className={'platform-logo me-2'}/>리디북스 <span
                            className={'novel-price'}>{ridi.novelPrice}</span>원</p>
                          </td>
                        </Link>
                      </tr>
                      : null
                  }
                </table>

                {/*<div className={'border p-3 rounded-3 my-2'}>
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
                </div>*/}
                {/*{
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
                }*/}
              </div>
            </div>
          </div>
        </div>
      </div>
      {/*작품 소개 글*/}
      <Row>
        <Col>
      <div className={'my-4 detail-intro'}>
        <h4 className={'detail-subtit'}>작품 소개</h4>
        <p className={'novel-desc px-0 pb-5'}>{baseItem.novelIntro}</p>
      </div>
        </Col>
      </Row>
    </div>

  )
}

export default NovelDetailInfo;