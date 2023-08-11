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
          const ridiRes = await axios.get(`https://ridibooks.com/api/search-api/search?adult_exclude=n&keyword=${props.title}`);
          console.log(ridiRes);
          const item = ridiRes.data.books[0];
          
          const ridiRes2 = await axios.get(`https://book-api.ridibooks.com/books/${props.seriesId}`);
          console.log(ridiRes2);
          const item2 = ridiRes2.data;
  
          const ridiRes3 = await axios.get(`https://book-api.ridibooks.com/books/${props.seriesId}/notices`);
          console.log(ridiRes3);
          const item3 = ridiRes.data.notices;
          
          const ridiObj = {
            platform: 3,
            platformId: item.b_id,
            title: item.title,
            thumbnail: "https://img.ridicdn.net/cover/"+ item.b_id +"/xxlarge",
            author: item.authors_info.map(auth => {
              return auth.name;
            }),
            starRate: item.buyer_rating_score,
            publi: item.publisher,
            category: item.parent_category_name.includes('BL') ? "BL" : item.parent_category_name.includes("로맨스") ? "로맨스" : item.parent_category_name.includes("로판") ? "로판" : item.parent_category_name.includes("판타지") ? "판타지" : null,
            count: item.book_count,
            price : item.price != 0 ? item.price : item.series_prices_info[0].max_price,
            completeYn: item.is_series_complete ? "Y" : "N",
            description: item.desc.replace(/<\/?[^>]+(>|$)/g, "").substring(13),
            ageGrade: item.age_limit == 19 ? "Y" : "N",
            novelUpdateDate: item3 == null ? '' : item3[0].title,
            novel_release: item2.publish.ridibooks_register.substring(0, 10),
            novelOrEbook: item.web_title.includes("e북") ? "단행본" : "웹소설"
          };
          
          
          setNovelInfo(ridiObj);
          
          // 카카오페이지 디테일 크롤링(로그인 포함)
          
          // 네이버 페이지 디테일 크롤링(로그인 포함)
          
          // db에 저장
          
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
      {/*<div className={'col-sm-4'}>썸네일</div>*/}
      {/*<div className={'col-sm-8'}>정보</div>*/}
      <p>{novelInfo.platform} </p>
      <p>{novelInfo.platformId}</p>
      <p>{novelInfo.title}</p>
      <p>{novelInfo.thumbnail}</p>
      <p>{novelInfo.author}</p>
      <p>{novelInfo.starRate}</p>
      <p>{novelInfo.publi}</p>
      <p>{novelInfo.category}</p>
      <p>{novelInfo.count}</p>
      <p>{novelInfo.price}</p>
      <p>{novelInfo.completeYn}</p>
      <p>{novelInfo.description}</p>
      <p>{novelInfo.ageGrade}</p>
      <p>{novelInfo.novelUpdateDate}</p>
      <p>{novelInfo.novel_release}</p>
      <p>{novelInfo.novelOrEbook}</p>
    </div>
  )
}

export default NovelDetailInfo;