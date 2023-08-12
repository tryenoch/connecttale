import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";
import axios from "axios";

function KakaoSearchResult(props) {
  // const [searchWord, setSearchWord] = useState(props.keyword);
  const [novelSearchList, setNovelSearchList] = useState([]);
  
  useEffect(() => {
    // 현재 로직상 useEffect는 props.keyword가 변경될 때 마다 실행하게끔 되어있는데, 아래 setSearchWord(props.keyword)가 존재하면 searchWord 스테이트가 계속 변경되게되어 useEffect가 무한루프가 되어버림
    // setSearchWord(props.keyword);
    
    axios.get('/searchKakao', {
      params : {
        searchWord: props.keyword
      }
    })
      .then(res => {
        // 카카오 페이지 검색결과 가져오기
        
        if (res.data.length != 0) {
          // console.log(res);
          let kakaoSearchResult = [];
          let data = {};
          // Promise.all을 사용하여 반복되는 axios 통신이 모두 완료된 후, 가져온 데이터들을 kakaoSearchResult에 저장하는 로직
          Promise.all(res.data.map(item => axios.get('https://page.kakao.com/_next/data/2.12.2/ko/content/' + item + '.json')))
            .then(res => {
              for (let i = 0; i < res.length; i++) {
                const item = res[i].data.pageProps
                const data = {
                  platform: 1,
                  platformId: item.seriesId,
                  title: item.metaInfo.ogTitle,
                  thumbnail: item.metaInfo.image,
                  author: item.metaInfo.author,
                  description: item.metaInfo.description,
                  publi: item.dehydratedState.queries[0].state.data.contentHomeAbout.detail.publisherName,
                  category: item.dehydratedState.queries[0].state.data.contentHomeAbout.detail.category,
                  price: item.dehydratedState.queries[0].state.data.contentHomeAbout.detail.retailPrice,
                  ageGrade: item.dehydratedState.queries[0].state.data.contentHomeAbout.detail.ageGrade == "Nineteen" ? "Y" : "N"
                }
                kakaoSearchResult.push(data);
              }
              setNovelSearchList(kakaoSearchResult);
            })
            .catch(err => {
              console.log(err.message);
            });
        }
      })
      .catch(err => {
        console.log(err.message);
      })
  }, [props.keyword])
  
  return (
    <div>
      {
        novelSearchList.length != 0
        ? novelSearchList.map((item, index) => {
          return (
            <div className={'row my-4 border-top border-bottom py-2'} key={index}>
              <div className={'col-sm-2'}>
                {
                  item.ageGrade == 'N'
                  ? <Link to={'#'}>
                      <img src={item.thumbnail} alt="" className={'w-100 h-100'}/>
                    </Link>
                  : <Link to={'#'}>
                      <img src="/onlyAdult.png" alt="" className={'w-100 h-100'}/>
                    </Link>
                }
              </div>
              <div className={'col-sm-10'}>
                <Link to={'#'} className={'text-decoration-none text-black fs-5 fw-bold'}>{item.title} <span className={'text-danger'}>{item.ageGrade == "Y" ? "[성인]" : null}</span>
                </Link><br/>
                <p className={'search-info'}>작가 : {item.author} [{item.category}]</p>
                <p className={'search-info'}>출판사 : {item.publi}</p>
                {
                  item.price != null
                    ? <p className={'search-price fw-bold'}>가격 : {item.price}</p>
                    : <p className={'search-price text-muted'}>가격 정보 없음</p>
                }
                <p className={'search-info'}>{item.description.substring(0, 170)}</p>
              </div>
            </div>
          )
        })
        : <div className={'d-flex justify-content-center'}>
            <p className={'my-5'}><span className={'fw-bold'}>'{props.keyword}'</span>로 조회된 검색 결과가 없습니다.</p>
          </div>
      }
    </div>
  )
}

export default KakaoSearchResult;