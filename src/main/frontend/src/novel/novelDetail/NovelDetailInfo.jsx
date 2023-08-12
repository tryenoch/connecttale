import React, {useEffect, useState} from 'react';
import axios from "axios";

function NovelDetailInfo(props) {
  // const [platformId, setPlatformId] = useState(props.platformId);
  const [novelInfo, setNovelInfo] = useState({});
  
  useEffect( () => {
    const fetchData = async () => {
      try {
        const res = await axios.get("/novelDetail", {
          params: {
            platformId: props.seriesId
          }
        })
        // console.log(res);
        // DB에 저장되어있는지 유무 확인
        if (res.data != '') {
          // DB에 저장되어 있다면 novelInfo값 변경
          setNovelInfo(res);
        }
        else {
          // DB에 저장되어 있지 않다면 DB에 저장
          
          // 리디북스 디테일 페이지 정보 크롤링
          const ridiRes = await axios.get(`https://ridibooks.com/api/search-api/search?adult_exclude=n&keyword=${props.title}`);
          // console.log(ridiRes);
          const item = ridiRes.data.books[0];
          
          const ridiRes2 = await axios.get(`https://book-api.ridibooks.com/books/${props.seriesId}`);
          // console.log(ridiRes2);
          const item2 = ridiRes2.data;
  
          const ridiRes3 = await axios.get(`https://book-api.ridibooks.com/books/${props.seriesId}/notices`);
          // console.log(ridiRes3);
          const item3 = ridiRes.data.notices;
          
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
            novelUpdateDate: item3 == null ? '' : item3[0].title,
            cateList: item.parent_category_name.includes('BL') ? "7" : item.parent_category_name.includes("로맨스") ? "3" : item.parent_category_name.includes("로판") ? "4" : item.parent_category_name.includes("판타지") ? "1" : null,
            novelOrEbook: item.web_title.includes("e북") ? "단행본" : "웹소설"
          };
          // 리디북스 디테일 정보 디비에 저장
          
          axios.post('/novelDetail', null, {
            params: {
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
              novelOrEbook: ridiObj.novelOrEbook
            }
          })
  
          // 서버에서 카카오페이지 디테일 크롤링(로그인 포함) -> db 저장
  
          // 서버에서 네이버 페이지 디테일 크롤링(로그인 포함) -> db 저장
          
          
          
          // fetchData를 다시 실행되게 해서 db에 저장된 데이터를 불러오게끔
  
          
          
        }
      }
      catch (err) {
        console.log(err.message);
      }
    }
    fetchData();
    
  }, [])
  
  return (
    <div className={'row'}>
      <h1>리디</h1>
      {/*<div className={'col-sm-4'}>썸네일</div>*/}
      {/*<div className={'col-sm-8'}>정보</div>*/}
      <p>{novelInfo.platform} </p>
      <p>{novelInfo.platformId}</p>
      <p>{novelInfo.novelTitle}</p>
      <p>{novelInfo.novelThumbnail}</p>
      <p>{novelInfo.novelAuthor}</p>
      <p>{novelInfo.novelStarRate}</p>
      <p>{novelInfo.novelPubli}</p>
      <p>{novelInfo.cateList}</p>
      <p>{novelInfo.novelCount}</p>
      <p>{novelInfo.novelPrice}</p>
      <p>{novelInfo.novelCompleteYn}</p>
      <p>{novelInfo.novelIntro}</p>
      <p>{novelInfo.novelAdult}</p>
      <p>{novelInfo.novelUpdateDate}</p>
      <p>{novelInfo.novelRelease}</p>
      <p>{novelInfo.novelOrEbook}</p>
    </div>
  )
}

export default NovelDetailInfo;