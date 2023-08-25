import React, {useEffect, useState} from 'react';
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";
import {fetchData} from "../../common/NovelDetailFetch2";
import {titleEdit} from "../../common/TitleEdit";

function NaverSearchResult2(props) {
  const [novelSearchList, setNovelSearchList] = useState([]);
  const navi = useNavigate();
  
  
  useEffect(() => {
    axios.get('/searchNaver', {
      params : {
        searchWord: props.keyword
      }
    })
      .then(res => {
        const item = res.data;
        // console.log(res);
        let naverSearchList = [];
        
        if(Object.keys(res.data) != 0) {
          for (let i = 0; i < item.title.length; i++) {
            // const item = res.data;
            const data = {
              platform: 2,
              platformId: item.platformId[i],
              title: titleEdit(item.title[i]),
              thumbnail: item.thumbnail[i],
              author: item.author[i],
              starRate: item.starRate[i],
              completeYn: item.completeYn[i],
              count: item.count[i],
              description: item.dsc[i],
              ageGrade: item.ageGrade[i],
              ebookCheck: item.title[i].includes('[단행본]') ? '단행본' : '웹소설'
            }
            naverSearchList.push(data);
          }
          setNovelSearchList(naverSearchList);
        }
      })
      .catch(err => {
        console.log(err.message);
      })
    
  }, [props.keyword])
  
  const handleLinkClick = async (item) => {
    if (sessionStorage.getItem("adult") == 'Y' || item.ageGrade == 'N') {
      try {
        const novelDetail = await fetchData(item.platformId, item.title, item.ebookCheck, item.ageGrade);
        navi(`/novelDetail/${item.title}`, {
          state: {
            novelDetail: novelDetail,
          }
        });
      } catch (error) {
        console.log(error.message);
      }
    }
    else {
      alert("성인만 이용가능한 컨텐츠입니다. 로그인하여 성인인증 하시기 바랍니다.")
    }
  };
  
  return (
    <div>
      {
        novelSearchList.length != 0
          ? novelSearchList.map((item, index) => {
            return (
              <Link
                onClick={e => {
                e.preventDefault();
                handleLinkClick(item);
              }}
                to={`/novelDetail/${item.title}`} key={index}>
                
                <div className={'row mt-2 mb-1 py-2 d-flex align-items-center cate-item'}>
                  <div className={'col-sm-2'}>
                    {
                      sessionStorage.getItem("adult") == 'Y' ? <img src={item.thumbnail} alt="" className={'w-100 h-100'}/> : item.ageGrade == 'Y' ? <img src="https://ssl.pstatic.net/static/nstore/thumb/19over_book2_79x119.gif" alt="성인 컨텐츠입니다." className={'w-100 h-100'}/> : <img src={item.thumbnail} alt="" className={'w-100 h-100'}/>
                    }
                  </div>
                  <div className={'col-sm-10'}>
                    <div className={'ms-2'}>
                      <p className={'search-info text-decoration-none text-black fs-5 fw-bold tit'}>
                        {item.title}
                        <span className={'text-danger'}>{item.ageGrade == "Y" ? " [성인]" : null}</span>
                        {
                          item.ebookCheck == '단행본' ? <span> [{item.ebookCheck}]</span> : null
                        }
                      </p>
                      <p className={'search-info'}>작가 : {item.author}</p>
                      {
                        item.price != null
                          ? <p className={'search-info search-price fw-bold'}>가격 : {item.price}</p>
                          : <p className={'search-info search-price text-muted'}>{null}</p>
                      }
                      <div className={'d-flex'}>
                        <p className={'search-info'}>
                          평점 <span className={'fw-bold search-score'}>{item.starRate}</span>&nbsp;|
                        </p>
                        <p>
                          &nbsp;<span className={'fw-bold'}>{item.completeYn}
                          {
                            item.ebookCheck === '웹소설'
                              ? <span>&nbsp;(총{item.count}화)</span>
                              : <span>&nbsp;(총{item.count}권)</span>
                          }
                  </span>
                        </p>
                      </div>
                      <p className={'mt-2 info-desc'}>{item.description.substring(0, 300)}</p>
                    </div>
                  </div>
                </div>
                <hr className={'search-border-bottom'} />
              </Link>
            )
          })
          : <div className={'d-flex justify-content-center'}>
            <p className={'my-5'}><span className={'fw-bold'}>'{props.keyword}'</span>로 조회된 검색 결과가 없습니다.</p>
          </div>
      }
    </div>
  )
}

export default NaverSearchResult2;