import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";
import axios from "axios";

function NaverSearchResult(props) {
  const [searchWord, setSearchWord] = useState(props.keyword);
  
  const [novelSearchList, setNovelSearchList]= useState([]);
  
  useEffect(() => {
    setSearchWord(props.keyword);
  
    axios.get('/searchNaver', {
      params : {
        searchWord: searchWord
      }
    })
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err.message);
      })
    
  }, [props.keyword])
  
  return (
    <div>
      {/*{*/}
      {/*  novelSearchList.map((item, index) => {*/}
      {/*    return (*/}
      {/*      <div className={'row my-4 border-top border-bottom py-2'} key={index}>*/}
      {/*        <div className={'col-sm-2'}>*/}
      {/*          {*/}
      {/*            item.ageGrade == 'All'*/}
      {/*              ? <Link to={'#'}>*/}
      {/*                <img src={item.thumbnail} alt="" className={'w-100 h-100'}/>*/}
      {/*              </Link>*/}
      {/*              : <Link to={'#'}>*/}
      {/*                <img src="/onlyAdult.png" alt="" className={'w-100 h-100'}/>*/}
      {/*              </Link>*/}
      {/*          }*/}
      {/*        </div>*/}
      {/*        <div className={'col-sm-10'}>*/}
      {/*          <Link to={'#'} className={'text-decoration-none text-black fs-5 fw-bold'}>{item.title}*/}
      {/*          </Link><br/>*/}
      {/*          <p className={'search-info'}>{item.author} [{item.category}]</p>*/}
      {/*          <p className={'search-info'}>{item.publi}</p>*/}
      {/*          {*/}
      {/*            item.price != null*/}
      {/*              ? <p className={'search-price fw-bold'}>가격 : {item.price}</p>*/}
      {/*              : <p className={'text-muted'}>가격 정보 없음</p>*/}
      {/*          }*/}
      {/*          <p className={'search-info'}>{item.description.substring(0, 170)}..</p>*/}
      {/*        </div>*/}
      {/*      </div>*/}
      {/*    )*/}
      {/*  })*/}
      {/*}*/}
    </div>
  )
}

export default NaverSearchResult;