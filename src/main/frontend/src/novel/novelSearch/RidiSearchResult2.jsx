import React, {useEffect, useState} from 'react';
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";

import {fetchData} from "../../common/NovelDetailFetch2";
import {titleEdit} from "../../common/TitleEdit";

function RidiSearchResult2(props) {
  const [novelSearchList, setNovelSearchList] = useState([]);
  const navi = useNavigate();
  
  useEffect(() => {
    const ridiFetchData = async () => {
      let ridiSearchList = [];
      
      const webNovel = '웹소설';
      const eBook = 'e북'
      
      for (let s = 0; s <= 216; s += 24) {
        try {
          const response = await axios.get("https://ridibooks.com/api/search-api/search?adult_exclude=n&keyword=" + props.keyword + "&start=" + s + "&what=base&where%5B%5D=book&site=ridi-store");
          const item = response.data.books;
          // console.log(item);
          
          for (let i = 0; i < item.length; i++) {
            const saveWebNovel = async (param) => {
              let intro = '';
              const res = await axios.get(`https://book-api.ridibooks.com/books/${item[i].b_id}/descriptions`)
                intro = res.data.descriptions.intro.replace(/(<([^>]+)>)/ig, '');
              // console.log(res);
              
              if (!item[i].parent_category_name.includes('웹툰')) {
                const data = {
                  platform: 3,
                  platformId: item[i].b_id,
                  title: titleEdit(item[i].title),
                  thumbnail: "https://img.ridicdn.net/cover/"+ item[i].b_id +"/xxlarge",
                  author: item[i].authors_info.map(auth => {
                    return auth.name;
                  }),
                  starRate: item[i].buyer_rating_score,
                  publi: item[i].publisher,
                  category: item[i].parent_category_name.replace(" " + param, ""),
                  count: item[i].book_count,
                  price: item[i].price != 0 ? item[i].price : item[i].series_prices_info[0].max_price,
                  completeYn: item[i].is_series_complete ? '완결' : '연재중',
                  description: intro,
                  ageGrade: item[i].age_limit == 19 ? "Y" : "N",
                  ebookCheck: item[i].web_title.includes('e북') ? '단행본' : '웹소설'
                }
                ridiSearchList.push(data);
              }
            }
            if (item[i].parent_category_name.includes('웹소설')) {
              saveWebNovel(webNovel);
            }
            else if (item[i].parent_category_name.includes('e북')) {
              if (item[i].parent_category_name.includes('로판')) {
                saveWebNovel(eBook);
              }
              else if (item[i].parent_category_name.includes('로맨스')) {
                saveWebNovel(eBook);
              }
              else if (item[i].parent_category_name.includes('판타지')) {
                saveWebNovel(eBook);
              }
              else if (!item[i].parent_category_name.includes('만화')) {
                saveWebNovel(eBook);
              }
            }
          }
        }
        catch (err) {
          console.log(err.message);
        }
      }
      setNovelSearchList(ridiSearchList);
    }
    ridiFetchData();

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
      alert("성인만 이용가능한 컨텐츠입니다. 로그인하여 성인인증 하시기 바랍니다.");
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
                  e.preventDefault(); // 링크 클릭 시 기본동작 방지
                  handleLinkClick(item); // 서버에서 데이터 유무 확인후 저장 및 가져오기
              }} to={`/novelDetail/${item.title}`} key={index}>
                
                <div className={'row my-4 border-top border-bottom py-2 d-flex align-items-center'}>
                  <div className={'col-sm-2'}>
                    {
                      sessionStorage.getItem("adult") == 'Y' ? <img src={item.thumbnail} alt="" className={'w-100 h-100'}/> : item.ageGrade == 'Y' ? <img src="https://ssl.pstatic.net/static/nstore/thumb/19over_book2_79x119.gif" alt="성인 컨텐츠입니다." className={'w-100 h-100'}/> : <img src={item.thumbnail} alt="" className={'w-100 h-100'}/>
                    }
                  </div>
                  <div className={'col-sm-10'}>
                    <div>
                      {/*onClick이벤트에 매개변수가 있을때는 페이지 로딩되자마자 함수가 바로 발생되서 이벤트가 발생했을때만 함수가 실행되도록 e => 를 붙여줘야 함*/}
                      <p className={'text-decoration-none text-black fs-5 fw-bold'}>
                        {item.title}
                        <span className={'text-danger'}>{item.ageGrade=='Y' ? ' [성인]' : null}</span>
                        {
                          item.ebookCheck == '단행본' ? <span className={'ms-2'}>[{item.ebookCheck}]</span> : null
                        }
                      </p>
                      <p className={'search-info'}>작가 : {item.author} [{item.category}]</p>
                      <p className={'search-info'}>출판사 : {item.publi}</p>
                      {
                        item.price != null
                          ? <p className={'search-info search-price fw-bold'}>가격 : {item.price}원</p>
                          : <p className={'search-info search-price text-muted'}>가격 정보</p>
                      }
                      <div className={'d-flex'}>
                        <p className={'search-info'}>
                          평점 <span className={'fw-bold search-score'}>{item.starRate}</span>&nbsp;|
                        </p>
                        <p>
                          &nbsp;<span className={'fw-bold'}>{item.completeYn}
                          {
                            item.ebookCheck == '단행본'
                              ? <span>&nbsp;(총{item.count}권)</span>
                              : <span>&nbsp;(총{item.count}화)</span>
                          }
                  </span>
                        </p>
                      </div>
                      <p className={'mt-2'}>{item.description.substring(0, 170)}</p>
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

export default RidiSearchResult2;