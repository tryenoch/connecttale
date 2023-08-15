import React, {useEffect, useState} from 'react';
import axios from "axios";
import {Link} from "react-router-dom";
import data from "bootstrap/js/src/dom/data";

function RidiSearchResult(props) {
  const [novelSearchList, setNovelSearchList] = useState([]);
  
  const fetchData = async (platformId, title, ebookCheck) => {
    try {
      const res = await axios.get("/novelDetail", {
        params: {
          platformId: props.platformId
        }
      })
      // console.log(res);
      // DB에 저장되어있는지 유무 확인
      // db에 해당 작품이 있으면 정보 꺼내오기
      if (Object.keys(res.data).length !== 0) {
        // DB에 저장되어 있다면 db에서 platform 데이터 들고와서 novelInfo 변경하기
        console.log(res.data);
        setNovelInfo(res.data);
      }
      
      // db에 해당 작품이 없으면 db 저장하기
      else {
        // 리디북스 디테일 페이지 정보 가져오기
        const ridiRes = await axios.get(`https://ridibooks.com/api/search-api/search?adult_exclude=n&keyword=${props.title}`);
        console.log(ridiRes);
        
        
        
        // 리디북스에 해당 작품 동일한 제목을 가진 작품이 있으면 db 저장, 없으면 카카오/네이버 검색 시도
        
        if (ridiRes.data.books) {
          for (let i = 0; i < ridiRes.data.books.length; i++) {
            if (props.title == ridiRes.data.books[i].title) {
              if (props.ebookCheck == '단행본') {
                if (ridiRes.data.books[i].web_title.includes('[e북]')) {
                  const item = ridiRes.data.books[i];
                  
                  const ridiRes2 = await axios.get(`https://book-api.ridibooks.com/books/${item.b_id}`);
                  console.log(ridiRes2);
                  const item2 = ridiRes2.data;
                  // console.log(item2);
                  
                  const ridiRes3 = await axios.get(`https://book-api.ridibooks.com/books/${item.b_id}/notices`);
                  console.log(ridiRes3);
                  
                  const item3 = ridiRes3.data.notices;
                  console.log(item3);
                  
                  const ridiObj = {
                    platform: 3,
                    platformId: item.b_id,
                    novelTitle: item.title,
                    novelThumbnail: "https://img.ridicdn.net/cover/"+ item.b_id +"/xxlarge",
                    novelIntro: item.desc.replace(/<\/?[^>]+(>|$)/g, "").substring(13),
                    novelAuthor: item.authors_info.map(auth => {
                      return auth.name;
                    }).toString(),
                    novelPubli: item.publisher,
                    novelCount: item.book_count,
                    novelPrice : item.price != 0 ? item.price : item.series_prices_info[0].max_price,
                    novelStarRate: item.buyer_rating_score,
                    novelCompleteYn: item.is_series_complete ? "Y" : "N",
                    novelAdult: item.age_limit == 19 ? "Y" : "N",
                    novelRelease: item2.publish.ridibooks_register.substring(0, 10),
                    novelUpdateDate: item3.length == 0 ? null : item3[0].title,
                    cateList: item.parent_category_name.includes('BL') ? "7" : item.parent_category_name.includes("로맨스") ? "3" : item.parent_category_name.includes("로판") ? "4" : item.parent_category_name.includes("판타지") ? "1" : null,
                    ebookCheck: item.web_title.includes("e북") ? "단행본" : "웹소설"
                  };
                  
                  
                  // 리디북스 디테일 정보 디비에 저장
                  const ridiRes4 = await axios.post('/novelDetail', null, {
                    params: {
                      id: props.platformId,
                      title: props.title,
                      ne: props.ebookCheck,
                      
                      platform: ridiObj.platform,
                      platformId: ridiObj.platformId,
                      novelTitle: ridiObj.novelTitle,
                      novelThumbnail: ridiObj.novelThumbnail,
                      novelIntro: ridiObj.novelIntro,
                      novelAuthor: ridiObj.novelAuthor,
                      novelPubli: ridiObj.novelPubli,
                      novelCount: ridiObj.novelCount,
                      novelPrice : ridiObj.novelPrice,
                      novelStarRate: ridiObj.novelStarRate,
                      novelCompleteYn: ridiObj.novelCompleteYn,
                      novelAdult: ridiObj.novelAdult,
                      novelRelease: ridiObj.novelRelease,
                      novelUpdateDate: ridiObj.novelUpdateDate,
                      cateList: ridiObj.cateList,
                      ebookCheck: ridiObj.ebookCheck
                    }
                  })
                }
              }
              else if (props.ebookCheck == '웹소설') {
                if (ridiRes.data.books[i].web_title == '') {
                  const item = ridiRes.data.books[i];
                  console.log(item);
                  
                  const ridiRes2 = await axios.get(`https://book-api.ridibooks.com/books/${item.b_id}`);
                  console.log(ridiRes2);
                  const item2 = ridiRes2.data;
                  // console.log(item2);
                  
                  const ridiRes3 = await axios.get(`https://book-api.ridibooks.com/books/${item.b_id}/notices`);
                  console.log(ridiRes3);
                  
                  const item3 = ridiRes3.data.notices;
                  console.log(item3);
                  
                  const ridiObj = {
                    platform: 3,
                    platformId: item.b_id,
                    novelTitle: item.title,
                    novelThumbnail: "https://img.ridicdn.net/cover/"+ item.b_id +"/xxlarge",
                    novelIntro: item.desc.replace(/<\/?[^>]+(>|$)/g, "").substring(13),
                    novelAuthor: item.authors_info.map(auth => {
                      return auth.name;
                    }).toString(),
                    novelPubli: item.publisher,
                    novelCount: item.book_count,
                    novelPrice : item.price != 0 ? item.price : item.series_prices_info[0].max_price,
                    novelStarRate: item.buyer_rating_score,
                    novelCompleteYn: item.is_series_complete ? "Y" : "N",
                    novelAdult: item.age_limit == 19 ? "Y" : "N",
                    novelRelease: item2.publish.ridibooks_register.substring(0, 10),
                    novelUpdateDate: item3.length == 0 ? null : item3[0].title,
                    cateList: item.parent_category_name.includes('BL') ? "7" : item.parent_category_name.includes("로맨스") ? "3" : item.parent_category_name.includes("로판") ? "4" : item.parent_category_name.includes("판타지") ? "1" : null,
                    ebookCheck: item.web_title.includes("e북") ? "단행본" : "웹소설"
                  };
                  
                  
                  // 리디북스 디테일 정보 디비에 저장
                  const ridiRes4 = await axios.post('/novelDetail', null, {
                    params: {
                      id: props.platformId,
                      title: props.title,
                      ne: props.ebookCheck,
                      
                      platform: ridiObj.platform,
                      platformId: ridiObj.platformId,
                      novelTitle: ridiObj.novelTitle,
                      novelThumbnail: ridiObj.novelThumbnail,
                      novelIntro: ridiObj.novelIntro,
                      novelAuthor: ridiObj.novelAuthor,
                      novelPubli: ridiObj.novelPubli,
                      novelCount: ridiObj.novelCount,
                      novelPrice : ridiObj.novelPrice,
                      novelStarRate: ridiObj.novelStarRate,
                      novelCompleteYn: ridiObj.novelCompleteYn,
                      novelAdult: ridiObj.novelAdult,
                      novelRelease: ridiObj.novelRelease,
                      novelUpdateDate: ridiObj.novelUpdateDate,
                      cateList: ridiObj.cateList,
                      ebookCheck: ridiObj.ebookCheck
                    }
                  })
                }
              }
            }
            else {
              const novelRes = await axios.post('/novelDetail', null, {
                params: {
                  id: props.platformId,
                  title: props.title,
                  ne: props.ebookCheck,
                }
              })
            }
          }
        }
        
        // fetchData를 다시 실행되게 해서 db에 저장된 데이터를 불러오게끔
        fetchData();
        
      }
    }
    catch (err) {
      console.log(err.message);
    }
  }
  
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
                price: item[i].price != 0 ? item[i].price : item[i].series_prices_info[0].max_price,
                completeYn: item[i].is_series_complete ? '완결' : '연재중',
                description: item[i].desc.replace(/<\/?[^>]+(>|$)/g, "").substring(13),
                ageGrade: item[i].age_limit == 19 ? "Y" : "N",
                ebookCheck: item[i].web_title_title.includes('e북') ? "단행본" : "웹소설"
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

  }, [props.keyword])
  
  return (
    <div>
      {
        novelSearchList.length != 0
          ? novelSearchList.map((item, index) => {
            return (
              <div className={'row my-4 border-top border-bottom py-2'} key={index}>
                <div className={'col-sm-2'}>
                  <Link to={`/novelDetail?platformId=${item.platformId}&title=${item.title}&ebookCheck=${item.ebookCheck}`} >
                    <img src={item.thumbnail} alt="" className={'w-100 h-100'} />
                  </Link>
                </div>
                <div className={'col-sm-10'}>
                  {/*onClick이벤트에 매개변수가 있을때는 페이지 로딩되자마자 함수가 바로 발생되서 이벤트가 발생했을때만 함수가 실행되도록 e => 를 붙여줘야 함*/}
                  <Link onClick={(e) => fetchData(item.platformId, item.title, item.ebookCheck} className={'text-decoration-none text-black fs-5 fw-bold'}>{item.title} <span className={'text-danger'}>{item.ageGrade=='Y' ? '[성인]' : null}</span>
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
                        item.ebookCheck == 'e북'
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