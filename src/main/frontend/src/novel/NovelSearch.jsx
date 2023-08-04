import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";
import NovelSearchScore from "./novelSearch/NovelSearchScore";
import NovelSearchPrice from "./novelSearch/NovelSearchPrice";
import axios from "axios";

function NovelSearch(props) {
  const [searchWord, setSearchWord] = useState();
  const [novelSearchList, setNovelSearchList] = useState([]);
  
  
  // 검색란에 입력한 단어로 searchWord를 수정
  useEffect(() => {
    setSearchWord();
  }, [])
  
  useEffect(() => {
    axios.get('/searchResult', {
      params : {
        searchWord: searchWord
      }
    })
      .then(res => {
      
      })
      .catch(err => {
        console.log(err.message);
      })
  })
  
  
  return (
    <div className={'container my-4'}>
      <div className={'row'}>
        <div className={'col-sm-9 mx-auto'}>
          <h4 className={'fw-bold'}>'{searchWord}' 검색 결과</h4>
          {/* 여기부터 map써서 구현해야됨 */}
          <div className={'row my-4 border-top border-bottom py-2'}>
            <div className={'col-sm-2'}>
              <Link to={'#'}>
                <img src="https://img.ridicdn.net/cover/2655001273/xxlarge#1" alt="" className={'w-100 h-100'}/>
              </Link>
            </div>
            <div className={'col-sm-10'}>
              <Link to={'#'} className={'text-decoration-none text-black'}>공포소설 속 조연은 사람으로 살고 싶다.
              </Link><br/>
              <p className={'search-info'}>인간추출기</p>
              <NovelSearchScore />
              <p className={'search-info'}>게이트 | 판타지 웹소설</p>
              <p className={'search-info'}>총 <span>108</span>화</p>
              <p className={'search-info'}>정신을 차려보니 웹소설 조연에 빙의했다. 문제는 이 소설이 끔찍한 공포소설이란 점! 정체불명의 신들과 마수들이 넘쳐나고 세계관 최약체가 바로 인간인 세상...</p>
              <NovelSearchPrice />
            
            </div>
          </div>
        </div>
      </div>
      
    </div>
  )
}

export default NovelSearch;