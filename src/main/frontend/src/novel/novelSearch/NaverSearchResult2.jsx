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
                
                <div className={'row my-4 border-top border-bottom py-2 d-flex align-items-center'}>
                  <div className={'col-sm-2'}>
                    <img src={item.thumbnail} alt={item.title} className={'w-100 h-100'} />
                  </div>
                  <div className={'col-sm-10'}>
                    <div className={'ms-2'}>
                      <p className={'search-info text-decoration-none text-black fs-5 fw-bold'}>
                        {item.title}
                        <span className={'text-danger'}>{item.ageGrade == "Y" ? "[성인]" : null}</span>
                        {
                          item.ebookCheck == '단행본' ? <span>[{item.ebookCheck}]</span> : null
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
                            item.title.indexOf('[단행본]') == -1
                              ? <span>&nbsp;(총{item.count}화)</span>
                              : <span>&nbsp;(총{item.count}권)</span>
                          }
                  </span>
                        </p>
                      </div>
                      <p className={'mt-2'}>{item.description.substring(0, 300)}</p>
                    </div>
                  </div>
                </div>
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