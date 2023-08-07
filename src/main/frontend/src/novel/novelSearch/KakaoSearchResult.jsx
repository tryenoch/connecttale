import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useSearchParams} from "react-router-dom";
import axios from "axios";

function KakaoSearchResult(props) {
  const [searchWord, setSearchWord] = useState(props.keyword);
  // const [kakaoSearchList, setKakaoSearchList] = useState([]);
  const [novelSearchList, setNovelSearchList] = useState([]);
  
  useEffect(() => {
    setSearchWord(props.keyword);
    
    axios.get('/search', {
      params : {
        searchWord: searchWord
      }
    })
      .then(res => {
        // 카카오 페이지 검색결과 가져오기
        if (res.data[0] == 'kakaoIdList') {
          console.log(res);
          let kakaoSearchResult = [];
        
          // Promise.all을 사용하여 반복되는 axios 통신이 모두 완료된 후, 가져온 데이터들을 kakaoSearchResult에 저장하는 로직
          Promise.all(res.data.slice(1).map(item => axios.get('https://page.kakao.com/_next/data/2.12.2/ko/content/' + item + '.json')))
            .then(res => {
            
              for (let i = 0; i < res.length; i++) {
                const item = res[i].data.pageProps
                const data = {
                  platform: '카카오',
                  platformId: item.seriesId,
                  title: item.metaInfo.ogTitle,
                  thumbnail: item.metaInfo.image,
                  author: item.metaInfo.author,
                  description: item.metaInfo.description,
                  publi: item.dehydratedState.queries[0].state.data.contentHomeAbout.detail.publisherName,
                  category: item.dehydratedState.queries[0].state.data.contentHomeAbout.detail.category,
                  price: item.dehydratedState.queries[0].state.data.contentHomeAbout.detail.retailPrice,
                  ageGrade: item.dehydratedState.queries[0].state.data.contentHomeAbout.detail.ageGrade
                }
                kakaoSearchResult.push(data);
              }
            
              setNovelSearchList(prevState => [...prevState, ...kakaoSearchResult]);
            
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
        novelSearchList.map((item, index) => {
          return (
            <div className={'row my-4 border-top border-bottom py-2'} key={index}>
              <div className={'col-sm-2'}>
                {
                  item.ageGrade == 'All'
                  ? <Link to={'#'}>
                      <img src={item.thumbnail} alt="" className={'w-100 h-100'}/>
                    </Link>
                  : <Link to={'#'}>
                      <img src="/onlyAdult.png" alt="" className={'w-100 h-100'}/>
                    </Link>
                }
              </div>
              <div className={'col-sm-10'}>
                <Link to={'#'} className={'text-decoration-none text-black fs-5 fw-bold'}>{item.title}
                </Link><br/>
                <p className={'search-info'}>{item.author} [{item.category}]</p>
                <p className={'search-info'}>{item.publi}</p>
                {
                  item.price != null
                    ? <p className={'search-price fw-bold'}>가격 : {item.price}</p>
                    : <p className={'text-muted'}>가격 정보 없음</p>
                }
                <p className={'search-info'}>{item.description.substring(0, 170)}..</p>
              </div>
            </div>
          )
        })
      }
    </div>
  )
}

export default KakaoSearchResult;