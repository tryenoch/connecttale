import React, {useEffect, useState} from 'react';
import axios from "axios";
import {Link} from "react-router-dom";
import data from "bootstrap/js/src/dom/data";

function RidiSearchResult(props) {
  const [novelSearchList, setNovelSearchList] = useState([]);
  
  useEffect(() => {
    const fetchData = async () => {
      let ridiSearchList = [];
      
      const webNovel = '웹소설';
      const eBook = 'e북'
      
      for (let s = 0; s <= 216; s += 24) {
        try {
          const response = await axios.get("https://ridibooks.com/api/search-api/search?adult_exclude=n&keyword=" + props.keyword + "&start=" + s + "&what=base&where%5B%5D=book&site=ridi-store");
          const item = response.data.books;
          // console.log(item);
          
          for (let i = 0; i < item.length; i++) {
            const saveWebNovel = (param) => {
              const data = {
                platform: 3,
                platformId: item[i].b_id,
                title: item[i].title,
                thumbnail: "https://img.ridicdn.net/cover/"+ item[i].b_id +"/xxlarge",
                author: item[i].authors_info.map(auth => {
                  return auth.name;
                }),
                starRate: item[i].buyer_rating_score,
                publi: item[i].publisher,
                category: item[i].parent_category_name.replace(" " + param, ""),
                count: item[i].book_count,
                price: item[i].price != null ? item[i].price : item[i].series_prices_info[0].max_price,
                completeYn: item[i].is_series_complete ? '완결' : '연재중',
                description: item[i].desc.replace(/<\/?[^>]+(>|$)/g, "").substring(13),
                ageGrade: item[i].age_limit == 19 ? "Y" : "N",
                novelOrEbook: item[i].web_title_title.includes('e북') ? "e북" : "웹소설"
              }
              ridiSearchList.push(data);
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
    fetchData();
  }, [props.keyword])
  
  return (
    <div>
      {
        novelSearchList.length != 0
          ? novelSearchList.map((item, index) => {
            return (
              <div className={'row my-4 border-top border-bottom py-2'} key={index}>
                <div className={'col-sm-2'}>
                  <Link to={`/novelDetail?platformId=${item.platformId}&title=${item.title}`} >
                    <img src={item.thumbnail} alt="" className={'w-100 h-100'} />
                  </Link>
                </div>
                <div className={'col-sm-10'}>
                  <Link to={`/novelDetail?platformId=${item.platformId}&title=${item.title}`} className={'text-decoration-none text-black fs-5 fw-bold'}>{item.title} <span className={'text-danger'}>{item.ageGrade=='Y' ? '[성인]' : null}</span>
                  </Link><br/>
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
                        item.novelOrEbook == 'e북'
                          ? <span>&nbsp;(총{item.count}권)</span>
                          : <span>&nbsp;(총{item.count}화)</span>
                      }
                  </span>
                    </p>
                  </div>
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

export default RidiSearchResult;