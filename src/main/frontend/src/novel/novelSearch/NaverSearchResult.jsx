import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";
import axios from "axios";

function NaverSearchResult(props) {
  // const [searchWord, setSearchWord] = useState(props.keyword);
  
  const [novelSearchList, setNovelSearchList]= useState([]);
  
  useEffect(() => {
    // setSearchWord(props.keyword);
  
    axios.get('/searchNaver', {
      params : {
        searchWord: props.keyword
      }
    })
      .then(res => {
        console.log(res);
        let naverSearchList = [];
        
        for (let i = 0; i < res.data.title.length; i++) {
          const item = res.data;
          const data = {
            platform: '2',
            platformId: item.platformId[i],
            title: item.title[i],
            thumbnail: item.thumbnail[i],
            author: item.author[i],
            starRate: item.starRate[i],
            completeYn: item.completeYn[i],
            count: item.count[i],
            lastUpdate: item.lastUpdate[i],
            description: item.description[i],
            publi: item.publi[i],
            category: item.category[i],
            price: item.price[i],
            ageGrade: item.ageGrade[i]
          }
          naverSearchList.push(data);
        }
        setNovelSearchList(naverSearchList);
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
                {/*{*/}
                {/*  item.ageGrade == 'All'*/}
                {/*    ? <Link to={'#'}>*/}
                {/*      <img src={item.thumbnail} alt="" className={'w-100 h-100'}/>*/}
                {/*    </Link>*/}
                {/*    : <Link to={'#'}>*/}
                {/*      <img src="/onlyAdult.png" alt="" className={'w-100 h-100'}/>*/}
                {/*    </Link>*/}
                {/*}*/}
                <Link to={'#'} >
                  <img src={item.thumbnail} alt="" className={'w-100 h-100'} />
                </Link>
              </div>
              <div className={'col-sm-10'}>
                <Link to={'#'} className={'text-decoration-none text-black fs-5 fw-bold'}>{item.title}
                </Link><br/>
                <p className={'search-info'}>{item.author} [{item.category}카테고리]</p>
                <p className={'search-info'}>{item.publi}출판사</p>
                {
                  item.price != null
                    ? <p className={'search-price fw-bold'}>가격 : {item.price}</p>
                    : <p className={'search-price text-muted'}>가격 정보</p>
                }
                <div className={'d-flex'}>
                  <p className={'search-info'}>
                    평점 <span className={'fw-bold search-score'}>{item.starRate}</span>&nbsp;|
                  </p>
                  <p>
                    &nbsp;update <span className={'fw-bold'}>{item.lastUpdate}</span>&nbsp;|
                  </p>
                  <p>
                    &nbsp; <span className={'fw-bold'}>{item.completeYn}</span>
                  </p>
                </div>
                <p className={'search-info'}>{item.description.substring(0, 170)}</p>
                
              </div>
            </div>
          )
        })
      }
    </div>
  )
}

export default NaverSearchResult;